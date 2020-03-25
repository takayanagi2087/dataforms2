/**
 * @fileOverview {@link DeveloperEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class DeveloperEditForm
 *
 * @extends EditForm
 */
class DeveloperEditForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		if (this.userInfoDataExists) {
			this.get("flagDiv").show();
		} else {
			this.get("flagDiv").hide();
		}
		var thisForm = this;
		this.get("userImportFlag").click(function() {
			if ($(this).prop("checked")) {
				thisForm.get("userInfoTable").hide();
			} else {
				thisForm.get("userInfoTable").show();
			}
		});
	}

	/**
	 * フォームのチェックを行います。
	 *
	 */
	validate() {
		if (this.get("userImportFlag").prop("checked")) {
			return true;
		} else {
			return super.validate();
		}
	}
}


