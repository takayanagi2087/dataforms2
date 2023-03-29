package test.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;

/**
 * Union test。
 *
 */
public class Union1Query extends Query {
	/**
	 * コンストラクタ。
	 */
	public Union1Query() {
		SqlTypeTable table = new SqlTypeTable();
		FieldList flist = new FieldList();
		flist.addAll(table.getFieldList());
		flist.remove(SqlTypeTable.Entity.ID_SQL_CLOB);
		this.setFieldList(flist);
		this.setMainTable(table);
		this.setCondition("m.sql_type_id=1");
		this.addUnionQuery(new Union2Query());
	}
}
