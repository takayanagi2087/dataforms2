package sample.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import dataforms.dao.Table;
import sample.dao.SupplierMasterTable;
import sample.dao.SupplierMasterDao;


/**
 * ページクラス。
 */
public class SupplierMasterPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterPage() {
		this.addForm(new SupplierMasterQueryForm());
		this.addForm(new SupplierMasterQueryResultForm());
		this.addForm(new SupplierMasterEditForm());

	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/sample";
	}

	/**
	 * 操作対象テーブルクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return 操作対象テーブル。
	 */
	public Class<? extends  Table> getTableClass() {
		return SupplierMasterTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return SupplierMasterDao.class;
	}

}
