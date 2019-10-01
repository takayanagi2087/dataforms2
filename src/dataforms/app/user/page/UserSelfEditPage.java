package dataforms.app.user.page;

import dataforms.app.base.page.UserPage;

/**
 * ユーザ情報更新ページクラス。
 * <pre>
 * ユーザが自分自身の情報を更新するページです。
 * </pre>
 */
public class UserSelfEditPage extends UserPage {
	/**
	 * コンストラクタ.
	 */
	public UserSelfEditPage() {
		this.addForm(new UserSelfEditForm());
	}

}
