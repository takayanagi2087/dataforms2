package dataforms.app.enumtype.dao;

import dataforms.dao.Query;

/**
 * 列挙型名称の問合せ。
 *
 */
public class EnumNameQuery extends Query {
	/**
	 * コンストラクタ。
	 */
	public EnumNameQuery() {
		EnumNameTable table = new EnumNameTable();
		this.setFieldList(table.getFieldList());
		this.setMainTable(table);
	}

	/**
	 * コンストラクタ。
	 * @param enumId 列挙型ID。
	 */
	public EnumNameQuery(final Long enumId) {
		this();
		this.setCondition("m.enum_id=:enum_id");
		EnumNameTable.Entity p = new EnumNameTable.Entity();
		p.setEnumId(enumId);
		this.setQueryFormData(p.getMap());
	}
}
