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
	private EnumTable table = null;

	/**
	 * コンストラクタ。
	 */
	public EnumTableQuery() {
		this.table = new EnumTable();
		this.setFieldList(this.table.getFieldList());
		this.setMainTable(this.table);
	}

	/**
	 * コンストラクタ。
	 * @param parentId 列挙型のID。
	 * <pre>
	 * 	nullを指定した場合、列挙型のリストを取得します。
	 * 列挙型のIDを指定した場合、そのオプションのリストを取得します。
	 * </pre>
	 */
	public EnumTableQuery(final Long parentId) {
		this();
		if (parentId != null) {
			this.setCondition("m.parent_id=:parent_id");
			EnumTable.Entity p = new EnumTable.Entity();
			p.setParentId(parentId);
			this.setQueryFormData(p.getMap());
		} else {
			this.setCondition("m.parent_id is null");
		}

	}
}

