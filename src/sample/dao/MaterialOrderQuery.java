package sample.dao;

import dataforms.dao.Query;
import dataforms.field.base.FieldList;



/**
 * 問い合わせクラスです。
 *
 */
public class MaterialOrderQuery extends Query {
	/**
	 * コンストラクタ.
	 */
	public MaterialOrderQuery() {
		this.setComment("発注ヘッダ部取得の問合せ");
		this.setDistinct(false);
		MaterialOrderTable materialOrderTable = new MaterialOrderTable();
		materialOrderTable.setAlias("m");
		SupplierMasterTable supplierMasterTable = new SupplierMasterTable();
		supplierMasterTable.setAlias("j0");

		this.setFieldList(new FieldList(
			materialOrderTable.getOrderIdField()
			, materialOrderTable.getOrderNoField()
			, materialOrderTable.getSupplierIdField()
			, supplierMasterTable.getSupplierCodeField()
			, supplierMasterTable.getSupplierNameField()
			, materialOrderTable.getOrderDateField()
			, materialOrderTable.getMemoField()
			, materialOrderTable.getCreateUserIdField()
			, materialOrderTable.getCreateTimestampField()
			, materialOrderTable.getUpdateUserIdField()
			, materialOrderTable.getUpdateTimestampField()
		));
		this.setMainTable(materialOrderTable);
		this.addInnerJoin(supplierMasterTable);

	}
}