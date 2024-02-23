package pagepat.page;

import java.util.Map;
import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.field.base.FieldList;
import dataforms.field.base.Field.Display;
import dataforms.htmltable.PageScrollHtmlTable;
import pagepat.dao.Test03Dao;
import pagepat.dao.TestTable;



/**
 * 一覧→1レコード編集ページ用問合せ結果フォームクラス。
 */
public class Test03QueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public Test03QueryResultForm() {
		Test03Dao dao = new Test03Dao();
		this.addPkFieldList(dao.getEditFormKeyList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, dao.getListFieldList());
		htmltable.getFieldList().get(TestTable.Entity.ID_TEST_ID).setQueryResultFormDisplay(Display.INPUT_HIDDEN);
		htmltable.getFieldList().get(TestTable.Entity.ID_CODE1).setQueryResultFormDisplay(Display.SPAN).setSortable(true);
		htmltable.getFieldList().get(TestTable.Entity.ID_CODE2).setQueryResultFormDisplay(Display.SPAN).setSortable(true);
		htmltable.getFieldList().get(TestTable.Entity.ID_NAME).setQueryResultFormDisplay(Display.SPAN).setSortable(true);
		htmltable.getFieldList().get(TestTable.Entity.ID_CREATE_USER_ID).setQueryResultFormDisplay(Display.INPUT_HIDDEN);
		htmltable.getFieldList().get(TestTable.Entity.ID_CREATE_TIMESTAMP).setQueryResultFormDisplay(Display.INPUT_HIDDEN);
		htmltable.getFieldList().get(TestTable.Entity.ID_UPDATE_USER_ID).setQueryResultFormDisplay(Display.INPUT_HIDDEN);
		htmltable.getFieldList().get(TestTable.Entity.ID_UPDATE_TIMESTAMP).setQueryResultFormDisplay(Display.INPUT_HIDDEN);

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
		Test03Dao dao = new Test03Dao(this);
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
		Test03Dao dao = new Test03Dao(this);
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