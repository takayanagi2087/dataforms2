package sample.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import sample.dao.MaterialMasterDao;


/**
 * ページクラス。
 */
public class MaterialMasterPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterPage() {
		this.addForm(new MaterialMasterQueryForm());
		this.addForm(new MaterialMasterQueryResultForm());
		this.addForm(new MaterialMasterEditForm());

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
		return MaterialMasterDao.class;
	}

}
