package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderQuery extends Query {
	/**
	 * 発注テーブル。
	 */
	private MaterialOrderTable materialOrderTable = null;

	/**
	 * 発注テーブルを取得します。
	 * @return 発注テーブル。
	 */
	public MaterialOrderTable getMaterialOrderTable() {
		return this.materialOrderTable;
	}

	/**
	 * 仕入先マスタ。
	 */
	private SupplierMasterTable supplierMasterTable = null;

	/**
	 * 仕入先マスタを取得します。
	 * @return 仕入先マスタ。
	 */
	public SupplierMasterTable getSupplierMasterTable() {
		return this.supplierMasterTable;
	}


	/**
	 * コンストラクタ.
	 */
	public MaterialOrderQuery() {
		this.setComment("発注ヘッダ部取得の問合せ");
		this.setDistinct(false);
		this.materialOrderTable = new MaterialOrderTable();
		this.materialOrderTable.setAlias("m");
		this.supplierMasterTable = new SupplierMasterTable();
		this.supplierMasterTable.setAlias("j0");

		this.setFieldList(new FieldList(
			this.materialOrderTable.getOrderIdField()
			, this.materialOrderTable.getOrderNoField()
			, this.materialOrderTable.getSupplierIdField()
			, this.supplierMasterTable.getSupplierCodeField()
			, this.supplierMasterTable.getSupplierNameField()
			, this.materialOrderTable.getOrderDateField()
			, this.materialOrderTable.getMemoField()
			, this.materialOrderTable.getCreateUserIdField()
			, this.materialOrderTable.getCreateTimestampField()
			, this.materialOrderTable.getUpdateUserIdField()
			, this.materialOrderTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderTable);
		this.addInnerJoin(supplierMasterTable);

	}
}