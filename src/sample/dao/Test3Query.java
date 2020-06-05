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
public class Test3Query extends Query {
	/**
	 * 。
	 */
	private TestSubQuery testSubQuery = null;

	/**
	 * を取得します。
	 * @return 。
	 */
	public TestSubQuery getTestSubQuery() {
		return this.testSubQuery;
	}


	/**
	 * コンストラクタ.
	 */
	public Test3Query() {
		this.setComment("Test");
		this.setDistinct(false);
		this.testSubQuery = new TestSubQuery();
		this.testSubQuery.setAlias("m");

		this.setFieldList(new FieldList(
			this.testSubQuery.getTestQuery().getMaterialUnitField()
			, new CountField("materialId", this.testSubQuery.getTestQuery().getMaterialIdField())
		));
		this.setMainTable(testSubQuery);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_UNIT = "materialUnit";
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";

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


	}
	/**
	 * 資材在庫単位フィールドを取得します。
	 * @return 資材在庫単位フィールド。
	 */
	public MaterialUnitField getMaterialUnitField() {
		return (MaterialUnitField) this.getField(Entity.ID_MATERIAL_UNIT);
	}

	/**
	 * 資材IDフィールドを取得します。
	 * @return 資材IDフィールド。
	 */
	public MaterialIdField getMaterialIdField() {
		return (MaterialIdField) this.getField(Entity.ID_MATERIAL_ID);
	}


}