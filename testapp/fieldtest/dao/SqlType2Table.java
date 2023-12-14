package fieldtest.dao;

import java.util.Map;
import dataforms.dao.Table;
import fieldtest.field.SqlSmallintField;
import fieldtest.field.SqlType2IdField;
import fieldtest.field.SqlVarcharField;
import fieldtest.field.SqlTypeIdField;
import fieldtest.field.SqlDateField;
import fieldtest.field.SqlClobField;
import fieldtest.field.SqlIntegerField;
import fieldtest.field.SqlTimestampField;
import fieldtest.field.SqlDoubleField;
import dataforms.util.NumberUtil;
import fieldtest.field.SqlCharField;
import fieldtest.field.SqlTimeField;
import fieldtest.field.SqlNumericField;


/**
 * SQLデータ型テーブル2クラス。
 *
 */
public class SqlType2Table extends Table {
	/**
	 * コンストラクタ。
	 */
	public SqlType2Table() {
		this.setAutoIncrementId(true);
		this.setComment("SQLデータ型テーブル2");
		this.addPkField(new SqlType2IdField()).setNotNull(true); //SQLデータ型2ID
		this.addField(new SqlTypeIdField()); //レコードID
		this.addField(new SqlCharField("sqlChar2")); //CHARフィールド
		this.addField(new SqlVarcharField("sqlVarchar2")); //VarcharField
		this.addField(new SqlSmallintField("sqlSmallint2")); //SMALLINTフィールド
		this.addField(new SqlIntegerField("sqlInteger2")); //INTEGERフィールド
		this.addField(new SqlDoubleField("sqlDouble2")); //DOUBLEフィールド
		this.addField(new SqlNumericField("sqlNumeric2")); //NUMERICフィールド
		this.addField(new SqlDateField("sqlDate2")); //DATEフィールド
		this.addField(new SqlTimeField("sqlTime2")); //TIMEフィールド
		this.addField(new SqlTimestampField("sqlTimestamp2")); //TIMESTAMPフィールド
		this.addField(new SqlClobField("sqlClob2")); //CLOBフィールド
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		SqlType2TableRelation r = new SqlType2TableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** SQLデータ型2IDのフィールドID。 */
		public static final String ID_SQL_TYPE2_ID = "sqlType2Id";
		/** レコードIDのフィールドID。 */
		public static final String ID_SQL_TYPE_ID = "sqlTypeId";
		/** CHARフィールドのフィールドID。 */
		public static final String ID_SQL_CHAR2 = "sqlChar2";
		/** VarcharFieldのフィールドID。 */
		public static final String ID_SQL_VARCHAR2 = "sqlVarchar2";
		/** SMALLINTフィールドのフィールドID。 */
		public static final String ID_SQL_SMALLINT2 = "sqlSmallint2";
		/** INTEGERフィールドのフィールドID。 */
		public static final String ID_SQL_INTEGER2 = "sqlInteger2";
		/** DOUBLEフィールドのフィールドID。 */
		public static final String ID_SQL_DOUBLE2 = "sqlDouble2";
		/** NUMERICフィールドのフィールドID。 */
		public static final String ID_SQL_NUMERIC2 = "sqlNumeric2";
		/** DATEフィールドのフィールドID。 */
		public static final String ID_SQL_DATE2 = "sqlDate2";
		/** TIMEフィールドのフィールドID。 */
		public static final String ID_SQL_TIME2 = "sqlTime2";
		/** TIMESTAMPフィールドのフィールドID。 */
		public static final String ID_SQL_TIMESTAMP2 = "sqlTimestamp2";
		/** CLOBフィールドのフィールドID。 */
		public static final String ID_SQL_CLOB2 = "sqlClob2";

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
		 * SQLデータ型2IDを取得します。
		 * @return SQLデータ型2ID。
		 */
		public java.lang.Long getSqlType2Id() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_SQL_TYPE2_ID));
		}

		/**
		 * SQLデータ型2IDを設定します。
		 * @param sqlType2Id SQLデータ型2ID。
		 */
		public void setSqlType2Id(final java.lang.Long sqlType2Id) {
			this.getMap().put(Entity.ID_SQL_TYPE2_ID, sqlType2Id);
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
		public java.lang.String getSqlChar2() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CHAR2);
		}

		/**
		 * CHARフィールドを設定します。
		 * @param sqlChar2 CHARフィールド。
		 */
		public void setSqlChar2(final java.lang.String sqlChar2) {
			this.getMap().put(Entity.ID_SQL_CHAR2, sqlChar2);
		}

		/**
		 * VarcharFieldを取得します。
		 * @return VarcharField。
		 */
		public java.lang.String getSqlVarchar2() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_VARCHAR2);
		}

		/**
		 * VarcharFieldを設定します。
		 * @param sqlVarchar2 VarcharField。
		 */
		public void setSqlVarchar2(final java.lang.String sqlVarchar2) {
			this.getMap().put(Entity.ID_SQL_VARCHAR2, sqlVarchar2);
		}

		/**
		 * SMALLINTフィールドを取得します。
		 * @return SMALLINTフィールド。
		 */
		public java.lang.Short getSqlSmallint2() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SQL_SMALLINT2));
		}

		/**
		 * SMALLINTフィールドを設定します。
		 * @param sqlSmallint2 SMALLINTフィールド。
		 */
		public void setSqlSmallint2(final java.lang.Short sqlSmallint2) {
			this.getMap().put(Entity.ID_SQL_SMALLINT2, sqlSmallint2);
		}

		/**
		 * INTEGERフィールドを取得します。
		 * @return INTEGERフィールド。
		 */
		public java.lang.Integer getSqlInteger2() {
			return NumberUtil.integerValueObject(this.getMap().get(Entity.ID_SQL_INTEGER2));
		}

		/**
		 * INTEGERフィールドを設定します。
		 * @param sqlInteger2 INTEGERフィールド。
		 */
		public void setSqlInteger2(final java.lang.Integer sqlInteger2) {
			this.getMap().put(Entity.ID_SQL_INTEGER2, sqlInteger2);
		}

		/**
		 * DOUBLEフィールドを取得します。
		 * @return DOUBLEフィールド。
		 */
		public java.lang.Double getSqlDouble2() {
			return (java.lang.Double) this.getMap().get(Entity.ID_SQL_DOUBLE2);
		}

		/**
		 * DOUBLEフィールドを設定します。
		 * @param sqlDouble2 DOUBLEフィールド。
		 */
		public void setSqlDouble2(final java.lang.Double sqlDouble2) {
			this.getMap().put(Entity.ID_SQL_DOUBLE2, sqlDouble2);
		}

		/**
		 * NUMERICフィールドを取得します。
		 * @return NUMERICフィールド。
		 */
		public java.math.BigDecimal getSqlNumeric2() {
			return (java.math.BigDecimal) this.getMap().get(Entity.ID_SQL_NUMERIC2);
		}

		/**
		 * NUMERICフィールドを設定します。
		 * @param sqlNumeric2 NUMERICフィールド。
		 */
		public void setSqlNumeric2(final java.math.BigDecimal sqlNumeric2) {
			this.getMap().put(Entity.ID_SQL_NUMERIC2, sqlNumeric2);
		}

		/**
		 * DATEフィールドを取得します。
		 * @return DATEフィールド。
		 */
		public java.sql.Date getSqlDate2() {
			return (java.sql.Date) this.getMap().get(Entity.ID_SQL_DATE2);
		}

		/**
		 * DATEフィールドを設定します。
		 * @param sqlDate2 DATEフィールド。
		 */
		public void setSqlDate2(final java.sql.Date sqlDate2) {
			this.getMap().put(Entity.ID_SQL_DATE2, sqlDate2);
		}

		/**
		 * TIMEフィールドを取得します。
		 * @return TIMEフィールド。
		 */
		public java.sql.Time getSqlTime2() {
			return (java.sql.Time) this.getMap().get(Entity.ID_SQL_TIME2);
		}

		/**
		 * TIMEフィールドを設定します。
		 * @param sqlTime2 TIMEフィールド。
		 */
		public void setSqlTime2(final java.sql.Time sqlTime2) {
			this.getMap().put(Entity.ID_SQL_TIME2, sqlTime2);
		}

		/**
		 * TIMESTAMPフィールドを取得します。
		 * @return TIMESTAMPフィールド。
		 */
		public java.sql.Timestamp getSqlTimestamp2() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_SQL_TIMESTAMP2);
		}

		/**
		 * TIMESTAMPフィールドを設定します。
		 * @param sqlTimestamp2 TIMESTAMPフィールド。
		 */
		public void setSqlTimestamp2(final java.sql.Timestamp sqlTimestamp2) {
			this.getMap().put(Entity.ID_SQL_TIMESTAMP2, sqlTimestamp2);
		}

		/**
		 * CLOBフィールドを取得します。
		 * @return CLOBフィールド。
		 */
		public java.lang.String getSqlClob2() {
			return (java.lang.String) this.getMap().get(Entity.ID_SQL_CLOB2);
		}

		/**
		 * CLOBフィールドを設定します。
		 * @param sqlClob2 CLOBフィールド。
		 */
		public void setSqlClob2(final java.lang.String sqlClob2) {
			this.getMap().put(Entity.ID_SQL_CLOB2, sqlClob2);
		}


	}

	/**
	 * SQLデータ型2IDフィールドを取得します。
	 * @return SQLデータ型2IDフィールド。
	 */
	public SqlType2IdField getSqlType2IdField() {
		return (SqlType2IdField) this.getField(Entity.ID_SQL_TYPE2_ID);
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
	public SqlCharField getSqlChar2Field() {
		return (SqlCharField) this.getField(Entity.ID_SQL_CHAR2);
	}

	/**
	 * VarcharFieldフィールドを取得します。
	 * @return VarcharFieldフィールド。
	 */
	public SqlVarcharField getSqlVarchar2Field() {
		return (SqlVarcharField) this.getField(Entity.ID_SQL_VARCHAR2);
	}

	/**
	 * SMALLINTフィールドフィールドを取得します。
	 * @return SMALLINTフィールドフィールド。
	 */
	public SqlSmallintField getSqlSmallint2Field() {
		return (SqlSmallintField) this.getField(Entity.ID_SQL_SMALLINT2);
	}

	/**
	 * INTEGERフィールドフィールドを取得します。
	 * @return INTEGERフィールドフィールド。
	 */
	public SqlIntegerField getSqlInteger2Field() {
		return (SqlIntegerField) this.getField(Entity.ID_SQL_INTEGER2);
	}

	/**
	 * DOUBLEフィールドフィールドを取得します。
	 * @return DOUBLEフィールドフィールド。
	 */
	public SqlDoubleField getSqlDouble2Field() {
		return (SqlDoubleField) this.getField(Entity.ID_SQL_DOUBLE2);
	}

	/**
	 * NUMERICフィールドフィールドを取得します。
	 * @return NUMERICフィールドフィールド。
	 */
	public SqlNumericField getSqlNumeric2Field() {
		return (SqlNumericField) this.getField(Entity.ID_SQL_NUMERIC2);
	}

	/**
	 * DATEフィールドフィールドを取得します。
	 * @return DATEフィールドフィールド。
	 */
	public SqlDateField getSqlDate2Field() {
		return (SqlDateField) this.getField(Entity.ID_SQL_DATE2);
	}

	/**
	 * TIMEフィールドフィールドを取得します。
	 * @return TIMEフィールドフィールド。
	 */
	public SqlTimeField getSqlTime2Field() {
		return (SqlTimeField) this.getField(Entity.ID_SQL_TIME2);
	}

	/**
	 * TIMESTAMPフィールドフィールドを取得します。
	 * @return TIMESTAMPフィールドフィールド。
	 */
	public SqlTimestampField getSqlTimestamp2Field() {
		return (SqlTimestampField) this.getField(Entity.ID_SQL_TIMESTAMP2);
	}

	/**
	 * CLOBフィールドフィールドを取得します。
	 * @return CLOBフィールドフィールド。
	 */
	public SqlClobField getSqlClob2Field() {
		return (SqlClobField) this.getField(Entity.ID_SQL_CLOB2);
	}



}
