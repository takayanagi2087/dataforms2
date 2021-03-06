/**
 * @fileOverview {@link RestoreForm}クラスを記述したファイルです。
 */

'use strict';

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
		this.get("restoreButton").click(() => { this.restore(); return false;	});
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



