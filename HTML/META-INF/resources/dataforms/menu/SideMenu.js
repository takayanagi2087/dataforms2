/**
 * @fileOverview {@link SideMenu}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class SideMenu
 *
 * サイドメニュークラス。
 * <pre>
 * </pre>
 * @extends Menu
 */
class SideMenu extends Menu {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisMenu = this;
		var menu = this.get();
		menu.find(".sideMenuGroup").click(function() {
			if (!thisMenu.multiOpenMenu) {
				thisMenu.hideAllMenu();
			}
			var menuGroupId = $(this).attr("data-menu-group-id");
			$(this).next().slideToggle("fast", function() {
				thisMenu.setCookie("menuGroup_" + menuGroupId, $(this).is(":visible"));
			});
			return false;
		});
		menu.find(".sideMenuGroup").each(function() {
			var menuGroupId = $(this).attr("data-menu-group-id");
			var status = thisMenu.getCookie("menuGroup_" + menuGroupId);
			if (status == "true") {
				$(this).next().show();
			} else {
				$(this).next().hide();
			}
		});
	}

	/**
	 * 全メニューを隠します。
	 */
	hideAllMenu() {
		var thisMenu = this;
		var menu = this.get();
		menu.find("[id$='.\pageList']").hide();
		menu.find(".sideMenuGroup").each(function () {
			var menuGroupId = $(this).attr("data-menu-group-id");
			thisMenu.setCookie("menuGroup_" + menuGroupId, false);
		});
	}

}



