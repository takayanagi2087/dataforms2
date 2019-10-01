package dataforms.devtool.table.page;

import dataforms.controller.QueryForm;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.TableClassNameField;



/**
 * 問い合わせフォームクラス。
 */
public class TableDataQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public TableDataQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField());
		this.addField(new TableClassNameField("tableClassName")).setAutocomplete(true).setCalcEventField(true);
	}
}
