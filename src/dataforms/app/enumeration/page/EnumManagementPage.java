package dataforms.app.enumeration.page;

import dataforms.app.base.page.AdminPage;
import dataforms.app.enumeration.dao.EnumManagementDao;
import dataforms.app.enumeration.dao.EnumOptionTable;
import dataforms.dao.Dao;
import dataforms.dao.Table;


/**
 * 列挙型管理ページクラス。
 */
public class EnumManagementPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public EnumManagementPage() {
		this.addForm(new EnumManagementQueryForm());
		this.addForm(new EnumManagementQueryResultForm());
		this.addForm(new EnumManagementEditForm());

	}


	/**
	 * 操作対象テーブルクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成で用のメソッドです。
	 * </pre>
	 * @return 操作対象テーブル。
	 */
	public Class<? extends  Table> getTableClass() {
		return EnumOptionTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成で用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return EnumManagementDao.class;
	}

}
