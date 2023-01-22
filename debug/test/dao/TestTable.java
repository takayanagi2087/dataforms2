package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import test.field.TextField;
import dataforms.util.NumberUtil;
import test.field.TestIdField;


/**
 * テストテーブルクラス。
 *
 */
public class TestTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public TestTable() {
		this.setAutoIncrementId(true);
		this.setComment("テストテーブル");
		this.addPkField(new TestIdField()); //テストID
		this.addField(new TextField()); //テキスト
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		TestTableRelation r = new TestTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** テストIDのフィールドID。 */
		public static final String ID_TEST_ID = "testId";
		/** テキストのフィールドID。 */
		public static final String ID_TEXT = "text";

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
		 * テキストを取得します。
		 * @return テキスト。
		 */
		public java.lang.String getText() {
			return (java.lang.String) this.getMap().get(Entity.ID_TEXT);
		}

		/**
		 * テキストを設定します。
		 * @param text テキスト。
		 */
		public void setText(final java.lang.String text) {
			this.getMap().put(Entity.ID_TEXT, text);
		}


	}
	/**
	 * テストIDフィールドを取得します。
	 * @return テストIDフィールド。
	 */
	public TestIdField getTestIdField() {
		return (TestIdField) this.getField(Entity.ID_TEST_ID);
	}

	/**
	 * テキストフィールドを取得します。
	 * @return テキストフィールド。
	 */
	public TextField getTextField() {
		return (TextField) this.getField(Entity.ID_TEXT);
	}


}
