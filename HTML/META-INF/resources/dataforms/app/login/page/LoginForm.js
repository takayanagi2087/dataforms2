/**
 * @fileOverview {@link LoginForm}クラスを記述したファイルです。
 */

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
	 * #resetButton ... リセット処理。
	 * #closeButton ... 閉じる処理。
	 * </pre>
	 */
	attach() {
		super.attach();
		var form = this;
		this.find('#loginButton').click(function() {
			form.login();
			return false;
		});
		this.find('#resetButton').click(function() {
			form.reset();
			return false;
		});
		if (this.passwordResetMailPage != null) {
			$(this.convertSelector("#passwordResetLink")).attr("href", currentPage.contextPath + this.passwordResetMailPage);
		} else {
			$(this.convertSelector("#passwordResetLink")).hide();
		}
		if (this.autoLogin) {
			this.find("#keepLogin").show();
			this.find("label[for='keepLogin']").show();
		} else {
			this.find("#keepLogin").hide();
			this.find("label[for='keepLogin']").hide();
		}
	}

}

