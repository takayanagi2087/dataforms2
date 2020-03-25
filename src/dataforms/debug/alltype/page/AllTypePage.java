package dataforms.debug.alltype.page;

import dataforms.app.base.page.AdminPage;
import dataforms.app.user.dialog.UserQueryDialog;


/**
 * 全データタイプの入力テスト用ページクラス。
 *
 */
public class AllTypePage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public AllTypePage() {
		this.addForm(new AllTypeQueryForm());
		this.addForm(new AllTypeQueryResultForm());
		this.addForm(new AllTypeEditForm());
		this.addDialog(new UserQueryDialog());
		
	}
}
