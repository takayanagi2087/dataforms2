package pagepat.page;

import dataforms.controller.QueryForm;
import pagepat.dao.TestTable;

/**
 * 問合せフォームクラス。
 */
public class Test07QueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public Test07QueryForm() {
		TestTable table = new TestTable();
		this.addField(table.getCode1Field());
	}
}
