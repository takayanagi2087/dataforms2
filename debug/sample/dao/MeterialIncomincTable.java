package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.IncomincIdField;
import sample.field.OrderItemIdField;
import sample.field.IncomincDateField;
import sample.field.QuantityField;
import sample.field.LimitDateField;


/**
 * 資材入庫テープクラス。
 *
 */
public class MeterialIncomincTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public MeterialIncomincTable() {
		this.setAutoIncrementId(true);
		this.setComment("資材入庫テープ");
		this.addPkField(new IncomincIdField()); //資材入庫ID
		this.addField(new OrderItemIdField()); //発注明細ID
		this.addField(new IncomincDateField()); //入庫日
		this.addField(new QuantityField()); //数量
		this.addField(new LimitDateField()); //使用期限
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		MeterialIncomincTableRelation r = new MeterialIncomincTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 資材入庫IDのフィールドID。 */
		public static final String ID_INCOMINC_ID = "incomincId";
		/** 発注明細IDのフィールドID。 */
		public static final String ID_ORDER_ITEM_ID = "orderItemId";
		/** 入庫日のフィールドID。 */
		public static final String ID_INCOMINC_DATE = "incomincDate";
		/** 数量のフィールドID。 */
		public static final String ID_QUANTITY = "quantity";
		/** 使用期限のフィールドID。 */
		public static final String ID_LIMIT_DATE = "limitDate";

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
		 * 資材入庫IDを取得します。
		 * @return 資材入庫ID。
		 */
		public java.lang.Long getIncomincId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_INCOMINC_ID);
		}

		/**
		 * 資材入庫IDを設定します。
		 * @param incomincId 資材入庫ID。
		 */
		public void setIncomincId(final java.lang.Long incomincId) {
			this.getMap().put(Entity.ID_INCOMINC_ID, incomincId);
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
		 * 入庫日を取得します。
		 * @return 入庫日。
		 */
		public java.sql.Date getIncomincDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_INCOMINC_DATE);
		}

		/**
		 * 入庫日を設定します。
		 * @param incomincDate 入庫日。
		 */
		public void setIncomincDate(final java.sql.Date incomincDate) {
			this.getMap().put(Entity.ID_INCOMINC_DATE, incomincDate);
		}

		/**
		 * 数量を取得します。
		 * @return 数量。
		 */
		public java.math.BigDecimal getQuantity() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_QUANTITY);
		}

		/**
		 * 数量を設定します。
		 * @param quantity 数量。
		 */
		public void setQuantity(final java.math.BigDecimal quantity) {
			this.getMap().put(Entity.ID_QUANTITY, quantity);
		}

		/**
		 * 使用期限を取得します。
		 * @return 使用期限。
		 */
		public java.sql.Date getLimitDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_LIMIT_DATE);
		}

		/**
		 * 使用期限を設定します。
		 * @param limitDate 使用期限。
		 */
		public void setLimitDate(final java.sql.Date limitDate) {
			this.getMap().put(Entity.ID_LIMIT_DATE, limitDate);
		}


	}
	/**
	 * 資材入庫IDフィールドを取得します。
	 * @return 資材入庫IDフィールド。
	 */
	public IncomincIdField getIncomincIdField() {
		return (IncomincIdField) this.getField(Entity.ID_INCOMINC_ID);
	}

	/**
	 * 発注明細IDフィールドを取得します。
	 * @return 発注明細IDフィールド。
	 */
	public OrderItemIdField getOrderItemIdField() {
		return (OrderItemIdField) this.getField(Entity.ID_ORDER_ITEM_ID);
	}

	/**
	 * 入庫日フィールドを取得します。
	 * @return 入庫日フィールド。
	 */
	public IncomincDateField getIncomincDateField() {
		return (IncomincDateField) this.getField(Entity.ID_INCOMINC_DATE);
	}

	/**
	 * 数量フィールドを取得します。
	 * @return 数量フィールド。
	 */
	public QuantityField getQuantityField() {
		return (QuantityField) this.getField(Entity.ID_QUANTITY);
	}

	/**
	 * 使用期限フィールドを取得します。
	 * @return 使用期限フィールド。
	 */
	public LimitDateField getLimitDateField() {
		return (LimitDateField) this.getField(Entity.ID_LIMIT_DATE);
	}


}
