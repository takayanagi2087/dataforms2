package dataforms.app.enumtype.dao;

import java.util.List;
import java.util.Map;

import dataforms.app.enumtype.page.EnumEditForm;
import dataforms.dao.Dao;
import dataforms.dao.JDBCConnectableObject;
import dataforms.exception.ApplicationException;
import dataforms.field.base.FieldList;
import dataforms.field.common.LangCodeField;
import dataforms.field.common.RowNoField;
import dataforms.servlet.DataFormsServlet;

/**
 * Daoクラス。
 *
 */
public class EnumDao extends Dao {
	/**
	 * コンストラクタ。
	 * @param obj JDBC接続可能オブジェクト。
	 * @throws Exception 例外。
	 */
	public EnumDao(final JDBCConnectableObject obj) throws Exception {
		super(obj);
	}

	/**
	 * 問い合わせ結果フォームのフィールドリストを取得します。
	 * @return 問い合わせ結果フォームのフィールドリスト。
	 */
	public static FieldList getQueryResultFieldList() {
		EnumTableQuery query = new EnumTableQuery();
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
		EnumTypeQuery query = new EnumTypeQuery();
		query.setQueryFormFieldList(flist);
		query.setQueryFormData(data);
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
		EnumTableQuery query = new EnumTableQuery();
		query.setQueryFormFieldList(flist);
		query.setQueryFormData(data);
		return this.executeQuery(query);
	}


	/**
	 * 名前を取得します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	private void queryName(final Map<String, Object> data) throws Exception {
		EnumTable.Entity e = new EnumTable.Entity(data);
		Long enumId = e.getEnumId();
		List<Map<String, Object>> list = this.executeQuery(new EnumNameQuery(enumId));
		for (Map<String, Object> m: list) {
			EnumNameTable.Entity ne = new EnumNameTable.Entity(m);
			String lang = ne.getLangCode();
			if (LangCodeField.DEFAULT.equals(lang)) {
				data.put("enumName", ne.getEnumName());
			} else {
				data.put(lang + "EnumName", ne.getEnumName());
			}
		}
	}


	/**
	 * PKでレコードを限定し、データを取得します。
	 * @param data 条件データ PKの情報をすべて含むマップ。
	 * @return ヒットしたレコード。
	 * @throws Exception 例外。
	 */
	public Map<String, Object> query(final Map<String, Object> data) throws Exception {
		EnumTableQuery query = new EnumTableQuery();
		query.setQueryFormFieldList(query.getMainTable().getPkFieldList());
		query.setQueryFormData(data);
		Map<String, Object> ret = this.executeRecordQuery(query);
		this.queryName(ret);
		EnumTable.Entity p = new EnumTable.Entity(ret);
		List<Map<String, Object>> optionList = this.executeQuery(new EnumOptionQuery(p.getEnumId()));
		for (Map<String, Object> m: optionList) {
			this.queryName(m);
		}
		ret.put(EnumEditForm.ID_OPTION_TABLE, optionList);
		return ret;
	}

	/**
	 * 列挙型名称テーブルを削除します。
	 * @param enumId 列挙型のID。
	 * @throws Exception 例外。
	 */
	private void deleteEnumTypeName(final Long enumId) throws Exception {
		String sql = "delete from enum_name where enum_id=:enum_id";
		EnumTable.Entity p = new EnumTable.Entity();
		p.setEnumId(enumId);
		this.executeUpdate(sql, p.getMap());
	}



	/**
	 * 列挙型名称テーブルを削除します。
	 * @param parentId 列挙型のID。
	 * @throws Exception 例外。
	 */
	private void deleteEnumOptionName(final Long parentId) throws Exception {
		String sql = "delete from enum_name where enum_id in (select enum_id from enum where parent_id=:parent_id)";
		EnumTable.Entity p = new EnumTable.Entity();
		p.setParentId(parentId);
		this.executeUpdate(sql, p.getMap());
	}

