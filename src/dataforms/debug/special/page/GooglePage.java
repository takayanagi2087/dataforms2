package dataforms.debug.special.page;

import dataforms.app.base.page.BasePage;

/**
 * Googleを表示するページ。
 *
 */
public class GooglePage extends BasePage {

	/**
	 * コンストラクタ。
	 */
	public GooglePage() {

	}

	@Override
	public String getMenuUrl() {
		return "https://www.google.com";
	}

	@Override
	public String getMenuTarget() {
		return "_blank";
	}
}
