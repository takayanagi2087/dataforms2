package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;

/**
 * 資材マスタDaoクラス。
 *
 */
public class MaterialOrderDao extends QuerySetDao {
	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public MaterialOrderDao() {
		this.setListQuery(new MaterialOrderListQuery());
		this.setMainQuery(new MaterialOrderQuery());
		this.addRelationQuery(new MaterialOrderItemQuery());
	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public MaterialOrderDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public MaterialOrderTable getMainTable() {
		return (MaterialOrderTable) this.getMainQuery().getMainTable();
	}

}
