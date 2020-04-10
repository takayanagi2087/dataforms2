package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;

/**
 * 資材マスタDaoクラス。
 *
 */
public class MaterialMasterDao extends QuerySetDao {
	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public MaterialMasterDao() {
		this.setComment("資材マスタDao");
		this.setSingleRecordQuery(new MaterialMasterTable());
		this.setListQuery(new MaterialMasterTable());
	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public MaterialMasterDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public MaterialMasterTable getMainTable() {
		return (MaterialMasterTable) this.getSingleRecordQuery().getMainTable();
	}

}
