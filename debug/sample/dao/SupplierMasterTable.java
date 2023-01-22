package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.SupplierIdField;
import sample.field.SupplierCodeField;
import sample.field.SupplierNameField;
import sample.field.SupplierKanaNameField;
import sample.field.PhoneNoField;
import sample.field.FaxNoField;
import dataforms.field.common.ZipCodeField;
import sample.field.AddressField;


/**
 * 仕入先マスタクラス。
 *
 */
public class SupplierMasterTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterTable() {
		this.setAutoIncrementId(true);
		this.setComment("仕入先マスタ");
		this.addPkField(new SupplierIdField()); //仕入先ID
		this.addField(new SupplierCodeField()); //仕入先コード
		this.addField(new SupplierNameField()); //仕入先名称
		this.addField(new SupplierKanaNameField()); //仕入先カナ名称
		this.addField(new PhoneNoField()); //電話番号
		this.addField(new FaxNoField()); //FAX番号
		this.addField(new ZipCodeField()); //郵便番号
		this.addField(new AddressField()); //住所
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		SupplierMasterTableRelation r = new SupplierMasterTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 仕入先IDのフィールドID。 */
		public static final String ID_SUPPLIER_ID = "supplierId";
		/** 仕入先コードのフィールドID。 */
		public static final String ID_SUPPLIER_CODE = "supplierCode";
		/** 仕入先名称のフィールドID。 */
		public static final String ID_SUPPLIER_NAME = "supplierName";
		/** 仕入先カナ名称のフィールドID。 */
		public static final String ID_SUPPLIER_KANA_NAME = "supplierKanaName";
		/** 電話番号のフィールドID。 */
		public static final String ID_PHONE_NO = "phoneNo";
		/** FAX番号のフィールドID。 */
		public static final String ID_FAX_NO = "faxNo";
		/** 郵便番号のフィールドID。 */
		public static final String ID_ZIP_CODE = "zipCode";
		/** 住所のフィールドID。 */
		public static final String ID_ADDRESS = "address";

