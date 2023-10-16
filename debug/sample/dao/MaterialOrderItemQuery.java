package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import sample.field.OrderItemIdField;
import sample.field.UnitPriceField;
import dataforms.field.common.CreateTimestampField;
import sample.field.MaterialIdField;
import dataforms.field.common.UpdateUserIdField;
import sample.field.MaterialUnitField;
import dataforms.field.common.CreateUserIdField;
import sample.field.AmountField;
import sample.field.MemoField;
import dataforms.util.NumberUtil;
import java.util.Map;
import dataforms.field.common.SortOrderField;
import sample.field.MaterialCodeField;
import dataforms.field.common.UpdateTimestampField;
import sample.field.MaterialNameField;
import sample.field.OrderIdField;



/**
 * 資材発注明細用の問合せクラスです。
 *
 */
public class MaterialOrderItemQuery extends Query {
	/**
	 * 資材発注テーブルを取得します。
	 * @return 資材発注テーブル。
	 */
	public MaterialOrderItemTable getMaterialOrderItemTable() {
		return (MaterialOrderItemTable) this.getTable(MaterialOrderItemTable.class, "m");
	}

	/**
	 * 発注テーブルを取得します。
	 * @return 発注テーブル。
	 */
	public MaterialOrderTable getMaterialOrderTable() {
		return (MaterialOrderTable) this.getTable(MaterialOrderTable.class, "j0");
	}

	/**
	 * 資材マスタを取得します。
	 * @return 資材マスタ。
	 */
	public MaterialMasterTable getMaterialMasterTable() {
		return (MaterialMasterTable) this.getTable(MaterialMasterTable.class, "j1");
	}


