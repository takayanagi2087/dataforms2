package dataforms.devtool.pageform.page;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.EditForm;
import dataforms.controller.Page;
import dataforms.controller.QueryForm;
import dataforms.controller.QueryResultForm;
import dataforms.controller.WebComponent;
import dataforms.dao.Dao;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.dao.page.QuerySetDaoGenerator;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.EditFormClassNameField;
import dataforms.devtool.field.EditFormSelectField;
import dataforms.devtool.field.EditTypeSelectField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.ListFormSelectField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.PageClassNameField;
import dataforms.devtool.field.PageNameField;
import dataforms.devtool.field.QueryFormClassNameField;
import dataforms.devtool.field.QueryFormSelectField;
import dataforms.devtool.field.QueryOrTableClassNameField;
import dataforms.devtool.field.QueryResultFormClassNameField;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.exception.ApplicationException;
import dataforms.field.base.Field;
import dataforms.field.base.Field.MatchType;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.SingleSelectField.HtmlFieldType;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.htmltable.HtmlTable;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.ClassNameUtil;
import dataforms.util.FileUtil;
import dataforms.util.MessagesUtil;
import dataforms.util.SequentialProperties;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;
import net.arnx.jsonic.JSON;

/**
 * ページ作成フォームクラス。
 *
 */
public class DaoAndPageGeneratorEditForm extends EditForm {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(DaoAndPageGeneratorEditForm.class);

	/**
	 * ページ名フィールドID。
	 */
	public static final String ID_FUNCTION_SELECT = "functionSelect";

	/**
	 * ページ名フィールドID。
	 */
	public static final String ID_PAGE_NAME = "pageName";
	/**
	 * ページパッケージ名フィールドID。
	 */
	public static final String ID_PACKAGE_NAME = "packageName";

	/**
	 * DAOパッケージ名フィールドID。
	 */
	public static final String ID_DAO_PACKAGE_NAME = "daoPackageName";

	/**
	 * DAOクラス名フィールドID。
	 */
	public static final String ID_DAO_CLASS_NAME = "daoClassName";

	/**
	 * ページクラス名フィールドID。
	 */
	public static final String ID_PAGE_CLASS_NAME = "pageClassName";

	/**
	 * JavaソースパスフィールドID。
	 */
	public static final String ID_JAVA_SOURCE_PATH = "javaSourcePath";

	/**
	 * 上書きモードフィールドID。
	 */
	public static final String ID_PAGE_CLASS_OVERWRITE_MODE = "pageClassOverwriteMode";

	/**
	 * 上書きモードフィールドID。
	 */
	public static final String ID_DAO_CLASS_OVERWRITE_MODE = "daoClassOverwriteMode";

	/**
	 * 問合せ結果フォーム上書きモードフィールドID。
	 */
	public static final String ID_QUERY_RESULT_FORM_CLASS_OVERWRITE_MODE = "queryResultFormClassOverwriteMode";

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
	 * 問合せフォーム上書きモードフィールドID。
	 */
	public static final String ID_QUERY_FORM_CLASS_OVERWRITE_MODE = "queryFormClassOverwriteMode";
	/**
	 * 編集フォーム上書きモードフィールドID。
	 */
	public static final String ID_EDIT_FORM_CLASS_OVERWRITE_MODE = "editFormClassOverwriteMode";
	/**
	 * 編集フォームレコード取得問合せの機能選択フィールドID。
	 */
	public static final String ID_EDIT_QUERY_FUNCTION_SELECT = "editQueryFunctionSelect";
	/**
	 * 編集フォームレコード取得問合せのパッケージ名フィールドID。
	 */
	public static final String ID_EDIT_QUERY_PACKAGE_NAME = "editQueryPackageName";
	/**
	 * 編集フォームレコード取得問合せのクラス名フィールドID。
	 */
	public static final String ID_EDIT_QUERY_CLASS_NAME = "editQueryClassName";
	/**
	 * キーフィールドリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_KEY_LIST = "multiRecordQueryKeyList";
	/**
	 * 複数レコード取得問合せリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_LIST = "multiRecordQueryList";
	/**
	 * 問合せクラス名のフィールドID。
	 */
	public static final String ID_QUERY_CLASS_NAME = "queryClassName";

