package dataforms.app.base.page;

import java.util.Map;

import dataforms.app.login.page.LoginInfoForm;
import dataforms.app.menu.page.SideMenuForm;
import dataforms.controller.Page;


/**
 * 基本ページクラス。
 */
public abstract class BasePage extends Page {
    /**
     * Logger.
     */
//    private static Logger log = Logger.getLogger(BasePage.class.getName());

	/**
	 * コンストラクタ.
	 */
	public BasePage() {
		// ヘッダにユーザ情報フォームを配置.
		this.addForm(new LoginInfoForm());
		// 左サイドバーにメニューフォームを配置.
		this.addForm(new SideMenuForm());
	}



	/**
	 * {@inheritDoc}
	 * 常に表示可能と判定します。
	 */
	@Override
	public boolean isAuthenticated(final Map<String, Object> params) throws Exception {
		return true;
	}
}
