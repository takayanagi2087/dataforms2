package dataforms.devtool.query.page;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.EditForm;
import dataforms.dao.Dao;
import dataforms.dao.Query;
import dataforms.dao.Query.JoinInfo;
import dataforms.dao.Table;
import dataforms.dao.TableList;
import dataforms.dao.sqlgen.SqlGenerator;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.AliasNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.JoinTypeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryClassNameField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.FlagField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.response.JsonResponse;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

/**
 * 問合せJavaクラス作成編集フォーム。
 *
 */
public class QueryGeneratorEditForm extends EditForm {



	/**
	 * パッケージ名フィールドID。
	 */
	private static final String ID_PACKAGE_NAME = "packageName";

	/**
	 * 問合せクラス名フィールドID。
	 */
	private static final String ID_QUERY_CLASS_NAME = "queryClassName";

	/**
	 * distinctフラグフィールドID。
	 */
	private static final String ID_DISTINCT_FLAG = "distinctFlag";

	/**
	 * 強制上書きフラグフィールドID。
	 */
	private static final String ID_FORCE_OVERWRITE = "forceOverwrite";

	/**
	 * 主テーブル機能選択フィールドID。
	 */
	private static final String ID_MAIN_TABLE_FUNCTION_SELECT = "mainTableFunctionSelect";

	/**
	 * 主テーブルパッケージ名称フィールドID。
	 */
	private static final String ID_MAIN_TABLE_PACKAGE_NAME = "mainTablePackageName";

	/**
	 * 主テーブルクラス名フィールドID。
	 */
	private static final String ID_MAIN_TABLE_CLASS_NAME = "mainTableClassName";

	/**
	 * 別名のフィールドID。
	 */
	private static final String ID_ALIAS_NAME = "aliasName";

	/**
	 * コメントフィールドID。
	 */
	private static final String ID_QUERY_COMMENT = "queryComment";


	/**
	 * テーブルクラス名フィールドID。
	 */
	// private static final String ID_TABLE_CLASS_NAME = "tableClassName";



	/**
	 * 結合テーブルリストID。
	 */
	private static final String ID_JOIN_TABLE_LIST = "joinTableList";



	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(QueryGeneratorEditForm.class);


