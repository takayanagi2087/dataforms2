
package fieldtest.dao;

import java.util.Map;

import dataforms.dao.Table;
import dataforms.util.NumberUtil;
import fieldtest.field.SqlCharField;
import fieldtest.field.SqlClobField;
import fieldtest.field.SqlDateField;
import fieldtest.field.SqlDoubleField;
import fieldtest.field.SqlIntegerField;
import fieldtest.field.SqlNumericField;
import fieldtest.field.SqlSmallintField;
import fieldtest.field.SqlTimeField;
import fieldtest.field.SqlTimestampField;
import fieldtest.field.SqlTypeIdField;
import fieldtest.field.SqlVarcharField;


/**
 * SQLデータ型のテストフィールドクラス。
 *
 */
public class SqlTypeTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public SqlTypeTable() {
		this.setAutoIncrementId(true);
		this.setComment("SQLデータ型のテストフィールド");
		this.addPkField(new SqlTypeIdField()).setNotNull(true); //レコードID
		this.addField(new SqlCharField()); //CHARフィールド
		this.addField(new SqlVarcharField()); //VarcharField
		this.addField(new SqlSmallintField()); //SMALLINTフィールド
		this.addField(new SqlIntegerField()); //INTEGERフィールド
		this.addField(new SqlDoubleField()); //DOUBLEフィールド
		this.addField(new SqlNumericField()); //NUMERICフィールド
		this.addField(new SqlDateField()); //DATEフィールド
		this.addField(new SqlTimeField()); //TIMEフィールド
		this.addField(new SqlTimestampField()); //TIMESTAMPフィールド
		this.addField(new SqlClobField()); //CLOBフィールド
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
		/** CHARフィールドのフィールドID。 */
		public static final String ID_SQL_CHAR = "sqlChar";
		/** VarcharFieldのフィールドID。 */
		public static final String ID_SQL_VARCHAR = "sqlVarchar";
		/** SMALLINTフィールドのフィールドID。 */
		public static final String ID_SQL_SMALLINT = "sqlSmallint";
		/** INTEGERフィールドのフィールドID。 */
		public static final String ID_SQL_INTEGER = "sqlInteger";
		/** DOUBLEフィールドのフィールドID。 */
		public static final String ID_SQL_DOUBLE = "sqlDouble";
		/** NUMERICフィールドのフィールドID。 */
		public static final String ID_SQL_NUMERIC = "sqlNumeric";
		/** DATEフィールドのフィールドID。 */
		public static final String ID_SQL_DATE = "sqlDate";
		/** TIMEフィールドのフィールドID。 */
		public static final String ID_SQL_TIME = "sqlTime";
		/** TIMESTAMPフィールドのフィールドID。 */
		public static final String ID_SQL_TIMESTAMP = "sqlTimestamp";
		/** CLOBフィールドのフィールドID。 */
		public static final String ID_SQL_CLOB = "sqlClob";

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
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_SQL_TYPE_ID));
		}

		/**
		 * レコードIDを設定します。
		 * @param sqlTypeId レコードID。
		 */
		public void setSqlTypeId(final java.lang.Long sqlTypeId) {
			this.getMap().put(Entity.ID_SQL_TYPE_ID, sqlTypeId);
		}

		/**
		 * CHARフィールドを取得します。
		 * @return CHARフィールド。
		 */
		public java.lang.String getSqlChar() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CHAR);
		}

		/**
		 * CHARフィールドを設定します。
		 * @param sqlChar CHARフィールド。
		 */
		public void setSqlChar(final java.lang.String sqlChar) {
			this.getMap().put(Entity.ID_SQL_CHAR, sqlChar);
		}

		/**
		 * VarcharFieldを取得します。
		 * @return VarcharField。
		 */
		public java.lang.String getSqlVarchar() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_VARCHAR);
		}

		/**
		 * VarcharFieldを設定します。
		 * @param sqlVarchar VarcharField。
		 */
		public void setSqlVarchar(final java.lang.String sqlVarchar) {
			this.getMap().put(Entity.ID_SQL_VARCHAR, sqlVarchar);
		}

		/**
		 * SMALLINTフィールドを取得します。
		 * @return SMALLINTフィールド。
		 */
		public java.lang.Short getSqlSmallint() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SQL_SMALLINT));
		}

		/**
		 * SMALLINTフィールドを設定します。
		 * @param sqlSmallint SMALLINTフィールド。
		 */
		public void setSqlSmallint(final java.lang.Short sqlSmallint) {
			this.getMap().put(Entity.ID_SQL_SMALLINT, sqlSmallint);
		}

		/**
		 * INTEGERフィールドを取得します。
		 * @return INTEGERフィールド。
		 */
		public java.lang.Integer getSqlInteger() {
			return NumberUtil.integerValueObject(this.getMap().get(Entity.ID_SQL_INTEGER));
		}

		/**
		 * INTEGERフィールドを設定します。
		 * @param sqlInteger INTEGERフィールド。
		 */
		public void setSqlInteger(final java.lang.Integer sqlInteger) {
			this.getMap().put(Entity.ID_SQL_INTEGER, sqlInteger);
		}

		/**
		 * DOUBLEフィールドを取得します。
		 * @return DOUBLEフィールド。
		 */
		public java.lang.Double getSqlDouble() {
			return (java.lang.Double) this.getMap().get(Entity.ID_SQL_DOUBLE);
		}

		/**
		 * DOUBLEフィールドを設定します。
		 * @param sqlDouble DOUBLEフィールド。
		 */
		public void setSqlDouble(final java.lang.Double sqlDouble) {
			this.getMap().put(Entity.ID_SQL_DOUBLE, sqlDouble);
		}

		/**
		 * NUMERICフィールドを取得します。
		 * @return NUMERICフィールド。
		 */
		public java.math.BigDecimal getSqlNumeric() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_SQL_NUMERIC);
		}

		/**
		 * NUMERICフィールドを設定します。
		 * @param sqlNumeric NUMERICフィールド。
		 */
		public void setSqlNumeric(final java.math.BigDecimal sqlNumeric) {
			this.getMap().put(Entity.ID_SQL_NUMERIC, sqlNumeric);
		}

		/**
		 * DATEフィールドを取得します。
		 * @return DATEフィールド。
		 */
		public java.sql.Date getSqlDate() {
			return (java.sql.Date) this.getMap().get(Entity.ID_SQL_DATE);
		}

		/**
		 * DATEフィールドを設定します。
		 * @param sqlDate DATEフィールド。
		 */
		public void setSqlDate(final java.sql.Date sqlDate) {
			this.getMap().put(Entity.ID_SQL_DATE, sqlDate);
		}

		/**
		 * TIMEフィールドを取得します。
		 * @return TIMEフィールド。
		 */
		public java.sql.Time getSqlTime() {
			return (java.sql.Time) this.getMap().get(Entity.ID_SQL_TIME);
		}

		/**
		 * TIMEフィールドを設定します。
		 * @param sqlTime TIMEフィールド。
		 */
		public void setSqlTime(final java.sql.Time sqlTime) {
			this.getMap().put(Entity.ID_SQL_TIME, sqlTime);
		}

		/**
		 * TIMESTAMPフィールドを取得します。
		 * @return TIMESTAMPフィールド。
		 */
		public java.sql.Timestamp getSqlTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_SQL_TIMESTAMP);
		}

		/**
		 * TIMESTAMPフィールドを設定します。
		 * @param sqlTimestamp TIMESTAMPフィールド。
		 */
		public void setSqlTimestamp(final java.sql.Timestamp sqlTimestamp) {
			this.getMap().put(Entity.ID_SQL_TIMESTAMP, sqlTimestamp);
		}

		/**
		 * CLOBフィールドを取得します。
		 * @return CLOBフィールド。
		 */
		public java.lang.String getSqlClob() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CLOB);
		}

		/**
		 * CLOBフィールドを設定します。
		 * @param sqlClob CLOBフィールド。
		 */
		public void setSqlClob(final java.lang.String sqlClob) {
			this.getMap().put(Entity.ID_SQL_CLOB, sqlClob);
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
	 * CHARフィールドフィールドを取得します。
	 * @return CHARフィールドフィールド。
	 */
	public SqlCharField getSqlCharField() {
		return (SqlCharField) this.getField(Entity.ID_SQL_CHAR);
	}

	/**
	 * VarcharFieldフィールドを取得します。
	 * @return VarcharFieldフィールド。
	 */
	public SqlVarcharField getSqlVarcharField() {
		return (SqlVarcharField) this.getField(Entity.ID_SQL_VARCHAR);
	}

	/**
	 * SMALLINTフィールドフィールドを取得します。
	 * @return SMALLINTフィールドフィールド。
	 */
	public SqlSmallintField getSqlSmallintField() {
		return (SqlSmallintField) this.getField(Entity.ID_SQL_SMALLINT);
	}

	/**
	 * INTEGERフィールドフィールドを取得します。
	 * @return INTEGERフィールドフィールド。
	 */
	public SqlIntegerField getSqlIntegerField() {
		return (SqlIntegerField) this.getField(Entity.ID_SQL_INTEGER);
	}

	/**
	 * DOUBLEフィールドフィールドを取得します。
	 * @return DOUBLEフィールドフィールド。
	 */
	public SqlDoubleField getSqlDoubleField() {
		return (SqlDoubleField) this.getField(Entity.ID_SQL_DOUBLE);
	}

	/**
	 * NUMERICフィールドフィールドを取得します。
	 * @return NUMERICフィールドフィールド。
	 */
	public SqlNumericField getSqlNumericField() {
		return (SqlNumericField) this.getField(Entity.ID_SQL_NUMERIC);
	}

	/**
	 * DATEフィールドフィールドを取得します。
	 * @return DATEフィールドフィールド。
	 */
	public SqlDateField getSqlDateField() {
		return (SqlDateField) this.getField(Entity.ID_SQL_DATE);
	}

	/**
	 * TIMEフィールドフィールドを取得します。
	 * @return TIMEフィールドフィールド。
	 */
	public SqlTimeField getSqlTimeField() {
		return (SqlTimeField) this.getField(Entity.ID_SQL_TIME);
	}

	/**
	 * TIMESTAMPフィールドフィールドを取得します。
	 * @return TIMESTAMPフィールドフィールド。
	 */
	public SqlTimestampField getSqlTimestampField() {
		return (SqlTimestampField) this.getField(Entity.ID_SQL_TIMESTAMP);
	}

	/**
	 * CLOBフィールドフィールドを取得します。
	 * @return CLOBフィールドフィールド。
	 */
	public SqlClobField getSqlClobField() {
		return (SqlClobField) this.getField(Entity.ID_SQL_CLOB);
	}



}
