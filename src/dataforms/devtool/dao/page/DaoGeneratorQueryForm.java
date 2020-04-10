package dataforms.devtool.dao.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.validator.RequiredValidator;

/**
 * Daoクラス問合せフォーム。
 *
 */
public class DaoGeneratorQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public DaoGeneratorQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new ClassNameField());
	}
}
