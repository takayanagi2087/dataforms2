package test.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;


/**
 * Daoクラスです。
 *
 */
public class TestSingleRecDao extends QuerySetDao {
	/**
	 * 複数レコード編集テストテーブル。
	 */
	private TestMultiRecTable testMultiRecTable = null;

	/**
	 * 複数レコード編集テストテーブルを取得します。
	 * @return 複数レコード編集テストテーブル。
	 */
	public TestMultiRecTable getTestMultiRecTable() {
		return this.testMultiRecTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public TestSingleRecDao() {
		this.setComment("1レコード編集ページ");
		this.setListQuery(this.testMultiRecTable = new TestMultiRecTable());
		this.setSingleRecordQuery(this.testMultiRecTable = new TestMultiRecTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public TestSingleRecDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル>
	 */
	public TestMultiRecTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (TestMultiRecTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (TestMultiRecTable) this.getMultiRecordQueryList().get(0).getMainTable();
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
