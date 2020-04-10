package dataforms.debug.special.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.field.base.FieldList;

/**
 * Daoクラス。
 *
 */
public class SmallMasterDao extends QuerySetDao {
	/**
	 * コンストラクタ。
	 */
	public SmallMasterDao() {
		SmallMasterTable table = new SmallMasterTable();
		this.setMultiRecordQueryKeyList(new FieldList(table.getKey1Field(), table.getKey2Field()));
		this.addMultiRecordQueryList(new SmallMasterTable());
		// this.setListQuery(new SmallMasterTable());
	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public SmallMasterDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}
}
