package dataforms.devtool.pageform.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * ページJavaクラス作成ページ。
 */
public class PageGeneratorPage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public PageGeneratorPage() {
		this.addForm(new PageGeneratorQueryForm());
		this.addForm(new PageGeneratorQueryResultForm());
		this.addForm(new PageGeneratorEditForm());
		this.setMenuItem(false);
	}


}
