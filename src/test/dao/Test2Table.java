package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import dataforms.util.NumberUtil;
import test.field.TypeField;
import test.field.Test2IdField;
import test.field.TestIdField;


/**
 * テスト2クラス。
 *
 */
public class Test2Table extends Table {
	/**
	 * コンストラクタ。
	 */
	public Test2Table() {
		this.setAutoIncrementId(true);
		this.setComment("テスト2");
		this.addPkField(new Test2IdField()); //テスト2ID
		this.addField(new TestIdField()); //テストID
		this.addField(new TypeField()); //タイプ
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		Test2TableRelation r = new Test2TableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** テスト2IDのフィールドID。 */
		public static final String ID_TEST2_ID = "test2Id";
		/** テストIDのフィールドID。 */
		public static final String ID_TEST_ID = "testId";
		/** タイプのフィールドID。 */
		public static final String ID_TYPE = "type";

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
		 * テスト2IDを取得します。
		 * @return テスト2ID。
		 */
		public java.lang.Long getTest2Id() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_TEST2_ID));
		}

		/**
		 * テスト2IDを設定します。
		 * @param test2Id テスト2ID。
		 */
		public void setTest2Id(final java.lang.Long test2Id) {
			this.getMap().put(Entity.ID_TEST2_ID, test2Id);
		}

		/**
		 * テストIDを取得します。
		 * @return テストID。
		 */
		public java.lang.Long getTestId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_TEST_ID));
		}

		/**
		 * テストIDを設定します。
		 * @param testId テストID。
		 */
		public void setTestId(final java.lang.Long testId) {
			this.getMap().put(Entity.ID_TEST_ID, testId);
		}

		/**
		 * タイプを取得します。
		 * @return タイプ。
		 */
		public java.lang.String getType() {
			return (java.lang.String) this.getMap().get(Entity.ID_TYPE);
		}

		/**
		 * タイプを設定します。
		 * @param type タイプ。
		 */
		public void setType(final java.lang.String type) {
			this.getMap().put(Entity.ID_TYPE, type);
		}


	}
	/**
	 * テスト2IDフィールドを取得します。
	 * @return テスト2IDフィールド。
	 */
	public Test2IdField getTest2IdField() {
		return (Test2IdField) this.getField(Entity.ID_TEST2_ID);
	}

	/**
	 * テストIDフィールドを取得します。
	 * @return テストIDフィールド。
	 */
	public TestIdField getTestIdField() {
		return (TestIdField) this.getField(Entity.ID_TEST_ID);
	}

	/**
	 * タイプフィールドを取得します。
	 * @return タイプフィールド。
	 */
	public TypeField getTypeField() {
		return (TypeField) this.getField(Entity.ID_TYPE);
	}


}
