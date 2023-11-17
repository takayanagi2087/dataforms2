/**
 * @fileOverview {@link FieldListForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class FieldListForm
 *
 * @extends Form
 */
class FieldListForm extends Form {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.listQuery = true;
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		this.get("okButton").click(() => {
			this.onOk();
		});
		this.get("cancelButton").click(() => {
			this.onCancel();
		});

	}

	/**
	 * 指定したクラスからフィールドリストを取得します。
	 * @param {Object} p テーブルまたは問合せクラス。
	 */
	async getFieldList(p) {
		logger.log("class=", p);
		this.listQuery = p.listQuery;
		let m = this.getWebMethod("getFieldList");
		let r = await m.execute("c=" + p.targetClass);
		if (r.status == JsonResponse.SUCCESS) {
			logger.dir(r);
			let fieldList = this.getComponent("fieldList");
			fieldList.setTableData(r.result);
			if (p.listQuery) {
				this.find(".listQuery").show();
				this.find(".editQuery").hide();
			} else {
				this.find(".listQuery").hide();
				this.find(".editQuery").show();
			}
		}
	}

	/**
	 * OKボタンの処理。
	 */
	onOk() {
		let editForm = currentPage.getComponent("editForm");
		let list = this.getComponent("fieldList").getTableData();
		let json = JSON.stringify(list);
		logger.log("json=" + json);
		if (this.listQuery) {
			editForm.getComponent("listQueryConfig").setValue(json);
		} else {
			editForm.getComponent("editQueryConfig").setValue(json);
		}
		this.parent.close();
	}

	/**
	 * Cancelボタンの処理。
	 */
	onCancel() {
		this.parent.close();
	}
}