	/**
	 * 列挙型名称テーブルを更新します。
	 * @param data オプションデータ。
	 * @throws Exception 例外。
	 */
	private void saveEnumName(final Map<String, Object> data) throws Exception {
		EnumTable.Entity e = new EnumTable.Entity(data);
		Long enumId = e.getEnumId();
		List<String> langList = DataFormsServlet.getSupportLanguageList();
		EnumNameTable table = new EnumNameTable();
		{
			EnumNameTable.Entity ne = new EnumNameTable.Entity();
			ne.setEnumId(enumId);
			ne.setLangCode(LangCodeField.DEFAULT);
			ne.setEnumName((String) data.get(EnumNameTable.Entity.ID_ENUM_NAME));
			ne.setCreateUserId(e.getCreateUserId());
			ne.setUpdateUserId(e.getUpdateUserId());
			this.executeInsert(table, ne.getMap());
		}
		for (String lang: langList) {
			EnumNameTable.Entity ne = new EnumNameTable.Entity();
			ne.setEnumId(enumId);
			ne.setLangCode(lang);
			ne.setEnumName((String) data.get(lang + "EnumName"));
			ne.setCreateUserId(e.getCreateUserId());
			ne.setUpdateUserId(e.getUpdateUserId());
			this.executeInsert(table, ne.getMap());
		}
	}

	/**
	 * 列挙型名称テーブルを更新します。
	 * @param list オプションデータリスト。
	 * @throws Exception 例外。
	 */
	private void saveEnumName(final List<Map<String, Object>> list) throws Exception {
		for (Map<String, Object> m: list) {
			this.saveEnumName(m);
		}
	}

	/**
	 * 選択肢リストを更新します。
	 * @param table テーブル。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	private void updateOptionList(EnumTable table, final Map<String, Object> data) throws Exception {
		EnumTable.Entity e = new EnumTable.Entity(data);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(EnumEditForm.ID_OPTION_TABLE);
		for (Map<String, Object> m: list) {
			m.put(EnumTable.Entity.ID_PARENT_ID, e.getEnumId());
		}
		this.deleteEnumOptionName(e.getEnumId());
		EnumTable.Entity p = new EnumTable.Entity();
		p.setParentId(e.getEnumId());
		FieldList flist = new FieldList();
		flist.addField(table.getParentIdField());
		this.saveTable(new EnumTable(), list, p.getMap(), flist);
		this.saveEnumName(list);

	}

	/**
	 * データを追加します。
	 * @param data データ。
	 * @return 追加件数。
	 * @throws Exception 例外。
	 */
	public int insert(final Map<String, Object> data) throws Exception {
		EnumTable table = new EnumTable();
		int ret = this.executeInsert(table, data);
		if (ret == 1) {
			this.saveEnumName(data);
			this.updateOptionList(table, data);
		}
		return ret;
	}

	/**
	 * データを更新します。
	 * @param data データ。
	 * @return 更新件数。
	 * @throws Exception 例外。
	 */
	public int update(final Map<String, Object> data) throws Exception {
		// 楽観ロックチェック
		EnumTable table = new EnumTable();
		boolean ret = this.isUpdatable(table, data);
		if (!ret) {
			throw new ApplicationException(this.getPage(), "error.notupdatable");
		}
		// データ更新
		int cnt = this.executeUpdate(table, data);
		if (cnt > 0) {
			EnumTable.Entity e = new EnumTable.Entity(data);
			this.deleteEnumTypeName(e.getEnumId());
			this.saveEnumName(data);
			this.updateOptionList(table, data);
		}
		return cnt;
	}

	/**
	 * データを削除します。
	 * @param data データ。
	 * @return 削除件数。
	 * @throws Exception 例外。
	 */
	public int delete(final Map<String, Object> data) throws Exception {
		EnumTable table = new EnumTable();
		EnumTable.Entity e = new EnumTable.Entity(data);
		this.deleteEnumTypeName(e.getEnumId());
		this.deleteEnumOptionName(e.getEnumId());
		EnumTable.Entity p = new EnumTable.Entity();
		p.setParentId(e.getEnumId());
		FieldList flist = new FieldList();
		flist.addField(table.getParentIdField());
		this.executeDelete(table, flist, p.getMap(), true); // オプションの削除
		int ret = this.executeDelete(table, data); // レコードの物理削除
		return ret;
	}
}
