package test.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import test.field.Code1Field;


/**
 * Daoクラスです。
 *
 */
public class TestMultiRecDao extends QuerySetDao {
	/**
	 * code1一覧の問合せ。
	 */
	private TestCode1Query testCode1Query = null;

	/**
	 * code1一覧の問合せを取得します。
	 * @return code1一覧の問合せ。
	 */
	public TestCode1Query getTestCode1Query() {
		return this.testCode1Query;
	}

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
	public TestMultiRecDao() {
		this.setComment("複数レコード編集用DAO");
		this.setListQuery(this.testCode1Query = new TestCode1Query());
		this.setSingleRecordQuery((Query) null);
		this.addMultiRecordQueryList(this.testMultiRecTable = new TestMultiRecTable());
		Query query = new SingleTableQuery(new TestMultiRecTable());
		this.setMultiRecordQueryKeyList(new FieldList(
			this.code1Field = (Code1Field) query.getFieldList().get(TestMultiRecTable.Entity.ID_CODE1)

		));

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public TestMultiRecDao(final JDBCConnectableObject cobj) throws Exception {
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
