package dataforms.devtool.pageform.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.devtool.javasrc.JavaSrcGenerator;
import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.field.base.Field;
import dataforms.field.base.Field.Display;
import dataforms.field.base.FieldList;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.StringUtil;
import net.arnx.jsonic.JSON;

/**
 * フォームソースジェネレータの基本クラス。
 */
public abstract class FormSrcGenerator extends JavaSrcGenerator {
	/**
	 * Logger.
	 */
	private Logger logger = LogManager.getLogger(FormSrcGenerator.class);


	/**
	 * フォームクラス名を取得します。
	 * @param data POSTデータ。
	 * @return フォームクラス名。
	 */
	protected abstract String getFormClassName(final Map<String, Object> data);

	/**
	 * フォームの種類毎のコンポーネントの配置処理を実装します。
	 * @param tmp テンプレート。
	 * @param formClassName フォームクラス名。
	 * @param data POSTデータ。
	 * @throws Exception 例外。
	 */
	protected abstract void setFormComponent(Template tmp, String formClassName, Map<String, Object> data) throws Exception;

	/**
	 * メソッドのテンプレートを設定する。
	 * @param tmp ソーステンプレレート。
	 * @throws Exception 例外。
	 */
	protected void setMethodTemplate(Template tmp) throws Exception {
		Template vfTmp = new Template(this.getClass(), "template/validateForm.java.template");
		Template wmTmp = new Template(this.getClass(), "template/webMethod.java.template");
		tmp.replace("validateForm", vfTmp.getSource());
		tmp.replace("webMethod", wmTmp.getSource());
	}

	@Override
	public void generage(Form form, Map<String, Object> data) throws Exception {
		Template tmp = this.getTemplate();
		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String pageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME);
		String daoPackageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_PACKAGE_NAME);
		String daoClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME);
		String formClassName = this.getFormClassName(data);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME, packageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_NAME, pageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME, daoClassName);
		String daocls = daoPackageName + "." + daoClassName;
		tmp.replace("daoClassFullName", daocls);
		this.setFormComponent(tmp, formClassName, data);
		this.setMethodTemplate(tmp);
		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String formclass = packageName + "." + formClassName;
		String srcPath = path + "/" + formclass.replaceAll("\\.", "/") + ".java";
		FileUtil.writeTextFileWithBackup(srcPath, tmp.getSource(), DataFormsServlet.getEncoding());
	}

	/**
	 * 指定されたクラスのフィールドリストを取得します。
	 * @param pkg パッケージ。
	 * @param cls クラス。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getFieldList(final String pkg, final String cls) throws Exception {
		FieldList flist = null;
		if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(cls)) {
			Class<?> clazz = Class.forName(pkg + "." + cls);
			Object obj = clazz.getConstructor().newInstance();
			if (obj instanceof Table) {
				Table table = (Table) obj;
				flist = table.getFieldList();
			} else if (obj instanceof Query) {
				Query query = (Query) obj;
				flist = query.getFieldList();
			}
		}
		return flist;
	}



	/**
	 * 問合せのフィールドリストを取得します。
	 * @param data POSTされたデータ。
	 * @param pkgid 問合せパッケージ名のフィールドID。
	 * @param clsid 問合せクラス名のフィールドID。
	 * @param confid フィールド設定情報のフィールドID。
	 * @param editKeyOnly 編集キーのみ。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getQueryFieldList(final Map<String, Object> data, final String pkgid, final String clsid, final String confid, final boolean editKeyOnly) throws Exception {
		FieldList ret = null;
		String pkg = (String) data.get(pkgid);
		String cls = (String) data.get(clsid);
		FieldList flist = this.getFieldList(pkg, cls);
		if (flist != null) {
			ret = new FieldList();
			String json = (String) data.get(confid);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list =  JSON.decode(json, ArrayList.class);
			for (Map<String, Object> m: list) {
				String fieldId = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
				String matchType = (String) m.get(SelectFieldHtmlTable.ID_MATCH_TYPE);
				String listFieldDisplay = (String) m.get(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY);
				String editFieldDisplay = (String) m.get(SelectFieldHtmlTable.ID_EDIT_FIELD_DISPLAY);
				String editKey = (String) m.get(SelectFieldHtmlTable.ID_EDIT_KEY);
				logger.debug("fieldId=" + fieldId);
				Field<?> field = flist.get(fieldId);
				field.setMatchType(Field.MatchType.valueOf(matchType));
				field.setQueryResultFormDisplay(Display.valueOf(listFieldDisplay));
				field.setEditFormDisplay(Display.valueOf(editFieldDisplay));
				if (!editKeyOnly) {
					// editKeyOnlyのみでない場合、全てのフィールドを転記
					ret.add(field);
				} else {
					if ("1".equals(editKey)) {
						ret.add(field);
					}
				}
			}
		}
		return  ret;
	}


	/**
	 * 問合せフォームのフィールドリストを取得します。
	 * @param data POSTされたデータ。
	 * @return 一覧取得問合せのフィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getQueryFormFieldList(final Map<String, Object> data) throws Exception {
		FieldList flist = this.getQueryFieldList(
			data
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_PACKAGE_NAME
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CLASS_NAME
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CONFIG
			, false
		);
		if (flist == null) {
			flist = this.getQueryFieldList(
				data
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CONFIG
				, true
			);
		}
		return flist;
	}

}
