/**
 * @fileOverview {@link ErrorPage}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class ErrorPage
 *
 * エラーメッセージページクラスです。
 * <pre>
 * エラーメッセージ表示ページです。
 * </pre>
 * @extends BasePage
 */
class ErrorPage extends BasePage {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var title = MessagesUtil.getMessage("errorpage.title");
		var backButton = MessagesUtil.getMessage("errorpage.backbutton");
		var message = this.errorMessage;
		$("title").html(title);
		$("h1").html(title);
		this.get("errorMessages").html(message);
		this.get("backButton").val(backButton);
	}
}

