/**
 * @fileOverview {@link SelectFieldHtmlTable}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class SelectFieldHtmlTable
 *
 * @extends HtmlTable
 */
class SelectFieldHtmlTable extends EditableHtmlTable {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * テーブルにデータを設定します。
	 * @param {Array} list テーブルデータ。
	 *
	 * <pre>
	 * テーブルデータを設定し、レコードフィールドのチェックボックスにイベント処理を登録します。
	 * </pre>
	 */
	setTableData(list) {
		super.setTableData(list);
		var form = this.getParentForm();
//		this.setRowSpan("tableClassName");
		var thisTable = this;
		this.find("[id$='selectTableClass']").click(function() {
			// 確認画面のロック対応
			if (form.mode == "confirm") {
				return false;
			}
			thisTable.checkTableField($(this));
			thisTable.disableDuplicate();
		});
		this.find("[id$='sel']").click(function() {
			// 確認画面のロック対応
			if (form.mode == "confirm") {
				return false;
			}
			thisTable.disableDuplicate();
		});
		thisTable.disableDuplicate();
	}

	/**
	 * テーブル選択チェックボックスのイベント処理を行います。
	 * @param {jQuery} jq クリックされたチェックボックス。
	 */
	checkTableField(jq) {
		logger.log("checkTableField");
		if (this.tableData != null) {
			for (var i = 0; i < this.tableData.length; i++) {
				var tc = jq.val();
				var d = this.tableData[i];
				if (tc == d.selectTableClass) {
					var ckid = "selectFieldList[" + i + "].sel";
					var fck = this.find("#" + this.selectorEscape(ckid));
					if (!fck.prop("disabled")) {
						fck.prop("checked", jq.prop("checked"));
					}
				}
			}
		}
	}

	/**
	 * フィールド選択チェックボックスのイベント処理を行います。
	 */
	disableDuplicate() {
		if (this.tableData != null) {
			var map = {};
			for (var i = 0; i < this.tableData.length; i++) {
				var ckid = "selectFieldList[" + i + "].sel";
				var ck = this.find("#" + this.selectorEscape(ckid));
				var fid = "selectFieldList[" + i + "].fieldId";
				var f = this.find("#" + this.selectorEscape(fid));
				logger.log("fieldId=" + f.val());
				if (map[f.val()] == true) {
					ck.prop("checked", false);
					ck.prop("disabled", true);
				} else {
					ck.prop("disabled", false);
				}
				if (ck.prop("checked") == true) {
					map[f.val()] = true;
				}
			}
		}
	}

}


