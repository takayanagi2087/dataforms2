package dataforms.devtool.dao.page;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.dao.Table;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.EditFormTypeField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryOrTableClassNameField;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.StringUtil;
import dataforms.validator.DisplayedRequiredValidator;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

public class DaoGeneratorEditForm extends EditForm {

	/**
	 * Logger。
	 */
	private static Logger logger = LogManager.getLogger(DaoGeneratorEditForm.class);

	/**
	 * 編集フォームタイプ。
	 */
	private static final String ID_EDIT_FORM_TYPE = "editFormType";

	/**
	 * パッケージ名。
	 */
	public static final String ID_PACKAGE_NAME = "packageName";

	/**
	 * Daoクラス名。
	 */
	public static final String ID_DAO_CLASS_NAME = "daoClassName";

	/**
	 * コメント。
	 */
	private static final String ID_COMMENT = "comment";

	/**
	 * 上書きモードフィールドID。
	 */
	public static final String ID_OVERWRITE_MODE = "overwriteMode";
	/**
	 * 一覧問合せの機能選択フィールドID。
	 */
	public static final String ID_LIST_QUERY_FUNCTION_SELECT = "listQueryFunctionSelect";
	/**
	 * 一覧問合せクラス名のフィールドID。
	 */
	public static final String ID_LIST_QUERY_CLASS_NAME = "listQueryClassName";
	/**
	 * 一覧問合せパッケージ名のフィールドID。
	 */
	public static final String ID_LIST_QUERY_PACKAGE_NAME = "listQueryPackageName";
	/**
	 * 編集フォームレコード取得問合せの機能選択フィールドID。
	 */
	public static final String ID_EDIT_FORM_QUERY_FUNCTION_SELECT = "editFormQueryFunctionSelect";
	/**
	 * 編集フォームレコード取得問合せのパッケージ名フィールドID。
	 */
	public static final String ID_EDIT_FORM_QUERY_PACKAGE_NAME = "editFormQueryPackageName";
	/**
	 * 編集フォームレコード取得問合せのクラス名フィールドID。
	 */
	public static final String ID_EDIT_FORM_QUERY_CLASS_NAME = "editFormQueryClassName";
	/**
	 * キーフィールドリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_KEY_LIST = "multiRecordQueryKeyList";
	/**
	 * 複数レコード取得問合せリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_LIST = "multiRecordQueryList";
	/**
	 * javaソースパスフィールドID。
	 */
	public static final String ID_JAVA_SOURCE_PATH = "javaSourcePath";
	/**
	 * 問合せクラス名のフィールドID。
	 */
	public static final String ID_QUERY_CLASS_NAME = "queryClassName";

	/**
	 * キーフィールドリスト。
	 */
	private static final String ID_KEY_FIELD_LIST = "keyFieldList";


