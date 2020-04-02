package sample.page;

import java.util.Map;

import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.field.base.FieldList;
import dataforms.htmltable.PageScrollHtmlTable;
import sample.dao.SupplierMasterDao;
import sample.dao.SupplierMasterTable;


/**
 * 問い合わせ結果フォームクラス。
 */
public class SupplierMasterQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterQueryResultForm() {
		SupplierMasterDao dao = new SupplierMasterDao();
		SupplierMasterTable table = dao.getMainTable();
		this.addPkFieldList(table.getPkFieldList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, dao.getListFieldList());
		htmltable.getFieldList().get(SupplierMasterTable.Entity.ID_SUPPLIER_CODE).setSortable(true);
		htmltable.getFieldList().get(SupplierMasterTable.Entity.ID_SUPPLIER_NAME).setSortable(true);
		htmltable.getFieldList().get(SupplierMasterTable.Entity.ID_SUPPLIER_KANA_NAME).setSortable(true);
		htmltable.getFieldList().get(SupplierMasterTable.Entity.ID_PHONE_NO).setSortable(true);
		htmltable.getFieldList().get(SupplierMasterTable.Entity.ID_FAX_NO).setSortable(true);

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
		SupplierMasterDao dao = new SupplierMasterDao(this);
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
		SupplierMasterDao dao = new SupplierMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}
}
