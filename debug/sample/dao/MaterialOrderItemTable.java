package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.OrderItemIdField;
import sample.field.OrderIdField;
import dataforms.field.common.SortOrderField;
import sample.field.MaterialIdField;
import sample.field.UnitPriceField;
import sample.field.AmountField;
import sample.field.MemoField;


/**
 * 資材発注テーブルクラス。
 *
 */
public class MaterialOrderItemTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderItemTable() {
		this.setAutoIncrementId(true);
		this.setComment("資材発注テーブル");
		this.addPkField(new OrderItemIdField()); //発注明細ID
		this.addField(new OrderIdField()); //発注ID
		this.addField(new SortOrderField()); //ソート順
		this.addField(new MaterialIdField()); //資材ID
		this.addField(new UnitPriceField("orderPrice")); //単価
		this.addField(new AmountField()); //数量
		this.addField(new MemoField("itemMemo")); //メモ
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		MaterialOrderItemTableRelation r = new MaterialOrderItemTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
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
		/** 単価のフィールドID。 */
		public static final String ID_ORDER_PRICE = "orderPrice";
		/** 数量のフィールドID。 */
		public static final String ID_AMOUNT = "amount";
		/** メモのフィールドID。 */
		public static final String ID_ITEM_MEMO = "itemMemo";

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
			return (java.lang.Long) this.getMap().get(Entity.ID_ORDER_ITEM_ID);
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
			return (java.lang.Long) this.getMap().get(Entity.ID_ORDER_ID);
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
			return (java.lang.Short) this.getMap().get(Entity.ID_SORT_ORDER);
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
			return (java.lang.Long) this.getMap().get(Entity.ID_MATERIAL_ID);
		}

		/**
		 * 資材IDを設定します。
		 * @param materialId 資材ID。
		 */
		public void setMaterialId(final java.lang.Long materialId) {
			this.getMap().put(Entity.ID_MATERIAL_ID, materialId);
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


}
