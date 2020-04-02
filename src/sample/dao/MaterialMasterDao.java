package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.TableGroupDao;

/**
 * 資材Daoクラス。
 *
 */
public class MaterialMasterDao extends TableGroupDao {
	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public MaterialMasterDao() {
		this.setMainQuery(new MaterialMasterTable());
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
}
