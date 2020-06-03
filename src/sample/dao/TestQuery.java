package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import java.util.Map;




/**
 * 問い合わせクラスです。
 *
 */
public class TestQuery extends Query {
	/**
	 * 資材マスタ。
	 */
	private MaterialMasterTable materialMasterTable = null;

	/**
	 * 資材マスタを取得します。
	 * @return 資材マスタ。
	 */
	public MaterialMasterTable getMaterialMasterTable() {
		return this.materialMasterTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestQuery() {
		this.setComment("Test");
		this.setDistinct(false);
		this.materialMasterTable = new MaterialMasterTable();
		this.materialMasterTable.setAlias("m");

		this.setFieldList(new FieldList(
			this.materialMasterTable.getMaterialIdField()
			, this.materialMasterTable.getMaterialCodeField()
			, this.materialMasterTable.getMaterialNameField()
			, this.materialMasterTable.getMaterialUnitField()
			, this.materialMasterTable.getUnitPriceField()
			, this.materialMasterTable.getOrderPointField()
			, this.materialMasterTable.getMemoField()
		));
		this.setMainTable(materialMasterTable);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_CODE = "materialCode";
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_NAME = "materialName";
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_UNIT = "materialUnit";
		/** nullのフィールドID。 */
		public static final String ID_UNIT_PRICE = "unitPrice";
		/** nullのフィールドID。 */
		public static final String ID_ORDER_POINT = "orderPoint";
		/** nullのフィールドID。 */
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
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.Long getMaterialId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_MATERIAL_ID);
		}

		/**
		 * nullを設定します。
		 * @param materialId null。
		 */
		public void setMaterialId(final java.lang.Long materialId) {
			this.getMap().put(Entity.ID_MATERIAL_ID, materialId);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.String getMaterialCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_CODE);
		}

		/**
		 * nullを設定します。
		 * @param materialCode null。
		 */
		public void setMaterialCode(final java.lang.String materialCode) {
			this.getMap().put(Entity.ID_MATERIAL_CODE, materialCode);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.String getMaterialName() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_NAME);
		}

		/**
		 * nullを設定します。
		 * @param materialName null。
		 */
		public void setMaterialName(final java.lang.String materialName) {
			this.getMap().put(Entity.ID_MATERIAL_NAME, materialName);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.String getMaterialUnit() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_UNIT);
		}

		/**
		 * nullを設定します。
		 * @param materialUnit null。
		 */
		public void setMaterialUnit(final java.lang.String materialUnit) {
			this.getMap().put(Entity.ID_MATERIAL_UNIT, materialUnit);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.math.BigDecimal getUnitPrice() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_UNIT_PRICE);
		}

		/**
		 * nullを設定します。
		 * @param unitPrice null。
		 */
		public void setUnitPrice(final java.math.BigDecimal unitPrice) {
			this.getMap().put(Entity.ID_UNIT_PRICE, unitPrice);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.math.BigDecimal getOrderPoint() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_ORDER_POINT);
		}

		/**
		 * nullを設定します。
		 * @param orderPoint null。
		 */
		public void setOrderPoint(final java.math.BigDecimal orderPoint) {
			this.getMap().put(Entity.ID_ORDER_POINT, orderPoint);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.String getMemo() {
			return (java.lang.String) this.getMap().get(Entity.ID_MEMO);
		}

		/**
		 * nullを設定します。
		 * @param memo null。
		 */
		public void setMemo(final java.lang.String memo) {
			this.getMap().put(Entity.ID_MEMO, memo);
		}


	}
	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.MaterialIdField getMaterialIdField() {
		return (sample.field.MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.MaterialCodeField getMaterialCodeField() {
		return (sample.field.MaterialCodeField) this.getField(Entity.ID_MATERIAL_CODE);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.MaterialNameField getMaterialNameField() {
		return (sample.field.MaterialNameField) this.getField(Entity.ID_MATERIAL_NAME);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.MaterialUnitField getMaterialUnitField() {
		return (sample.field.MaterialUnitField) this.getField(Entity.ID_MATERIAL_UNIT);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.UnitPriceField getUnitPriceField() {
		return (sample.field.UnitPriceField) this.getField(Entity.ID_UNIT_PRICE);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.OrderPointField getOrderPointField() {
		return (sample.field.OrderPointField) this.getField(Entity.ID_ORDER_POINT);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public sample.field.MemoField getMemoField() {
		return (sample.field.MemoField) this.getField(Entity.ID_MEMO);
	}


}