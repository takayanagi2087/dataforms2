package test.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;

/**
 * Union test。
 *
 */
public class Union2Query extends Query {
	/**
	 * コンストラクタ。
	 */
	public Union2Query() {
		SqlTypeTable table = new SqlTypeTable();
		FieldList flist = new FieldList();
		flist.addAll(table.getFieldList());
		flist.remove(SqlTypeTable.Entity.ID_SQL_CLOB);
		this.setFieldList(flist);
		this.setMainTable(table);
		this.setCondition("m.sql_char=:sql_char");
	}
}
