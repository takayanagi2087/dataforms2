package dataforms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.exception.ApplicationException;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.common.RowNoField;

/**
 * 関連する問合せの集合を操作するDao。
 */
public class QuerySetDao extends Dao {
	/**
	 * Logger。
	 */
	// private static Logger logger = Logger.getLogger(TableSetDao.class);

	/**
	 * 単一レコード取得用問合せ。
	 * <pre>
	 * 編集対象の1レコードを取得する問合せを指定します。
	 * </pre>
	 */
	private Query singleRecordQuery = null;

	/**
	 * 複数レコード取得用問合せ。
	 * <pre>
	 * 「検索条件指定 → 一覧表示 → 1件編集」
	 *  singleRecordQueryで取得したレコードに関連した別テーブルの
	 *  レコードを同時に編集したい場合、その情報を取得する問合せを
	 *  追加します。この問合せは複数追加することができます。
	 *
	 * 「検索条件指定 → 複数件編集」
	 *  singleRecordQueryは設定せず、multiRecordQueryListに1件指定します。
	 *  取得条件はmultiRecordQueryKeyListで指定します。
	 * </pre>
	 */
	private List<Query> multiRecordQueryList = null;

	/**
	 * 複数レコード取得時のキーフィールドリスト。
	 */
	private FieldList multiRecordQueryKeyList = null;

	/**
	 * 一覧問合せ。
	 */
	private Query listQuery = null;

	/**
	 * コンストラクタ。
	 */
	public QuerySetDao()  {

	}

	/**
	 * コンストラクタ。
	 * @param obj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public QuerySetDao(final JDBCConnectableObject obj) throws Exception {
		super(obj);
	}

	/**
	 * 単一レコード取得用問合せを設定します。
	 *
	 * @param query 問合せ。
	 */
	public void setSingleRecordQuery(final Query query) {
		this.singleRecordQuery = query;
	}

	/**
	 * 単一レコード取得用問合せを設定します。
	 * @param table このテーブルでSingleTableQueryを作成し関連問合せとします。
	 */
	public void setSingleRecordQuery(final Table table) {
		this.singleRecordQuery = new SingleTableQuery(table);
	}

	/**
	 * 単一レコード取得用問合せを取得します。
	 * @return 単一レコード取得用問合せ。
	 */
	public Query getSingleRecordQuery() {
		return this.singleRecordQuery;
	}

	/**
	 * 複数レコード取得時のキーフィールドリストを取得します。
	 * <pre>
	 * multiRecordQueryKeyList指定されていない場合、singleRecordQueryの
	 * mainTableの主キーフィールドリストを返します。
	 * </pre>
	 * @return 複数レコード取得時のキーフィールドリスト。
	 */
	public FieldList getMultiRecordQueryKeyList() {
		if (this.multiRecordQueryKeyList != null) {
			return this.multiRecordQueryKeyList;
		} else {
			if (this.getSingleRecordQuery() != null) {
				// 通常はMainTableのPK。
				return this.getSingleRecordQuery().getMainTable().getPkFieldList();
			} else {
				return null;
			}
		}
	}

	/**
	 * 複数レコード取得時のキーフィールドリストを設定します。
	 * @param keyList 複数レコード取得時のキーフィールドリスト。
	 */
	public void setMultiRecordQueryKeyList(final FieldList keyList) {
		this.multiRecordQueryKeyList = keyList;
	}

	/**
	 * 複数レコード取得用問合せを追加します。
	 * @param query 関連問合せ。
	 */
	public void addMultiRecordQueryList(final Query query) {
		if (this.multiRecordQueryList == null) {
			this.multiRecordQueryList = new ArrayList<Query>();
		}
		this.multiRecordQueryList.add(query);
	}

	/**
	 * 複数レコード取得用問合せを追加します。
	 * @param table このテーブルでSingleTableQueryを作成し関連問合せとします。
	 */
	public void addMultiRecordQueryList(final Table table) {
		this.addMultiRecordQueryList(new SingleTableQuery(table));
	}

