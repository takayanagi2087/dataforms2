package sample.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.field.base.FieldList;
import dataforms.dao.Query;


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
	 * コンストラクタ。
	 * @throws Exception 例外。
	 */
	public TestMultiRecDao() {
		this.setComment("複数レコード編集用DAO");
		this.setListQuery(this.testCode1Query = new TestCode1Query());
		this.setSingleRecordQuery((Query) null);
		this.addMultiRecordQueryList(this.testMultiRecTable = new TestMultiRecTable());
		Query query = this.getMultiRecordQueryList().get(0);
		this.setMultiRecordQueryKeyList(new FieldList(query.getFieldList().get(TestMultiRecTable.Entity.ID_CODE1)));

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

}
