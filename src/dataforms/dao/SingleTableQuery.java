package dataforms.dao;

/**
 * 単一テーブルの問合せ。
 *
 */
public class SingleTableQuery extends Query {
	/**
	 * コンストラクタ。
	 * @param table テーブル。
	 */
	public SingleTableQuery(final Table table) {
		this.setFieldList(table.getFieldList());
		this.setMainTable(table);
	}
}
