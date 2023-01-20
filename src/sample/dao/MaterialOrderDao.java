package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * Daoクラスです。
 *
 */
public class MaterialOrderDao extends QuerySetDao {
	/**
	 * 資材発注一覧用問合せ。
	 */
	private MaterialOrderListQuery materialOrderListQuery = null;

	/**
	 * 資材発注一覧用問合せを取得します。
	 * @return 資材発注一覧用問合せ。
	 */
	public MaterialOrderListQuery getMaterialOrderListQuery() {
		return this.materialOrderListQuery;
	}

	/**
	 * 発注ヘッダ部取得の問合せ。
	 */
	private MaterialOrderQuery materialOrderQuery = null;

	/**
	 * 発注ヘッダ部取得の問合せを取得します。
	 * @return 発注ヘッダ部取得の問合せ。
	 */
	public MaterialOrderQuery getMaterialOrderQuery() {
		return this.materialOrderQuery;
	}

	/**
	 * 資材発注明細用の問合せ。
	 */
	private MaterialOrderItemQuery materialOrderItemQuery = null;

	/**
	 * 資材発注明細用の問合せを取得します。
	 * @return 資材発注明細用の問合せ。
	 */
	public MaterialOrderItemQuery getMaterialOrderItemQuery() {
		return this.materialOrderItemQuery;
	}


	/**
	 * コンストラクタ。
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
	 * @return 主テーブル。
	 */
	public MaterialOrderTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (MaterialOrderTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (MaterialOrderTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