	/**
	 * 問合せフォームクラス名フィールドID。
	 */
	public static final String ID_QUERY_FORM_CLASS_NAME = "queryFormClassName";

	/**
	 * 問合せ結果フォームクラス名フィールドID。
	 */
	public static final String ID_QUERY_RESULT_FORM_CLASS_NAME = "queryResultFormClassName";
	/**
	 * 編集フォームクラス名フィールドID。
	 */
	public static final String ID_EDIT_FORM_CLASS_NAME = "editFormClassName";

	/**
	 * 一覧問合せ設定フィールドの設定情報。
	 */
	public static final String ID_LIST_QUERY_CONFIG = "listQueryConfig";

	/**
	 * 編集問合せフィールドの設定情報。
	 */
	public static final String ID_EDIT_QUERY_CONFIG = "editQueryConfig";

	/**
	 * 問合せフォームの有無。
	 */
	public static final String ID_QUERY_FORM_SELECT = "queryFormSelect";

	/**
	 * 問合せ結果フォームの有無。
	 */
	public static final String ID_LIST_FORM_SELECT = "listFormSelect";

	/**
	 * 編集フォームの有無。
	 */
	public static final String ID_EDIT_FORM_SELECT = "editFormSelect";

	/**
	 * 編集フォームの種類。
	 */
	public static final String ID_EDIT_TYPE_SELECT = "editTypeSelect";

	/**
	 * 複数レコード編集問合せのフィールド設定。
	 */
	private static final String ID_QUERY_CONFIG = "queryConfig";

