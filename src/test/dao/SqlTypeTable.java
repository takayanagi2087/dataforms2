package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import test.field.SqlBigintField;
import test.field.SqlVarcharField;
import test.field.SqlNumericField;
import test.field.SqlClobField;
import test.field.SqlTimestampField;
import test.field.SqlDoubleField;
import test.field.SqlTypeIdField;
import test.field.SqlIntegerField;
import test.field.SqlTimeField;
import test.field.SqlCharField;
import dataforms.util.NumberUtil;
import test.field.SqlDateField;
import test.field.AddressField;
import dataforms.field.common.ZipCodeField;
import test.field.SqlSmallintField;


/**
 * SQLデータ型テストテーブルクラス。
 *
 */
public class SqlTypeTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public SqlTypeTable() {
		this.setAutoIncrementId(true);
		this.setComment("SQLデータ型テストテーブル");
		this.addPkField(new SqlTypeIdField()); //レコードID
		this.addField(new SqlCharField()); //Charフィールド
		this.addField(new SqlVarcharField()); //Varcharフィールド
		this.addField(new SqlSmallintField()); //Smallintフィールド
		this.addField(new SqlIntegerField()); //Integerフィールド
		this.addField(new SqlBigintField()); //Bigintフィールド
		this.addField(new SqlDoubleField()); //Doubleフィールド
		this.addField(new SqlNumericField()); //Numericフィールド
		this.addField(new SqlDateField()); //Dateフィールド
		this.addField(new SqlTimeField()); //Timeフィールド
		this.addField(new SqlTimestampField()); //Timestampフィールド
		this.addField(new SqlClobField()); //Clobフィールド
		this.addField(new ZipCodeField()); //郵便番号
		this.addField(new AddressField()); //住所
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		SqlTypeTableRelation r = new SqlTypeTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** レコードIDのフィールドID。 */
		public static final String ID_SQL_TYPE_ID = "sqlTypeId";
		/** CharフィールドのフィールドID。 */
		public static final String ID_SQL_CHAR = "sqlChar";
		/** VarcharフィールドのフィールドID。 */
		public static final String ID_SQL_VARCHAR = "sqlVarchar";
		/** SmallintフィールドのフィールドID。 */
		public static final String ID_SQL_SMALLINT = "sqlSmallint";
		/** IntegerフィールドのフィールドID。 */
		public static final String ID_SQL_INTEGER = "sqlInteger";
		/** BigintフィールドのフィールドID。 */
		public static final String ID_SQL_BIGINT = "sqlBigint";
		/** DoubleフィールドのフィールドID。 */
		public static final String ID_SQL_DOUBLE = "sqlDouble";
		/** NumericフィールドのフィールドID。 */
		public static final String ID_SQL_NUMERIC = "sqlNumeric";
		/** DateフィールドのフィールドID。 */
		public static final String ID_SQL_DATE = "sqlDate";
		/** TimeフィールドのフィールドID。 */
		public static final String ID_SQL_TIME = "sqlTime";
		/** TimestampフィールドのフィールドID。 */
		public static final String ID_SQL_TIMESTAMP = "sqlTimestamp";
		/** ClobフィールドのフィールドID。 */
		public static final String ID_SQL_CLOB = "sqlClob";
		/** 郵便番号のフィールドID。 */
		public static final String ID_ZIP_CODE = "zipCode";
		/** 住所のフィールドID。 */
		public static final String ID_ADDRESS = "address";

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
		public java.lang.Long getSqlTypeId() {
			return NumberUtil.longValue(this.getMap().get(Entity.ID_SQL_TYPE_ID));
		}

		/**
		 * レコードIDを設定します。
		 * @param sqlTypeId レコードID。
		 */
		public void setSqlTypeId(final java.lang.Long sqlTypeId) {
			this.getMap().put(Entity.ID_SQL_TYPE_ID, sqlTypeId);
		}