	/**
	 * 複数レコード取得用問合せリストを取得します。
	 * @return 関連問合せリスト。
	 */
	public List<Query> getMultiRecordQueryList() {
		return multiRecordQueryList;
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
	 * 一覧用問合せを設定します。
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
		FieldList list = new FieldList();
		list.addField(new RowNoField());
		list.addAll(this.listQuery.getFieldList());
		return list;
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
	protected String setMultiRecordQueryResult(final Query q, final Map<String, Object> data,  final Map<String, Object> ret) throws Exception {
		//Query query = this.getMainQuery();
		q.setConditionFieldList(this.getMultiRecordQueryKeyList());
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
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.putAll(data);
		Query query = this.getSingleRecordQuery();
		if (query != null) {
			query.setConditionFieldList(query.getMainTable().getPkFieldList());
			query.setConditionData(data);
			Map<String, Object> rec = this.executeRecordQuery(query);
			if (rec != null) {
				ret.putAll(rec);
			}
		}
		if (this.multiRecordQueryList != null) {
			for (Query q: this.multiRecordQueryList) {
				this.setMultiRecordQueryResult(q, data, ret);
			}
		}
		return ret;
	}

	/**
	 * 単一レコード取得用問合せに対応したテーブルにデータを追加します。
	 * <pre>
	 * 単一レコード取得用問合せのmainTableへの追加処理が実装されています。
	 * mainTable以外のテーブルの保存が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	protected void insertSingleRecordTable(final Map<String, Object> data) throws Exception {
		if (this.getSingleRecordQuery() != null) {
			Table table = this.getSingleRecordQuery().getMainTable();
			this.executeInsert(table, data);
		}
	}

	/**
	 * 複数レコード取得用問合せに対応したテーブルにデータを追加します。
	 * <pre>
	 * 複数レコード取得用問合せのmainTableの追加処理が実装されています。
	 * mainTable以外のテーブルの保存が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	protected void insertMultiRecordTable(final Query q, final Map<String, Object> data) throws Exception {
		Table table = q.getMainTable();
		String id = q.getListId();
		FieldList pklist = this.getMultiRecordQueryKeyList();
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
		this.insertSingleRecordTable(data);
		if (this.multiRecordQueryList != null) {
			for (Query q: this.multiRecordQueryList) {
				this.insertMultiRecordTable(q, data);
			}
		}
	}

	/**
	 * 単一レコード取得用問合せに対応したテーブルを更新します。
	 * <pre>
	 * 単一レコード取得用問合せのmainTableの更新処理が実装されています。
	 * mainTable以外のテーブルの更新が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	public void updateSingleRecordTable(final Map<String, Object> data) throws Exception {
		if (this.getSingleRecordQuery() != null) {
			Table table = this.getSingleRecordQuery().getMainTable();
			boolean ret = this.isUpdatable(table, data);
			if (!ret) {
				throw new ApplicationException(this.getPage(), "error.notupdatable");
			}
			// データ更新
			this.executeUpdate(table, data);
		}
	}

	/**
	 * 複数レコード取得用問合せに対応したテーブルを更新します。
	 * <pre>
	 * 複数レコード取得用問合せのmainTableの更新処理が実装されています。
	 * mainTable以外のテーブルの更新が必要な場合オーバーライドして実装してください。
	 * </pre>
	 *
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 *
	 */
	public void updateMultiRecordTable(final Query q, final Map<String, Object> data) throws Exception {
		this.insertMultiRecordTable(q, data);
	}

	/**
	 * テーブル群を更新します。
	 * @param data 更新データ。
	 * @throws Exception 例外。
	 */
	public void update(final Map<String, Object> data) throws Exception {
		this.updateSingleRecordTable(data);
		if (this.multiRecordQueryList != null) {
			for (Query q: this.multiRecordQueryList) {
				this.updateMultiRecordTable(q, data);
			}
		}
	}

	/**
	 * 単一レコード取得用問合せに対応したテーブルデータの削除を行います。
	 * <pre>
	 * 単一レコード取得用問合せのmainTableの削除処理が実装されています。
	 * mainTable以外のテーブルの削除が必要な場合オーバーライドして実装してください。
	 * </pre>
	 * @param data 登録データ。
	 * @throws Exception 例外。
	 */
	protected void deleteSingleRecordTable(final Map<String, Object> data) throws Exception {
		if (this.getSingleRecordQuery() != null) {
			Table table = this.getSingleRecordQuery().getMainTable();
			this.executeDelete(table, data);
		}
	}

	/**
	 * 複数レコード取得用問合せに対応したテーブルデータを削除します。
	 * <pre>
	 * 複数レコード取得用問合せのmainTableの削除処理が実装されています。
	 * mainTable以外のテーブルの削除が必要な場合オーバーライドして実装してください。
	 * </pre>
	 *
	 * @param q 関連テーブル問合せ。
	 * @param data データ。
	 * @throws Exception 例外。
	 *
	 */
	public void deleteMultiRecordTable(final Query q, final Map<String, Object> data) throws Exception {
		FieldList pklist = this.getMultiRecordQueryKeyList();
		Table table = q.getMainTable();
		this.executeDelete(table, pklist, data, true);
	}

	/**
	 * データを削除します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	public void delete(final Map<String, Object> data) throws Exception {
		if (this.multiRecordQueryList != null) {
			for (Query q: this.multiRecordQueryList) {
				this.deleteMultiRecordTable(q, data);
			}
		}
		this.deleteSingleRecordTable(data);
	}
}
