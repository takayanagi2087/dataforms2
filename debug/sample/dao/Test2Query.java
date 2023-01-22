package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import java.util.Map;
import sample.field.MaterialIdField;
import dataforms.field.sqlfunc.CountField;
import sample.field.MaterialUnitField;




/**
 * 問い合わせクラスです。
 *
 */
public class Test2Query extends Query {
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
	public Test2Query() {
		this.setComment("Test2");
		this.setDistinct(false);
		this.materialMasterTable = new MaterialMasterTable();
		this.materialMasterTable.setAlias("m");

		this.setFieldList(new FieldList(
			new CountField("materialId", this.materialMasterTable.getMaterialIdField()).setComment("資材IDa")
			, this.materialMasterTable.getMaterialUnitField().setComment("資材在庫単位a")
		));
		this.setMainTable(materialMasterTable);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 資材IDaのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** 資材在庫単位aのフィールドID。 */
		public static final String ID_MATERIAL_UNIT = "materialUnit";

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
		 * 資材IDaを取得します。
		 * @return 資材IDa。
		 */
		public java.lang.Long getMaterialId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_MATERIAL_ID);
		}

		/**
		 * 資材IDaを設定します。
		 * @param materialId 資材IDa。
		 */
		public void setMaterialId(final java.lang.Long materialId) {
			this.getMap().put(Entity.ID_MATERIAL_ID, materialId);
		}

		/**
		 * 資材在庫単位aを取得します。
		 * @return 資材在庫単位a。
		 */
		public java.lang.String getMaterialUnit() {
			return (java.lang.String) this.getMap().get(Entity.ID_MATERIAL_UNIT);
		}

		/**
		 * 資材在庫単位aを設定します。
		 * @param materialUnit 資材在庫単位a。
		 */
		public void setMaterialUnit(final java.lang.String materialUnit) {
			this.getMap().put(Entity.ID_MATERIAL_UNIT, materialUnit);
		}


	}
	/**
	 * 資材IDaフィールドを取得します。
	 * @return 資材IDaフィールド。
	 */
	public MaterialIdField getMaterialIdField() {
		return (MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}

	/**
	 * 資材在庫単位aフィールドを取得します。
	 * @return 資材在庫単位aフィールド。
	 */
	public MaterialUnitField getMaterialUnitField() {
		return (MaterialUnitField) this.getField(Entity.ID_MATERIAL_UNIT);
	}


}