	/**
	 * コンストラクタ。
	 */
	public DaoAndPageGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		FunctionSelectField funcField = new FunctionSelectField();
		funcField.setPackageOption("page", "dao");
		funcField.setPackageFieldId(ID_PACKAGE_NAME, ID_DAO_PACKAGE_NAME);
		funcField.setCalcEventField(true);
		// 生成するクラス
		this.addField(funcField);
		this.addField(new PageNameField()).addValidator(new RequiredValidator());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator()).setComment("ページパッケージ名");
		this.addField(new PageClassNameField())	.addValidator(new RequiredValidator()).setCalcEventField(true).setAutocomplete(false);
		this.addField(new OverwriteModeField(ID_PAGE_CLASS_OVERWRITE_MODE));
		this.addField(new PackageNameField("daoPackageName")).addValidator(new RequiredValidator()).setComment("DAOパッケージ名");
		this.addField(new DaoClassNameField()).addValidator(new RequiredValidator()).setComment("DAOクラス名");
		this.addField(new OverwriteModeField(ID_DAO_CLASS_OVERWRITE_MODE));
		// ページの機能
		this.addField(new ListFormSelectField().setHtmlFieldType(HtmlFieldType.SELECT)).setComment("問合せ結果");
		this.addField(new QueryFormSelectField().setHtmlFieldType(HtmlFieldType.SELECT)).setComment("問合せ条件");
		this.addField(new EditFormSelectField().setHtmlFieldType(HtmlFieldType.SELECT)).setComment("データ編集");
		this.addField(new EditTypeSelectField().setHtmlFieldType(HtmlFieldType.SELECT)).setComment("編集対象");

		this.addField(new QueryFormClassNameField());
		this.addField(new OverwriteModeField(ID_QUERY_FORM_CLASS_OVERWRITE_MODE));
		this.addField(new QueryResultFormClassNameField());
		this.addField(new OverwriteModeField(ID_QUERY_RESULT_FORM_CLASS_OVERWRITE_MODE));

		this.addField((new FunctionSelectField(ID_LIST_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_LIST_QUERY_PACKAGE_NAME).setComment("一覧問合せの機能"));
		this.addField((new PackageNameField(ID_LIST_QUERY_PACKAGE_NAME)).setComment("一覧問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_LIST_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_LIST_QUERY_PACKAGE_NAME))
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
		this.addField(new TextField(ID_LIST_QUERY_CONFIG));	// 一覧問合せの設定情報
		this.addField(new EditFormClassNameField());
		this.addField(new OverwriteModeField(ID_EDIT_FORM_CLASS_OVERWRITE_MODE));
		//
		this.addField((new FunctionSelectField(ID_EDIT_QUERY_FUNCTION_SELECT))
			.setPackageFieldId(ID_EDIT_QUERY_PACKAGE_NAME)
			.setComment("単一レコード取得用問合せの機能"));
		this.addField((new PackageNameField(ID_EDIT_QUERY_PACKAGE_NAME))
			.setComment("単一レコード取得用問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_EDIT_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_EDIT_QUERY_PACKAGE_NAME))
			.setCalcEventField(true)
			.setAutocomplete(true)
			.setRelationDataAcquisition(true);
		this.addField(new TextField(ID_EDIT_QUERY_CONFIG));	// 編集対象取得問合せの設定情報
		//
		{
			FieldList flist = new FieldList();
			flist.addField(new FunctionSelectField());
			flist.addField(new PackageNameField());
			flist.addField(new QueryOrTableClassNameField(ID_QUERY_CLASS_NAME))
				.setAutocomplete(true)
				.setRelationDataAcquisition(true);
			flist.addField(new TextField(ID_QUERY_CONFIG));
			EditableHtmlTable list = new EditableHtmlTable(ID_MULTI_RECORD_QUERY_LIST, flist);
			this.addHtmlTable(list);
		}
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
		this.setFormData(ID_PAGE_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
		this.setFormData(ID_DAO_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
		this.setFormData(ID_QUERY_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
		this.setFormData(ID_QUERY_RESULT_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
		this.setFormData(ID_EDIT_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);

		this.setFormData(ID_LIST_FORM_SELECT, "1");
		this.setFormData(ID_QUERY_FORM_SELECT, "1");
		this.setFormData(ID_EDIT_FORM_SELECT, "1");
		this.setFormData(ID_EDIT_TYPE_SELECT, "0");
	}

	// TODO:後で共通化
	/**
	 *
	 * Function.propertiesのパスを取得します。
	 * @param functionPath 機能のパス。
	 * @return Function.propertiesのパス。
	 * @throws Exception 例外。
	 */
	private String getFunctionPropertiesPath(final String functionPath) throws Exception {
		String webResourcePath = DeveloperPage.getWebSourcePath();
		String funcprop = this.getPage().getAppropriatePath(functionPath + "/Function.properties", this.getPage().getRequest());
		if (funcprop == null) {
			funcprop = functionPath + "/Function.properties";
		}
		funcprop = webResourcePath + funcprop;
		return funcprop;
	}

	// TODO:後で共通化
	/**
	 * Function.propertiesを読み込みます。
	 * @param funcprop Function.propertiesのパス。
	 * @return 読み込んだ内容。
	 * @throws Exception 例外。
	 */
	private SequentialProperties readFunctionProperties(final String funcprop) throws Exception {
		String text = "";
		File propfile = new File(funcprop);
		if (propfile.exists()) {
			text = FileUtil.readTextFile(funcprop, DataFormsServlet.getEncoding());
		}
		SequentialProperties prop = new SequentialProperties();
		prop.loadText(text);
		return prop;
	}


	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		String pkg = (String) data.get(ID_PACKAGE_NAME);
		String cls = (String) data.get(ID_PAGE_CLASS_NAME);
		String classname = pkg + "." + cls;
		Class<?> clazz = Class.forName(classname);
		Page p = (Page) clazz.getDeclaredConstructor().newInstance();
		PageClassInfo pi = new PageClassInfo(p);
		// Class<? extends Table> tblcls = pi.getTableClass();
		Class<? extends Dao> daocls = pi.getDaoClass();
		String functionPath = pi.getFunctionPath();
		if (daocls == null || functionPath == null) {
			throw new ApplicationException(this.getPage(), "error.notgeneratedpage");
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.putAll(data);
		ret.put(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
		if (daocls.getName().equals(dataforms.dao.Dao.class.getName())) {
			ret.put(ID_DAO_PACKAGE_NAME, "");
			ret.put(ID_DAO_CLASS_NAME, "");
		} else {
			ret.put(ID_DAO_PACKAGE_NAME, ClassNameUtil.getPackageName(daocls.getName()));
			ret.put(ID_DAO_CLASS_NAME, daocls.getSimpleName());
			Dao dao = daocls.getConstructor().newInstance();
			if (dao instanceof QuerySetDao) {
				this.getQueryInfo(dao, p, ret);
			}
		}
		ret.put(ID_PAGE_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
		ret.put(ID_FUNCTION_SELECT, functionPath);

		String funcprop = this.getFunctionPropertiesPath(functionPath);
		SequentialProperties prop = this.readFunctionProperties(funcprop);
		String name = (String) prop.get(pkg + "." + cls);
		ret.put(ID_PAGE_NAME, name);

		this.getFormInfo(p, ret);

		return ret;
	}

	/**
	 * 一覧取得問合せのフィールド設定を取得する。
	 * @param flist フィールドリスト。
	 * @param qform 問合せフォーム。
	 * @param qrform 問合せ結果フォーム。
	 * @param dao Daoクラスのインスタンス。
	 * @return 一覧取得問合せのフィールド設定。
	 */
	private List<Map<String, Object>> getListQueryFieldConf(final FieldList flist, final QueryForm qform, final QueryResultForm qrform, final QuerySetDao dao) {
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(flist, "");
		if (qform != null) {
			// QueryFormからMatchTypeを取得する。
			FieldList qflist = qform.getFieldList();
			for (Map<String, Object> m: list) {
				String fid = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
				Field<?> qf = qflist.get(fid);
				if (qf == null) {
					qf = qflist.get(fid + "From");
				}
				if (qf == null) {
					m.put(SelectFieldHtmlTable.ID_MATCH_TYPE, MatchType.NONE);
				} else {
					m.put(SelectFieldHtmlTable.ID_MATCH_TYPE, qf.getMatchType());
				}
			}
		}
		if (qrform != null) {
			// 検索結果Formのフィールド表示情報を取得する。
			HtmlTable table = (HtmlTable) qrform.getComponent(Page.ID_QUERY_RESULT);
			if (table != null) {
				FieldList qrflist = table.getFieldList();
				for (Map<String, Object> m: list) {
					String fid = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
					Field<?> qrf = qrflist.get(fid);
					if (qrf == null) {
						m.put(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY, Field.Display.NONE);
					} else {
						m.put(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY, qrf.getQueryResultFormDisplay());
					}
				}
			}
		}
		if (dao != null) {
			// 編集対象を特定するキーを読み込む。
			FieldList keyList = dao.getMultiRecordQueryKeyList();
			if (keyList != null) {
				for (Map<String, Object> m: list) {
					String fid = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
					Field<?> kf = keyList.get(fid);
					if (kf != null) {
						logger.error("keyfield=" + kf.getId() + ", disp=" + m.get(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY));
						Field.Display disp = (Field.Display) m.get(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY);
						if (disp != Field.Display.INPUT_HIDDEN) {
							m.put(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY, Field.Display.INPUT_READONLY);
						}
						m.put(SelectFieldHtmlTable.ID_EDIT_KEY, "1");
					}
				}
			}
		}
		return list;
	}

	/**
	 * 編集対象取得問合せのフィールド設定を取得する。
	 * @param query 問合せ。
	 * @param eform 編集フォーム。
	 * @return 編集問合せのフィールド設定。
	 */
	private List<Map<String, Object>> getEditQueryFieldConf(final Query query, final EditForm eform) {
		FieldList flist = query.getFieldList();
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(flist, "");
		if (eform != null) {
			String tableId = query.getListId();
			FieldList eflist = null;
			WebComponent comp = eform.getComponent(tableId);
			if (comp instanceof HtmlTable) {
				eflist = ((HtmlTable) comp).getFieldList();
			}
			if (eflist == null) {
				eflist = eform.getFieldList();
			}
			for (Map<String, Object> m: list) {
				String fid = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
				Field<?> ef = eflist.get(fid);
				if (ef == null) {
					m.put(SelectFieldHtmlTable.ID_EDIT_FIELD_DISPLAY, Field.Display.NONE);
				} else {
					m.put(SelectFieldHtmlTable.ID_EDIT_FIELD_DISPLAY, ef.getEditFormDisplay());
				}
			}
		}
		return list;
	}

	/**
	 * 問合せ情報を設定する。
	 * @param dao DAOクラスのインスタンス。む
	 * @param p ページクラスのインスタンス。
	 * @param ret フォームに表示するデータマップ。
	 */
	private void getQueryInfo(final Dao dao, final Page p, final Map<String, Object> ret) {
		QuerySetDao querySetDao = (QuerySetDao) dao;
		Query listQuery = querySetDao.getListQuery();
		logger.debug("listQuery package=" + listQuery.getClass().getPackageName());
		logger.debug("listQuery class=" + listQuery.getClass().getName());
		if (listQuery != null) {
			if (listQuery instanceof SingleTableQuery) {
				ret.put(ID_LIST_QUERY_PACKAGE_NAME, ((SingleTableQuery) listQuery).getMainTable().getClass().getPackageName());
				ret.put(ID_LIST_QUERY_CLASS_NAME, ((SingleTableQuery) listQuery).getMainTable().getClass().getSimpleName());
			} else {
				ret.put(ID_LIST_QUERY_PACKAGE_NAME, listQuery.getClass().getPackageName());
				ret.put(ID_LIST_QUERY_CLASS_NAME, listQuery.getClass().getSimpleName());
			}
			FieldList flist = listQuery.getFieldList();
			QueryForm qf = (QueryForm) p.getComponent(Page.ID_QUERY_FORM);
			QueryResultForm qrf = (QueryResultForm) p.getComponent(Page.ID_QUERY_RESULT_FORM);
			List<Map<String, Object>> list = this.getListQueryFieldConf(flist, qf, qrf, querySetDao);
			ret.put(ID_LIST_QUERY_CONFIG, JSON.encode(list));

		}
		Query editQuery = querySetDao.getSingleRecordQuery();
		if (editQuery == null) {
			List<Query> list = querySetDao.getMultiRecordQueryList();
			if (list != null && list.size() > 0) {
				editQuery = list.get(0);
			}
		}
		if (editQuery != null) {
			if (editQuery instanceof SingleTableQuery) {
				ret.put(ID_EDIT_QUERY_PACKAGE_NAME, ((SingleTableQuery) editQuery).getMainTable().getClass().getPackageName());
				ret.put(ID_EDIT_QUERY_CLASS_NAME, ((SingleTableQuery) editQuery).getMainTable().getClass().getSimpleName());
			} else {
				ret.put(ID_EDIT_QUERY_PACKAGE_NAME, editQuery.getClass().getPackageName());
				ret.put(ID_EDIT_QUERY_CLASS_NAME, editQuery.getClass().getSimpleName());
			}
			EditForm ef = (EditForm) p.getComponent(Page.ID_EDIT_FORM);
			List<Map<String, Object>> list = this.getEditQueryFieldConf(editQuery, ef);
			ret.put(ID_EDIT_QUERY_CONFIG, JSON.encode(list));
			List<Query> qlist = querySetDao.getMultiRecordQueryList();
			List<Map<String, Object>> multiRecordQueryList = new ArrayList<Map<String, Object>>();
			if (qlist != null && qlist.size() > 0) {
				for (Query q: qlist) {
					String pkg = q.getClass().getPackageName();
					String cls = q.getClass().getSimpleName();
					if (q instanceof SingleTableQuery) {
						pkg = ((SingleTableQuery) q).getMainTable().getClass().getPackageName();
						cls = ((SingleTableQuery) q).getMainTable().getClass().getSimpleName();
					}
					Map<String, Object> m = new HashMap<String, Object>();
					m.put(ID_PACKAGE_NAME, pkg);
					m.put(ID_QUERY_CLASS_NAME, cls);
					List<Map<String, Object>> mflist = this.getEditQueryFieldConf(q, ef);
					m.put(ID_QUERY_CONFIG, JSON.encode(mflist));
					multiRecordQueryList.add(m);
				}
				ret.put(ID_MULTI_RECORD_QUERY_LIST, multiRecordQueryList);
			}
		}
	}

	/**
	 * Daoのインスタンスを取得します。
	 * @param page ページ。
	 * @return Daoのインスタンス。
	 * @throws Exception 例外。
	 */
	private Dao getDaoInstance(final Page page) throws Exception {
		Method m = page.getClass().getMethod("getDaoClass");
		if (m != null) {
			@SuppressWarnings("unchecked")
			Class<? extends Dao> cl = (Class<? extends Dao>) m.invoke(page);
			return cl.getConstructor().newInstance();
		} else {
			return null;
		}
	}

	/**
	 * フォームの情報を設定する。
	 * @param p ページクラスのインスタンス。
	 * @param ret フォームに表示するデータマップ。
	 */
	private void getFormInfo(final Page p, final Map<String, Object> ret) throws Exception {
		Map<String, WebComponent> fm = p.getFormMap();
		String qf = "0";
		String qrf = "0";
		String ef = "0";

		for (String key: fm.keySet()) {
			logger.debug("*** key=" + key);
			WebComponent cmp = fm.get(key);
			if (cmp != null) {
				if (cmp instanceof QueryForm) {
					ret.put(ID_QUERY_FORM_CLASS_NAME, cmp.getClass().getSimpleName());
					ret.put(ID_QUERY_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
					qf = "1";
				} else if (cmp instanceof QueryResultForm) {
					ret.put(ID_QUERY_RESULT_FORM_CLASS_NAME, cmp.getClass().getSimpleName());
					ret.put(ID_QUERY_RESULT_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
					qrf = "1";
				} else if (cmp instanceof EditForm) {
					ret.put(ID_EDIT_FORM_CLASS_NAME, cmp.getClass().getSimpleName());
					ret.put(ID_EDIT_FORM_CLASS_OVERWRITE_MODE, OverwriteModeField.ERROR);
					ef = "1";
				}
			}
		}
		ret.put(ID_QUERY_FORM_SELECT, qf);
		ret.put(ID_LIST_FORM_SELECT, qrf);
		ret.put(ID_EDIT_FORM_SELECT, ef);
		Dao dao = this.getDaoInstance(p);
		if (dao != null) {
			if ("1".equals(ef)) {
				logger.debug("page dao=" + dao.getClass().getName());
				if (dao instanceof QuerySetDao) {
					String editTypeSelect = "";
					QuerySetDao querySetDao = (QuerySetDao) dao;
					Query sq = querySetDao.getSingleRecordQuery();
					List<Query> mql = querySetDao.getMultiRecordQueryList();
					if (sq != null && mql == null) {
						editTypeSelect = "0";
					} else if (sq != null && mql != null) {
						editTypeSelect = "1";
					} else if (sq == null && mql != null) {
						editTypeSelect = "2";
					}
					ret.put(ID_EDIT_TYPE_SELECT, editTypeSelect);
				}
			}
		}
	}

	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		return false;
	}


	@Override
	public List<ValidationError> validate(final Map<String, Object> param) throws Exception {
		List<ValidationError> ret =  super.validate(param);
		if (ret.size() == 0) {

		}
		return ret;

	}

	@Override
	protected void insertData(Map<String, Object> data) throws Exception {
		QuerySetDaoGenerator gen = new QuerySetDaoGenerator();
		gen.generage(this, data);
	}

	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		// 何もしない
	}

	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		// 何もしない
	}


	@Override
	protected String getSavedMessage(final Map<String, Object> data) {
		return MessagesUtil.getMessage(this.getPage(), "message.javasourcecreated");
	}

	/**
	 * フィールドのデフォルト設定情報を取得する。
	 * @param p パラメータ。
	 * @return フィールドのデフォルト設定情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response getFieldConfig(final Map<String, Object> p) throws Exception  {
		String cls = (String) p.get("c");
		FieldList flist = SelectFieldHtmlTable.getFieldList(cls);
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(flist, "");
		return new JsonResponse(JsonResponse.SUCCESS, list);
	}
}
