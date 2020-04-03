package sample.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderListQuery extends Query {
	/**
	 * コンストラクタ.
	 */
	public MaterialOrderListQuery() {
		this.setComment("資材発注一覧用問合せ");
		this.setDistinct(false);
		MaterialOrderItemTable materialOrderItemTable = new MaterialOrderItemTable();
		materialOrderItemTable.setAlias("m");
		MaterialMasterTable materialMasterTable = new MaterialMasterTable();
		materialMasterTable.setAlias("j0");
		MaterialOrderTable materialOrderTable = new MaterialOrderTable();
		materialOrderTable.setAlias("j1");
		SupplierMasterTable supplierMasterTable = new SupplierMasterTable();
		supplierMasterTable.setAlias("j2");

		this.setFieldList(new FieldList(
			materialOrderItemTable.getOrderItemIdField()
			, materialOrderItemTable.getOrderIdField()
			, materialOrderTable.getOrderNoField()
			, materialOrderTable.getOrderDateField()
			, materialOrderItemTable.getSortOrderField()
			, materialOrderItemTable.getMaterialIdField()
			, materialMasterTable.getMaterialCodeField()
			, materialMasterTable.getMaterialNameField()
			, materialOrderItemTable.getOrderPriceField()
			, materialOrderItemTable.getAmountField()
			, materialOrderItemTable.getItemMemoField()
			, materialOrderTable.getSupplierIdField()
			, supplierMasterTable.getSupplierCodeField()
			, supplierMasterTable.getSupplierNameField()
			, materialOrderItemTable.getCreateUserIdField()
			, materialOrderItemTable.getCreateTimestampField()
			, materialOrderItemTable.getUpdateUserIdField()
			, materialOrderItemTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderItemTable);
		this.addInnerJoin(materialMasterTable);
		this.addInnerJoin(materialOrderTable);
		this.addInnerJoin(supplierMasterTable);

	}
}