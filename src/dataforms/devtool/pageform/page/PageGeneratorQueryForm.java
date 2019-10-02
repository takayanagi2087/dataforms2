package dataforms.devtool.pageform.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.validator.RequiredValidator;

/**
 * ページクラス検索フォーム。
 *
 */
public class PageGeneratorQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public PageGeneratorQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new ClassNameField());
	}
}
