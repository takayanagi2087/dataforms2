package test.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * Daoクラスです。
 *
 */
public class SqlTypeDao extends QuerySetDao {
	/**
	 * SQLデータ型テストテーブル。
	 */
	private SqlTypeTable sqlTypeTable = null;

	/**
	 * SQLデータ型テストテーブルを取得します。
	 * @return SQLデータ型テストテーブル。
	 */
	public SqlTypeTable getSqlTypeTable() {
		return this.sqlTypeTable;
	}

	/**
	 * SQLデータ型テストテーブル。
	 */
	private SqlTypeListTable sqlTypeListTable = null;

	/**
	 * SQLデータ型テストテーブルを取得します。
	 * @return SQLデータ型テストテーブル。
	 */
	public SqlTypeListTable getSqlTypeListTable() {
		return this.sqlTypeListTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public SqlTypeDao() {
		this.setComment("SqlTypeテーブルアクセスクラス");
		this.setListQuery(this.sqlTypeTable = new SqlTypeTable());
		this.setSingleRecordQuery(this.sqlTypeTable = new SqlTypeTable());
		this.addMultiRecordQueryList(this.sqlTypeListTable = new SqlTypeListTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public SqlTypeDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public SqlTypeTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (SqlTypeTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (SqlTypeTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
