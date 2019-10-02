package dataforms.devtool.expwebres.page;

import dataforms.devtool.base.page.DeveloperPage;

/**
 * webリソースエクスポートページクラス。
 *
 */
public class ExportWebResourcePage extends DeveloperPage {
	/**
	 * コンストラクタ。
	 */
	public ExportWebResourcePage() {
		this.addForm(new ExportWebResourceQueryForm());
		this.addForm(new ExportWebResourceQueryResultForm());
	}
}

