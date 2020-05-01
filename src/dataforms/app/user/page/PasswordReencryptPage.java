package dataforms.app.user.page;

import dataforms.app.base.page.AdminPage;
import dataforms.dao.Dao;


/**
 * ページクラス。
 */
public class PasswordReencryptPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public PasswordReencryptPage() {
		this.addForm(new PasswordRecenryptForm());
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
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return Dao.class;
	}

}
