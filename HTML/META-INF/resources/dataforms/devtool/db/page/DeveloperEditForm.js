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
			this.find("#flagDiv").show();
		} else {
			this.find("#flagDiv").hide();
		}
		var thisForm = this;
		this.find("#userImportFlag").click(function() {
			if ($(this).prop("checked")) {
				thisForm.find("#userInfoTable").hide();
			} else {
				thisForm.find("#userInfoTable").show();
			}
		});
	}


	validate() {
		if (this.find("#userImportFlag").prop("checked")) {
			return true;
		} else {
			return super.validate();
		}
	}
}


