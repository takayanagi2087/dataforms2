package dataforms.devtool.pageform.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.EditFormSelectField;
import dataforms.devtool.field.EditTypeSelectField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JavaSourcePathField;
import dataforms.devtool.field.ListFormSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.PageClassNameField;
import dataforms.devtool.field.PageNameField;
import dataforms.devtool.field.QueryFormSelectField;
import dataforms.field.common.SingleSelectField.HtmlFieldType;
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
	 * コンストラクタ。
	 */
	public DaoAndPageGeneratorEditForm() {
		this.addField(new JavaSourcePathField());
		FunctionSelectField funcField = new FunctionSelectField();
		funcField.setPackageOption("page", "dao");
		funcField.setPackageFieldId("packageName", "daoPackageName");
		funcField.setCalcEventField(true);
		this.addField(funcField);
		this.addField(new PageNameField()).addValidator(new RequiredValidator());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator()).setComment("ページパッケージ名");
		this.addField(new PageClassNameField())	.addValidator(new RequiredValidator()).setCalcEventField(true).setAutocomplete(false);
		this.addField(new PackageNameField("daoPackageName")).addValidator(new RequiredValidator()).setComment("DAOパッケージ名");
		this.addField(new DaoClassNameField()).addValidator(new RequiredValidator()).setComment("DAOクラス名");
		this.addField(new ListFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("問合せ結果");
		this.addField(new QueryFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("問合せ条件");
		this.addField(new EditFormSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("データ編集");
		this.addField(new EditTypeSelectField().setHtmlFieldType(HtmlFieldType.RADIO)).setComment("編集対象");

	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData(ID_JAVA_SOURCE_PATH, DeveloperPage.getJavaSourcePath());
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
