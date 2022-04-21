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
		this.get("userImportFlag").click((ev) => {
			if ($(ev.target).prop("checked")) {
				this.get("userInfoTable").hide();
			} else {
				this.get("userInfoTable").show();
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


