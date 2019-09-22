/**
 * @fileOverview {@link RestoreForm}クラスを記述したファイルです。
 */

/**
 * @class RestoreForm
 *
 * @extends Form
 */
class RestoreForm extends Form {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.find("#restoreButton").click(function() {
			thisForm.restore();
			return false;
		});
	}

	/**
	 * リストア処理を行います。
	 */
	restore() {
		var thisForm = this;
		if (this.validate()) {
			var systemname = MessagesUtil.getMessage("message.systemname");
			var msg = MessagesUtil.getMessage("message.restoreconfirm");
			currentPage.confirm(systemname, msg, function() {
				thisForm.submit("restore", function(r) {
					thisForm.parent.resetErrorStatus();
					if (r.status == ServerMethod.INVALID) {
						currentPage.setErrorInfo(thisForm.getValidationResult(r), thisForm);
					} else {
						currentPage.alert(systemname, r.result);
					}
				});
			});
		}
	}
}



