package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import test.field.TestDoubleField;
import test.field.TestMultiRecIdField;
import test.field.Code2Field;
import dataforms.util.NumberUtil;
import dataforms.field.common.SortOrderField;
import test.field.Code1Field;
import test.field.TestNumericField;
import test.field.TestBigintField;
import test.field.ContentsField;
import test.field.TestSmallintField;
import test.field.TestIntegerField;


/**
 * 複数レコード編集テストテーブルクラス。
 *
 */
public class TestMultiRecTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public TestMultiRecTable() {
		this.setAutoIncrementId(true);
		this.setComment("複数レコード編集テストテーブル");
		this.addPkField(new TestMultiRecIdField()); //レコードID
		this.addField(new SortOrderField()); //ソート順
		this.addField(new Code1Field()); //コード1
		this.addField(new Code2Field()); //コード2
		this.addField(new ContentsField()); //内容
		this.addField(new TestSmallintField()); //2バイト整数
		this.addField(new TestIntegerField()); //4バイト整数
		this.addField(new TestBigintField()); //8バイト整数
		this.addField(new TestDoubleField()); //不動小数点実数
		this.addField(new TestNumericField()); //10進数
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		TestMultiRecTableRelation r = new TestMultiRecTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** レコードIDのフィールドID。 */
		public static final String ID_TEST_MULTI_REC_ID = "testMultiRecId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** コード2のフィールドID。 */
		public static final String ID_CODE2 = "code2";
		/** 内容のフィールドID。 */
		public static final String ID_CONTENTS = "contents";
		/** 2バイト整数のフィールドID。 */
		public static final String ID_TEST_SMALLINT = "testSmallint";
		/** 4バイト整数のフィールドID。 */
		public static final String ID_TEST_INTEGER = "testInteger";
		/** 8バイト整数のフィールドID。 */
		public static final String ID_TEST_BIGINT = "testBigint";
		/** 不動小数点実数のフィールドID。 */
		public static final String ID_TEST_DOUBLE = "testDouble";
		/** 10進数のフィールドID。 */
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
		 * レコードIDを取得します。
		 * @return レコードID。
		 */
		public java.lang.Long getTestMultiRecId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_TEST_MULTI_REC_ID));
		}

		/**
		 * レコードIDを設定します。
		 * @param testMultiRecId レコードID。
		 */
		public void setTestMultiRecId(final java.lang.Long testMultiRecId) {
			this.getMap().put(Entity.ID_TEST_MULTI_REC_ID, testMultiRecId);
		}

		/**
		 * ソート順を取得します。
		 * @return ソート順。
		 */
		public java.lang.Short getSortOrder() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SORT_ORDER));
		}

		/**
		 * ソート順を設定します。
		 * @param sortOrder ソート順。
		 */
		public void setSortOrder(final java.lang.Short sortOrder) {
			this.getMap().put(Entity.ID_SORT_ORDER, sortOrder);
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
		 * 内容を取得します。
		 * @return 内容。
		 */
		public java.lang.String getContents() {
			return (java.lang.String) this.getMap().get(Entity.ID_CONTENTS);
		}

		/**
		 * 内容を設定します。
		 * @param contents 内容。
		 */
		public void setContents(final java.lang.String contents) {
			this.getMap().put(Entity.ID_CONTENTS, contents);
		}

		/**
		 * 2バイト整数を取得します。
		 * @return 2バイト整数。
		 */
		public java.lang.Short getTestSmallint() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_TEST_SMALLINT));
		}

		/**
		 * 2バイト整数を設定します。
		 * @param testSmallint 2バイト整数。
		 */
		public void setTestSmallint(final java.lang.Short testSmallint) {
			this.getMap().put(Entity.ID_TEST_SMALLINT, testSmallint);
		}

		/**
		 * 4バイト整数を取得します。
		 * @return 4バイト整数。
		 */
		public java.lang.Integer getTestInteger() {
			return NumberUtil.integerValueObject(this.getMap().get(Entity.ID_TEST_INTEGER));
		}

		/**
		 * 4バイト整数を設定します。
		 * @param testInteger 4バイト整数。
		 */
		public void setTestInteger(final java.lang.Integer testInteger) {
			this.getMap().put(Entity.ID_TEST_INTEGER, testInteger);
		}

		/**
		 * 8バイト整数を取得します。
		 * @return 8バイト整数。
		 */
		public java.lang.Long getTestBigint() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_TEST_BIGINT));
		}

		/**
		 * 8バイト整数を設定します。
		 * @param testBigint 8バイト整数。
		 */
		public void setTestBigint(final java.lang.Long testBigint) {
			this.getMap().put(Entity.ID_TEST_BIGINT, testBigint);
		}

		/**
		 * 不動小数点実数を取得します。
		 * @return 不動小数点実数。
		 */
		public java.lang.Double getTestDouble() {
			return (java.lang.Double) this.getMap().get(Entity.ID_TEST_DOUBLE);
		}

		/**
		 * 不動小数点実数を設定します。
		 * @param testDouble 不動小数点実数。
		 */
		public void setTestDouble(final java.lang.Double testDouble) {
			this.getMap().put(Entity.ID_TEST_DOUBLE, testDouble);
		}

		/**
		 * 10進数を取得します。
		 * @return 10進数。
		 */
		public java.math.BigDecimal getTestNumeric() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_TEST_NUMERIC);
		}

		/**
		 * 10進数を設定します。
		 * @param testNumeric 10進数。
		 */
		public void setTestNumeric(final java.math.BigDecimal testNumeric) {
			this.getMap().put(Entity.ID_TEST_NUMERIC, testNumeric);
		}


	}
	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public TestMultiRecIdField getTestMultiRecIdField() {
		return (TestMultiRecIdField) this.getField(Entity.ID_TEST_MULTI_REC_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
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
	 * 内容フィールドを取得します。
	 * @return 内容フィールド。
	 */
	public ContentsField getContentsField() {
		return (ContentsField) this.getField(Entity.ID_CONTENTS);
	}

	/**
	 * 2バイト整数フィールドを取得します。
	 * @return 2バイト整数フィールド。
	 */
	public TestSmallintField getTestSmallintField() {
		return (TestSmallintField) this.getField(Entity.ID_TEST_SMALLINT);
	}

	/**
	 * 4バイト整数フィールドを取得します。
	 * @return 4バイト整数フィールド。
	 */
	public TestIntegerField getTestIntegerField() {
		return (TestIntegerField) this.getField(Entity.ID_TEST_INTEGER);
	}

	/**
	 * 8バイト整数フィールドを取得します。
	 * @return 8バイト整数フィールド。
	 */
	public TestBigintField getTestBigintField() {
		return (TestBigintField) this.getField(Entity.ID_TEST_BIGINT);
	}

	/**
	 * 不動小数点実数フィールドを取得します。
	 * @return 不動小数点実数フィールド。
	 */
	public TestDoubleField getTestDoubleField() {
		return (TestDoubleField) this.getField(Entity.ID_TEST_DOUBLE);
	}

	/**
	 * 10進数フィールドを取得します。
	 * @return 10進数フィールド。
	 */
	public TestNumericField getTestNumericField() {
		return (TestNumericField) this.getField(Entity.ID_TEST_NUMERIC);
	}


}
