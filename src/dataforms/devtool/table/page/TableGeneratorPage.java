package dataforms.devtool.table.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * ページクラス。
 */
public class TableGeneratorPage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public TableGeneratorPage() {
		this.addForm(new TableGeneratorQueryForm());
		this.addForm(new TableGeneratorQueryResultForm());
		this.addForm(new TableGeneratorEditForm());

	}
}
