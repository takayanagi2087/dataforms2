package dataforms.devtool.func.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * 機能管理ページクラス。
 *
 */
public class FuncManagementPage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public FuncManagementPage() {
		this.addForm(new FuncEditForm());
	}
}
