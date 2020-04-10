package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;

/**
 * 仕入先マスタDaoクラス。
 *
 */
public class SupplierMasterDao extends QuerySetDao {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterDao() {
		this.setComment("仕入先マスタDao");
		this.setMainQuery(new SupplierMasterTable());
		this.setListQuery(new SupplierMasterTable());
	}

	/**
	 * コンストラクタ。
	 * @param obj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public SupplierMasterDao(final JDBCConnectableObject obj) throws Exception {
		this();
		this.init(obj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public SupplierMasterTable getMainTable() {
		return (SupplierMasterTable) this.getMainQuery().getMainTable();
	}
}
