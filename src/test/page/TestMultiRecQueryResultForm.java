package test.page;


import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.field.base.FieldList;
import dataforms.htmltable.PageScrollHtmlTable;
import test.dao.TestCode1Query;
import test.dao.TestMultiRecDao;



/**
 * 問い合わせ結果フォームクラス。
 */
public class TestMultiRecQueryResultForm extends QueryResultForm {
	/**
	 * Logger。
	 */
	private static Logger logger = LogManager.getLogger(TestMultiRecQueryResultForm.class);

	/**
	 * コンストラクタ。
	 */
	public TestMultiRecQueryResultForm() {
		TestMultiRecDao dao = new TestMultiRecDao();
		this.addPkFieldList(dao.getEditFormKeyList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, dao.getListFieldList());
		htmltable.getFieldList().get(TestCode1Query.Entity.ID_CODE1).setSortable(true);
		htmltable.getFieldList().get(TestCode1Query.Entity.ID_CNT).setSortable(true);

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
		TestMultiRecDao dao = new TestMultiRecDao(this);
		TestCode1Query q = dao.getTestCode1Query();
		logger.debug(q.getCntField());
		logger.debug("getTestSmallintField=" + q.getTestSmallintField().getClass().getName());
		logger.debug("getTestIntegerField=" + q.getTestIntegerField().getClass().getName());
		logger.debug("getTestBigintField=" + q.getTestBigintField().getClass().getName());
		logger.debug("getTestDoubleField=" + q.getTestDoubleField().getClass().getName());
		logger.debug("getTestNumericField=" + q.getTestNumericField().getClass().getName());

		Map<String, Object> ret = dao.queryPage(data, queryFormFieldList);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) ret.get("queryResult");
		for (Map<String, Object> m: list) {
			TestCode1Query.Entity e = new TestCode1Query.Entity(m);
			logger.debug("---------------------");
			logger.debug("cnt=" +  e.getCnt());
			logger.debug("testSmallint=" +  e.getTestSmallint());
			logger.debug("testInteger=" +  e.getTestInteger());
			logger.debug("testBigint=" +  e.getTestBigint());
			logger.debug("testDouble=" +  e.getTestDouble());
			logger.debug("testNumeric=" +  e.getTestNumeric());
		}
		return ret;
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
		TestMultiRecDao dao = new TestMultiRecDao(this);
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
			ret = new JsonResponse(JsonResponse.SUCCESS, data);
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
