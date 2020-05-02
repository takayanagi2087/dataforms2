/**
 * @fileOverview {@link LoginForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class LoginForm
 * ログインフォームクラス。
 * <pre>
 * ユーザIDとパスワードを入力しログイン処理を行います。
 * </pre>
 * @extends Form
 */
class LoginForm extends Form {
	/**
	 * ログイン処理を行います。
	 *
	 */
	login() {
		var form = this;
		if (form.validate()) {
			form.submit("login", function(result) {
				form.parent.resetErrorStatus();
				if (result.status == ServerMethod.SUCCESS) {
					if (form.parent instanceof Dialog) {
						form.parent.close();
					}
					currentPage.toTopPage();
				} else {
					form.parent.setErrorInfo(form.getValidationResult(result), form);
				}
			});
		}
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 以下のイベント処理を登録します。
	 * #loginButton ... ログイン処理。
	 * </pre>
	 */
	attach() {
		super.attach();
		var form = this;
		this.get("loginButton").click(() => {
			this.login();
			return false;
		});
		if (this.passwordResetMailPage != null) {
			$(this.convertSelector("#passwordResetLink")).attr("href", currentPage.contextPath + this.passwordResetMailPage);
		} else {
			$(this.convertSelector("#passwordResetLink")).hide();
		}
		if (this.autoLogin) {
			this.get("keepLogin").show();
			this.find("label[for='" + this.get("keepLogin").attr("id") + "']").show();
		} else {
			this.get("keepLogin").hide();
			this.find("label[for='" + this.get("keepLogin").attr("id") + "']").hide();
		}
	}

}

