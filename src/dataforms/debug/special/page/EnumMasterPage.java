package dataforms.debug.special.page;

import dataforms.app.base.page.AdminPage;
import dataforms.app.enumeration.page.EnumMasterEditForm;

/**
 * 列挙型マスタフォームデバッグページ。
 *
 */
public class EnumMasterPage extends AdminPage {
	/**
	 * コンストラクタ。
	 */
	public EnumMasterPage() {
		this.addForm(new EnumMasterEditForm("section", true));
//		this.addForm(new EnumMasterEditForm("test"));
	}
}
