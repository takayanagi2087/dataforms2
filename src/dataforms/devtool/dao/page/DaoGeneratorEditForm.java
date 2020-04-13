package dataforms.devtool.dao.page;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.dao.Table;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryOrTableClassNameField;
import dataforms.devtool.field.QueryTypeField;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.StringUtil;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

public class DaoGeneratorEditForm extends EditForm {

	/**
	 * Logger。
	 */
	private static Logger logger = Logger.getLogger(DaoGeneratorEditForm.class);

	/**
	 * 問合せタイプ。
	 */
	private static final String ID_QUERY_TYPE = "queryType";

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
		this.addField(new QueryTypeField()).setComment("編集フォーム");
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
		this.addField((new FunctionSelectField(ID_EDIT_FORM_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME).setComment("単一レコード取得用問合せの機能"));
		this.addField((new PackageNameField(ID_EDIT_FORM_QUERY_PACKAGE_NAME)).setComment("単一レコード取得用問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_EDIT_FORM_QUERY_CLASS_NAME)).setPackageNameFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME))
			.setCalcEventField(true)
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
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
		this.setFormData(ID_QUERY_TYPE, "1");
		this.setFormData(ID_OVERWRITE_MODE, "error");
	}

	@Override
	protected Map<String, Object> queryNewData(final Map<String, Object> data) throws Exception {
		Map<String, Object> ret = super.queryNewData(data);
		ret.put(ID_QUERY_TYPE, "1");
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
		logger.debug("DAO class name=" + daoClassName);
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
			ret.put(ID_QUERY_TYPE, "0");
		} else if (dao.getSingleRecordQuery() != null) {
			ret.put(ID_QUERY_TYPE, "1");
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
			ret.put(ID_QUERY_TYPE, "2");
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
		Table table = this.getMainTable(packageName + "." + className);
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(table.getFieldList());
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

	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		List<String> implist = new ArrayList<String>();
		String javasrc = this.getStringResourse("template/QuerySetDao.java.template");
		//logger.debug("template=" + javasrc);
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String daoClassName = (String) data.get(ID_DAO_CLASS_NAME);
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", packageName);
		javasrc = javasrc.replaceAll("\\$\\{daoClassName\\}", daoClassName);
		String daoclass = packageName + "." + daoClassName;
		implist.add(daoclass);
		String comment = (String) data.get(ID_COMMENT);
		javasrc = javasrc.replaceAll("\\$\\{comment\\}", comment);
		{
			String queryPackage = (String) data.get("listQueryPackageName");
			String queryClass = (String) data.get("listQueryClassName");
			if (!StringUtil.isBlank(queryClass)) {
				implist.add(queryPackage + "." + queryClass);
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "new " + queryClass + "()");
			} else {
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "(Query) null");
			}
		}
		String queryType = (String) data.get("queryType");
		if ("0".equals(queryType)) {
			javasrc = this.noEditForm(javasrc);
		} else if ("1".equals(queryType)) {
			javasrc = this.singleRecordEditForm(data, implist, javasrc);
		} else {
			javasrc = this.multiRecordEditForm(data, implist, javasrc);
		}
		StringBuilder isb = new StringBuilder();
		for (String s: implist) {
			isb.append("import " + s + ";\n");
		}
		javasrc = javasrc.replaceAll("\\$\\{importTables\\}", isb.toString());
		logger.debug("javasrc=" + javasrc);

		String path = (String) data.get(ID_JAVA_SOURCE_PATH);
		String srcPath = path + "/" + daoclass.replaceAll("\\.", "/") + ".java";
		FileUtil.writeTextFileWithBackup(srcPath, javasrc, DataFormsServlet.getEncoding());

	}

	/**
	 * 選択フィールドリストのソースを作成します。
	 * @param data フォームデータ。
	 * @return 選択フィールドリストのソース。
	 * @throws Exception 例外。
	 */
	private String getKeyListSource(final Map<String, Object> data) throws Exception {
		StringBuilder sb = new StringBuilder();
		String table = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
		sb.append("\t\t" + table + " table = new " + table + "();\n");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(ID_KEY_FIELD_LIST);
		sb.append("\t\tthis.setMultiRecordQueryKeyList(new FieldList(");
		StringBuilder fsb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String sel = (String) m.get("sel");
			if ("1".equals(sel)) {
				if (fsb.length() > 0) {
					fsb.append(", ");
				}
				String fieldId = (String) m.get("fieldId");
				String fc =  fieldId.substring(0, 1).toUpperCase();
				fsb.append("table.get" + fc + fieldId.substring(1)+ "Field()");
			}
		}
		sb.append(fsb.toString());
		sb.append("));\n");
		return sb.toString();
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
	private String multiRecordEditForm(final Map<String, Object> data, final List<String> implist, String javasrc) throws Exception {
		String packagename = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
		String classname = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
		String src = this.getKeyListSource(data) + "\t\tthis.addMultiRecordQueryList(new " + classname + "());\n";
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", src);
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		implist.add(packagename + "." + classname);
		Table mainTable = this.getMainTable(packagename + "." + classname);
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
		implist.add("dataforms.field.base.FieldList");
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
	private String singleRecordEditForm(final Map<String, Object> data, final List<String> implist, String javasrc)
			throws Exception {
		{
			String queryPackage = (String) data.get(ID_EDIT_FORM_QUERY_PACKAGE_NAME);
			String queryClass = (String) data.get(ID_EDIT_FORM_QUERY_CLASS_NAME);
			if (!StringUtil.isBlank(queryClass)) {
				implist.add(queryPackage + "." + queryClass);
				javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "new " + queryClass + "()");
			}
			String className = queryPackage + "." + queryClass;
			Table mainTable = this.getMainTable(className);
			javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
		}
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("multiRecordQueryList");
		for (Map<String, Object> m: list) {
			String pkgname = (String) m.get("packageName");
			String clsname = (String) m.get("queryClassName");
			implist.add(pkgname + "." + clsname);
			sb.append("\t\tthis.addMultiRecordQueryList(new " + clsname + "());\n");
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
		Class<?> qclass = Class.forName(className);
		Object obj = qclass.getConstructor().newInstance();
		if (obj instanceof Query) {
			Query q = (Query) obj;
			mainTable = q.getMainTable();
		} else if (obj instanceof Table){
			mainTable = (Table) obj;
		}
		return mainTable;
	}

	/**
	 * 編集フォーム無DAOソース生成を行います。
	 * @param javasrc javaソース文字列。
	 * @return javaソース文字列。
	 */
	private String noEditForm(String javasrc) {
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", "");
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
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
