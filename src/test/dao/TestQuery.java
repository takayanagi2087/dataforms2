package test.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import test.field.TextField;
import dataforms.util.NumberUtil;
import test.field.TypeField;
import java.util.Map;
import dataforms.field.sqlfunc.AliasField;
import test.field.Test2IdField;
import test.field.TestIdField;




/**
 * 問い合わせクラスです。
 *
 */
public class TestQuery extends Query {
	/**
	 * テスト2。
	 */
	private Test2Table test2Table = null;

	/**
	 * テスト2を取得します。
	 * @return テスト2。
	 */
	public Test2Table getTest2Table() {
		return this.test2Table;
	}

	/**
	 * テストテーブル。
	 */
	private TestTable testTableJ0 = null;

	/**
	 * テストテーブルを取得します。
	 * @return テストテーブル。
	 */
	public TestTable getTestTableJ0() {
		return this.testTableJ0;
	}

	/**
	 * テストテーブル。
	 */
	private TestTable testTableJ1 = null;

	/**
	 * テストテーブルを取得します。
	 * @return テストテーブル。
	 */
	public TestTable getTestTableJ1() {
		return this.testTableJ1;
	}


	/**
	 * コンストラクタ.
	 */
	public TestQuery() {
		this.setComment("テスト");
		this.setDistinct(false);
		this.test2Table = new Test2Table();
		this.test2Table.setAlias("m");
		this.testTableJ0 = new TestTable();
		this.testTableJ0.setAlias("j0");
		this.testTableJ1 = new TestTable();
		this.testTableJ1.setAlias("j1");

		this.setFieldList(new FieldList(
			this.test2Table.getTest2IdField().setComment("")
			, this.test2Table.getTestIdField().setComment("")
			, this.test2Table.getTypeField().setComment("")
			, new AliasField("atext", this.testTableJ0.getTextField().setComment(""))
			, new AliasField("btext", this.testTableJ0.getTextField().setComment(""))
		));
		this.setMainTable(test2Table);
		this.addInnerJoin(testTableJ0);
		this.addLeftJoin(testTableJ1);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** のフィールドID。 */
		public static final String ID_TEST2_ID = "test2Id";
		/** のフィールドID。 */
		public static final String ID_TEST_ID = "testId";
		/** のフィールドID。 */
		public static final String ID_TYPE = "type";
		/** のフィールドID。 */
		public static final String ID_ATEXT = "atext";
		/** のフィールドID。 */
		public static final String ID_BTEXT = "btext";

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

		/**
		 * テキストを取得します。
		 * @return テキスト。
		 */
		public java.lang.String getAtext() {
			return (java.lang.String) this.getMap().get(Entity.ID_ATEXT);
		}

		/**
		 * テキストを設定します。
		 * @param atext テキスト。
		 */
		public void setAtext(final java.lang.String atext) {
			this.getMap().put(Entity.ID_ATEXT, atext);
		}

		/**
		 * テキストを取得します。
		 * @return テキスト。
		 */
		public java.lang.String getBtext() {
			return (java.lang.String) this.getMap().get(Entity.ID_BTEXT);
		}

		/**
		 * テキストを設定します。
		 * @param btext テキスト。
		 */
		public void setBtext(final java.lang.String btext) {
			this.getMap().put(Entity.ID_BTEXT, btext);
		}


	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public Test2IdField getTest2IdField() {
		return (Test2IdField) this.getField(Entity.ID_TEST2_ID);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public TestIdField getTestIdField() {
		return (TestIdField) this.getField(Entity.ID_TEST_ID);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public TypeField getTypeField() {
		return (TypeField) this.getField(Entity.ID_TYPE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public TextField getAtextField() {
		return (TextField) this.getField(Entity.ID_ATEXT);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public TextField getBtextField() {
		return (TextField) this.getField(Entity.ID_BTEXT);
	}


}