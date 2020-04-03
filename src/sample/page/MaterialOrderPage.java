package sample.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import dataforms.dao.Table;
import sample.dao.MaterialOrderTable;
import sample.dao.MaterialOrderDao;


/**
 * ページクラス。
 */
public class MaterialOrderPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderPage() {
		this.addForm(new MaterialOrderQueryForm());
		this.addForm(new MaterialOrderQueryResultForm());
		this.addForm(new MaterialOrderEditForm());

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
		return MaterialOrderTable.class;
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return MaterialOrderDao.class;
	}

}
