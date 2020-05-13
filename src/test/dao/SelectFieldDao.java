package test.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.dao.Query;


/**
 * Daoクラスです。
 *
 */
public class SelectFieldDao extends QuerySetDao {
	/**
	 * 選択肢フィールドテーブル。
	 */
	private SelectFieldTable selectFieldTable = null;

	/**
	 * 選択肢フィールドテーブルを取得します。
	 * @return 選択肢フィールドテーブル。
	 */
	public SelectFieldTable getSelectFieldTable() {
		return this.selectFieldTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public SelectFieldDao() {
		this.setComment("選択肢フィールドテーブルDAO");
		this.setListQuery(this.selectFieldTable = new SelectFieldTable());
		this.setSingleRecordQuery((Query) null);
		this.addMultiRecordQueryList(this.selectFieldTable = new SelectFieldTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public SelectFieldDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public SelectFieldTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (SelectFieldTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (SelectFieldTable) this.getMultiRecordQueryList().get(0).getMainTable();
			}
		}
		return null;
	}

}
