package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.field.SupplierKanaNameField;
import sample.field.SupplierCodeField;
import sample.dao.SupplierMasterTable;
import sample.field.FaxNoField;
import sample.field.AddressField;
import sample.field.SupplierNameField;
import dataforms.field.common.ZipCodeField;
import sample.field.PhoneNoField;



/**
 * 問い合わせフォームクラス。
 */
public class SupplierMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterQueryForm() {
		this.addField(new SupplierCodeField(SupplierMasterTable.Entity.ID_SUPPLIER_CODE)).setMatchType(MatchType.FULL).setComment("仕入先コード");
		this.addField(new SupplierNameField(SupplierMasterTable.Entity.ID_SUPPLIER_NAME)).setMatchType(MatchType.FULL).setComment("仕入先名称");
		this.addField(new SupplierKanaNameField(SupplierMasterTable.Entity.ID_SUPPLIER_KANA_NAME)).setMatchType(MatchType.FULL).setComment("仕入先カナ名称");
		this.addField(new PhoneNoField(SupplierMasterTable.Entity.ID_PHONE_NO)).setMatchType(MatchType.FULL).setComment("電話番号");
		this.addField(new FaxNoField(SupplierMasterTable.Entity.ID_FAX_NO)).setMatchType(MatchType.FULL).setComment("FAX番号");
		this.addField(new ZipCodeField(SupplierMasterTable.Entity.ID_ZIP_CODE)).setMatchType(MatchType.FULL).setComment("郵便番号");
		this.addField(new AddressField(SupplierMasterTable.Entity.ID_ADDRESS)).setMatchType(MatchType.FULL).setComment("住所");

	}
}
