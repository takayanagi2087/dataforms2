/**
 * @fileOverview {@link UserRegistForm}クラスを記述したファイルです。
 */

/**
 * @class UserRegistForm
 *
 * 外部ユーザ登録フォーム。
 * @extends EditForm
 */
class UserRegistForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		logger.log("config=" + JSON.stringify(this.config));
		var thisForm = this;
		if (this.config.loginIdIsMail) {
			this.find("#loginId").parents("tr:first").hide();
			this.find("#mailAddress").change(function() {
				thisForm.copyToLoginId($(this));
			});
		}
		if (!this.config.mailCheck) {
			this.find("#mailAddressCheck").parents("tr:first").hide();
		}
	};

	/**
	 * メールアドレスをloginIdへコピーします。
	 */
	copyToLoginId(txt) {
		this.setFieldValue("loginId", txt.val());
	}
}


