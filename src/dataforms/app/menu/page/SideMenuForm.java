package dataforms.app.menu.page;

import dataforms.menu.Menu;
import dataforms.menu.SideMenu;

/**
 * サイドメニューフォームクラス。
 *
 */
public class SideMenuForm extends MenuForm {
	/**
	 * コンストラクタ。
	 */
	public SideMenuForm() {
		super(null);
	}

	@Override
	protected Menu newMenuComponent() {
		SideMenu menu = new SideMenu();
		return menu;
	}


}
