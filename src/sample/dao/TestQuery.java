package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import sample.field.MemoField;
import sample.field.UnitPriceField;
import java.util.Map;
import sample.field.MaterialCodeField;
import dataforms.field.sqlfunc.SqlField;
import sample.field.MaterialIdField;
import sample.field.MaterialUnitField;
import dataforms.field.sqltype.NumericField;
import sample.field.OrderPointField;
import sample.field.MaterialNameField;




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
			, new SqlField(new NumericField("unitPrice2",10, 2), "m.unit_price * 2")
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
		/** のフィールドID。 */
		public static final String ID_UNIT_PRICE2 = "unitPrice2";

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
		 * 発注点を取得します。
		 * @return 発注点。
		 */
		public java.math.BigDecimal getOrderPoint() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_ORDER_POINT);
		}

		/**
		 * 発注点を設定します。
		 * @param orderPoint 発注点。
		 */
		public void setOrderPoint(final java.math.BigDecimal orderPoint) {
			this.getMap().put(Entity.ID_ORDER_POINT, orderPoint);
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
		 * nullを取得します。
		 * @return null。
		 */
		public java.math.BigDecimal getUnitPrice2() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_UNIT_PRICE2);
		}

		/**
		 * nullを設定します。
		 * @param unitPrice2 null。
		 */
		public void setUnitPrice2(final java.math.BigDecimal unitPrice2) {
			this.getMap().put(Entity.ID_UNIT_PRICE2, unitPrice2);
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
	 * 発注点フィールドを取得します。
	 * @return 発注点フィールド。
	 */
	public OrderPointField getOrderPointField() {
		return (OrderPointField) this.getField(Entity.ID_ORDER_POINT);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}

	/**
	 * nullフィールドを取得します。
	 * @return nullフィールド。
	 */
	public NumericField getUnitPrice2Field() {
		return (NumericField) this.getField(Entity.ID_UNIT_PRICE2);
	}


}