	/**
	 * コンストラクタ。
	 */
	public QueryGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		((FunctionSelectField) this.addField(new FunctionSelectField())).setPackageOption("dao");
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new QueryClassNameField()).setAutocomplete(false).addValidator(new RequiredValidator());
		this.addField(new AliasNameField()).setCalcEventField(true);

		this.addField(new FlagField(ID_DISTINCT_FLAG));
		this.addField(new FlagField(ID_FORCE_OVERWRITE));
		this.addField(new TextField(ID_QUERY_COMMENT));

		this.addField((new FunctionSelectField(ID_MAIN_TABLE_FUNCTION_SELECT)).setPackageFieldId(ID_MAIN_TABLE_PACKAGE_NAME)).setComment("主テーブルの機能");
		this.addField(new PackageNameField(ID_MAIN_TABLE_PACKAGE_NAME)).setComment("主テーブルのパッケージ").addValidator(new RequiredValidator());
		this.addField((new TableClassNameField(ID_MAIN_TABLE_CLASS_NAME)).setPackageNameFieldId(ID_MAIN_TABLE_PACKAGE_NAME))
			.setAutocomplete(true)
			.setRelationDataAcquisition(true)
			.setComment("主テーブルクラス名")
			.addValidator(new RequiredValidator());

		EditableHtmlTable joinTableList = new JoinHtmlTable(ID_JOIN_TABLE_LIST);
		joinTableList.setCaption("JOINするテーブルリスト");
		this.addHtmlTable(joinTableList);
		SelectFieldHtmlTable slectFieldList = new SelectFieldHtmlTable("selectFieldList");
		slectFieldList.setCaption("選択フィールドリスト");
		this.addHtmlTable(slectFieldList);

	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("javaSourcePath", DeveloperPage.getJavaSourcePath());
	}

	/**
	 * JOINテーブルリストのデータを取得します。
	 * @param list JOINテーブルリスト。
	 * @return JOINテーブルリストのデータ。
	 */
	private List<Map<String, Object>> getJoinTableData(final List<Query.JoinInfo> list) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		if (list != null) {
			for (Query.JoinInfo jinfo: list) {
				Table t = jinfo.getJoinTable();
				Map<String, Object> m = new HashMap<String, Object>();
				if (JoinInfo.INNER_JOIN.equals(jinfo.getJoinType())) {
					m.put(JoinHtmlTable.ID_JOIN_TYPE, JoinTypeField.INNER_JOIN);
				} else if (JoinInfo.LEFT_JOIN.equals(jinfo.getJoinType())) {
					m.put(JoinHtmlTable.ID_JOIN_TYPE, JoinTypeField.LEFT_JOIN);
				} else if (JoinInfo.RIGHT_JOIN.equals(jinfo.getJoinType())) {
					m.put(JoinHtmlTable.ID_JOIN_TYPE, JoinTypeField.RIGHT_JOIN);
				}
				m.put(ID_PACKAGE_NAME, t.getClass().getPackageName());
				m.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, t.getClass().getSimpleName());
				m.put(JoinHtmlTable.ID_ALIAS_NAME, t.getAlias());
				m.put(JoinHtmlTable.ID_JOIN_CONDITION, jinfo.getGeneratedCondition());
				logger.debug("generatedCondition=" + jinfo.getGeneratedCondition());
				ret.add(m);
			}
		}
		return ret;
	}

	/**
	 * 問合せのインスタンスを取得します。
	 * @param packageName パッケージ名。
	 * @param queryClassName 問合せクラス名。
	 * @return 問合せインスタンス。
	 * @throws Exception 例外。
	 */
	private Query getQueryInstance(final String packageName, final String queryClassName) throws Exception {
		logger.debug("packageName=" + packageName + ",queryClassName=" + queryClassName);
		Query q = null;
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Query> clazz  = (Class<? extends Query>) Class.forName(packageName + "." + queryClassName);
			q = clazz.getDeclaredConstructor().newInstance();
			q.buildJoinInfoList();
			Dao dao = new Dao(this);
			dao.getSqlGenerator().generateQuerySql(q);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return q;
	}


	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String queryClassName = (String) data.get(ID_QUERY_CLASS_NAME);
		Query q = this.getQueryInstance(packageName, queryClassName);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("javaSourcePath", DeveloperPage.getJavaSourcePath());
		ret.put(ID_PACKAGE_NAME, packageName);
		ret.put(ID_QUERY_CLASS_NAME, queryClassName);
		if (q.isDistinct()) {
			ret.put(ID_DISTINCT_FLAG, "1");
		} else {
			ret.put(ID_DISTINCT_FLAG, "0");
		}
		ret.put(ID_QUERY_COMMENT, q.getComment());
		ret.put(ID_FORCE_OVERWRITE, "0");
		ret.put(ID_MAIN_TABLE_PACKAGE_NAME, q.getMainTable().getClass().getPackage().getName());
		ret.put(ID_MAIN_TABLE_CLASS_NAME, q.getMainTable().getClass().getSimpleName());
		ret.put(ID_ALIAS_NAME, q.getMainTable().getAlias());
		ret.put(ID_JOIN_TABLE_LIST, this.getJoinTableData(q.getJoinInfoList()));
		List<Map<String, Object>> flist = this.queryTableFieldList(ret);
		FieldList qfl = q.getFieldList();
		for (Map<String, Object> m: flist) {
			String fid = (String) m.get("fieldId");
			String tclass = (String) m.get("selectTableClass");
			Field<?> qfield = qfl.get(fid);
			if (qfield != null) {
				logger.debug(qfield.getId() + ":" + fid + "," + qfield.getTable().getClass().getName() + ":" + tclass);
				if (qfield.getTable().getClass().getName().equals(tclass)) {
					m.put("sel", "1");
				} else {
					m.put("sel", "0");
				}
			} else {
				m.put("sel", "0");
			}
		}
		ret.put("selectFieldList", flist);
		return ret;
	}


	/**
	 * クラスの存在チェック。
	 * @param name クラス名。
	 * @return 存在する場合true。
	 */
	private boolean classExists(final String name) {
		try {
			Class.forName(name);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * 各種JOINテーブルのバリデーションを行います。
	 * @param id テーブルID。
	 * @param list テーブルの入力内容リスト。
	 * @return バリデーション結果。
	 */
	private List<ValidationError> validateJoinTable(final String id, final List<Map<String, Object>> list) {
		List<ValidationError> ret = new ArrayList<ValidationError>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> ent = list.get(i);
			String packageName = (String) ent.get(ID_PACKAGE_NAME);
			String className = (String) ent.get(JoinHtmlTable.ID_TABLE_CLASS_NAME);
			String tClass = packageName + "." + className;
			if (!this.classExists(tClass)) {
				ret.add(new ValidationError(id + "[" + i + "].tableClassName", MessagesUtil.getMessage(this.getPage(), "error.tableclassnotfound", "{0}", tClass)));
			}
		}
		return ret;
	}



	@SuppressWarnings("unchecked")
	@Override
	protected List<ValidationError> validateForm(final Map<String, Object> data) throws Exception {
		List<ValidationError> ret = new ArrayList<ValidationError>();
		String mtClass = (String) data.get(ID_MAIN_TABLE_PACKAGE_NAME) + "." + (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		if (!this.classExists(mtClass)) {
			ret.add(new ValidationError(ID_MAIN_TABLE_CLASS_NAME, MessagesUtil.getMessage(this.getPage(), "error.tableclassnotfound", "{0}", mtClass)));
		}
		ret.addAll(this.validateJoinTable(ID_JOIN_TABLE_LIST, (List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST)));

		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String queryClassName = (String) data.get(ID_QUERY_CLASS_NAME);
		String javaSrc = (String) data.get("javaSourcePath");
		String srcPath = javaSrc + "/" + packageName.replaceAll("\\.", "/");
		String query = srcPath + "/" + queryClassName + ".java";
		String forceOverwrite = (String) data.get(ID_FORCE_OVERWRITE);
		if (!"1".equals(forceOverwrite)) {
			File f = new File(query);
			if (f.exists()) {
				ret.add(new ValidationError(ID_QUERY_CLASS_NAME, this.getPage().getMessage("error.sourcefileexist", queryClassName + ".java")));
			}
		}
		return ret;
	}

	/**
	 * 1テーブル中のフィールドリストを取得します。
	 * @param tableClass テーブルクラス名。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	private List<Map<String, Object>> queryTableFieldList(final String tableClass) throws Exception {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		@SuppressWarnings("unchecked")
		Class<? extends Table> clazz = (Class<? extends Table>) Class.forName(tableClass);
		Table table = clazz.getDeclaredConstructor().newInstance();
		FieldList flist = table.getFieldList();
		for (Field<?> f: flist) {
			Map<String, Object> ent = new HashMap<String, Object>();
			ent.put("selectTableClass", table.getClass().getName());
			ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getName());
			ent.put("selectTableClassName", table.getClass().getSimpleName());
			ent.put("sel", "0");
			ent.put("fieldId", f.getId());
			ent.put("fieldClassName", f.getClass().getName());
			ent.put("comment", f.getComment());
			ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getSimpleName());
			ret.add(ent);
		}
		return ret;

	}

	/**
	 * JOINテーブルのフィールドリストを取得します。
	 * @param list JOINテーブルリスト。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	private List<Map<String, Object>> queryJoinTableFieldList(final List<Map<String, Object>> list) throws Exception {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> ent = list.get(i);
			String packageName = (String) ent.get(ID_PACKAGE_NAME);
			String className = (String) ent.get(JoinHtmlTable.ID_TABLE_CLASS_NAME);
			String tClass = packageName + "." + className;
			ret.addAll(this.queryTableFieldList(tClass));
		}
		return ret;
	}


	/**
	 * 問合せ対象のフィールドリストを取得します。
	 * @param data フォームデータ。
	 * @return 問合せ対象のフィールドリスト
	 * @throws Exception 例外。
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> queryTableFieldList(final Map<String, Object> data) throws Exception {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		String mtClass = (String) data.get(ID_MAIN_TABLE_PACKAGE_NAME) + "." + (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		logger.debug("mainTable=" + mtClass);
		ret.addAll(this.queryTableFieldList(mtClass));
		ret.addAll(this.queryJoinTableFieldList((List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST)));
		return ret;

	}

	/**
	 * テーブルのフィールドリストを取得します。
	 * @param param パラメータ。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse getFieldList(final Map<String, Object> param) throws Exception {
		JsonResponse ret = null;
		param.put(ID_FORCE_OVERWRITE, "1");
		Map<String, Object> p = new HashMap<String, Object>();
		p.putAll(param);
		p.put(ID_FORCE_OVERWRITE, "1");
		List<ValidationError> vlist = this.validate(p);
		if (vlist.size() == 0) {
			Map<String, Object> data = this.convertToServerData(param);
			List<Map<String, Object>> list = this.queryTableFieldList(data);
			ret = new JsonResponse(JsonResponse.SUCCESS, list);
		} else {
			ret = new JsonResponse(JsonResponse.INVALID, vlist);
		}
		return ret;
	}

	/**
	 * 問合せクラスから結合条件を取得します。
	 * @param query 問合せ。
	 * @param t テーブル。
	 * @return 結合条件。
	 */
	private String getJoinCondition(final Query query, final Table t) {
		String ret = null;
		if (query != null) {
			List<Query.JoinInfo> list =query.getJoinInfoList();
			String className = t.getClass().getName();
			for (Query.JoinInfo ji: list) {
				if (ji.getJoinTable().getClass().getName().equals(className)) {
					ret = ji.getGeneratedCondition();
				}
			}
		}
		return ret;
	}


	/**
	 * 各JOINテーブルの結合条件を取得します。
	 * @param tlist 関連テーブルの全リスト。
	 * @param jlist JOINテーブルリスト。
	 * @param query 問合せクラスのインスタンス。
	 * @return 結合条件。
	 * @throws Exception 例外。
	 */
	private List<Map<String, Object>> queryJoinCondition(final List<Query.JoinInfo> tlist, final TableList jlist, final Query query) throws Exception {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		Table mainTable = tlist.get(0).getJoinTable();
		for (int i = 0; i < jlist.size(); i++) {
			Table t =jlist.get(i);
			String joinCondition = mainTable.getJoinCondition(t, t.getAlias());
			logger.debug("joinConditon=" + joinCondition);
			Map<String, Object> rec = new HashMap<String, Object>();
			if (StringUtil.isBlank(joinCondition)) {
				Dao dao = new Dao(this);
				SqlGenerator gen = dao.getSqlGenerator();
				joinCondition = gen.getJoinConditionOtherThamMainTable(tlist, new Query.JoinInfo(Query.JoinInfo.INNER_JOIN, t, null));
				if (StringUtil.isBlank(joinCondition)) {
					joinCondition = this.getJoinCondition(query, t);
					if (joinCondition == null) {
						joinCondition = MessagesUtil.getMessage(this.getPage(), "message.joinconditionnotfound");
					}
				}
			}
			rec.put("joinCondition", joinCondition);
			ret.add(rec);
		}
		return ret;
	}

	/**
	 * 各種JOINテーブルのリストを取得します。
	 * @param list POSTされたJOINテーブル情報。
	 * @param defaultAlias 	デフォルト別名。
	 * @return 各種JOINテーブルのリスト。
	 * @throws Exception 例外。
	 */
	private TableList getJoinTableList(final List<Map<String, Object>> list, final String defaultAlias) throws Exception {
		TableList ret = new TableList();
		for (int i= 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			String packageName = (String) m.get(ID_PACKAGE_NAME);
			String className = (String) m.get(JoinHtmlTable.ID_TABLE_CLASS_NAME);
			String alias = (String) m.get(ID_ALIAS_NAME);
			if (StringUtil.isBlank(alias)) {
				alias = defaultAlias + i;
			}
			String tcn = packageName + "." + className;
			@SuppressWarnings("unchecked")
			Class<? extends Table> tc = (Class<? extends Table>) Class.forName(tcn);
			Table jt = tc.getDeclaredConstructor().newInstance();
			jt.setAlias(alias);
			ret.add(jt);
		}
		return ret;
	}

	/**
	 * 関連する全てのテーブルリストを取得します。
	 * <pre>
	 * リストの先頭はmainTableとなります。
	 * </pre>
	 * @param data POSTされたデータ。
	 * @return 関連する全デーブルリスト。
	 * @throws Exception 例外。
	 */
	@SuppressWarnings("unchecked")
	private List<Query.JoinInfo> getTableList(final Map<String, Object> data) throws Exception {
		TableList list = new TableList();
		String packageName = (String) data.get(ID_MAIN_TABLE_PACKAGE_NAME);
		String mainTableClassName = (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		String aliasName = (String) data.get(ID_ALIAS_NAME);
		if (StringUtil.isBlank(aliasName)) {
			aliasName = "m";
		}
		Class<? extends Table> clazz = (Class<? extends Table>) Class.forName(packageName + "." + mainTableClassName);
		Table mainTable = clazz.getDeclaredConstructor().newInstance();
		mainTable.setAlias(aliasName);
		list.add(mainTable);

		List<Map<String, Object>> join = (List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST);
		list.addAll(this.getJoinTableList(join, "i"));
		List<Query.JoinInfo> ret = new ArrayList<Query.JoinInfo>();
		for (Table t: list) {
			ret.add(new Query.JoinInfo(Query.JoinInfo.INNER_JOIN, t, null));
		}
		return ret;
	}

	/**
	 * 各テーブルのJOIN条件式を取得します。
	 * @param data POSTされたデータ。
	 * @return JOIN条件式が設定されたリスト。
	 * @throws Exception 例外。
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> queryJoinCondition(final Map<String, Object> data) throws Exception {
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String queryClassName = (String) data.get(ID_QUERY_CLASS_NAME);
		Query q = this.getQueryInstance(packageName, queryClassName);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Query.JoinInfo> list = this.getTableList(data);
		List<Map<String, Object>> join = (List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST);
		ret.put(ID_JOIN_TABLE_LIST, this.queryJoinCondition(list, this.getJoinTableList(join, "i"), q));
		return ret;
	}

	/**
	 * 各テーブルのJOIN条件式を取得します。
	 * @param param POSTされたデータ。
	 * @return JOIN条件式が設定されたリスト。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse getJoinCondition(final Map<String, Object> param) throws Exception {
		JsonResponse ret = null;
		param.put(ID_FORCE_OVERWRITE, "1");
		Map<String, Object> p = new HashMap<String, Object>();
		p.putAll(param);
		p.put(ID_FORCE_OVERWRITE, "1");
		List<ValidationError> vlist = this.validate(p);
		if (vlist.size() == 0) {
			Map<String, Object> data = this.convertToServerData(param);
			Map<String, Object> cond = this.queryJoinCondition(data);
			ret = new JsonResponse(JsonResponse.SUCCESS, cond);
		} else {
			ret = new JsonResponse(JsonResponse.SUCCESS, new HashMap<String, Object>());
		}
		return ret;

	}


	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		return false;
	}


	/**
	 * 各JOINリストのインポート文を作成します。
	 * @param list  POSTされたデータ。
	 * @return インポート文。
	 */
	private String generateImportTableList(final List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> m:list) {
			String packageName = (String) m.get(ID_PACKAGE_NAME);
			String tableClassName = (String) m.get(JoinHtmlTable.ID_TABLE_CLASS_NAME);
			sb.append("import " + packageName + "." + tableClassName + ";\n");
		}
		return sb.toString();
	}

	/**
	 * テーブルクラスのインポート文を作成。
	 * @param data POSTされたデータ。
	 * @return インポート文。
	 */
	@SuppressWarnings("unchecked")
	private String generateImportTables(final Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		String packageName = (String) data.get(ID_MAIN_TABLE_PACKAGE_NAME);
		String mainTableClassName = (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		sb.append("import " + packageName + "." + mainTableClassName + ";\n");
		sb.append(this.generateImportTableList((List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST)));
		return sb.toString();
	}

	/**
	 * テーブルクラスのインスタンス変数名を取得します。
	 * @param classname クラス名。
	 * @return テーブルクラスのインスタンス変数名。
	 */
	private String getTableVariableName(final String classname) {
		String ret = classname.substring(0, 1).toLowerCase() + classname.substring(1);
		return ret;
	}

	/**
	 * 各JOINリストのインポート文を作成します。
	 * @param list  POSTされたデータ。
	 * @return インポート文。
	 */
	private String generateNewTableList(final List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> m:list) {
			String tableClassName = (String) m.get(JoinHtmlTable.ID_TABLE_CLASS_NAME);
			sb.append("\t\t" + tableClassName + " " + this.getTableVariableName(tableClassName) + " = new " + tableClassName + "();\n");
			String aliasName = (String) m.get(ID_ALIAS_NAME);
			if (!StringUtil.isBlank(aliasName)) {
				sb.append("\t\t" + this.getTableVariableName(tableClassName) + ".setAlias(\"" + aliasName + "\");\n");
			}
		}
		return sb.toString();
	}


	/**
	 * テーブルクラスのインポート文を作成。
	 * @param data POSTされたデータ。
	 * @return インポート文。
	 */
	@SuppressWarnings("unchecked")
	private String generateNewTables(final Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		String mainTableClassName = (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		sb.append("\t\t" + mainTableClassName + " " + this.getTableVariableName(mainTableClassName) + " = new " + mainTableClassName + "();\n");
		String aliasName = (String) data.get(ID_ALIAS_NAME);
		if (!StringUtil.isBlank(aliasName)) {
			sb.append("\t\t" + this.getTableVariableName(mainTableClassName) + ".setAlias(\"" + aliasName + "\");\n");
		}
		sb.append(this.generateNewTableList((List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST)));
		return sb.toString();
	}

	/**
	 * 選択フィールド設定ソースを生成します。
	 * @param data POSTされたデータ。
	 * @return 選択フィールド設定ソース。
	 */
	private String generateSelectFieldList(final Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("selectFieldList");
		for (Map<String, Object> m: list) {
			String sel = (String) m.get("sel");
			if ("1".equals(sel)) {
				if (sb.length() > 0) {
					sb.append("\t\t\t, ");
				} else {
					sb.append("\t\t\t");
				}
				String tableClassName = (String) m.get("selectTableClassName");
				logger.debug("tableClassName=" + tableClassName);
				String fieldId = (String) m.get("fieldId");
				String uFieldId = StringUtil.firstLetterToUpperCase(fieldId);
				sb.append(this.getTableVariableName(tableClassName) + ".");
				sb.append("get" + uFieldId + "Field()\n");
			}
		}
		return "\t\tthis.setFieldList(new FieldList(\n" + sb.toString() + "\t\t));";
	}

	/**
	 * JOINの設定ソースを生成します。
	 * @param data POSTされたデータ。
	 * @return JOINの設定ソース。
	 */
	@SuppressWarnings("unchecked")
	private String generateJoinTables(final Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ID_JOIN_TABLE_LIST);
		for (Map<String, Object> m: list) {
			String joinType = (String) m.get("joinType");
			String tableName = this.getTableVariableName((String) m.get(JoinHtmlTable.ID_TABLE_CLASS_NAME));
			if (JoinTypeField.INNER_JOIN.equals(joinType)) {
				sb.append("\t\tthis.addInnerJoin(" + tableName + ");\n");
			} else if (JoinTypeField.LEFT_JOIN.equals(joinType)) {
				sb.append("\t\tthis.addLeftJoin(" + tableName + ");\n");
			} else if (JoinTypeField.RIGHT_JOIN.equals(joinType)) {
				sb.append("\t\tthis.addRightJoin(" + tableName + ");\n");
			}
		}
		return sb.toString();
	}

	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		String javasrc = this.getStringResourse("template/Query.java.template");
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String queryClassName = (String) data.get(ID_QUERY_CLASS_NAME);
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", packageName);
		javasrc = javasrc.replaceAll("\\$\\{queryClassName\\}", queryClassName);
		javasrc = javasrc.replaceAll("\\$\\{importTables\\}", this.generateImportTables(data));
		javasrc = javasrc.replaceAll("\\$\\{newTables\\}", this.generateNewTables(data));
		javasrc = javasrc.replaceAll("\\$\\{selectFields\\}", this.generateSelectFieldList(data));
		String mainTableClassName = (String) data.get(ID_MAIN_TABLE_CLASS_NAME);
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", this.getTableVariableName(mainTableClassName));
		String distinctFlag = (String) data.get(ID_DISTINCT_FLAG);
		if ("1".equals(distinctFlag)) {
			javasrc = javasrc.replaceAll("\\$\\{distinctFlag\\}", "true");
		} else {
			javasrc = javasrc.replaceAll("\\$\\{distinctFlag\\}", "false");
		}
		javasrc = javasrc.replaceAll("\\$\\{queryComment\\}", (String) data.get("queryComment"));
		javasrc = javasrc.replaceAll("\\$\\{joinTables\\}", this.generateJoinTables(data));
		String javaSrc = (String) data.get("javaSourcePath");
		String srcPath = javaSrc + "/" + packageName.replaceAll("\\.", "/");
		String query = srcPath + "/" + queryClassName + ".java";
		FileUtil.writeTextFileWithBackup(query, javasrc, DataFormsServlet.getEncoding());
		logger.debug("javasrc=\n" + javasrc);
	}

	@Override
	protected String getSavedMessage(final Map<String, Object> data) {
		return MessagesUtil.getMessage(this.getPage(), "message.javasourcecreated");
	}

	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		// 何もしない
	}

	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		//　何もしない。
	}

}
