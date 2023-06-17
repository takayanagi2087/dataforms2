package dataforms.debug.special.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;
import dataforms.field.base.FieldList;


/**
 * Daoクラスです。
 *
 */
public class SmallMasterDao extends QuerySetDao {
	/**
	 * コンストラクタ。
	 */
	public SmallMasterDao() {
		this.setComment("");
		this.setListQuery((Query) null);
		this.setSingleRecordQuery((Query) null);
		SmallMasterTable table = new SmallMasterTable();
		this.setMultiRecordQueryKeyList(new FieldList(table.getKey1Field(), table.getKey2Field()));
		this.addMultiRecordQueryList(new SmallMasterTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public SmallMasterDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル。
	 */
	public SmallMasterTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (SmallMasterTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (SmallMasterTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
