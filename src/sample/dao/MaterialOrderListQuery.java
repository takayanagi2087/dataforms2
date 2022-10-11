package sample.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderListQuery extends Query {
	/**
	 * 資材発注テーブル。
	 */
	private MaterialOrderItemTable materialOrderItemTable = null;

	/**
	 * 資材発注テーブルを取得します。
	 * @return 資材発注テーブル。
	 */
	public MaterialOrderItemTable getMaterialOrderItemTable() {
		return this.materialOrderItemTable;
	}

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
	public MaterialOrderListQuery() {
		this.setComment("資材発注一覧用問合せ");
		this.setDistinct(false);
		this.materialOrderItemTable = new MaterialOrderItemTable();
		this.materialOrderItemTable.setAlias("m");
		this.materialMasterTable = new MaterialMasterTable();
		this.materialMasterTable.setAlias("j0");
		this.materialOrderTable = new MaterialOrderTable();
		this.materialOrderTable.setAlias("j1");
		this.supplierMasterTable = new SupplierMasterTable();
		this.supplierMasterTable.setAlias("j2");

		this.setFieldList(new FieldList(
			this.materialOrderItemTable.getOrderItemIdField()
			, this.materialOrderItemTable.getOrderIdField()
			, this.materialOrderTable.getOrderNoField()
			, this.materialOrderTable.getOrderDateField()
			, this.materialOrderItemTable.getSortOrderField()
			, this.materialOrderItemTable.getMaterialIdField()
			, this.materialMasterTable.getMaterialCodeField()
			, this.materialMasterTable.getMaterialNameField()
			, this.materialOrderItemTable.getOrderPriceField()
			, this.materialOrderItemTable.getAmountField()
			, this.materialOrderItemTable.getItemMemoField()
			, this.materialOrderTable.getSupplierIdField()
			, this.supplierMasterTable.getSupplierCodeField()
			, this.supplierMasterTable.getSupplierNameField()
			, this.materialOrderItemTable.getCreateUserIdField()
			, this.materialOrderItemTable.getCreateTimestampField()
			, this.materialOrderItemTable.getUpdateUserIdField()
			, this.materialOrderItemTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderItemTable);
		this.addLeftJoin(materialMasterTable);
		this.addLeftJoin(materialOrderTable);
		this.addLeftJoin(supplierMasterTable);

	}
}