		/**
		 * Charフィールドを取得します。
		 * @return Charフィールド。
		 */
		public java.lang.String getSqlChar() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CHAR);
		}

		/**
		 * Charフィールドを設定します。
		 * @param sqlChar Charフィールド。
		 */
		public void setSqlChar(final java.lang.String sqlChar) {
			this.getMap().put(Entity.ID_SQL_CHAR, sqlChar);
		}

		/**
		 * Varcharフィールドを取得します。
		 * @return Varcharフィールド。
		 */
		public java.lang.String getSqlVarchar() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_VARCHAR);
		}

		/**
		 * Varcharフィールドを設定します。
		 * @param sqlVarchar Varcharフィールド。
		 */
		public void setSqlVarchar(final java.lang.String sqlVarchar) {
			this.getMap().put(Entity.ID_SQL_VARCHAR, sqlVarchar);
		}

		/**
		 * Smallintフィールドを取得します。
		 * @return Smallintフィールド。
		 */
		public java.lang.Short getSqlSmallint() {
			return NumberUtil.shortValue(this.getMap().get(Entity.ID_SQL_SMALLINT));
		}

		/**
		 * Smallintフィールドを設定します。
		 * @param sqlSmallint Smallintフィールド。
		 */
		public void setSqlSmallint(final java.lang.Short sqlSmallint) {
			this.getMap().put(Entity.ID_SQL_SMALLINT, sqlSmallint);
		}

		/**
		 * Integerフィールドを取得します。
		 * @return Integerフィールド。
		 */
		public java.lang.Integer getSqlInteger() {
			return NumberUtil.intValue(this.getMap().get(Entity.ID_SQL_INTEGER));
		}

		/**
		 * Integerフィールドを設定します。
		 * @param sqlInteger Integerフィールド。
		 */
		public void setSqlInteger(final java.lang.Integer sqlInteger) {
			this.getMap().put(Entity.ID_SQL_INTEGER, sqlInteger);
		}

		/**
		 * Bigintフィールドを取得します。
		 * @return Bigintフィールド。
		 */
		public java.lang.Long getSqlBigint() {
			return NumberUtil.longValue(this.getMap().get(Entity.ID_SQL_BIGINT));
		}

		/**
		 * Bigintフィールドを設定します。
		 * @param sqlBigint Bigintフィールド。
		 */
		public void setSqlBigint(final java.lang.Long sqlBigint) {
			this.getMap().put(Entity.ID_SQL_BIGINT, sqlBigint);
		}

		/**
		 * Doubleフィールドを取得します。
		 * @return Doubleフィールド。
		 */
		public java.lang.Double getSqlDouble() {
			return (java.lang.Double) this.getMap().get(Entity.ID_SQL_DOUBLE);
		}

		/**
		 * Doubleフィールドを設定します。
		 * @param sqlDouble Doubleフィールド。
		 */
		public void setSqlDouble(final java.lang.Double sqlDouble) {
			this.getMap().put(Entity.ID_SQL_DOUBLE, sqlDouble);
		}

		/**
		 * Numericフィールドを取得します。
		 * @return Numericフィールド。
		 */
		public java.math.BigDecimal getSqlNumeric() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_SQL_NUMERIC);
		}

		/**
		 * Numericフィールドを設定します。
		 * @param sqlNumeric Numericフィールド。
		 */
		public void setSqlNumeric(final java.math.BigDecimal sqlNumeric) {
			this.getMap().put(Entity.ID_SQL_NUMERIC, sqlNumeric);
		}

		/**
		 * Dateフィールドを取得します。
		 * @return Dateフィールド。
		 */
		public java.sql.Date getSqlDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_SQL_DATE);
		}

		/**
		 * Dateフィールドを設定します。
		 * @param sqlDate Dateフィールド。
		 */
		public void setSqlDate(final java.sql.Date sqlDate) {
			this.getMap().put(Entity.ID_SQL_DATE, sqlDate);
		}

		/**
		 * Timeフィールドを取得します。
		 * @return Timeフィールド。
		 */
		public java.sql.Time getSqlTime() {
			return (java.sql.Time) this.getMap().get(Entity.ID_SQL_TIME);
		}

		/**
		 * Timeフィールドを設定します。
		 * @param sqlTime Timeフィールド。
		 */
		public void setSqlTime(final java.sql.Time sqlTime) {
			this.getMap().put(Entity.ID_SQL_TIME, sqlTime);
		}

		/**
		 * Timestampフィールドを取得します。
		 * @return Timestampフィールド。
		 */
		public java.sql.Timestamp getSqlTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_SQL_TIMESTAMP);
		}

		/**
		 * Timestampフィールドを設定します。
		 * @param sqlTimestamp Timestampフィールド。
		 */
		public void setSqlTimestamp(final java.sql.Timestamp sqlTimestamp) {
			this.getMap().put(Entity.ID_SQL_TIMESTAMP, sqlTimestamp);
		}

		/**
		 * Clobフィールドを取得します。
		 * @return Clobフィールド。
		 */
		public java.lang.String getSqlClob() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CLOB);
		}

		/**
		 * Clobフィールドを設定します。
		 * @param sqlClob Clobフィールド。
		 */
		public void setSqlClob(final java.lang.String sqlClob) {
			this.getMap().put(Entity.ID_SQL_CLOB, sqlClob);
		}

		/**
		 * 郵便番号を取得します。
		 * @return 郵便番号。
		 */
		public java.lang.String getZipCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_ZIP_CODE);
		}

		/**
		 * 郵便番号を設定します。
		 * @param zipCode 郵便番号。
		 */
		public void setZipCode(final java.lang.String zipCode) {
			this.getMap().put(Entity.ID_ZIP_CODE, zipCode);
		}

		/**
		 * 住所を取得します。
		 * @return 住所。
		 */
		public java.lang.String getAddress() {
			return (java.lang.String) this.getMap().get(Entity.ID_ADDRESS);
		}

		/**
		 * 住所を設定します。
		 * @param address 住所。
		 */
		public void setAddress(final java.lang.String address) {
			this.getMap().put(Entity.ID_ADDRESS, address);
		}


	}
	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public SqlTypeIdField getSqlTypeIdField() {
		return (SqlTypeIdField) this.getField(Entity.ID_SQL_TYPE_ID);
	}

	/**
	 * Charフィールドフィールドを取得します。
	 * @return Charフィールドフィールド。
	 */
	public SqlCharField getSqlCharField() {
		return (SqlCharField) this.getField(Entity.ID_SQL_CHAR);
	}

	/**
	 * Varcharフィールドフィールドを取得します。
	 * @return Varcharフィールドフィールド。
	 */
	public SqlVarcharField getSqlVarcharField() {
		return (SqlVarcharField) this.getField(Entity.ID_SQL_VARCHAR);
	}

	/**
	 * Smallintフィールドフィールドを取得します。
	 * @return Smallintフィールドフィールド。
	 */
	public SqlSmallintField getSqlSmallintField() {
		return (SqlSmallintField) this.getField(Entity.ID_SQL_SMALLINT);
	}

	/**
	 * Integerフィールドフィールドを取得します。
	 * @return Integerフィールドフィールド。
	 */
	public SqlIntegerField getSqlIntegerField() {
		return (SqlIntegerField) this.getField(Entity.ID_SQL_INTEGER);
	}

	/**
	 * Bigintフィールドフィールドを取得します。
	 * @return Bigintフィールドフィールド。
	 */
	public SqlBigintField getSqlBigintField() {
		return (SqlBigintField) this.getField(Entity.ID_SQL_BIGINT);
	}

	/**
	 * Doubleフィールドフィールドを取得します。
	 * @return Doubleフィールドフィールド。
	 */
	public SqlDoubleField getSqlDoubleField() {
		return (SqlDoubleField) this.getField(Entity.ID_SQL_DOUBLE);
	}

	/**
	 * Numericフィールドフィールドを取得します。
	 * @return Numericフィールドフィールド。
	 */
	public SqlNumericField getSqlNumericField() {
		return (SqlNumericField) this.getField(Entity.ID_SQL_NUMERIC);
	}

	/**
	 * Dateフィールドフィールドを取得します。
	 * @return Dateフィールドフィールド。
	 */
	public SqlDateField getSqlDateField() {
		return (SqlDateField) this.getField(Entity.ID_SQL_DATE);
	}

	/**
	 * Timeフィールドフィールドを取得します。
	 * @return Timeフィールドフィールド。
	 */
	public SqlTimeField getSqlTimeField() {
		return (SqlTimeField) this.getField(Entity.ID_SQL_TIME);
	}

	/**
	 * Timestampフィールドフィールドを取得します。
	 * @return Timestampフィールドフィールド。
	 */
	public SqlTimestampField getSqlTimestampField() {
		return (SqlTimestampField) this.getField(Entity.ID_SQL_TIMESTAMP);
	}

	/**
	 * Clobフィールドフィールドを取得します。
	 * @return Clobフィールドフィールド。
	 */
	public SqlClobField getSqlClobField() {
		return (SqlClobField) this.getField(Entity.ID_SQL_CLOB);
	}

	/**
	 * 郵便番号フィールドを取得します。
	 * @return 郵便番号フィールド。
	 */
	public ZipCodeField getZipCodeField() {
		return (ZipCodeField) this.getField(Entity.ID_ZIP_CODE);
	}

	/**
	 * 住所フィールドを取得します。
	 * @return 住所フィールド。
	 */
	public AddressField getAddressField() {
		return (AddressField) this.getField(Entity.ID_ADDRESS);
	}


}
