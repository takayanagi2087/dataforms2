package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * Daoクラスです。
 *
 */
public class MaterialMasterDao extends QuerySetDao {
	/**
	 * 資材マスタ。
	 */
	private MaterialMasterTable materialMasterTable = null;

	/**
	 * 資材マスタを取得します。
	 * @return 資材マスタ。
	 */
	public MaterialMasterTable getMaterialMasterTable() {
		return this.materialMasterTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public MaterialMasterDao() {
		this.setComment("資材マスタDao");
		this.setListQuery(this.materialMasterTable = new MaterialMasterTable());
		this.setSingleRecordQuery(this.materialMasterTable = new MaterialMasterTable());

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
		if (this.getSingleRecordQuery() != null) {
			return (MaterialMasterTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (MaterialMasterTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
