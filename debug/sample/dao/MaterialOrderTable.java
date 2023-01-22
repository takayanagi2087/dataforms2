package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.OrderIdField;
import sample.field.OrderNoField;
import sample.field.SupplierIdField;
import sample.field.OrderDateField;
import sample.field.MemoField;


/**
 * 発注テーブルクラス。
 *
 */
public class MaterialOrderTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderTable() {
		this.setAutoIncrementId(true);
		this.setComment("発注テーブル");
		this.addPkField(new OrderIdField()); //発注ID
		this.addField(new OrderNoField()); //発注番号
		this.addField(new SupplierIdField()); //仕入先ID
		this.addField(new OrderDateField()); //発注日
		this.addField(new MemoField()); //メモ
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		MaterialOrderTableRelation r = new MaterialOrderTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
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
		/** 発注日のフィールドID。 */
		public static final String ID_ORDER_DATE = "orderDate";
		/** メモのフィールドID。 */
		public static final String ID_MEMO = "memo";

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


}
