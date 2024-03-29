package fieldtest.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * SqlTypeページ用DAOクラスです。
 *
 */
public class SqlTypeDao extends QuerySetDao {
	/**
	 * SQLデータ型のテストフィールド。
	 */
	private SqlTypeTable sqlTypeTable = null;

	/**
	 * SQLデータ型のテストフィールドを取得します。
	 * @return SQLデータ型のテストフィールド。
	 */
	public SqlTypeTable getSqlTypeTable() {
		return this.sqlTypeTable;
	}

	/**
	 * SQLデータ型テーブル2。
	 */
	private SqlType2Table sqlType2Table = null;

	/**
	 * SQLデータ型テーブル2を取得します。
	 * @return SQLデータ型テーブル2。
	 */
	public SqlType2Table getSqlType2Table() {
		return this.sqlType2Table;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public SqlTypeDao() {
		this.setComment("SqlTypeページ用DAOクラス");
		this.setListQuery(this.sqlTypeTable = new SqlTypeTable());
		this.setSingleRecordQuery(this.sqlTypeTable);
		this.addMultiRecordQueryList(this.sqlType2Table = new SqlType2Table());

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
	 * @return 主テーブル。
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

	//
	// 追加、更新、削除処理を改造する場合は以下のメソッドをオーバーライドしてください。
	// QuerySetDaoクラスではsingleRecordQuery,multiRecordQueryListに登録された各問合せ
	// のmainTableのみ操作するようになっています。
	//
	/**
	 * テーブル群を追加します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void insert(final Map<String, Object> data) throws Exception {
		super.insert(data);
	}
*/

	/**
	 * テーブル群を更新します。
	 * @param data 更新データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void update(final Map<String, Object> data) throws Exception {
		super.update(data);
	}
*/

	/**
	 * データを削除します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void delete(final Map<String, Object> data) throws Exception {
		super.delete(data);
	}
*/

}
