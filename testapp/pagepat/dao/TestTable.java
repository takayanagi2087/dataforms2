package pagepat.dao;

import java.util.Map;

import dataforms.dao.Table;
import dataforms.util.NumberUtil;
import fieldtest.field.TestIdField;
import pagepat.field.Code1Field;
import pagepat.field.Code2Field;
import pagepat.field.NameField;


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
		this.addPkField(new TestIdField()).setNotNull(true); //レコードID
		this.addField(new Code1Field()); //コード1
		this.addField(new Code2Field()); //コード2
		this.addField(new NameField()); //名称
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
		/** レコードIDのフィールドID。 */
		public static final String ID_TEST_ID = "testId";
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** コード2のフィールドID。 */
		public static final String ID_CODE2 = "code2";
		/** 名称のフィールドID。 */
		public static final String ID_NAME = "name";

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
		 * レコードIDを取得します。
		 * @return レコードID。
		 */
		public java.lang.Long getTestId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_TEST_ID));
		}

		/**
		 * レコードIDを設定します。
		 * @param testId レコードID。
		 */
		public void setTestId(final java.lang.Long testId) {
			this.getMap().put(Entity.ID_TEST_ID, testId);
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
		public java.lang.String getCode2() {
			return (java.lang.String) this.getMap().get(Entity.ID_CODE2);
		}

		/**
		 * コード2を設定します。
		 * @param code2 コード2。
		 */
		public void setCode2(final java.lang.String code2) {
			this.getMap().put(Entity.ID_CODE2, code2);
		}

		/**
		 * 名称を取得します。
		 * @return 名称。
		 */
		public java.lang.String getName() {
			return (java.lang.String) this.getMap().get(Entity.ID_NAME);
		}

		/**
		 * 名称を設定します。
		 * @param name 名称。
		 */
		public void setName(final java.lang.String name) {
			this.getMap().put(Entity.ID_NAME, name);
		}


	}

	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public TestIdField getTestIdField() {
		return (TestIdField) this.getField(Entity.ID_TEST_ID);
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
	public Code2Field getCode2Field() {
		return (Code2Field) this.getField(Entity.ID_CODE2);
	}

	/**
	 * 名称フィールドを取得します。
	 * @return 名称フィールド。
	 */
	public NameField getNameField() {
		return (NameField) this.getField(Entity.ID_NAME);
	}



}
