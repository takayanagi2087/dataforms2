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
		this.setDistinct(false);
		MaterialOrderTable materialOrderTable = new MaterialOrderTable();
		SupplierMasterTable supplierMasterTable = new SupplierMasterTable();

		this.setFieldList(new FieldList(
			materialOrderTable.getOrderIdField()
			, materialOrderTable.getOrderNoField()
			, materialOrderTable.getSupplierIdField()
			, materialOrderTable.getOrderDateField()
			, materialOrderTable.getMemoField()
			, materialOrderTable.getCreateUserIdField()
			, materialOrderTable.getCreateTimestampField()
			, materialOrderTable.getUpdateUserIdField()
			, materialOrderTable.getUpdateTimestampField()
			, supplierMasterTable.getSupplierCodeField()
			, supplierMasterTable.getSupplierNameField()
			, supplierMasterTable.getSupplierKanaNameField()
			, supplierMasterTable.getPhoneNoField()
			, supplierMasterTable.getFaxNoField()
			, supplierMasterTable.getZipCodeField()
			, supplierMasterTable.getAddressField()
		));
		this.setMainTable(materialOrderTable);
		this.addInnerJoin(supplierMasterTable);

	}
}