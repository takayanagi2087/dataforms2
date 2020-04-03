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
		this.setComment("資材発注明細用の問合せ");
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
			, materialMasterTable.getMaterialCodeField()
			, materialMasterTable.getMaterialNameField()
			, materialMasterTable.getUnitPriceField()
			, materialMasterTable.getMaterialUnitField()
			, materialOrderItemTable.getOrderPriceField()
			, materialOrderItemTable.getAmountField()
			, materialOrderItemTable.getItemMemoField()
			, materialOrderItemTable.getCreateUserIdField()
			, materialOrderItemTable.getCreateTimestampField()
			, materialOrderItemTable.getUpdateUserIdField()
			, materialOrderItemTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderItemTable);
		this.addInnerJoin(materialOrderTable);
		this.addInnerJoin(materialMasterTable);

	}
}