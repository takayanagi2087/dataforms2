package dataforms.devtool.field;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import dataforms.field.base.Field;
import dataforms.field.sqltype.VarcharField;
import dataforms.util.ClassFinder;
import dataforms.util.StringUtil;

/**
 * パッケージ名を除いたクラス名フィールドクラス。
 *
 */
public class SimpleClassNameField extends VarcharField {

	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(SimpleClassNameField.class);

	/**
	 * フィールドコメント。
	 *
	 */
	private static final String COMMENT = "単純クラス名";
	/**
	 * 項目名。
	 */
	private static final int LENGTH = 256;

	/**
	 * autocompleで検索する際の基本クラス。
	 */
	private List<Class<?>> baseClassList = null;

	/**
	 * パッケージ名フィールドID。
	 */
	private String packageNameFieldId = "packageName";

	/**
	 * 例外パターンのリスト。
	 */
	private List<String> exceptionPatternList = new ArrayList<String>();


	/**
	 * コンストラクタ。
	 */
	public SimpleClassNameField() {
		super(null, LENGTH);
		this.setComment(COMMENT);
		this.setAjaxParameter(AjaxParameter.FORM);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SimpleClassNameField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
		this.setAjaxParameter(AjaxParameter.FORM);
	}

	/**
	 * autocomplete検索時のベースクラスを追加します。
	 * @param baseClass autocomplete検索時のベースクラス。
	 * @return 設定したフィールド。
	 */
	public Field<?> addBaseClass(final Class<?> baseClass) {
		if (this.baseClassList == null) {
			this.baseClassList = new ArrayList<Class<?>>();
		}
		this.baseClassList.add(baseClass);
		return this;
	}

	/**
	 * パッケージ名フィールドのIDを取得します。
	 * @return パッケージ名フィールドのID。
	 */
	public String getPackageNameFieldId() {
		return packageNameFieldId;
	}

	/**
	 * パッケージ名フィールドIDを設定します。
	 * @param packageNameFieldId パッケージ名フィールドID。
	 * @return 設定されたフィールド。
	 */
	public SimpleClassNameField setPackageNameFieldId(final String packageNameFieldId) {
		this.packageNameFieldId = packageNameFieldId;
		return this;
	}

	/**
	 * 例外パターンを追加します。
	 * <pre>
	 * 指定された例外パターンを含むクラス名はautocompleteリストから除外されます。
	 * </pre>
	 * @param pattern 例外パターン(正規表現文字列)。
	 */
	protected void addExceptionPattern(final String pattern) {
		this.exceptionPatternList.add(pattern);
	}

	/**
	 * 例外パターンのチェック。
	 * @param classname クラス名。
	 * @return クラス名に例外パターンを含む場合true。
	 * @throws Exception 例外。
	 */
	protected boolean isExcetionClass(final String classname) throws Exception {
		boolean ret = false;
		for (String regex: this.exceptionPatternList) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(classname);
			if (m.find()) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	/**
	 * クラス名を取得します。
	 * @param c クラス。
	 * @return クラス名。
	 */
	private String getClassName(final Class<?> c) {
		String []sp = c.getName().split("\\.");
		return sp[sp.length -1];
	}

	/**
	 * 指定されたパッケージ中のクラスリストを取得します。
	 *
	 * @param packageName パッケージ名。
	 * @return クラスリスト。
	 * @throws Exception 例外。
	 */
	private List<Class<?>> findClasses(final String packageName) throws Exception {
		List<Class<?>> ret = new ArrayList<Class<?>>();
		ClassFinder finder = new ClassFinder();
		for (Class<?> cls: this.baseClassList) {
			List<Class<?>> list = finder.findClasses(packageName, cls);
			if (list != null) {
				ret.addAll(list);
			}
		}
		return ret;
	}

	@Override
	protected List<Map<String, Object>> queryAutocompleteSourceList(final 	Map<String, Object> data) throws Exception {
		String id = (String) data.get("currentFieldId");
		String rowid = this.getHtmlTableRowId(id);
		String colid = this.getHtmlTableColumnId(id);
		String packageName = (String) data.get(StringUtil.isBlank(rowid) ? this.getPackageNameFieldId() : rowid + "." + this.getPackageNameFieldId());
		String simpleClassName = (String) data.get(id);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (StringUtil.isBlank(packageName)) {
			return result;
		}
		List<Class<?>> list = this.findClasses(packageName);
		for (Class<?> c: list) {
			if (this.isExcetionClass(c.getName())) {
				continue;
			}

			String name = this.getClassName(c);
			if (name.toLowerCase().indexOf(simpleClassName.toLowerCase()) >= 0) {
				if (!Modifier.isAbstract(c.getModifiers())) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put(colid, name);
					m.put(this.getPackageNameFieldId(), c.getPackage().getName());
					result.add(m);
				}
			}
		}
		return this.convertToAutocompleteList(rowid, result, colid, colid, this.getPackageNameFieldId());
	}

	@Override
	protected Map<String, Object> queryRelationData(final Map<String, Object> data) throws Exception {
		String id = (String) data.get("currentFieldId");
		String rowid = this.getHtmlTableRowId(id);
		String colid = this.getHtmlTableColumnId(id);
		String packageName = (String) data.get(StringUtil.isBlank(rowid) ? this.getPackageNameFieldId() : rowid + "." + this.getPackageNameFieldId());
		String simpleClassName = (String) data.get(id);
		logger.debug("packageName=" + packageName + ", simpleClassName=" + simpleClassName);
		if (StringUtil.isBlank(simpleClassName)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Class<?>> list = this.findClasses(packageName);
		for (Class<?> c: list) {
			if (this.isExcetionClass(c.getName())) {
				continue;
			}
			String name = this.getClassName(c);
			logger.debug("className=" + name);
			if (name.equals(simpleClassName)) {
				if (!Modifier.isAbstract(c.getModifiers())) {
					ret.put(colid, name);
					ret.put(this.getPackageNameFieldId(), c.getPackage().getName());
					break;
				}
			}
		}
		return ret;
	}

}

