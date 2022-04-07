package test.dao;

import dataforms.dao.Query;
import dataforms.dao.SubQuery;
import dataforms.field.base.Field.SortOrder;
import dataforms.field.base.FieldList;

/**
 * Unionをサブクエリにした問合せ。
 *
 */
public class UnionSubQuery extends Query {
	/**
	 * コンストラクタ。
	 */
	public UnionSubQuery() {
		SubQuery sq = new Union1Query().createSubQuery();
		this.setFieldList(sq.getFieldList());
		this.setMainTable(sq);
		this.setOrderByFieldList(new FieldList(sq.getField(SqlTypeTable.Entity.ID_SQL_TYPE_ID).setSortOrder(SortOrder.DESC)));
	}
}
