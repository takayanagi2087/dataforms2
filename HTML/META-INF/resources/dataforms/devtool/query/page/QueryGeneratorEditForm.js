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
		var thisForm = this;
		this.get("selectAll").click(function() {
			if (thisForm.mode == "confirm") {
				return false;
			}
			var selectFieldList = thisForm.getComponent("selectFieldList");
			var ck = $(this).prop("checked");
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

	/**
	 * フォームに対してデータを設定します。
	 * @param {Object} data 設定するデータ。
	 */
	setFormData(data) {
		var thisForm = this;
		super.setFormData(data);
		this.setFunctionSelect("functionSelect", data.packageName);
		let joinTableList = this.getComponent("joinTableList");
		for (let i = 0; i < joinTableList.getRowCount(); i++) {
			let pkg = joinTableList.getRowField(i, "packageName").getValue();
			let id = "joinTableList[" + i + "].functionSelect";
			this.setFunctionSelect(id, pkg);
		}
		this.setFunctionSelect("mainTableFunctionSelect", data.mainTablePackageName);
		if (data.joinTableList != null || data.leftJoinTableList != null || data.rightJoinTableList != null) {
			thisForm.submit("getJoinCondition", function(r) {
				currentPage.resetErrorStatus();
				if (r.status == ServerMethod.SUCCESS) {
					logger.log("field list=" + JSON.stringify(r.result));
					thisForm.setJoinCondition(r.result);
				}
			});
		}
	}

	/**
	 * フィールドリストを取得します。
	 */
	getFieldList() {
		var thisForm = this;
		this.submit("getFieldList", function(r) {
			currentPage.resetErrorStatus();
			if (r.status == ServerMethod.SUCCESS) {
				logger.log("field list=" + JSON.stringify(r.result));
				var ftbl = thisForm.getComponent("selectFieldList");
				ftbl.setTableData(r.result);
				thisForm.submit("getJoinCondition", function(r) {
					currentPage.resetErrorStatus();
					if (r.status == ServerMethod.SUCCESS) {
						logger.log("field list=" + JSON.stringify(r.result));
						thisForm.setJoinCondition(r.result);
					}
				});
			}
		});
	}


	/**
	 * テーブルクラス入力時のフィールドリスト取得。
	 * @param {jQuery} f 更新されたフィールド。
	 */
	onCalc(f) {
		super.onCalc(f);
	}

	/**
	 * 指定されたJOINテーブルの結合時要件を表示します。
	 * @param {Table} table HTMLテーブルのインスタンス。
	 * @param {Array} list 結合条件リスト。
	 */
	setJoinConditionToTable(table, list) {
		if (list != null) {
			var cf = table.getColumnField("joinCondition");
			for (var i = 0; i < list.length; i++) {
				var f = table.getRowField(i, cf);
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


