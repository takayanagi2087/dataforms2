package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.LeavingIdField;
import sample.field.MaterialIdField;
import sample.field.LeavingDateField;
import sample.field.QuantityField;
import sample.field.MemoField;


/**
 * 資材出庫テーブルクラス。
 *
 */
public class MaterialLeavingTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public MaterialLeavingTable() {
		this.setAutoIncrementId(true);
		this.setComment("資材出庫テーブル");
		this.addPkField(new LeavingIdField()); //出庫ID
		this.addField(new MaterialIdField()); //資材ID
		this.addField(new LeavingDateField()); //出庫日
		this.addField(new QuantityField()); //数量
		this.addField(new MemoField()); //メモ
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		MaterialLeavingTableRelation r = new MaterialLeavingTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 出庫IDのフィールドID。 */
		public static final String ID_LEAVING_ID = "leavingId";
		/** 資材IDのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** 出庫日のフィールドID。 */
		public static final String ID_LEAVING_DATE = "leavingDate";
		/** 数量のフィールドID。 */
		public static final String ID_QUANTITY = "quantity";
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
		 * 出庫IDを取得します。
		 * @return 出庫ID。
		 */
		public java.lang.Long getLeavingId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_LEAVING_ID);
		}

		/**
		 * 出庫IDを設定します。
		 * @param leavingId 出庫ID。
		 */
		public void setLeavingId(final java.lang.Long leavingId) {
			this.getMap().put(Entity.ID_LEAVING_ID, leavingId);
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
		 * 出庫日を取得します。
		 * @return 出庫日。
		 */
		public java.sql.Date getLeavingDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_LEAVING_DATE);
		}

		/**
		 * 出庫日を設定します。
		 * @param leavingDate 出庫日。
		 */
		public void setLeavingDate(final java.sql.Date leavingDate) {
			this.getMap().put(Entity.ID_LEAVING_DATE, leavingDate);
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
	 * 出庫IDフィールドを取得します。
	 * @return 出庫IDフィールド。
	 */
	public LeavingIdField getLeavingIdField() {
		return (LeavingIdField) this.getField(Entity.ID_LEAVING_ID);
	}

	/**
	 * 資材IDフィールドを取得します。
	 * @return 資材IDフィールド。
	 */
	public MaterialIdField getMaterialIdField() {
		return (MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}

	/**
	 * 出庫日フィールドを取得します。
	 * @return 出庫日フィールド。
	 */
	public LeavingDateField getLeavingDateField() {
		return (LeavingDateField) this.getField(Entity.ID_LEAVING_DATE);
	}

	/**
	 * 数量フィールドを取得します。
	 * @return 数量フィールド。
	 */
	public QuantityField getQuantityField() {
		return (QuantityField) this.getField(Entity.ID_QUANTITY);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}


}
