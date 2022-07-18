package test.page;

import dataforms.app.base.page.BasePage;

/**
 * WebMethodのテストページ。
 *
 */
public class WebMethodPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public WebMethodPage() {
		this.addForm(new WebMethodForm());
	}
}
