package dataforms.debug.alltype.dao;

import java.util.List;
import java.util.Map;

import dataforms.dao.Dao;
import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.exception.ApplicationException;
import dataforms.field.base.FieldList;
import dataforms.field.common.RowNoField;

/**
 * Daoクラス。
 *
 */
public class SingleSelectDao extends Dao {
	/**
	 * コンストラクタ。
	 * @param obj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public SingleSelectDao(final JDBCConnectableObject obj) throws Exception {
		super(obj);
	}

	/**
	 * QueryFormで指定した条件で行う問い合わせクラスです。
	 */
	public static class SingleSelectTableQuery extends Query {
		/**
		 * コンストラクタ。
		 */
		public SingleSelectTableQuery() {
			Table table = new SingleSelectTable();
			this.setFieldList(table.getFieldList());
			this.setMainTable(table);
		}
	}

	/**
	 * 問い合わせ結果フォームのフィールドリストを取得します。
	 * @return 問い合わせ結果フォームのフィールドリスト。
	 */
	public static FieldList getQueryResultFieldList() {
		Query query = new SingleSelectTableQuery();
		FieldList list = new FieldList();
		list.addField(new RowNoField());
		list.addAll(query.getFieldList());
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
		Query query = new SingleSelectTableQuery();
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
		Query query = new SingleSelectTableQuery();
		query.setConditionFieldList(flist);
		query.setConditionData(data);
		return this.executeQuery(query);
	}


	/**
	 * PKでレコードを限定し、データを取得します。
	 * @param data 条件データ PKの情報をすべて含むマップ。
	 * @return ヒットしたレコード。
	 * @throws Exception 例外。
	 */
	public Map<String, Object> query(final Map<String, Object> data) throws Exception {
		Query query = new SingleSelectTableQuery();
		query.setConditionFieldList(query.getMainTable().getPkFieldList());
		query.setConditionData(data);
		return this.executeRecordQuery(query);
	}

	/**
	 * データを追加します。
	 * @param data データ。
	 * @return 追加件数。
	 * @throws Exception 例外。
	 */
	public int insert(final Map<String, Object> data) throws Exception {
		return this.executeInsert(new SingleSelectTable(), data);
	}


	/**
	 * データを更新します。
	 * @param data データ。
	 * @return 更新件数。
	 * @throws Exception 例外。
	 */
	public int update(final Map<String, Object> data) throws Exception {
		// 楽観ロックチェック
		Table table = new SingleSelectTable();
		boolean ret = this.isUpdatable(table, data);
		if (!ret) {
			throw new ApplicationException(this.getPage(), "error.notupdatable");
		}
		// データ更新
		return this.executeUpdate(table, data);
	}

	/**
	 * データを削除します。
	 * @param data データ。
	 * @return 削除件数。
	 * @throws Exception 例外。
	 */
	public int delete(final Map<String, Object> data) throws Exception {
//		return this.executeRemove(new SingleSelectTable(), data); // レコードの論理削除(DeleteFlagの設定)
		return this.executeDelete(new SingleSelectTable(), data); // レコードの物理削除
	}
}
