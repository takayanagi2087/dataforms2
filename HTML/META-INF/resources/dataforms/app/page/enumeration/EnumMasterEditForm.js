/**
 * @fileOverview {@link EnumMasterEditForm}クラスを記述したファイルです。
 */

/**
 * @class EnumMasterEditForm
 *
 * @extends EnumManagementEditForm
 */
class EnumMasterEditForm extends EnumManagementEditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		var optionNameList = this.getComponent("optionNameList");
		optionNameList.onAddTr = function(rowid) {
			thisForm.setMultiLanguageMode();
		};
	}

	/**
	 * 多言語モードの設定を行います。
	 */
	setMultiLanguageMode() {
		logger.log("multiLanguage=" + this.multiLanguage);
		if (!this.multiLanguage) {
			this.find(".langCode").hide();
		} else {
			this.find(".langCode").show();
		}
	}

	/**
	 * フォームのデータを設定します。
	 * @param {Object} data データ。
	 */
	setFormData(data) {
		super.setFormData(data);
		this.setMultiLanguageMode();
	}

}


