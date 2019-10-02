package dataforms.devtool.webres.page;

import dataforms.controller.Dialog;

/**
 * Webリソース作成ダイアログクラス。
 */
public class WebResourceDialog extends Dialog {

	/**
	 * コンストラクタ。
	 */
	public WebResourceDialog() {
		super(null);
		this.addForm(new WebResourceForm());
	}

}
