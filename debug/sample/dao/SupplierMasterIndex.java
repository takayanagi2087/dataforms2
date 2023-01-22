package sample.dao;

import dataforms.dao.Index;
import dataforms.field.base.FieldList;

/**
 * 資材コードインデックス。
 *
 */
public class SupplierMasterIndex extends Index {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterIndex() {
		this.setUnique(true);
		SupplierMasterTable table = new SupplierMasterTable();
		this.setTable(table);
		this.setFieldList(new FieldList(table.getSupplierCodeField()));
		this.setViolationMessageKey("error.duplicatesuppliercode");
	}
}
