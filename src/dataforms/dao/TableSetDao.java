package dataforms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataforms.exception.ApplicationException;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;

/**
 * 関連するテーブルの集合を操作するDao。
 */
public class TableSetDao extends Dao {
	/**
	 * Logger。
	 */
	// private static Logger logger = Logger.getLogger(TableSetDao.class);

	/**
	 * 主問合せ。
	 */
	private Query mainQuery = null;

	/**
	 * 関連問合せ。
	 */
	private List<Query> relationQueryList = null;

	/**
	 * 一覧問合せ。
	 */
	private Query listQuery = null;

	/**
	 * コンストラクタ。
	 */
	public TableSetDao()  {

	}

	/**
	 * コンストラクタ。
	 * @param obj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public TableSetDao(final JDBCConnectableObject obj) throws Exception {
		super(obj);
	}

	/**
	 * 主となる問合せを設定します。
	 * @param query 問合せ。
	 */
	public void setMainQuery(final Query query) {
		this.mainQuery = query;
	}

	/**
	 * 主となるテーブルを設定します。
	 * @param table このテーブルでSingleTableQueryを作成し関連問合せとします。
	 */
	public void setMainQuery(final Table table) {
		this.mainQuery = new SingleTableQuery(table);
	}

	/**
	 * 主問合せを取得します。
	 * @return 主問合せ。
	 */
	public Query getMainQuery() {
		return this.mainQuery;
	}

	/**
	 * 関連問合せを追加します。
	 * @param query 関連問合せ。
	 */
	public void addRelationQuery(final Query query) {
		if (this.relationQueryList == null) {
			this.relationQueryList = new ArrayList<Query>();
		}
		this.relationQueryList.add(query);
	}

	/**
	 * 関連問合せを追加します。
	 * @param table このテーブルでSingleTableQueryを作成し関連問合せとします。
	 */
	public void addRelationQuery(final Table table) {
		this.addRelationQuery(new SingleTableQuery(table));
	}

	/**
	 * 関連問合せリストを取得します。
	 * @return 関連問合せリスト。
	 */
	public List<Query> getRelationQueryList() {
		return relationQueryList;
	}

	/**
	 * 一覧用問合せを取得します。
	 * @return 一覧用問合せを取得。
	 */
	public Query getListQuery() {
		return listQuery;
	}

	/**
	 * 一覧用問合せを取得します。
	 * @param listQuery 一覧用問合せを取得。
	 */
	public void setListQuery(final Query listQuery) {
		this.listQuery = listQuery;
	}


	/**
	 * 一覧用問合せを取得します。
	 * @param table このテーブルでSingleTableQueryを作成し一覧用問合とします。
	 */
	public void setListQuery(final Table table) {
		this.listQuery = new SingleTableQuery(table);
	}

	/**
	 * 検索結果のフィールドリストを取得をします。
	 * @return 検索結果のフィールドリスト。
	 */
	public FieldList getListFieldList() {
		return this.listQuery.getFieldList();
	}


	/**
	 * QueryFormから入力された条件から、テーブルを検索し、指定されたページの情報を返します。
	 * @param data 条件データ。
	 * @param flist 条件フィールドリスト。
	 * @return 検索結果。
	 * @throws Exception 例外。
	 */
	public Map<String, Object> queryPage(final Map<String, Object> data, final FieldList flist) throws Exception {
		Query query = this.getListQuery();
		query.setConditionFieldList(flist);
		query.setConditionData(data);
		String sortOrder = (String) data.get("sortOrder");
		FieldList sflist = query.getFieldList().getOrderByFieldList(sortOrder);
		if (sflist.size() == 0) {
			query.setOrderByFieldList(query.getMainTable().getPkFieldList());
		} else {
			query.setOrderByFieldList(sflist);
		}
		return this.executePageQuery(query);
	}

	/**
	 * QueryFormから入力された条件から、テーブルを検索し、マッチするすべてのデータを返します。
	 * @param data 条件データ。
	 * @param flist 条件フィールドリスト。
	 * @return 検索結果。
	 * @throws Exception 例外。
	 */
	public List<Map<String, Object>> query(final Map<String, Object> data, final FieldList flist) throws Exception {
		Query query = this.getListQuery();
		query.setConditionFieldList(flist);
		query.setConditionData(data);
		return this.executeQuery(query);
	}


	/**
	 * 関連問合せの結果を取得します。
	 * @param q 関連問合せ。
	 * @param data 検索条件データ。
	 * @param ret 結果を登録するマップ。
	 * @return テーブルID。
	 * @throws Exception 例外。
	 */
	protected String setRelationQueryResult(final Query q, final Map<String, Object> data,  final Map<String, Object> ret) throws Exception {
		Query query = this.getMainQuery();
		q.setConditionFieldList(query.getMainTable().getPkFieldList());
		q.setConditionData(data);
		String tid = q.getListId();
		List<Map<String, Object>> list = this.executeQuery(q);
		ret.put(tid, list);
		return tid;
	}


