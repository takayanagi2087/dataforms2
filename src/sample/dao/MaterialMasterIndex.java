package sample.dao;

import dataforms.dao.Index;
import dataforms.field.base.FieldList;

/**
 * 資材コードインデックス。
 *
 */
public class MaterialMasterIndex extends Index {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterIndex() {
		this.setUnique(true);
		MaterialMasterTable table = new MaterialMasterTable();
		this.setTable(table);
		this.setFieldList(new FieldList(table.getMaterialCodeField()));
		this.setViolationMessageKey("error.duplicatematerialcode");
	}
}
