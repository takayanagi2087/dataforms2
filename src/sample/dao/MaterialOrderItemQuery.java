package sample.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderItemQuery extends Query {
	/**
	 * コンストラクタ.
	 */
	public MaterialOrderItemQuery() {
		this.setDistinct(false);
		MaterialOrderItemTable materialOrderItemTable = new MaterialOrderItemTable();
		materialOrderItemTable.setAlias("m");
		MaterialOrderTable materialOrderTable = new MaterialOrderTable();
		materialOrderTable.setAlias("j0");
		MaterialMasterTable materialMasterTable = new MaterialMasterTable();
		materialMasterTable.setAlias("j1");

		this.setFieldList(new FieldList(
			materialOrderItemTable.getOrderItemIdField()
			, materialOrderItemTable.getOrderIdField()
			, materialOrderItemTable.getSortOrderField()
			, materialOrderItemTable.getMaterialIdField()
			, materialOrderItemTable.getOrderPriceField()
			, materialOrderItemTable.getAmountField()
			, materialOrderItemTable.getItemMemoField()
			, materialOrderItemTable.getCreateUserIdField()
			, materialOrderItemTable.getCreateTimestampField()
			, materialOrderItemTable.getUpdateUserIdField()
			, materialOrderItemTable.getUpdateTimestampField()
			, materialOrderTable.getOrderNoField()
			, materialOrderTable.getSupplierIdField()
			, materialOrderTable.getOrderDateField()
			, materialOrderTable.getMemoField()
			, materialMasterTable.getMaterialCodeField()
			, materialMasterTable.getMaterialNameField()
			, materialMasterTable.getMaterialUnitField()
			, materialMasterTable.getUnitPriceField()
			, materialMasterTable.getOrderPointField()
		));
		this.setMainTable(materialOrderItemTable);
		this.addInnerJoin(materialOrderTable);
		this.addInnerJoin(materialMasterTable);

	}
}