/**
 * @fileOverview {@link EnumQueryForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class EnumQueryForm
 *
 * @extends QueryForm
 */
class EnumQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

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
			currentPage.alert(null, data.result);
		}
	}
}

