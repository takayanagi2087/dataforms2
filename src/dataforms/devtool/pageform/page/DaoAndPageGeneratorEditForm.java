package dataforms.devtool.pageform.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.devtool.base.page.DeveloperPage;
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
import dataforms.field.base.FieldList;
import dataforms.field.common.SingleSelectField.HtmlFieldType;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.util.MessagesUtil;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

/**
 * ページ作成フォームクラス。
 *
 */
public class DaoAndPageGeneratorEditForm extends EditForm {


	/**
	 * JavaソースパスフィールドID。
	 */
	private static final String ID_JAVA_SOURCE_PATH = "javaSourcePath";

	/**
	 * 上書きモードフィールドID。
	 */
	private static final String ID_PAGE_CLASS_OVERWRITE_MODE = "pageClassOverwriteMode";

	/**
	 * 上書きモードフィールドID。
	 */
	private static final String ID_DAO_CLASS_OVERWRITE_MODE = "daoClassOverwriteMode";

	/**
	 * 問合せ結果フォーム上書きモードフィールドID。
	 */
	private static final String ID_QUERY_RESULT_FORM_CLASS_OVERWRITE_MODE = "queryResultFormClassOverwriteMode";

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
	 * 問合せフォーム上書きモードフィールドID。
	 */
	private static final String ID_QUERY_FORM_CLASS_OVERWRITE_MODE = "queryFormClassOverwriteMode";
	/**
	 * 編集フォーム上書きモードフィールドID。
	 */
	private static final String ID_EDIT_FORM_CLASS_OVERWRITE_MODE = "editFormClassOverwriteMode";
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
	public DaoAndPageGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		FunctionSelectField funcField = new FunctionSelectField();
		funcField.setPackageOption("page", "dao");
		funcField.setPackageFieldId("packageName", "daoPackageName");
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
		this.addField(new ListFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("問合せ結果");
		this.addField(new QueryFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("問合せ条件");
		this.addField(new EditFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("データ編集");
		this.addField(new EditTypeSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("編集対象");

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

		this.addField(new EditFormClassNameField());
		this.addField(new OverwriteModeField(ID_EDIT_FORM_CLASS_OVERWRITE_MODE));

		//
		this.addField((new FunctionSelectField(ID_EDIT_FORM_QUERY_FUNCTION_SELECT))
			.setPackageFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME)
			.setComment("単一レコード取得用問合せの機能"));
		this.addField((new PackageNameField(ID_EDIT_FORM_QUERY_PACKAGE_NAME))
			.setComment("単一レコード取得用問合せのパッケージ"));
		this.addField((new QueryOrTableClassNameField(ID_EDIT_FORM_QUERY_CLASS_NAME))
			.setPackageNameFieldId(ID_EDIT_FORM_QUERY_PACKAGE_NAME))
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
			SelectFieldHtmlTable fieldList = new SelectFieldHtmlTable(ID_KEY_FIELD_LIST, true);
			this.addHtmlTable(fieldList);
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

		this.setFormData("listFormSelect", "1");
		this.setFormData("queryFormSelect", "1");
		this.setFormData("editFormSelect", "1");
		this.setFormData("editTypeSelect", "0");
	}


	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		return ret;
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
		// TODO 自動生成されたメソッド・スタブ

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
}
