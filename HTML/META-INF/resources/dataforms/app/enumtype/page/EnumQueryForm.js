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
			this.find("#exportInitDataButton").click(() => {
				this.exportInitData()
			});
		} else {
			this.find("#exportInitDataButton").remove();
		}
	}

	/**
	 * データのエクスポートを行います。
	 */
	exportInitData() {
		currentPage.confirm(null, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"), () => {
			this.submit("export", (data) => {
				currentPage.alert(null, data.result);
			});
		});
	}

}

