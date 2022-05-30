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
				this.exportInitData();
			});
			this.get("importDataButton").click(() => {
				this.importInitData();
			});
			this.get("importV1DataButton").click(() => {
				this.importV1InitData();
			});
		} else {
			this.get("exportInitDataButton").remove();
			this.get("importDataButton").remove();
			this.get("importV1DataButton").remove();
		}
	}

	/**
	 * データのエクスポートを行います。
	 */
	async exportInitData() {
		let ret = await currentPage.confirm(null, MessagesUtil.getMessage("message.exportAsInitialDataConfirm"));
		if (ret) {
			let data = await this.submit("export");
			await currentPage.alert(null, data.result);
		}
	}

	/**
	 * データのインポートを行います。
	 */
	async importInitData() {
		let ret = await currentPage.confirm(null, MessagesUtil.getMessage("message.importInitialDataConfirm"));
		if (ret) {
			let data = await this.submit("importData");
			await currentPage.alert(null, data.result);
		}
	}

	/**
	 * ver1.x形式のデータのインポートを行います。
	 */
	async importV1InitData() {
		let ret = await currentPage.confirm(null, MessagesUtil.getMessage("message.importV1InitialDataConfirm"));
		if (ret) {
			let data = await this.submit("importV1Data");
			await currentPage.alert(null, data.result);
		}
	}

}



