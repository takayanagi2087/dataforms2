package dataforms.devtool.dao.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.FieldSingleSelectField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryClassNameField;
import dataforms.devtool.field.QueryTypeField;
import dataforms.field.base.FieldList;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.validator.RequiredValidator;

public class DaoGeneratorEditForm extends EditForm {
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
	 * 主問合せの機能選択フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_FUNCTION_SELECT = "singleRecordQueryFunctionSelect";
	/**
	 * 主問合せのパッケージ名フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_PACKAGE_NAME = "singleRecordQueryPackageName";
	/**
	 * 主問合せのクラス名フィールドID。
	 */
	public static final String ID_SINGLE_RECORD_QUERY_CLASS_NAME = "singleRecordQueryClassName";
	/**
	 * キーフィールドリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_KEY_LIST = "multiRecordQueryKeyList";
	/**
	 * 関連問合せリストID。
	 */
	public static final String ID_MULTI_RECORD_QUERY_LIST = "multiRecordQueryList";
	/**
	 * javaソースパスフィールドID。
	 */
	public static final String ID_JAVA_SOURCE_PATH = "javaSourcePath";

	/**
	 * コンストラクタ。
	 */
	public DaoGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		this.addField(new QueryTypeField()).setComment("問合せパターン");
		this.addField(new OverwriteModeField(ID_OVERWRITE_MODE)).setComment("上書きモード");
		FunctionSelectField funcField = new FunctionSelectField();
		funcField.setPackageOption("dao");
		funcField.setCalcEventField(true);
		this.addField(funcField);
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new DaoClassNameField()).addValidator(new RequiredValidator());
		//
		this.addField((new FunctionSelectField(ID_LIST_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_LIST_QUERY_PACKAGE_NAME).setComment("一覧問合せの機能"));
		this.addField((new PackageNameField(ID_LIST_QUERY_PACKAGE_NAME)).setComment("一覧問合せのパッケージ").addValidator(new RequiredValidator()));
		this.addField((new QueryClassNameField(ID_LIST_QUERY_CLASS_NAME)).setPackageNameFieldId(ID_LIST_QUERY_PACKAGE_NAME));
		//
		this.addField((new FunctionSelectField(ID_SINGLE_RECORD_QUERY_FUNCTION_SELECT)).setPackageFieldId(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME).setComment("単一レコード取得用問合せの機能"));
		this.addField((new PackageNameField(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME)).setComment("単一レコード取得用問合せのパッケージ").addValidator(new RequiredValidator()));
		this.addField((new QueryClassNameField(ID_SINGLE_RECORD_QUERY_CLASS_NAME)).setPackageNameFieldId(ID_SINGLE_RECORD_QUERY_PACKAGE_NAME));
		//
		{
			FieldList flist = new FieldList();
			flist.addField(new FieldSingleSelectField());
			EditableHtmlTable list = new EditableHtmlTable(ID_MULTI_RECORD_QUERY_KEY_LIST, flist);
			this.addHtmlTable(list);
		}
		//
		{
			FieldList flist = new FieldList();
			flist.addField(new FunctionSelectField());
			flist.addField(new PackageNameField());
			flist.addField(new QueryClassNameField());
			EditableHtmlTable list = new EditableHtmlTable(ID_MULTI_RECORD_QUERY_LIST, flist);
			this.addHtmlTable(list);
		}
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
	}


	@Override
	protected Map<String, Object> queryData(Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	protected boolean isUpdate(Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	protected void insertData(Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void updateData(Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void deleteData(Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
