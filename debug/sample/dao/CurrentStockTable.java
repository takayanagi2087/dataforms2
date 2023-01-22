package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.CurrentStockIdField;
import sample.field.MaterialIdField;
import sample.field.QuantityField;
import sample.field.LimitDateField;


/**
 * 現在在庫数クラス。
 *
 */
public class CurrentStockTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public CurrentStockTable() {
		this.setAutoIncrementId(true);
		this.setComment("現在在庫数");
		this.addPkField(new CurrentStockIdField()); //在庫ID
		this.addField(new MaterialIdField()); //資材ID
		this.addField(new QuantityField("currentQuantity")); //数量
		this.addField(new LimitDateField()); //使用期限
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		CurrentStockTableRelation r = new CurrentStockTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 在庫IDのフィールドID。 */
		public static final String ID_CURRENT_STOCK_ID = "currentStockId";
		/** 資材IDのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** 数量のフィールドID。 */
		public static final String ID_CURRENT_QUANTITY = "currentQuantity";
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
		 * 在庫IDを取得します。
		 * @return 在庫ID。
		 */
		public java.lang.Long getCurrentStockId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_CURRENT_STOCK_ID);
		}

		/**
		 * 在庫IDを設定します。
		 * @param currentStockId 在庫ID。
		 */
		public void setCurrentStockId(final java.lang.Long currentStockId) {
			this.getMap().put(Entity.ID_CURRENT_STOCK_ID, currentStockId);
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
		 * 数量を取得します。
		 * @return 数量。
		 */
		public java.math.BigDecimal getCurrentQuantity() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_CURRENT_QUANTITY);
		}

		/**
		 * 数量を設定します。
		 * @param currentQuantity 数量。
		 */
		public void setCurrentQuantity(final java.math.BigDecimal currentQuantity) {
			this.getMap().put(Entity.ID_CURRENT_QUANTITY, currentQuantity);
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
	 * 在庫IDフィールドを取得します。
	 * @return 在庫IDフィールド。
	 */
	public CurrentStockIdField getCurrentStockIdField() {
		return (CurrentStockIdField) this.getField(Entity.ID_CURRENT_STOCK_ID);
	}

	/**
	 * 資材IDフィールドを取得します。
	 * @return 資材IDフィールド。
	 */
	public MaterialIdField getMaterialIdField() {
		return (MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}

	/**
	 * 数量フィールドを取得します。
	 * @return 数量フィールド。
	 */
	public QuantityField getCurrentQuantityField() {
		return (QuantityField) this.getField(Entity.ID_CURRENT_QUANTITY);
	}

	/**
	 * 使用期限フィールドを取得します。
	 * @return 使用期限フィールド。
	 */
	public LimitDateField getLimitDateField() {
		return (LimitDateField) this.getField(Entity.ID_LIMIT_DATE);
	}


}
