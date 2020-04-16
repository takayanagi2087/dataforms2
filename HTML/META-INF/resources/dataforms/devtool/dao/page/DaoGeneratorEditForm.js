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
		this.onChangeType("1");
		this.find("[name='queryType']").click(function() {
			thisForm.getKeyFieldList();
		});
	}

	/**
	 * 問合せタイプを設定します。
	 * @param {String} type 問合せタイプ。
	 *
	 */
	onChangeType(type) {
		if (type == "0") {
			this.find("table .editFormQuery").hide();
			this.find("div.singleRecord").hide();
			this.find("div.multiRecord").hide();
		} else if (type == "1") {
			this.find("table .editFormQuery").show();
			this.find("div.singleRecord").show();
			this.find("div.multiRecord").hide();
		} else {
			this.find("table .editFormQuery").show();
			this.find("div.singleRecord").hide();
			this.find("div.multiRecord").show();
		}
	}

	/**
	 * フォームに対してデータを設定します。
	 * @param {Object} data フォームデータ。
	 */
	setFormData(data) {
		logger.dir(data);
		super.setFormData(data);
		let fsel = this.getComponent("functionSelect");
		fsel.selectPackage(data.packageName);
		let lqsel = this.getComponent("listQueryFunctionSelect");
		lqsel.selectPackage(data.listQueryPackageName);
		let sqsel = this.getComponent("editFormQueryFunctionSelect");
		sqsel.selectPackage(data.editFormQueryPackageName);
		let qlist = this.getComponent("multiRecordQueryList");
		for (let i = 0; i < qlist.getRowCount(); i++) {
			let sel = qlist.getRowField(i, "functionSelect");
			let pkg = qlist.getRowField(i, "packageName").getValue();
			sel.selectPackage(pkg);
		}
		this.onChangeType(data.queryType);
	}

	/**
	 * キーフィールドリスト取得します。
	 *
	 */
	getKeyFieldList() {
		let type = this.find("[name='queryType']:checked").val();
		if (type == "2") {
			this.submit("getKeyList", (r) => {
				logger.log("getKeyList r=" + JSON.stringify(r));
				let list = this.getComponent("keyFieldList");
				list.setTableData(r.result);
			});
		} else {
			let list = this.getComponent("keyFieldList");
			list.setTableData([]);
		}
	}

	/**
	 * 計算処理。
	 * @param {jQuery} field イベント発生フィールド。
	 */
	onCalc(field) {
		if (field != null) {
			if (field.attr("name") == "editFormQueryClassName") {
				this.getKeyFieldList();
			}
		}
	}
}
