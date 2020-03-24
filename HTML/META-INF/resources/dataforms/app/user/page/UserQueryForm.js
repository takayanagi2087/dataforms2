/**
 * @fileOverview {@link UserQueryForm}クラスを記述したファイルです。
 */

/**
 * @class UserQueryForm
 *
 * @extends QueryForm
 */
class UserQueryForm extends QueryForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		if (currentPage.userInfo.userLevel == "developer") {
			this.get("exportInitDataButton").click(() => {
				this.exportInitData()
			});
		} else {
			this.get("exportInitDataButton").remove();
		}
	}

	/**
	 * データのエクスポートを行います。
	 */
	exportInitData() {
		var thisForm = this;
		currentPage.confirm(null, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"), function() {
			thisForm.submit("export", function(data) {
				currentPage.alert(null, data.result);
			});
		});
	}
}



