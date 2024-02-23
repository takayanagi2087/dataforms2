package dataforms.devtool.pageform.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * DaoとページJavaクラス作成ページ。
 */
public class DaoAndPageGeneratorPage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public DaoAndPageGeneratorPage() {
		this.addForm(new PageGeneratorQueryForm());
		this.addForm(new PageGeneratorQueryResultForm());
		this.addForm(new DaoAndPageGeneratorEditForm());
		this.addDialog(new FieldListDialog());
	}
}
