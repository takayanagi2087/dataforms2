package pagepat.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.field.base.FieldList;
import pagepat.field.Code1Field;
import dataforms.dao.Query;


/**
 * 一覧→複数レコード編集ページ用DAOクラスです。
 *
 */
public class Test04Dao extends QuerySetDao {
	/**
	 * Code1件数取得問合せ。
	 */
	private Code1CountQuery code1CountQuery = null;

	/**
	 * Code1件数取得問合せを取得します。
	 * @return Code1件数取得問合せ。
	 */
	public Code1CountQuery getCode1CountQuery() {
		return this.code1CountQuery;
	}

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
	 * コード1フィールド。
	 */
	private Code1Field code1Field = null;

	/**
	 * コード1フィールドを取得します。
	 * @return コード1フィールド。
	 */
	public Code1Field getCode1Field() {
		return this.code1Field;
	}


	/**
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public Test04Dao() {
		this.setComment("一覧→複数レコード編集ページ用DAOクラス");
		this.setListQuery(this.code1CountQuery = new Code1CountQuery());
		this.setSingleRecordQuery((Query) null);
		this.addMultiRecordQueryList(this.testTable = new TestTable());
		Query query = new SingleTableQuery(new TestTable());
		this.setMultiRecordQueryKeyList(new FieldList(
			this.code1Field = (Code1Field) query.getFieldList().get(TestTable.Entity.ID_CODE1)

		));

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public Test04Dao(final JDBCConnectableObject cobj) throws Exception {
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
