package dataforms.debug.alltype.page;

import dataforms.app.base.page.AdminPage;
import dataforms.dialog.image.ImageDialog;

/**
 * ページクラス。
 */
public class FileTestPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public FileTestPage() {
		this.addForm(new FileTestQueryForm());
		this.addForm(new FileTestQueryResultForm());
		this.addForm(new FileTestEditForm());
		this.addDialog(new ImageDialog());
	}
	
}
