package test.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.Query;
import dataforms.dao.QuerySetDao;


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
	 */
	public SelectFieldDao() {
		this.setComment("選択肢フィールドテーブルDAO");
		this.setListQuery((Query) null);
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
	 * @return 主テーブル。
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

	//
	// 追加、更新、削除処理を改造する場合は以下のメソッドをオーバーライドしてください。
	// QuerySetDaoクラスではsingleRecordQuery,multiRecordQueryListに登録された各問合せ
	// のmainTableのみ操作するようになっています。
	//
	/**
	 * テーブル群を追加します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void insert(final Map<String, Object> data) throws Exception {
		super.insert(data);
	}
*/

	/**
	 * テーブル群を更新します。
	 * @param data 更新データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void update(final Map<String, Object> data) throws Exception {
		super.update(data);
	}
*/

	/**
	 * データを削除します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
/*
	@Override
	public void delete(final Map<String, Object> data) throws Exception {
		super.delete(data);
	}
*/

}
