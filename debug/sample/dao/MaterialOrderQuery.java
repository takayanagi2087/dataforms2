package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import sample.field.SupplierCodeField;
import sample.field.SupplierIdField;
import dataforms.field.common.CreateTimestampField;
import sample.field.SupplierNameField;
import sample.field.OrderDateField;
import dataforms.field.common.UpdateUserIdField;
import dataforms.field.common.CreateUserIdField;
import sample.field.MemoField;
import dataforms.util.NumberUtil;
import java.util.Map;
import sample.field.OrderNoField;
import dataforms.field.common.UpdateTimestampField;
import sample.field.OrderIdField;



/**
 * 発注ヘッダ部取得の問合せクラスです。
 *
 */
public class MaterialOrderQuery extends Query {
	/**
	 * 発注テーブルを取得します。
	 * @return 発注テーブル。
	 */
	public MaterialOrderTable getMaterialOrderTable() {
		return (MaterialOrderTable) this.getTable(MaterialOrderTable.class, "m");
	}

	/**
	 * 仕入先マスタを取得します。
	 * @return 仕入先マスタ。
	 */
	public SupplierMasterTable getSupplierMasterTable() {
		return (SupplierMasterTable) this.getTable(SupplierMasterTable.class, "j0");
	}


	/**
	 * コンストラクタ.
	 */
	public MaterialOrderQuery() 	{
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

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 発注IDのフィールドID。 */
		public static final String ID_ORDER_ID = "orderId";
		/** 発注番号のフィールドID。 */
		public static final String ID_ORDER_NO = "orderNo";
		/** 仕入先IDのフィールドID。 */
		public static final String ID_SUPPLIER_ID = "supplierId";
		/** 仕入先コードのフィールドID。 */
		public static final String ID_SUPPLIER_CODE = "supplierCode";
		/** 仕入先名称のフィールドID。 */
		public static final String ID_SUPPLIER_NAME = "supplierName";
		/** 発注日のフィールドID。 */
		public static final String ID_ORDER_DATE = "orderDate";
		/** メモのフィールドID。 */
		public static final String ID_MEMO = "memo";
		/** 作成者IDのフィールドID。 */
		public static final String ID_CREATE_USER_ID = "createUserId";
		/** 作成日時のフィールドID。 */
		public static final String ID_CREATE_TIMESTAMP = "createTimestamp";
		/** 更新者IDのフィールドID。 */
		public static final String ID_UPDATE_USER_ID = "updateUserId";
		/** 更新日時のフィールドID。 */
		public static final String ID_UPDATE_TIMESTAMP = "updateTimestamp";

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
		 * 発注IDを取得します。
		 * @return 発注ID。
		 */
		public java.lang.Long getOrderId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_ORDER_ID));
		}

		/**
		 * 発注IDを設定します。
		 * @param orderId 発注ID。
		 */
		public void setOrderId(final java.lang.Long orderId) {
			this.getMap().put(Entity.ID_ORDER_ID, orderId);
		}

		/**
		 * 発注番号を取得します。
		 * @return 発注番号。
		 */
		public java.lang.String getOrderNo() {
			return (java.lang.String) this.getMap().get(Entity.ID_ORDER_NO);
		}

		/**
		 * 発注番号を設定します。
		 * @param orderNo 発注番号。
		 */
		public void setOrderNo(final java.lang.String orderNo) {
			this.getMap().put(Entity.ID_ORDER_NO, orderNo);
		}

		/**
		 * 仕入先IDを取得します。
		 * @return 仕入先ID。
		 */
		public java.lang.Long getSupplierId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_SUPPLIER_ID));
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
		 * 発注日を取得します。
		 * @return 発注日。
		 */
		public java.sql.Date getOrderDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_ORDER_DATE);
		}

		/**
		 * 発注日を設定します。
		 * @param orderDate 発注日。
		 */
		public void setOrderDate(final java.sql.Date orderDate) {
			this.getMap().put(Entity.ID_ORDER_DATE, orderDate);
		}

		/**
		 * メモを取得します。
		 * @return メモ。
		 */
		public java.lang.String getMemo() {
			return (java.lang.String) this.getMap().get(Entity.ID_MEMO);
		}

		/**
		 * メモを設定します。
		 * @param memo メモ。
		 */
		public void setMemo(final java.lang.String memo) {
			this.getMap().put(Entity.ID_MEMO, memo);
		}

		/**
		 * 作成者IDを取得します。
		 * @return 作成者ID。
		 */
		public java.lang.Long getCreateUserId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_CREATE_USER_ID));
		}

		/**
		 * 作成者IDを設定します。
		 * @param createUserId 作成者ID。
		 */
		public void setCreateUserId(final java.lang.Long createUserId) {
			this.getMap().put(Entity.ID_CREATE_USER_ID, createUserId);
		}

		/**
		 * 作成日時を取得します。
		 * @return 作成日時。
		 */
		public java.sql.Timestamp getCreateTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_CREATE_TIMESTAMP);
		}

		/**
		 * 作成日時を設定します。
		 * @param createTimestamp 作成日時。
		 */
		public void setCreateTimestamp(final java.sql.Timestamp createTimestamp) {
			this.getMap().put(Entity.ID_CREATE_TIMESTAMP, createTimestamp);
		}

		/**
		 * 更新者IDを取得します。
		 * @return 更新者ID。
		 */
		public java.lang.Long getUpdateUserId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_UPDATE_USER_ID));
		}

		/**
		 * 更新者IDを設定します。
		 * @param updateUserId 更新者ID。
		 */
		public void setUpdateUserId(final java.lang.Long updateUserId) {
			this.getMap().put(Entity.ID_UPDATE_USER_ID, updateUserId);
		}

		/**
		 * 更新日時を取得します。
		 * @return 更新日時。
		 */
		public java.sql.Timestamp getUpdateTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_UPDATE_TIMESTAMP);
		}

		/**
		 * 更新日時を設定します。
		 * @param updateTimestamp 更新日時。
		 */
		public void setUpdateTimestamp(final java.sql.Timestamp updateTimestamp) {
			this.getMap().put(Entity.ID_UPDATE_TIMESTAMP, updateTimestamp);
		}


	}

	/**
	 * 発注IDフィールドを取得します。
	 * @return 発注IDフィールド。
	 */
	public OrderIdField getOrderIdField() {
		return (OrderIdField) this.getField(Entity.ID_ORDER_ID);
	}

	/**
	 * 発注番号フィールドを取得します。
	 * @return 発注番号フィールド。
	 */
	public OrderNoField getOrderNoField() {
		return (OrderNoField) this.getField(Entity.ID_ORDER_NO);
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
	 * 発注日フィールドを取得します。
	 * @return 発注日フィールド。
	 */
	public OrderDateField getOrderDateField() {
		return (OrderDateField) this.getField(Entity.ID_ORDER_DATE);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}

	/**
	 * 作成者IDフィールドを取得します。
	 * @return 作成者IDフィールド。
	 */
	public CreateUserIdField getCreateUserIdField() {
		return (CreateUserIdField) this.getField(Entity.ID_CREATE_USER_ID);
	}

	/**
	 * 作成日時フィールドを取得します。
	 * @return 作成日時フィールド。
	 */
	public CreateTimestampField getCreateTimestampField() {
		return (CreateTimestampField) this.getField(Entity.ID_CREATE_TIMESTAMP);
	}

	/**
	 * 更新者IDフィールドを取得します。
	 * @return 更新者IDフィールド。
	 */
	public UpdateUserIdField getUpdateUserIdField() {
		return (UpdateUserIdField) this.getField(Entity.ID_UPDATE_USER_ID);
	}

	/**
	 * 更新日時フィールドを取得します。
	 * @return 更新日時フィールド。
	 */
	public UpdateTimestampField getUpdateTimestampField() {
		return (UpdateTimestampField) this.getField(Entity.ID_UPDATE_TIMESTAMP);
	}



}