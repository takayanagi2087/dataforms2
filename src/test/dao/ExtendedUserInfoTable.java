package test.dao;

import java.util.Map;

import dataforms.app.user.dao.UserInfoTable;
import dataforms.debug.user.field.AddressField;
import dataforms.field.common.ZipCodeField;

/**
 * 拡張ユーザ情報テーブルクラス。
 *
 */
public class ExtendedUserInfoTable extends UserInfoTable {
	/**
	 * コンストラクタ。
	 */
	public ExtendedUserInfoTable() {
		this.addField(new ZipCodeField()); //郵便番号
		this.addField(new AddressField()); //住所
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends UserInfoTable.Entity {
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
