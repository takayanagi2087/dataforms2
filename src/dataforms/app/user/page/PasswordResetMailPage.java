package dataforms.app.user.page;

import dataforms.app.base.page.BasePage;

/**
 * パスワードリセットメール送信フォームクラス。
 *
 */
public class PasswordResetMailPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public PasswordResetMailPage() {
		this.addForm(new PasswordResetMailForm());
	}
}
