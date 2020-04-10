/**
 * @fileOverview {@link DaoGeneratorEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class DaoGeneratorEditForm
 *
 * @extends EditForm
 */
class DaoGeneratorEditForm extends EditForm {
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
		let thisForm = this;
		this.find("[name='queryType']").click(function() {
			thisForm.onChangeType($(this).val());
		});
		this.onChangeType("0");
	}

	/**
	 * 問合せタイプを設定します。
	 * @param {String} type 問合せタイプ。
	 *
	 */
	onChangeType(type) {
		if (type == "0") {
			this.find("div.singleRecord").show();
			this.find("div.multiRecord").hide();
		} else {
			this.find("div.singleRecord").hide();
			this.find("div.multiRecord").show();
		}
	}
}

