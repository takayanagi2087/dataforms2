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
	async exportInitData() {
		let ret = await currentPage.confirm(null, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"));
		if (ret) {
			let data = await this.submit("export");
			await currentPage.alert(null, data.result);
		}
	}
}



