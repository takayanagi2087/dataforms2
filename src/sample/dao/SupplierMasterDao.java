package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * Daoクラスです。
 *
 */
public class SupplierMasterDao extends QuerySetDao {
	/**
	 * 仕入先マスタ。
	 */
	private SupplierMasterTable supplierMasterTable = null;

	/**
	 * 仕入先マスタを取得します。
	 * @return 仕入先マスタ。
	 */
	public SupplierMasterTable getSupplierMasterTable() {
		return this.supplierMasterTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public SupplierMasterDao() {
		this.setComment("仕入先マスタDao");
		this.setListQuery(this.supplierMasterTable = new SupplierMasterTable());
		this.setSingleRecordQuery(this.supplierMasterTable = new SupplierMasterTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public SupplierMasterDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public SupplierMasterTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (SupplierMasterTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (SupplierMasterTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
