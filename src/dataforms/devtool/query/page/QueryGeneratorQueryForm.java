package dataforms.devtool.query.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryClassNameField;
import dataforms.validator.RequiredValidator;

/**
 * 問い合わせフォームクラス。
 */
public class QueryGeneratorQueryForm extends QueryForm {
	
	/**
	 * コンストラクタ。
	 */
	public QueryGeneratorQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new QueryClassNameField("queryClassName")).setAutocomplete(true).setRelationDataAcquisition(true);
	}
}
