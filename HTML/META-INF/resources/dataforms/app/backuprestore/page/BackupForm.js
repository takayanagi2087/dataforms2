/**
 * @fileOverview {@link BackupForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class BackupForm
 *
 * @extends Form
 */
class BackupForm extends Form {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		this.get("backupButton").click(() => { this.backup(); return false;});
	}

	/**
	 * バックアップ処理。
	 */
	backup() {
		var thisForm = this;
		thisForm.parent.resetErrorStatus();
		this.submit("backup", function(r) {
			thisForm.parent.resetErrorStatus();
			if (r.status == ServerMethod.INVALID) {
				currentPage.setErrorInfo(thisForm.getValidationResult(r), thisForm);
			} else {
				var systemname = MessagesUtil.getMessage("message.systemname");
				currentPage.alert(systemname, r.result);
			}
		});
	}
}


