package test.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import dataforms.field.sqlfunc.SumField;
import dataforms.field.sqltype.BigintField;
import dataforms.field.sqlfunc.MaxField;
import test.field.Code1Field;
import dataforms.field.sqlfunc.CountField;
import test.field.TestBigintField;
import dataforms.field.sqlfunc.MinField;
import test.field.TestSmallintField;
import test.field.TestIntegerField;
import test.field.TestDoubleField;
import dataforms.util.NumberUtil;
import java.util.Map;
import test.field.TestNumericField;
import dataforms.field.sqlfunc.AvgField;




/**
 * 問い合わせクラスです。
 *
 */
public class TestCode1Query extends Query {
	/**
	 * 複数レコード編集テストテーブル。
	 */
	private TestMultiRecTable testMultiRecTable = null;

	/**
	 * 複数レコード編集テストテーブルを取得します。
	 * @return 複数レコード編集テストテーブル。
	 */
	public TestMultiRecTable getTestMultiRecTable() {
		return this.testMultiRecTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestCode1Query() {
		this.setComment("code1一覧の問合せ");
		this.setDistinct(false);
		this.testMultiRecTable = new TestMultiRecTable();
		this.testMultiRecTable.setAlias("m");

		this.setFieldList(new FieldList(
			this.testMultiRecTable.getCode1Field()
			, new CountField("cnt", this.testMultiRecTable.getTestMultiRecIdField()).setComment("件数")
			, new AvgField("testSmallint", this.testMultiRecTable.getTestSmallintField()).setComment("Smallint平均")
			, new MaxField("testInteger", this.testMultiRecTable.getTestIntegerField()).setComment("int最大値")
			, new MinField("testBigint", this.testMultiRecTable.getTestBigintField()).setComment("long最小値")
			, new SumField("testDouble", this.testMultiRecTable.getTestDoubleField()).setComment("double合計")
			, new AvgField("testNumeric", this.testMultiRecTable.getTestNumericField()).setComment("Numeric平均")
		));
		this.setMainTable(testMultiRecTable);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** 件数のフィールドID。 */
		public static final String ID_CNT = "cnt";
		/** Smallint平均のフィールドID。 */
		public static final String ID_TEST_SMALLINT = "testSmallint";
		/** int最大値のフィールドID。 */
		public static final String ID_TEST_INTEGER = "testInteger";
		/** long最小値のフィールドID。 */
		public static final String ID_TEST_BIGINT = "testBigint";
		/** double合計のフィールドID。 */
		public static final String ID_TEST_DOUBLE = "testDouble";
		/** Numeric平均のフィールドID。 */
		public static final String ID_TEST_NUMERIC = "testNumeric";

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
		 * 件数を取得します。
		 * @return 件数。
		 */
		public java.lang.Long getCnt() {
			return NumberUtil.longValue(this.getMap().get(Entity.ID_CNT));
		}

		/**
		 * 件数を設定します。
		 * @param cnt 件数。
		 */
		public void setCnt(final java.lang.Long cnt) {
			this.getMap().put(Entity.ID_CNT, cnt);
		}

		/**
		 * Smallint平均を取得します。
		 * @return Smallint平均。
		 */
		public java.lang.Short getTestSmallint() {
			return NumberUtil.shortValue(this.getMap().get(Entity.ID_TEST_SMALLINT));
		}

		/**
		 * Smallint平均を設定します。
		 * @param testSmallint Smallint平均。
		 */
		public void setTestSmallint(final java.lang.Short testSmallint) {
			this.getMap().put(Entity.ID_TEST_SMALLINT, testSmallint);
		}

		/**
		 * int最大値を取得します。
		 * @return int最大値。
		 */
		public java.lang.Integer getTestInteger() {
			return NumberUtil.intValue(this.getMap().get(Entity.ID_TEST_INTEGER));
		}

		/**
		 * int最大値を設定します。
		 * @param testInteger int最大値。
		 */
		public void setTestInteger(final java.lang.Integer testInteger) {
			this.getMap().put(Entity.ID_TEST_INTEGER, testInteger);
		}

		/**
		 * long最小値を取得します。
		 * @return long最小値。
		 */
		public java.lang.Long getTestBigint() {
			return NumberUtil.longValue(this.getMap().get(Entity.ID_TEST_BIGINT));
		}

		/**
		 * long最小値を設定します。
		 * @param testBigint long最小値。
		 */
		public void setTestBigint(final java.lang.Long testBigint) {
			this.getMap().put(Entity.ID_TEST_BIGINT, testBigint);
		}

		/**
		 * double合計を取得します。
		 * @return double合計。
		 */
		public java.lang.Double getTestDouble() {
			return (java.lang.Double) this.getMap().get(Entity.ID_TEST_DOUBLE);
		}

		/**
		 * double合計を設定します。
		 * @param testDouble double合計。
		 */
		public void setTestDouble(final java.lang.Double testDouble) {
			this.getMap().put(Entity.ID_TEST_DOUBLE, testDouble);
		}

		/**
		 * Numeric平均を取得します。
		 * @return Numeric平均。
		 */
		public java.math.BigDecimal getTestNumeric() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_TEST_NUMERIC);
		}

		/**
		 * Numeric平均を設定します。
		 * @param testNumeric Numeric平均。
		 */
		public void setTestNumeric(final java.math.BigDecimal testNumeric) {
			this.getMap().put(Entity.ID_TEST_NUMERIC, testNumeric);
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
	 * 件数フィールドを取得します。
	 * @return 件数フィールド。
	 */
	public BigintField getCntField() {
		return (BigintField) this.getField(Entity.ID_CNT);
	}

	/**
	 * Smallint平均フィールドを取得します。
	 * @return Smallint平均フィールド。
	 */
	public TestSmallintField getTestSmallintField() {
		return (TestSmallintField) this.getField(Entity.ID_TEST_SMALLINT);
	}

	/**
	 * int最大値フィールドを取得します。
	 * @return int最大値フィールド。
	 */
	public TestIntegerField getTestIntegerField() {
		return (TestIntegerField) this.getField(Entity.ID_TEST_INTEGER);
	}

	/**
	 * long最小値フィールドを取得します。
	 * @return long最小値フィールド。
	 */
	public TestBigintField getTestBigintField() {
		return (TestBigintField) this.getField(Entity.ID_TEST_BIGINT);
	}

	/**
	 * double合計フィールドを取得します。
	 * @return double合計フィールド。
	 */
	public TestDoubleField getTestDoubleField() {
		return (TestDoubleField) this.getField(Entity.ID_TEST_DOUBLE);
	}

	/**
	 * Numeric平均フィールドを取得します。
	 * @return Numeric平均フィールド。
	 */
	public TestNumericField getTestNumericField() {
		return (TestNumericField) this.getField(Entity.ID_TEST_NUMERIC);
	}


}