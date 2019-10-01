package dataforms.app.enumeration.page;

import java.util.Map;

import dataforms.app.enumeration.dao.EnumManagementDao;
import dataforms.app.enumeration.dao.EnumOptionTable;
import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.dao.Table;
import dataforms.field.base.FieldList;
import dataforms.htmltable.PageScrollHtmlTable;


/**
 * 列挙型管理問い合わせ結果フォームクラス。
 */
public class EnumManagementQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public EnumManagementQueryResultForm() {
		Table table = new EnumOptionTable();
		this.addPkFieldList(table.getPkFieldList());
		PageScrollHtmlTable htmltable = new PageScrollHtmlTable(Page.ID_QUERY_RESULT, EnumManagementDao.getQueryResultFieldList());
		this.addHtmlTable(htmltable);
	}

	@Override
	protected Map<String, Object> queryPage(final Map<String, Object> data, final FieldList queryFormFieldList) throws Exception {
		EnumManagementDao dao = new EnumManagementDao(this);
		return dao.queryPage(data, queryFormFieldList);
	}

	@Override
	protected void deleteData(final Map<String, Object> data) throws Exception {
		EnumManagementDao dao = new EnumManagementDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}
}
