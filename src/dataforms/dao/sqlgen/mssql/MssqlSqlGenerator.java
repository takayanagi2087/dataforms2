package dataforms.dao.sqlgen.mssql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.SqlGeneratorImpl;
import dataforms.dao.Query;
import dataforms.dao.QueryPager;
import dataforms.dao.sqldatatype.SqlBlob;
import dataforms.dao.sqldatatype.SqlClob;
import dataforms.dao.sqldatatype.SqlTimestamp;
import dataforms.dao.sqlgen.SqlGenerator;
import dataforms.field.base.Field;

/**
 * MS SQL Server用SQL Generator.
 *
 */
@SqlGeneratorImpl(databaseProductName = MssqlSqlGenerator.DATABASE_PRODUCT_NAME)
public class MssqlSqlGenerator extends SqlGenerator {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(MssqlSqlGenerator.class);

	/**
	 * データベースシステムの名称。
	 */
	public static final String DATABASE_PRODUCT_NAME = "Microsoft SQL Server";

	/**
	 * コンストラクタ.
	 * @param conn JDBC接続情報.
	 */
	public MssqlSqlGenerator(final Connection conn) {
		super(conn);
	}

	@Override
	public String getDatabaseProductName() {
		return DATABASE_PRODUCT_NAME;
	}


/*	@Override
	public String getDatabaseType(final Field<?> field) {
		String type = field.getDbDependentType(DATABASE_PRODUCT_NAME);
		if (type != null) {
			return type;
		}
		return super.getDatabaseType(field);
	}*/

	/**
	 * {@inheritDoc}
	 * テーブル、カラムのcommentはサポートされていません。
	 *
	 */
	@Override
	protected CommentSyntax getCommentSyntax() {
		// commentはサポートしない.
		return SqlGenerator.CommentSyntax.NONE;
	}

	/**
	 * {@inheritDoc}
	 * シーケンスをサポートしているのでtrueを返します。
	 */
	@Override
	public boolean isSequenceSupported() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * テーブル情報を取得するときには、DatabaseMetadataに対し、大文字のテーブル名を渡す必要があるので
	 * テーブル名を大文字に変換します。
	 */
	@Override
	public String convertTableNameForDatabaseMetaData(final String tblname) {
		return tblname.toUpperCase();
	}

	/**
	 * {@inheritDoc}
	 * SYS.OBJECTSにテーブルが登録されているか確認するSQLを作成します。
	 */
	@Override
	public String generateTableExistsSql() {
		String sql = "select count(*) as TABLE_EXISTS from SYS.OBJECTS where type_desc='USER_TABLE' and  LOWER(NAME)=:table_name";
		return sql;
	}

	/**
	 * {@inheritDoc}
	 * SYS.OBJECTSにシーケンスが登録されているか確認するSQLを作成します。
	 */
	@Override
	public String generateSequenceExistsSql() {
		String sql = "select count(*) as SEQUENCE_EXISTS from SYS.OBJECTS where type_desc='SEQUENCE_OBJECT' and LOWER(name)=:sequence_name";
		return sql;
	}

	@Override
	public String generateRenameTableSql(final String oldname, final String newname) {
		String sql = "sp_rename " + oldname + ", " + newname;
		return sql;
	}

	@Override
	public String generateCreateSequenceSql(final String sequencename, final Long startValue) throws Exception {
		String ret = null;
		ret = "create sequence " + sequencename + " as bigint start with " + startValue;
		return ret;
	}

	@Override
	public String generateGetRecordIdSql(final String tablename) throws Exception {
		return "select next value for " + tablename + "_seq as seq";
	}

	@Override
	public String generateSysTimestampSql() {
		return "current_timestamp";
	}

	@Override
	public String generateGetPageSql(final QueryPager qp) {
		Query query  = qp.getQuery();
		String orderBy = this.getOrderBySql(query);
		String orgsql = this.getOrgSql(qp, false);
		String sql = "select * from (select row_number() over(" + orderBy + ") as row_no, m.* from (" + orgsql + ") as m) as m where (:row_from + 1) <= m.row_no and m.row_no <= (:row_to + 1)";
		return sql;
	}


	@Override
	public String getRebildSqlFolder() {
		return "/WEB-INF/dbRebuild/mssql";
	}

	@Override
	public String getConstraintViolationException(SQLException ex) {
		if (ex instanceof SQLIntegrityConstraintViolationException) {
			if ("org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException".equals(ex.getClass().getName())) {
				DerbySQLIntegrityConstraintViolationException dex = (DerbySQLIntegrityConstraintViolationException) ex;
				logger.debug(() -> "dex.getTableName()=" + dex.getTableName());
				logger.debug(() -> "dex.getConstraintName()=" + dex.getConstraintName());
				return dex.getConstraintName();
			}
		}
		return null;
	}

	@Override
	public String getDatabaseType(Field<?> field) {
		String type = super.getDatabaseType(field);
		if (field instanceof SqlTimestamp) {
			type = "datetime";
		} else if (field instanceof SqlBlob) {
			type = "varbinary(max)";
		} else if (field instanceof SqlClob) {
			type = "varchar(max)";
		}

		return type;
	}
}
