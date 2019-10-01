package dataforms.app.user.page;

import dataforms.app.base.page.BasePage;

/**
 * 外部ユーザ登録ページ。
 *
 */
public class UserRegistPage extends BasePage {
	
	
	/**
	 * コンストラクタ。
	 */
	public UserRegistPage() {
		this.addForm(new UserRegistForm());
		this.setMenuItem(true);
	}
	
}
