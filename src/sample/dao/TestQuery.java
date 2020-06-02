package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;




/**
 * 問い合わせクラスです。
 *
 */
public class TestQuery extends Query {
	/**
	 * 資材マスタ。
	 */
	private MaterialMasterTable materialMasterTable = null;

	/**
	 * 資材マスタを取得します。
	 * @return 資材マスタ。
	 */
	public MaterialMasterTable getMaterialMasterTable() {
		return this.materialMasterTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestQuery() {
		this.setComment("Test");
		this.setDistinct(false);
		this.materialMasterTable = new MaterialMasterTable();

		this.setFieldList(new FieldList(
			this.materialMasterTable.getMaterialIdField()
			, this.materialMasterTable.getMaterialCodeField()
			, this.materialMasterTable.getMaterialNameField()
			, this.materialMasterTable.getMaterialUnitField()
			, this.materialMasterTable.getUnitPriceField()
			, this.materialMasterTable.getOrderPointField()
			, this.materialMasterTable.getMemoField()
		));
		this.setMainTable(materialMasterTable);

	}
}