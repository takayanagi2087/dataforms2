package sample.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
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
