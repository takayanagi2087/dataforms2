package dataforms.app.enumtype.dao;

import dataforms.dao.Query;

/**
 * EnumTable用の問合せクラス。
 *
 */
public class EnumTableQuery extends Query {
	/**
	 * コンストラクタ。
	 */
	public EnumTableQuery() {
		EnumTable table = new EnumTable();
		this.setFieldList(table.getFieldList());
		this.setMainTable(table);
	}
}

