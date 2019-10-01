package dataforms.debug.alltype.dao;

import dataforms.dao.Index;
import dataforms.field.base.FieldList;

/**
 * AllTypeTableのインデックス。
 *
 */
public class AllTypeIndex extends Index {
	/**
	 * コンストラクタ。
	 */
	public AllTypeIndex() {
		AllTypeTable table = new AllTypeTable();
		this.setUnique(false);
		this.setTable(table);
		this.setFieldList(new FieldList(
			table.getField("charField")
			, table.getField("varcharField")
		));
	}

}
