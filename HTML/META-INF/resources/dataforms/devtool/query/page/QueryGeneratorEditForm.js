/**
 * @fileOverview {@link QueryGeneratorEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryGeneratorEditForm
 *
 * @extends EditForm
 */
class QueryGeneratorEditForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		this.get("selectAll").click((ev) => {
			logger.log("selectAll");
			if (this.mode == "confirm") {
				return false;
			}
			let selectFieldList = this.getComponent("selectFieldList");
			let ck = $(ev.currentTarget).prop("checked");
			selectFieldList.checkAll(ck);
		});
	}

	/**
	 * パッケージ名から機能を設定します。
	 * @param {String} sel 機能選択肢のid。
	 * @param {String} pkg パッケージ名。
	 */
	setFunctionSelect(sel, pkg) {
		let fsel = this.getComponent(sel);
		let v = fsel.getValue();
		if (v == null || v.length == 0) {
			fsel.selectPackage(pkg);
		}
	}

	async getJoinCondition() {
		let r = await this.submit("getJoinCondition");
		currentPage.resetErrorStatus();
		if (r.status == JsonResponse.SUCCESS) {
			logger.log("field list=" + JSON.stringify(r.result));
			this.setJoinCondition(r.result);
		}
		return r;
	}

	/**
	 * フォームに対してデータを設定します。
	 * @param {Object} data 設定するデータ。
	 */
	async setFormData(data) {
		try {
			super.setFormData(data);
			this.setFunctionSelect("functionSelect", data.packageName);
			let joinTableList = this.getComponent("joinTableList");
			for (let i = 0; i < joinTableList.getRowCount(); i++) {
				let pkg = joinTableList.getRowField(i, "packageName").getValue();
				let id = "joinTableList[" + i + "].functionSelect";
				this.setFunctionSelect(id, pkg);
			}
			this.setFunctionSelect("mainTableFunctionSelect", data.mainTablePackageName);
			if (data.joinTableList != null) {
				logger.log("data=", data);
				await this.getJoinCondition();
			}
		} catch (e) {
			currentPage.reportError(e);
		}
	}

	/**
	 * フィールドリストを取得します。
	 */
	async getFieldList() {
		try {
			await this.getJoinCondition();
			let r = await this.submit("getFieldList");
			currentPage.resetErrorStatus();
			if (r.status == JsonResponse.SUCCESS) {
				this.get("selectAll").prop("checked", false);
				logger.log("field list=" + JSON.stringify(r.result));
				let ftbl = this.getComponent("selectFieldList");
				ftbl.setTableData(r.result);
				let cr = await this.submit("getJoinCondition");
				currentPage.resetErrorStatus();
				if (cr.status == JsonResponse.SUCCESS) {
					logger.log("field list=" + JSON.stringify(cr.result));
					this.setJoinCondition(cr.result);
				}
			}
		} catch (e) {
			currentPage.reportError(e);
		}
	}


	/**
	 * テーブルクラス入力時のフィールドリスト取得。
	 * @param {jQuery} f 更新されたフィールド。
	 */
	onCalc(f) {
		super.onCalc(f);
		if (f != null) {
			logger.log("onCalc=" + f.attr("id"));
			this.getFieldList();
		}
	}

	/**
	 * 指定されたJOINテーブルの結合時要件を表示します。
	 * @param {Table} table HTMLテーブルのインスタンス。
	 * @param {Array} list 結合条件リスト。
	 */
	setJoinConditionToTable(table, list) {
		if (list != null) {
			let cf = table.getColumnField("joinCondition");
			for (let i = 0; i < list.length; i++) {
				let f = table.getRowField(i, cf);
				f.setValue(list[i].joinCondition);
			}
		}
	}

	/**
	 * 結合条件を表示します。
	 * @param {Object} data データ。
	 */
	setJoinCondition(data) {
		this.setJoinConditionToTable(this.getComponent("joinTableList"), data.joinTableList);
		this.setJoinConditionToTable(this.getComponent("leftJoinTableList"), data.leftJoinTableList);
		this.setJoinConditionToTable(this.getComponent("rightJoinTableList"), data.rightJoinTableList);
	}
}


