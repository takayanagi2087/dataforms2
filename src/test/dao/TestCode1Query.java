package test.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;



/**
 * 問い合わせクラスです。
 *
 */
public class TestCode1Query extends Query {
	/**
	 * 複数レコード編集テストテーブル。
	 */
	private TestMultiRecTable testMultiRecTable = null;

	/**
	 * 複数レコード編集テストテーブルを取得します。
	 * @return 複数レコード編集テストテーブル。
	 */
	public TestMultiRecTable getTestMultiRecTable() {
		return this.testMultiRecTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestCode1Query() {
		this.setComment("code1一覧の問合せ");
		this.setDistinct(true);
		this.testMultiRecTable = new TestMultiRecTable();
		this.testMultiRecTable.setAlias("m");

		this.setFieldList(new FieldList(
			this.testMultiRecTable.getCode1Field()
		));
		this.setMainTable(testMultiRecTable);

	}
}