package pagepat.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.dao.Query;


/**
 * 1レコード編集ページです。
 *
 */
public class Test05Dao extends QuerySetDao {
	/**
	 * テストテーブル。
	 */
	private TestTable testTable = null;

	/**
	 * テストテーブルを取得します。
	 * @return テストテーブル。
	 */
	public TestTable getTestTable() {
		return this.testTable;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public Test05Dao() {
		this.setComment("1レコード編集ページ");
		this.setListQuery((Query) null);
		this.setSingleRecordQuery(this.testTable = new TestTable());

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public Test05Dao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル。
	 */
	public TestTable getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (TestTable) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (TestTable) this.getMultiRecordQueryList().get(0).getMainTable();
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