	/**
	 * コンストラクタ。
	 */
	public DaoGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		this.addField(new EditFormTypeField()).setComment("編集フォーム");
		this.addField(new OverwriteModeField(ID_OVERWRITE_MODE)).setComment("上書きモード");
		FunctionSelectField funcField = new FunctionSelectField();
		funcField.setPackageOption("dao");
		funcField.setCalcEventField(true);
		this.addField(funcField);
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new DaoClassNameField()).addValidator(new RequiredValidator());
		this.addField(new TextField(ID_COMMENT));
		//
		this.addField((new FunctionSelectField(ID_LIST_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_LIST_QUERY_PACKAGE_NAME).setComment("一覧問合せの機能"));
		this.addField((new PackageNameField(ID_LIST_QUERY_PACKAGE_NAME)).setComment("一覧問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_LIST_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_LIST_QUERY_PACKAGE_NAME))
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
		//
		this.addField((new FunctionSelectField(ID_EDIT_FORM_QUERY_FUNCTION_SELECT))
			.setPackageFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME)
			.setComment("単一レコード取得用問合せの機能"))
			.addValidator(new DisplayedRequiredValidator());
		this.addField((new PackageNameField(ID_EDIT_FORM_QUERY_PACKAGE_NAME))
			.setComment("単一レコード取得用問合せのパッケージ"))
			.addValidator(new DisplayedRequiredValidator());
		this.addField((new QueryOrTableClassNameField(ID_EDIT_FORM_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME))
			.setCalcEventField(true)
			.setAutocomplete(true)
			.setRelationDataAcquisition(true)
			.addValidator(new DisplayedRequiredValidator());
		//
		{
			FieldList flist = new FieldList();
			flist.addField(new FunctionSelectField());
			flist.addField(new PackageNameField());
			flist.addField(new QueryOrTableClassNameField(ID_QUERY_CLASS_NAME))
				.setAutocomplete(true)
				.setRelationDataAcquisition(true);
			EditableHtmlTable list = new EditableHtmlTable(ID_MULTI_RECORD_QUERY_LIST, flist);
			this.addHtmlTable(list);
		}
		{
			SelectFieldHtmlTable fieldList = new SelectFieldHtmlTable(ID_KEY_FIELD_LIST);
			this.addHtmlTable(fieldList);
		}

	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
		this.setFormData(ID_EDIT_FORM_TYPE, "1");
		this.setFormData(ID_OVERWRITE_MODE, "error");
	}

	@Override
	protected Map<String, Object> queryNewData(final Map<String, Object> data) throws Exception {
		Map<String, Object> ret = super.queryNewData(data);
		ret.put(ID_EDIT_FORM_TYPE, "1");
		ret.put(ID_OVERWRITE_MODE, "error");
		return ret;
	}

	/**
	 * 指定されたaueryがSingleTableQueryの場合、mainTableを返します。
	 * @param query 問合せ。
	 * @return mainTable。
	 */
	private Object getQueryOrTableClass(final Query query) {
		if (query instanceof SingleTableQuery) {
			return query.getMainTable();
		} else {
			return query;
		}
	}

	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		String pkgName = (String) data.get(ID_PACKAGE_NAME);
		String className = (String) data.get(ID_DAO_CLASS_NAME);
		String daoClassName = pkgName + "." + className;
		logger.debug(() -> "DAO class name=" + daoClassName);
		Class<?> daoclass = Class.forName(daoClassName);
		QuerySetDao dao = (QuerySetDao) daoclass.getConstructor().newInstance();

		ret.put(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
		ret.put(ID_OVERWRITE_MODE, "error");
		ret.put(ID_PACKAGE_NAME, daoclass.getPackageName());
		ret.put(ID_DAO_CLASS_NAME, daoclass.getSimpleName());
		ret.put(ID_COMMENT, dao.getComment());
		if (dao.getListQuery() != null) {
			Object obj = this.getQueryOrTableClass(dao.getListQuery());
			ret.put(ID_LIST_QUERY_PACKAGE_NAME, obj.getClass().getPackageName());
			ret.put(ID_LIST_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
		}

		if (dao.getSingleRecordQuery() == null && dao.getMultiRecordQueryList() == null) {
			ret.put(ID_EDIT_FORM_TYPE, "0");
		} else if (dao.getSingleRecordQuery() != null) {
			ret.put(ID_EDIT_FORM_TYPE, "1");
			{
				Object obj = this.getQueryOrTableClass(dao.getSingleRecordQuery());
				ret.put(ID_EDIT_FORM_QUERY_PACKAGE_NAME, obj.getClass().getPackageName());
				ret.put(ID_EDIT_FORM_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
			}
			List<Query> qlist = dao.getMultiRecordQueryList();
			if (qlist != null) {
				List<Map<String, Object>> mqlist = new ArrayList<Map<String, Object>>();
				for (Query q: qlist) {
					Object obj = this.getQueryOrTableClass(q);
					Map<String, Object> m = new HashMap<String, Object>();
					m.put(ID_PACKAGE_NAME, obj.getClass().getPackageName());
					m.put(ID_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
					mqlist.add(m);
				}
				ret.put(ID_MULTI_RECORD_QUERY_LIST, mqlist);
			}
		} else {
			ret.put(ID_EDIT_FORM_TYPE, "2");
			List<Query> qlist = dao.getMultiRecordQueryList();
			if (qlist != null && qlist.size() > 0) {
				Query q = qlist.get(0);
				Object obj = this.getQueryOrTableClass(q);
				ret.put(ID_EDIT_FORM_QUERY_PACKAGE_NAME, obj.getClass().getPackageName());
				ret.put(ID_EDIT_FORM_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
				List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(q.getFieldList());
				list = SelectFieldHtmlTable.selectKey(list, dao.getMultiRecordQueryKeyList());
				ret.put(ID_KEY_FIELD_LIST, list);
			}
		}
		return ret;
	}

	@WebMethod
	public Response getKeyList(final Map<String, Object> p) throws Exception {
		Map<String, Object> data = this.convertToServerData(p);
		String packageName = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
		String className = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
		FieldList flist = new FieldList();
		if (!StringUtil.isBlank(className)) {
			Table table = this.getMainTable(packageName + "." + className);
			flist.addAll(table.getFieldList());
		}
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(flist);
		Response resp = new JsonResponse(JsonResponse.SUCCESS, list);
		return resp;
	}


	@Override
	protected List<ValidationError> validateForm(Map<String, Object> data) throws Exception {
		List<ValidationError> list = super.validateForm(data);
		if (list.size() == 0) {
			String path = (String) data.get(ID_JAVA_SOURCE_PATH);
			String packageName = (String) data.get(ID_PACKAGE_NAME);
			String daoClassName = (String) data.get(ID_DAO_CLASS_NAME);
			String daoclass = packageName + "." + daoClassName;
			String srcPath = path + "/" + daoclass.replaceAll("\\.", "/") + ".java";
			String overwriteMode = (String) data.get("overwriteMode");
			if (OverwriteModeField.ERROR.equals(overwriteMode)) {
				File tbl = new File(srcPath);
				if (tbl.exists()) {
					list.add(new ValidationError(ID_DAO_CLASS_NAME, this.getPage().getMessage("error.sourcefileexist", daoClassName + ".java")));
				}
			}
		}
		return list;
	}

	@Override
	protected boolean isUpdate(Map<String, Object> data) throws Exception {
		return false;
	}


	/**
	 * 問合せに使用するテーブルプロパティ設定ソースを作成します。
	 * @param packageName パッケージ名。
	 * @param tableClassName テーブルクラス名。
	 * @return テーブルプロパティ設定ソース。
	 * @throws Exception 例外。
	 */
	private String getProperty(final String packageName, final String tableClassName) throws Exception {
		String src = "	/**\n" +
					"	 * ${comment}。\n" +
					"	 */\n" +
					"	private ${className} ${variableName} = null;\n\n" +
					"	/**\n" +
					"	 * ${comment}を取得します。\n" +
					"	 * @return ${comment}。\n" +
					"	 */\n" +
					"	public ${className} get${className}() {\n" +
					"		return this.${variableName};\n" +
					"	}\n\n";

		Class<?> c = Class.forName(packageName + "." + tableClassName);
		Object obj = (Object) c.getConstructor().newInstance();
		String comment = "";
		if (obj instanceof Table) {
			Table table = (Table) obj;
			comment = table.getComment();
		} else if (obj instanceof Query){
			Query query = (Query) obj;
			comment = query.getComment();
		}
		String ret = src.replaceAll("\\$\\{className\\}", tableClassName);
		ret = ret.replaceAll("\\$\\{variableName\\}", StringUtil.firstLetterToLowerCase(tableClassName));
		ret = ret.replaceAll("\\$\\{comment\\}", comment);
		return ret;

	}


	/**
	 * テーブルまたは問合せのプロパティコードを作成します。
	 * @param data データ。
	 * @return プロパティのソース。
	 * @throws Exception 例外。
	 */
	private String getProperties(final Map<String, Object> data) throws Exception {
		Set<String> set = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		{
			String packageName = (String) data.get(ID_LIST_QUERY_PACKAGE_NAME);
			String className = (String) data.get(ID_LIST_QUERY_CLASS_NAME);
			String fullClassName = packageName + "." + className;
			if (!StringUtil.isBlank(className)) {
				if (!set.contains(fullClassName)) {
					sb.append(this.getProperty(packageName, className));
					set.add(fullClassName);
				}
			}
		}
		{
			String packageName = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
			String className = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
			String fullClassName = packageName + "." + className;
			if (!StringUtil.isBlank(className)) {
				if (!set.contains(fullClassName)) {
					sb.append(this.getProperty(packageName, className));
					set.add(fullClassName);
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ID_MULTI_RECORD_QUERY_LIST);
		for (Map<String, Object> m: list) {
			String packageName = (String) m.get(ID_PACKAGE_NAME);
			String className = (String) m.get(ID_QUERY_CLASS_NAME);
			String fullClassName = packageName + "." + className;
			if (!StringUtil.isBlank(className)) {
				if (!set.contains(fullClassName)) {
					sb.append(this.getProperty(packageName, className));
					set.add(fullClassName);
				}
			}
		}
		return sb.toString();
	}


	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		Set<String> implist = new HashSet<String>();
		String javasrc = this.getStringResourse("template/QuerySetDao.java.template");
		//logger.debug("template=" + javasrc);
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String daoClassName = (String) data.get(ID_DAO_CLASS_NAME);
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", packageName);
		javasrc = javasrc.replaceAll("\\$\\{daoClassName\\}", daoClassName);
		javasrc = javasrc.replaceAll("\\$\\{properties\\}", this.getProperties(data));

		String daoclass = packageName + "." + daoClassName;
		String comment = (String) data.get(ID_COMMENT);
		javasrc = javasrc.replaceAll("\\$\\{comment\\}", comment);
		{
			String queryPackage = (String) data.get("listQueryPackageName");
			String queryClass = (String) data.get("listQueryClassName");
			if (!StringUtil.isBlank(queryClass)) {
				if (!packageName.equals(queryPackage)) {
					implist.add(queryPackage + "." + queryClass);
				}
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "this." + StringUtil.firstLetterToLowerCase(queryClass) + " = new " + queryClass + "()");
			} else {
				implist.add(Query.class.getName());
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "(Query) null");
			}
		}
		String editFormType = (String) data.get(ID_EDIT_FORM_TYPE);
		if ("0".equals(editFormType)) {
			javasrc = this.noEditForm(javasrc, implist);
		} else if ("1".equals(editFormType)) {
			javasrc = this.singleRecordEditForm(data, implist, javasrc);
		} else {
			javasrc = this.multiRecordEditForm(data, implist, javasrc);
		}
		StringBuilder isb = new StringBuilder();
		for (String s: implist) {
			isb.append("import " + s + ";\n");
		}
		javasrc = javasrc.replaceAll("\\$\\{importTables\\}", isb.toString());
		logger.debug("javasrc={}", javasrc);

		String path = (String) data.get(ID_JAVA_SOURCE_PATH);
		String srcPath = path + "/" + daoclass.replaceAll("\\.", "/") + ".java";
		FileUtil.writeTextFileWithBackup(srcPath, javasrc, DataFormsServlet.getEncoding());

	}

	/**
	 * 選択フィールドリストのソースを作成します。
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @return 選択フィールドリストのソース。
	 * @throws Exception 例外。
	 */
	private String getKeyListSource(final Map<String, Object> data, final Set<String> implist) throws Exception {
		int cnt = 0;
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ID_KEY_FIELD_LIST);
		sb.append("\t\tQuery query = this.getMultiRecordQueryList().get(0);\n");
		sb.append("\t\tthis.setMultiRecordQueryKeyList(new FieldList(");
		StringBuilder fsb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String sel = (String) m.get("sel");
			if ("1".equals(sel)) {
				if (fsb.length() > 0) {
					fsb.append(", ");
				}
				String fieldId = (String) m.get("fieldId");
				String tbl = (String) m.get("tableClassName");
				String fld = "query.getFieldList().get(" + tbl + ".Entity.ID_" + StringUtil.camelToSnake(fieldId).toUpperCase() + ")";
				fsb.append(fld);
				cnt++;
			}
		}
		sb.append(fsb.toString());
		sb.append("));\n");
		if (cnt > 0) {
			implist.add("dataforms.field.base.FieldList");
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * 複数レコード編集フォーム用DAOソース生成を行います。
	 *
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @param javasrc javaソーステキスト。
	 * @return javaソーステキスト。
	 * @throws Exception 例外。
	 */
	private String multiRecordEditForm(final Map<String, Object> data, final Set<String> implist, String javasrc) throws Exception {
		String p = (String) data.get(ID_PACKAGE_NAME);
		String packagename = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
		String classname = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
		String src = "\t\tthis.addMultiRecordQueryList(this." + StringUtil.firstLetterToLowerCase(classname) + " = new " + classname + "());\n"
					+ this.getKeyListSource(data, implist);
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", src);
		implist.add(Query.class.getName());
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		if (!p.equals(packagename)) {
			implist.add(packagename + "." + classname);
		}

		Table mainTable = this.getMainTable(packagename + "." + classname);
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
		return javasrc;
	}

	/**
	 * 1レコード編集フォーム用DAOソース生成を行います。
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @param javasrc javaソーステキスト。
	 * @return javaソーステキスト。
	 * @throws Exception 例外。
	 */
	private String singleRecordEditForm(final Map<String, Object> data, final Set<String> implist, String javasrc) throws Exception {
		String p = (String) data.get(ID_PACKAGE_NAME);
		{
			String queryPackage = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
			String queryClass = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
			if (!StringUtil.isBlank(queryClass)) {
				if (!StringUtil.isBlank(queryClass)) {
					if (!p.equals(queryPackage)) {
						implist.add(queryPackage + "." + queryClass);
					}
					javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "this." + StringUtil.firstLetterToLowerCase(queryClass) + " = new " + queryClass + "()");
				} else {
					implist.add(Query.class.getName());
					javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
				}
			}
			if (!StringUtil.isBlank(queryClass)) {
				String className = queryPackage + "." + queryClass;
				Table mainTable = this.getMainTable(className);
				javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
			} else {
				implist.add(Table.class.getName());
				javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
			}
		}
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("multiRecordQueryList");
		for (Map<String, Object> m: list) {
			String pkgname = (String) m.get("packageName");
			String clsname = (String) m.get("queryClassName");
			if (!p.equals(pkgname)) {
				implist.add(pkgname + "." + clsname);
			}
			sb.append("\t\tthis.addMultiRecordQueryList(this." + StringUtil.firstLetterToLowerCase(clsname) + " = new " + clsname + "());\n");
		}
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", sb.toString());
		return javasrc;
	}

	/**
	 * Mainテーブルを取得します。
	 * @param className QueryまたはTableのクラス名。
	 * @return 主テーブルのインスタンス。
	 * @throws Exception 例外。
	 */
	private Table getMainTable(final String className) throws Exception {
		Table mainTable = null;
		if (!StringUtil.isBlank(className)) {
			Class<?> qclass = Class.forName(className);
			Object obj = qclass.getConstructor().newInstance();
			if (obj instanceof Query) {
				Query q = (Query) obj;
				mainTable = q.getMainTable();
			} else if (obj instanceof Table){
				mainTable = (Table) obj;
			}
		}
		return mainTable;
	}

	/**
	 * 編集フォーム無DAOソース生成を行います。
	 * @param javasrc javaソース文字列。
	 * @param implist インポートリスト。
	 * @return javaソース文字列。
	 */
	private String noEditForm(String javasrc, final Set<String> implist) {
		implist.add(Table.class.getName());
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", "");
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
		implist.add("dataforms.dao.Query");
		return javasrc;
	}

	@Override
	protected void updateData(Map<String, Object> data) throws Exception {
		// 使用しない。
	}

	@Override
	public void deleteData(Map<String, Object> data) throws Exception {
		// 使用しない。
	}

}
