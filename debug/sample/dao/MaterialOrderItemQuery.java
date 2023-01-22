package sample.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderItemQuery extends Query {
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
	public MaterialOrderItemQuery() {
		this.setComment("資材発注明細用の問合せ");
		this.setDistinct(false);
		this.materialOrderItemTable = new MaterialOrderItemTable();
		this.materialOrderItemTable.setAlias("m");
		this.materialOrderTable = new MaterialOrderTable();
		this.materialOrderTable.setAlias("j0");
		this.materialMasterTable = new MaterialMasterTable();
		this.materialMasterTable.setAlias("j1");

		this.setFieldList(new FieldList(
			this.materialOrderItemTable.getOrderItemIdField()
			, this.materialOrderItemTable.getOrderIdField()
			, this.materialOrderItemTable.getSortOrderField()
			, this.materialOrderItemTable.getMaterialIdField()
			, this.materialMasterTable.getMaterialCodeField()
			, this.materialMasterTable.getMaterialNameField()
			, this.materialMasterTable.getUnitPriceField()
			, this.materialMasterTable.getMaterialUnitField()
			, this.materialOrderItemTable.getOrderPriceField()
			, this.materialOrderItemTable.getAmountField()
			, this.materialOrderItemTable.getItemMemoField()
			, this.materialOrderItemTable.getCreateUserIdField()
			, this.materialOrderItemTable.getCreateTimestampField()
			, this.materialOrderItemTable.getUpdateUserIdField()
			, this.materialOrderItemTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderItemTable);
		this.addInnerJoin(materialOrderTable);
		this.addInnerJoin(materialMasterTable);

	}
}