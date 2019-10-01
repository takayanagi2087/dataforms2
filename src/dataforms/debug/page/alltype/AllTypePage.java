package dataforms.debug.page.alltype;

import dataforms.app.base.page.AdminPage;


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
		//this.addDialog(new UserQueryDialog());
		
	}
}
