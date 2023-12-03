package pagepat.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;


/**
 * 単純なページ ページクラス。
 */
public class SimplePage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public SimplePage() {
		this.addForm(new SimpleForm());
	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/pagepat";
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return null;
	}
}
