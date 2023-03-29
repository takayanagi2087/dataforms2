package sample.page;

import java.util.Map;

import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.dao.Table;
import dataforms.field.base.FieldList;
import dataforms.htmltable.PageScrollHtmlTable;
import sample.dao.MaterialMasterDao;
import sample.dao.MaterialMasterTable;



/**
 * 問い合わせ結果フォームクラス。
 */
public class MaterialMasterQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterQueryResultForm() {
		MaterialMasterDao dao = new MaterialMasterDao();
		Table table = dao.getMainTable();
		this.addPkFieldList(table.getPkFieldList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, dao.getListFieldList());
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_CODE).setSortable(true);
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_NAME).setSortable(true);
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_UNIT).setSortable(true);
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_UNIT_PRICE).setSortable(true);
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_ORDER_POINT).setSortable(true);
		htmltable.getFieldList().get(MaterialMasterTable.Entity.ID_MEMO).setSortable(true);

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
		MaterialMasterDao dao = new MaterialMasterDao(this);
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
		MaterialMasterDao dao = new MaterialMasterDao(this);
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
			ret = null;
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