	/**
	 * PKでレコードを限定し、データを取得します。
	 * @param data 条件データ PKの情報をすべて含むマップ。
	 * @return ヒットしたレコード。
	 * @throws Exception 例外。
	 */
	public Map<String, Object> query(final Map<String, Object> data) throws Exception {
		Query query = this.getMainQuery();
		query.setConditionFieldList(query.getMainTable().getPkFieldList());
		query.setConditionData(data);
		Map<String, Object> ret = this.executeRecordQuery(query);
		if (this.relationQueryList != null) {
			for (Query q: this.relationQueryList) {
				this.setRelationQueryResult(q, data, ret);
			}
		}
		return ret;
	}

	/**
	 * 主テーブルを追加します。
	 * <pre>
	 * デフォルトの実装では主問合せのmainTableの追加処理が実装されています。
	 * mainTable以外のテーブルの保存が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	protected void insertMainTable(final Map<String, Object> data) throws Exception {
		Table table = this.getMainQuery().getMainTable();
		this.executeInsert(table, data);
	}

	/**
	 * 関連テーブルを追加します。
	 * <pre>
	 * デフォルトの実装では関連問合せのmainTableの追加処理が実装されています。
	 * mainTable以外のテーブルの保存が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	protected void insertRelationTable(final Query q, final Map<String, Object> data) throws Exception {
		Table table = q.getMainTable();
		String id = q.getListId();
		FieldList pklist = this.getMainQuery().getMainTable().getPkFieldList();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(id);
		// 各関連テーブルにPKの値を設定します。
		for (Map<String, Object> m: list) {
			for (Field<?> pk: pklist) {
				m.put(pk.getId(), data.get(pk.getId()));
			}
		}
		this.saveTable(table, list, data, pklist);
	}

	/**
	 * テーブル群を追加します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	public void insert(final Map<String, Object> data) throws Exception {
		this.insertMainTable(data);
		if (this.relationQueryList != null) {
			for (Query q: this.relationQueryList) {
				this.insertRelationTable(q, data);
			}
		}
	}

	/**
	 * 主テーブルを更新します。
	 * <pre>
	 * デフォルトの実装では主問合せのmainTableの追加処理が実装されています。
	 * mainTable以外のテーブルの更新が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	public void updateMainTable(final Map<String, Object> data) throws Exception {
		Table table = this.getMainQuery().getMainTable();
		boolean ret = this.isUpdatable(table, data);
		if (!ret) {
			throw new ApplicationException(this.getPage(), "error.notupdatable");
		}
		// データ更新
		this.executeUpdate(table, data);
	}

	/**
	 * 関連テーブルを更新します。
	 * <pre>
	 * デフォルトの実装では関連問合せのmainTableの更新処理が実装されています。
	 * mainTable以外のテーブルの更新が必要な場合オーバーライドして実装してください。
	 * </pre>
	 *
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 *
	 */
	public void updateRelationTable(final Query q, final Map<String, Object> data) throws Exception {
		this.insertRelationTable(q, data);
	}

	/**
	 * テーブル群を更新します。
	 * @param data 更新データ。
	 * @throws Exception 例外。
	 */
	public void update(final Map<String, Object> data) throws Exception {
		this.updateMainTable(data);
		if (this.relationQueryList != null) {
			for (Query q: this.relationQueryList) {
				this.updateRelationTable(q, data);
			}
		}
	}

	/**
	 * 主テーブルを削除します。
	 * <pre>
	 * デフォルトの実装では主問合せのmainTableの削除処理が実装されています。
	 * mainTable以外のテーブルの削除が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	public void deleteMainTable(final Map<String, Object> data) throws Exception {
		Table table = this.getMainQuery().getMainTable();
		this.executeDelete(table, data);
	}

	/**
	 * 関連テーブルを削除します。
	 * <pre>
	 * デフォルトの実装では関連問合せのmainTableの削除処理が実装されています。
	 * mainTable以外のテーブルの削除が必要な場合オーバーライドして実装してください。
	 * </pre>
	 *
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 *
	 */
	public void deleteRelationTable(final Query q, final Map<String, Object> data) throws Exception {
		Table mainTable = this.getMainQuery().getMainTable();
		FieldList pklist = mainTable.getPkFieldList();
		Table table = q.getMainTable();
		this.executeDelete(table, pklist, data, true);
	}

	/**
	 * データを削除します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	public void delete(final Map<String, Object> data) throws Exception {
		this.deleteMainTable(data);
		if (this.relationQueryList != null) {
			for (Query q: this.relationQueryList) {
				this.deleteRelationTable(q, data);
			}
		}
	}
}
