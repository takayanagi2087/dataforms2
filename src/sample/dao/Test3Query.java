package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import java.util.Map;
import sample.field.MaterialIdField;
import dataforms.field.sqlfunc.CountField;
import dataforms.field.sqlfunc.AliasField;
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
			new CountField("materialId", this.testSubQuery.getTestQuery().getMaterialIdField())
			, new AliasField("u", testSubQuery.getTestQuery().getMaterialUnitField())
		));
		this.setMainTable(testSubQuery);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** nullのフィールドID。 */
		public static final String ID_MATERIAL_ID = "materialId";
		/** nullのフィールドID。 */
		public static final String ID_U = "u";

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
		 * 資材在庫単位を取得します。
		 * @return 資材在庫単位。
		 */
		public java.lang.String getU() {
			return (java.lang.String) this.getMap().get(Entity.ID_U);
		}

		/**
		 * 資材在庫単位を設定します。
		 * @param u 資材在庫単位。
		 */
		public void setU(final java.lang.String u) {
			this.getMap().put(Entity.ID_U, u);
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
	 * 資材在庫単位フィールドを取得します。
	 * @return 資材在庫単位フィールド。
	 */
	public MaterialUnitField getUField() {
		return (MaterialUnitField) this.getField(Entity.ID_U);
	}


}