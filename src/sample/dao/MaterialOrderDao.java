package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;

/**
 * 資材マスタDaoクラス。
 *
 */
public class MaterialOrderDao extends QuerySetDao {

	/**
	 * 資材発注一覧の問合せ。
	 */
	private MaterialOrderListQuery materialOrderListQuery = null;

	/**
	 * 資材発注情報取得問合せ。
	 */
	private MaterialOrderQuery materialOrderQuery = null;

	/**
	 * 資材発注明細取得問合せ。
	 */
	private MaterialOrderItemQuery materialOrderItemQuery = null;

	/**
	 * 資材発注一覧の問合せを取得します。
	 * @return 資材発注一覧の問合せ。
	 */
	public MaterialOrderListQuery getMaterialOrderListQuery() {
		return materialOrderListQuery;
	}

	/**
	 * 資材発注情報取得問合せを取得します。
	 * @return 資材発注情報取得問合せ。
	 */
	public MaterialOrderQuery getMaterialOrderQuery() {
		return materialOrderQuery;
	}

	/**
	 * 資材発注明細取得問合せを取得します。
	 * @return 資材発注明細取得問合せ。
	 */
	public MaterialOrderItemQuery getMaterialOrderItemQuery() {
		return materialOrderItemQuery;
	}

	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public MaterialOrderDao() {
		this.setComment("発注情報Dao");
		this.setListQuery(this.materialOrderListQuery = new MaterialOrderListQuery());
		this.setSingleRecordQuery(this.materialOrderQuery = new MaterialOrderQuery());
		this.addMultiRecordQueryList(this.materialOrderItemQuery = new MaterialOrderItemQuery());
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
		return (MaterialOrderTable) this.getSingleRecordQuery().getMainTable();
	}

}
