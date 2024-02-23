package pagepat.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import dataforms.field.sqltype.BigintField;
import pagepat.field.Code1Field;
import dataforms.util.NumberUtil;
import java.util.Map;
import dataforms.field.sqlfunc.CountField;



/**
 * Code1件数取得問合せクラスです。
 *
 */
public class Code1CountQuery extends Query {
	/**
	 * テストテーブルを取得します。
	 * @return テストテーブル。
	 */
	public TestTable getTestTable() {
		return (TestTable) this.getTable(TestTable.class, "m");
	}


	/**
	 * コンストラクタ.
	 */
	public Code1CountQuery() 	{
		this.setComment("Code1件数取得問合せ");
		this.setDistinct(false);
		TestTable testTable = new TestTable();
		testTable.setAlias("m");

		this.setFieldList(new FieldList(
			testTable.getCode1Field()
			, new CountField("cnt", testTable.getCode2Field())
		));
		this.setMainTable(testTable);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** コード2のフィールドID。 */
		public static final String ID_CNT = "cnt";

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
		 * コード1を取得します。
		 * @return コード1。
		 */
		public java.lang.String getCode1() {
			return (java.lang.String) this.getMap().get(Entity.ID_CODE1);
		}

		/**
		 * コード1を設定します。
		 * @param code1 コード1。
		 */
		public void setCode1(final java.lang.String code1) {
			this.getMap().put(Entity.ID_CODE1, code1);
		}

		/**
		 * コード2を取得します。
		 * @return コード2。
		 */
		public java.lang.Long getCnt() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_CNT));
		}

		/**
		 * コード2を設定します。
		 * @param cnt コード2。
		 */
		public void setCnt(final java.lang.Long cnt) {
			this.getMap().put(Entity.ID_CNT, cnt);
		}


	}

	/**
	 * コード1フィールドを取得します。
	 * @return コード1フィールド。
	 */
	public Code1Field getCode1Field() {
		return (Code1Field) this.getField(Entity.ID_CODE1);
	}

	/**
	 * コード2フィールドを取得します。
	 * @return コード2フィールド。
	 */
	public BigintField getCntField() {
		return (BigintField) this.getField(Entity.ID_CNT);
	}



}