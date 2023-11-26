package pagepat.dao;

import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.QuerySetDao;
import dataforms.dao.SingleTableQuery;
import dataforms.field.base.FieldList;
import pagepat.field.Code1Field;
import dataforms.dao.Query;


/**
 * 複数レコード編集DAOです。
 *
 */
public class MultiRecDao extends QuerySetDao {
	/**
	 * Code1件数問合せ。
	 */
	private Code1CountQuery code1CountQuery = null;

	/**
	 * Code1件数問合せを取得します。
	 * @return Code1件数問合せ。
	 */
	public Code1CountQuery getCode1CountQuery() {
		return this.code1CountQuery;
	}

	/**
	 * コード12テーブル。
	 */
	private Code12Table code12Table = null;

	/**
	 * コード12テーブルを取得します。
	 * @return コード12テーブル。
	 */
	public Code12Table getCode12Table() {
		return this.code12Table;
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
	public MultiRecDao() {
		this.setComment("複数レコード編集DAO");
		this.setListQuery(this.code1CountQuery = new Code1CountQuery());
		this.setSingleRecordQuery((Query) null);
		this.addMultiRecordQueryList(this.code12Table = new Code12Table());
		Query query = new SingleTableQuery(new Code12Table());
		this.setMultiRecordQueryKeyList(new FieldList(
			this.code1Field = (Code1Field) query.getFieldList().get(Code12Table.Entity.ID_CODE1)

		));

	}

	/**
	 * コンストラクタ。
	 * @param cobj JDBC接続可能Object。
	 * @throws Exception 例外。
	 */
	public MultiRecDao(final JDBCConnectableObject cobj) throws Exception {
		this();
		this.init(cobj);
	}

	/**
	 * 主テーブルを取得します。
	 * @return 主テーブル。
	 */
	public Code12Table getMainTable() {
		if (this.getSingleRecordQuery() != null) {
			return (Code12Table) this.getSingleRecordQuery().getMainTable();
		} else {
			if (this.getMultiRecordQueryList() != null) {
				return (Code12Table) this.getMultiRecordQueryList().get(0).getMainTable();
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