		/**
		 * コンストラクタ。
		 */
		public Entity() {

		}
		/**
		 * コンストラクタ。
		 * @param map 操作対象マップ。
		 */
		public Entity(final Map<String, Object> map) {
			super(map);
		}
		/**
		 * 仕入先IDを取得します。
		 * @return 仕入先ID。
		 */
		public java.lang.Long getSupplierId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_SUPPLIER_ID);
		}

		/**
		 * 仕入先IDを設定します。
		 * @param supplierId 仕入先ID。
		 */
		public void setSupplierId(final java.lang.Long supplierId) {
			this.getMap().put(Entity.ID_SUPPLIER_ID, supplierId);
		}

		/**
		 * 仕入先コードを取得します。
		 * @return 仕入先コード。
		 */
		public java.lang.String getSupplierCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_SUPPLIER_CODE);
		}

		/**
		 * 仕入先コードを設定します。
		 * @param supplierCode 仕入先コード。
		 */
		public void setSupplierCode(final java.lang.String supplierCode) {
			this.getMap().put(Entity.ID_SUPPLIER_CODE, supplierCode);
		}

		/**
		 * 仕入先名称を取得します。
		 * @return 仕入先名称。
		 */
		public java.lang.String getSupplierName() {
			return (java.lang.String) this.getMap().get(Entity.ID_SUPPLIER_NAME);
		}

		/**
		 * 仕入先名称を設定します。
		 * @param supplierName 仕入先名称。
		 */
		public void setSupplierName(final java.lang.String supplierName) {
			this.getMap().put(Entity.ID_SUPPLIER_NAME, supplierName);
		}

		/**
		 * 仕入先カナ名称を取得します。
		 * @return 仕入先カナ名称。
		 */
		public java.lang.String getSupplierKanaName() {
			return (java.lang.String) this.getMap().get(Entity.ID_SUPPLIER_KANA_NAME);
		}

		/**
		 * 仕入先カナ名称を設定します。
		 * @param supplierKanaName 仕入先カナ名称。
		 */
		public void setSupplierKanaName(final java.lang.String supplierKanaName) {
			this.getMap().put(Entity.ID_SUPPLIER_KANA_NAME, supplierKanaName);
		}

		/**
		 * 電話番号を取得します。
		 * @return 電話番号。
		 */
		public java.lang.String getPhoneNo() {
			return (java.lang.String) this.getMap().get(Entity.ID_PHONE_NO);
		}

		/**
		 * 電話番号を設定します。
		 * @param phoneNo 電話番号。
		 */
		public void setPhoneNo(final java.lang.String phoneNo) {
			this.getMap().put(Entity.ID_PHONE_NO, phoneNo);
		}

		/**
		 * FAX番号を取得します。
		 * @return FAX番号。
		 */
		public java.lang.String getFaxNo() {
			return (java.lang.String) this.getMap().get(Entity.ID_FAX_NO);
		}

		/**
		 * FAX番号を設定します。
		 * @param faxNo FAX番号。
		 */
		public void setFaxNo(final java.lang.String faxNo) {
			this.getMap().put(Entity.ID_FAX_NO, faxNo);
		}

		/**
		 * 郵便番号を取得します。
		 * @return 郵便番号。
		 */
		public java.lang.String getZipCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_ZIP_CODE);
		}

		/**
		 * 郵便番号を設定します。
		 * @param zipCode 郵便番号。
		 */
		public void setZipCode(final java.lang.String zipCode) {
			this.getMap().put(Entity.ID_ZIP_CODE, zipCode);
		}

		/**
		 * 住所を取得します。
		 * @return 住所。
		 */
		public java.lang.String getAddress() {
			return (java.lang.String) this.getMap().get(Entity.ID_ADDRESS);
		}

		/**
		 * 住所を設定します。
		 * @param address 住所。
		 */
		public void setAddress(final java.lang.String address) {
			this.getMap().put(Entity.ID_ADDRESS, address);
		}


	}
	/**
	 * 仕入先IDフィールドを取得します。
	 * @return 仕入先IDフィールド。
	 */
	public SupplierIdField getSupplierIdField() {
		return (SupplierIdField) this.getField(Entity.ID_SUPPLIER_ID);
	}

	/**
	 * 仕入先コードフィールドを取得します。
	 * @return 仕入先コードフィールド。
	 */
	public SupplierCodeField getSupplierCodeField() {
		return (SupplierCodeField) this.getField(Entity.ID_SUPPLIER_CODE);
	}

	/**
	 * 仕入先名称フィールドを取得します。
	 * @return 仕入先名称フィールド。
	 */
	public SupplierNameField getSupplierNameField() {
		return (SupplierNameField) this.getField(Entity.ID_SUPPLIER_NAME);
	}

	/**
	 * 仕入先カナ名称フィールドを取得します。
	 * @return 仕入先カナ名称フィールド。
	 */
	public SupplierKanaNameField getSupplierKanaNameField() {
		return (SupplierKanaNameField) this.getField(Entity.ID_SUPPLIER_KANA_NAME);
	}

	/**
	 * 電話番号フィールドを取得します。
	 * @return 電話番号フィールド。
	 */
	public PhoneNoField getPhoneNoField() {
		return (PhoneNoField) this.getField(Entity.ID_PHONE_NO);
	}

	/**
	 * FAX番号フィールドを取得します。
	 * @return FAX番号フィールド。
	 */
	public FaxNoField getFaxNoField() {
		return (FaxNoField) this.getField(Entity.ID_FAX_NO);
	}

	/**
	 * 郵便番号フィールドを取得します。
	 * @return 郵便番号フィールド。
	 */
	public ZipCodeField getZipCodeField() {
		return (ZipCodeField) this.getField(Entity.ID_ZIP_CODE);
	}

	/**
	 * 住所フィールドを取得します。
	 * @return 住所フィールド。
	 */
	public AddressField getAddressField() {
		return (AddressField) this.getField(Entity.ID_ADDRESS);
	}


}
