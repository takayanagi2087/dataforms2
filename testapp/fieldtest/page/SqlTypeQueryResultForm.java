package fieldtest.page;

import java.util.Map;
import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.field.base.FieldList;
import dataforms.htmltable.PageScrollHtmlTable;
import fieldtest.dao.SqlTypeDao;
import fieldtest.dao.SqlTypeTable;



/**
 * 問い合わせ結果フォームクラス。
 */
public class SqlTypeQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public SqlTypeQueryResultForm() {
		SqlTypeDao dao = new SqlTypeDao();
		this.addPkFieldList(dao.getEditFormKeyList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, dao.getListFieldList());
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_CHAR).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_VARCHAR).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_SMALLINT).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_INTEGER).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_DOUBLE).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_NUMERIC).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_DATE).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_TIME).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_TIMESTAMP).setSortable(true);
		htmltable.getFieldList().get(SqlTypeTable.Entity.ID_SQL_CLOB).setSortable(true);

		this.addHtmlTable(htmltable);
	}

	/**
	 * 問い合わせを行い、1ページ分の問い合わせ結果を返します。
	 *
	 * @param data 問い合わせフォームの入力データ。
	 * @param queryFormFieldList 問い合わせフォームのフィールドリスト。
	 * @return 問い合わせ結果。
	 *
	 */
	@Override
	protected Map<String, Object> queryPage(final Map<String, Object> data, final FieldList queryFormFieldList) throws Exception {
		SqlTypeDao dao = new SqlTypeDao(this);
		return dao.queryPage(data, queryFormFieldList);
	}

	/**
	 * 問い合わせ結果リストのデータを削除します。
	 * <pre>
	 * 問い合わせ結果リストからの削除が不要な場合、HTMLから削除ボタンを削除し、
	 * このメソッドを何もしないメソッドにしてください。
	 * </pre>
	 * @param data 選択したデータのPKの値を記録したマップ。
	 */
	@Override
	protected void deleteData(final Map<String, Object> data) throws Exception {
		SqlTypeDao dao = new SqlTypeDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}

	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			Map<String, Object> data = this.convertToServerData(p);
			ret = new JsonResponse(JsonResponse.SUCCESS, data);	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
