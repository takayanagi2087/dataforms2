package dataforms.devtool.table.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.validator.RequiredValidator;

/**
 * 問い合わせフォームクラス。
 */
public class TableGeneratorQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public TableGeneratorQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new ClassNameField());
	}
}
