package dataforms.devtool.version.page;

import dataforms.app.base.page.BasePage;

/**
 * バージョン情報ページクラスです。
 *
 */
public class VersionInfoPage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public VersionInfoPage() {
		this.addForm(new VersionInfoForm());
	}
}
