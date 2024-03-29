package dataforms.debug.alltype.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import dataforms.dao.Table;
import dataforms.debug.alltype.dao.SingleSelectDao;
import dataforms.debug.alltype.dao.SingleSelectTable;


/**
 * ページクラス。
 */
public class SelectItemPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public SelectItemPage() {
		this.addForm(new SelectItemQueryForm());
		this.addForm(new SelectItemQueryResultForm());
		this.addForm(new SelectItemEditForm());

	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/dataforms/debug";
	}

	/**
	 * 操作対象テーブルクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return 操作対象テーブル。
	 */
	public Class<? extends  Table> getTableClass() {
		return SingleSelectTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return SingleSelectDao.class;
	}

}
