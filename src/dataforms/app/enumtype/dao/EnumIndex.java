package dataforms.app.enumtype.dao;

import dataforms.dao.Index;
import dataforms.field.base.FieldList;

/**
 * EnumTableのインデックス。
 *
 */
public class EnumIndex extends Index {
	/**
	 * コンストラクタ。
	 */
	public EnumIndex() {
		EnumTable table = new EnumTable();
		this.setUnique(true);
		this.setTable(table);
		this.setFieldList(new FieldList(table.getEnumCodeField()));
		this.setViolationMessageKey("error.duplicateenumcode");
	}
}