	/**
	 * コンストラクタ.
	 */
	public MaterialOrderItemQuery() 	{
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

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 発注明細IDのフィールドID。 */
		public static final String ID_ORDER_ITEM_ID = "orderItemId";
		/** 発注IDのフィールドID。 */
		public static final String ID_ORDER_ID = "orderId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** 資材IDのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** 資材コードのフィールドID。 */
		public static final String ID_MATERIAL_CODE = "materialCode";
		/** 資材名称のフィールドID。 */
		public static final String ID_MATERIAL_NAME = "materialName";
		/** 単価のフィールドID。 */
		public static final String ID_UNIT_PRICE = "unitPrice";
		/** 資材在庫単位のフィールドID。 */
		public static final String ID_MATERIAL_UNIT = "materialUnit";
		/** 単価のフィールドID。 */
		public static final String ID_ORDER_PRICE = "orderPrice";
		/** 数量のフィールドID。 */
		public static final String ID_AMOUNT = "amount";
		/** メモのフィールドID。 */
		public static final String ID_ITEM_MEMO = "itemMemo";
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
		 * 発注明細IDを取得します。
		 * @return 発注明細ID。
		 */
		public java.lang.Long getOrderItemId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_ORDER_ITEM_ID));
		}

		/**
		 * 発注明細IDを設定します。
		 * @param orderItemId 発注明細ID。
		 */
		public void setOrderItemId(final java.lang.Long orderItemId) {
			this.getMap().put(Entity.ID_ORDER_ITEM_ID, orderItemId);
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
		 * ソート順を取得します。
		 * @return ソート順。
		 */
		public java.lang.Short getSortOrder() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SORT_ORDER));
		}

		/**
		 * ソート順を設定します。
		 * @param sortOrder ソート順。
		 */
		public void setSortOrder(final java.lang.Short sortOrder) {
			this.getMap().put(Entity.ID_SORT_ORDER, sortOrder);
		}

		/**
		 * 資材IDを取得します。
		 * @return 資材ID。
		 */
		public java.lang.Long getMaterialId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_MATERIAL_ID));
		}

		/**
		 * 資材IDを設定します。
		 * @param materialId 資材ID。
		 */
		public void setMaterialId(final java.lang.Long materialId) {
			this.getMap().put(Entity.ID_MATERIAL_ID, materialId);
		}

		/**
		 * 資材コードを取得します。
		 * @return 資材コード。
		 */
		public java.lang.String getMaterialCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_CODE);
		}

		/**
		 * 資材コードを設定します。
		 * @param materialCode 資材コード。
		 */
		public void setMaterialCode(final java.lang.String materialCode) {
			this.getMap().put(Entity.ID_MATERIAL_CODE, materialCode);
		}

		/**
		 * 資材名称を取得します。
		 * @return 資材名称。
		 */
		public java.lang.String getMaterialName() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_NAME);
		}

		/**
		 * 資材名称を設定します。
		 * @param materialName 資材名称。
		 */
		public void setMaterialName(final java.lang.String materialName) {
			this.getMap().put(Entity.ID_MATERIAL_NAME, materialName);
		}

		/**
		 * 単価を取得します。
		 * @return 単価。
		 */
		public java.math.BigDecimal getUnitPrice() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_UNIT_PRICE);
		}

		/**
		 * 単価を設定します。
		 * @param unitPrice 単価。
		 */
		public void setUnitPrice(final java.math.BigDecimal unitPrice) {
			this.getMap().put(Entity.ID_UNIT_PRICE, unitPrice);
		}

		/**
		 * 資材在庫単位を取得します。
		 * @return 資材在庫単位。
		 */
		public java.lang.String getMaterialUnit() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_UNIT);
		}

		/**
		 * 資材在庫単位を設定します。
		 * @param materialUnit 資材在庫単位。
		 */
		public void setMaterialUnit(final java.lang.String materialUnit) {
			this.getMap().put(Entity.ID_MATERIAL_UNIT, materialUnit);
		}

		/**
		 * 単価を取得します。
		 * @return 単価。
		 */
		public java.math.BigDecimal getOrderPrice() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_ORDER_PRICE);
		}

		/**
		 * 単価を設定します。
		 * @param orderPrice 単価。
		 */
		public void setOrderPrice(final java.math.BigDecimal orderPrice) {
			this.getMap().put(Entity.ID_ORDER_PRICE, orderPrice);
		}

		/**
		 * 数量を取得します。
		 * @return 数量。
		 */
		public java.math.BigDecimal getAmount() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_AMOUNT);
		}

		/**
		 * 数量を設定します。
		 * @param amount 数量。
		 */
		public void setAmount(final java.math.BigDecimal amount) {
			this.getMap().put(Entity.ID_AMOUNT, amount);
		}

		/**
		 * メモを取得します。
		 * @return メモ。
		 */
		public java.lang.String getItemMemo() {
			return (java.lang.String) this.getMap().get(Entity.ID_ITEM_MEMO);
		}

		/**
		 * メモを設定します。
		 * @param itemMemo メモ。
		 */
		public void setItemMemo(final java.lang.String itemMemo) {
			this.getMap().put(Entity.ID_ITEM_MEMO, itemMemo);
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
	 * 発注明細IDフィールドを取得します。
	 * @return 発注明細IDフィールド。
	 */
	public OrderItemIdField getOrderItemIdField() {
		return (OrderItemIdField) this.getField(Entity.ID_ORDER_ITEM_ID);
	}

	/**
	 * 発注IDフィールドを取得します。
	 * @return 発注IDフィールド。
	 */
	public OrderIdField getOrderIdField() {
		return (OrderIdField) this.getField(Entity.ID_ORDER_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
	}

	/**
	 * 資材IDフィールドを取得します。
	 * @return 資材IDフィールド。
	 */
	public MaterialIdField getMaterialIdField() {
		return (MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}

	/**
	 * 資材コードフィールドを取得します。
	 * @return 資材コードフィールド。
	 */
	public MaterialCodeField getMaterialCodeField() {
		return (MaterialCodeField) this.getField(Entity.ID_MATERIAL_CODE);
	}

	/**
	 * 資材名称フィールドを取得します。
	 * @return 資材名称フィールド。
	 */
	public MaterialNameField getMaterialNameField() {
		return (MaterialNameField) this.getField(Entity.ID_MATERIAL_NAME);
	}

	/**
	 * 単価フィールドを取得します。
	 * @return 単価フィールド。
	 */
	public UnitPriceField getUnitPriceField() {
		return (UnitPriceField) this.getField(Entity.ID_UNIT_PRICE);
	}

	/**
	 * 資材在庫単位フィールドを取得します。
	 * @return 資材在庫単位フィールド。
	 */
	public MaterialUnitField getMaterialUnitField() {
		return (MaterialUnitField) this.getField(Entity.ID_MATERIAL_UNIT);
	}

	/**
	 * 単価フィールドを取得します。
	 * @return 単価フィールド。
	 */
	public UnitPriceField getOrderPriceField() {
		return (UnitPriceField) this.getField(Entity.ID_ORDER_PRICE);
	}

	/**
	 * 数量フィールドを取得します。
	 * @return 数量フィールド。
	 */
	public AmountField getAmountField() {
		return (AmountField) this.getField(Entity.ID_AMOUNT);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getItemMemoField() {
		return (MemoField) this.getField(Entity.ID_ITEM_MEMO);
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