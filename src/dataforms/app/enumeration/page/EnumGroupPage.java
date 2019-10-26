package dataforms.app.enumeration.page;

import dataforms.app.base.page.AdminPage;
import dataforms.app.enumeration.dao.EnumGroupDao;
import dataforms.app.enumeration.dao.EnumGroupTable;
import dataforms.dao.Dao;
import dataforms.dao.Table;


/**
 * ページクラス。
 */
public class EnumGroupPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public EnumGroupPage() {
		this.addForm(new EnumGroupQueryForm());
		this.addForm(new EnumGroupQueryResultForm());
		this.addForm(new EnumGroupEditForm());

	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/dataforms/app";
	}

	/**
	 * 操作対象テーブルクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return 操作対象テーブル。
	 */
	public Class<? extends  Table> getTableClass() {
		return EnumGroupTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return EnumGroupDao.class;
	}

}
