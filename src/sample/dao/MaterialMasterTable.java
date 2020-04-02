package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.MaterialIdField;
import sample.field.MaterialCodeField;
import sample.field.MaterialNameField;
import sample.field.MaterialUnitField;
import sample.field.UnitPriceField;
import sample.field.MemoField;


/**
 * 資材マスタクラス。
 *
 */
public class MaterialMasterTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterTable() {
		this.setAutoIncrementId(true);
		this.setComment("資材マスタ");
		this.addPkField(new MaterialIdField()); //資材ID
		this.addField(new MaterialCodeField()); //資材コード
		this.addField(new MaterialNameField()); //資材名称
		this.addField(new MaterialUnitField()); //資材在庫単位
		this.addField(new UnitPriceField()); //単価
		this.addField(new MemoField()); //メモ
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		MaterialMasterTableRelation r = new MaterialMasterTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 資材IDのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** 資材コードのフィールドID。 */
		public static final String ID_MATERIAL_CODE = "materialCode";
		/** 資材名称のフィールドID。 */
		public static final String ID_MATERIAL_NAME = "materialName";
		/** 資材在庫単位のフィールドID。 */
		public static final String ID_MATERIAL_UNIT = "materialUnit";
		/** 単価のフィールドID。 */
		public static final String ID_UNIT_PRICE = "unitPrice";
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
	public UnitPriceField getUnitPriceField() {
		return (UnitPriceField) this.getField(Entity.ID_UNIT_PRICE);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}


}
