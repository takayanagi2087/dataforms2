package dataforms.devtool.db.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.validator.RequiredValidator;

/**
 * DB管理ページの検索条件フォームクラス。
 *
 */
public class TableManagementQueryForm extends QueryForm {
    /**
     * Logger.
     */
//    private static Logger log = Logger.getLogger(TableManagementQueryForm.class.getName());

    /**
	 * コンストラクタ.
	 */
	public TableManagementQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new ClassNameField());
	}
}
