package dataforms.debug.special.page;

import dataforms.app.base.page.AdminPage;
import dataforms.dao.Dao;
import dataforms.dao.Table;
import dataforms.debug.special.dao.SmallMasterDao;
import dataforms.debug.special.dao.SmallMasterTable;


/**
 * ページクラス。
 */
public class SmallMasterPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public SmallMasterPage() {
		this.addForm(new SmallMasterQueryForm());
		this.addForm(new SmallMasterEditForm());
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
		return SmallMasterTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return SmallMasterDao.class;
	}

}
