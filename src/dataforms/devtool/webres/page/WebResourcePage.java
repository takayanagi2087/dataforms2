package dataforms.devtool.webres.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * Webリソースページクラス。
 * <pre>
 * 指定されたPage javaクラスに含まれるWebComponentのに対応した、
 * html,jsを作成するためのページです。
 * </pre>
 */
public class WebResourcePage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public WebResourcePage() {
		this.addForm(new WebResourceQueryForm());
		this.addForm(new WebResourceQueryResultForm());
		this.addDialog(new WebResourceDialog());
	}
}
