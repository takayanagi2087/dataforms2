package dataforms.devtool.dao.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.dao.Table;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.FieldSingleSelectField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryOrTableClassNameField;
import dataforms.devtool.field.QueryTypeField;
import dataforms.field.base.FieldList;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.util.StringUtil;
import dataforms.validator.RequiredValidator;

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
	 * 単一レコード取得問合せの機能選択フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_FUNCTION_SELECT = "singleRecordQueryFunctionSelect";
	/**
	 * 単一レコード取得問合せのパッケージ名フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_PACKAGE_NAME = "singleRecordQueryPackageName";
	/**
	 * 単一レコード取得問合せのクラス名フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_CLASS_NAME = "singleRecordQueryClassName";
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
		//
		this.addField((new FunctionSelectField(ID_LIST_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_LIST_QUERY_PACKAGE_NAME).setComment("一覧問合せの機能"));
		this.addField((new PackageNameField(ID_LIST_QUERY_PACKAGE_NAME)).setComment("一覧問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_LIST_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_LIST_QUERY_PACKAGE_NAME))
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
		//
		this.addField((new FunctionSelectField(ID_SINGLE_RECORD_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME).setComment("単一レコード取得用問合せの機能"));
		this.addField((new PackageNameField(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME)).setComment("単一レコード取得用問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_SINGLE_RECORD_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME))
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
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
			FieldList flist = new FieldList();
			flist.addField(new FieldSingleSelectField());
			EditableHtmlTable list = new EditableHtmlTable(ID_MULTI_RECORD_QUERY_KEY_LIST, flist);
			this.addHtmlTable(list);
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
		if (dao.getSingleRecordQuery() == null || dao.getMultiRecordQueryList() == null) {
			ret.put(ID_QUERY_TYPE, "0");
		} else if (dao.getSingleRecordQuery() != null) {
			ret.put(ID_QUERY_TYPE, "1");
		} else {
			ret.put(ID_QUERY_TYPE, "2");
		}
		ret.put(ID_PACKAGE_NAME, daoclass.getPackageName());
		ret.put(ID_DAO_CLASS_NAME, daoclass.getSimpleName());
		if (dao.getListQuery() != null) {
			Object obj = this.getQueryOrTableClass(dao.getListQuery());
			ret.put(ID_LIST_QUERY_PACKAGE_NAME, obj.getClass().getPackageName());
			ret.put(ID_LIST_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
		}

		if (dao.getSingleRecordQuery() != null) {
			Object obj = this.getQueryOrTableClass(dao.getSingleRecordQuery());
			ret.put(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME, obj.getClass().getPackageName());
			ret.put(ID_SINGLE_RECORD_QUERY_CLASS_NAME, obj.getClass().getSimpleName());
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
		return ret;
	}

	@Override
	protected boolean isUpdate(Map<String, Object> data) throws Exception {
		return false;
	}

	@Override
	protected void insertData(Map<String, Object> data) throws Exception {
		List<String> implist = new ArrayList<String>();
		String javasrc = this.getStringResourse("template/QuerySetDao.java.template");
		//logger.debug("template=" + javasrc);
		String packageName = (String) data.get(ID_PACKAGE_NAME);
		String daoClassName = (String) data.get(ID_DAO_CLASS_NAME);
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", packageName);
		javasrc = javasrc.replaceAll("\\$\\{daoClassName\\}", daoClassName);
		implist.add(packageName + "." + daoClassName);
		{
			String queryPackage = (String) data.get("listQueryPackageName");
			String queryClass = (String) data.get("listQueryClassName");
			if (!StringUtil.isBlank(queryClass)) {
				implist.add(queryPackage + "." + queryClass);
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "new " + queryClass + "()");
			} else {
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "null");
			}
		}
		String queryType = (String) data.get("queryType");
		if ("1".equals(queryType)) {
			{
				String queryPackage = (String) data.get("singleRecordQueryPackageName");
				String queryClass = (String) data.get("singleRecordQueryClassName");
				if (!StringUtil.isBlank(queryClass)) {
					implist.add(queryPackage + "." + queryClass);
					javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "new " + queryClass + "()");
				}
				String className = queryPackage + "." + queryClass;
				Class<?> qclass = Class.forName(className);
				Query q = (Query) qclass.getConstructor().newInstance();
				Table mainTable = q.getMainTable();
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
		} else {

		}
		logger.debug("javasrc=" + javasrc);
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
