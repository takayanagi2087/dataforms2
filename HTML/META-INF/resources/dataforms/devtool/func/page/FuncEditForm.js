/**
 * @fileOverview {@link FuncEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class FuncEditForm
 *
 * @extends EditForm
 */
class FuncEditForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		this.get("exportInitDataButton").click(() => {
			this.exportInitData();
		});
	}

	/**
	 * データのエクスポートを行います。
	 */
	async exportInitData() {
		if (await currentPage.confirm(null, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"))) {
			let data = await this.submit("export");
			currentPage.alert(null, data.result);
		}
	}


	/**
	 * 編集モードにします。
	 */
	toEditMode() {
		super.toEditMode();
		let table = this.getComponent("funcTable");
		this.find("[id$='\\.funcPath']").each((_, tx) => {
			let f = this.getComponent($(tx).attr(this.getIdAttribute()));
			logger.log("id=" + f.id + ":" + $(tx).val());
			if ($(tx).val().indexOf("/dataforms") == 0) {
				let field = table.getSameRowField($(tx), "funcName");
				let namef = this.getComponent(field.attr(this.getIdAttribute()));
				f.lock(true);
				namef.lock(true);
			}
		});
	}
}



