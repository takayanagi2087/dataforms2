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
		var thisTable = this;
		this.find("[id$='selectTableClass']").click(function() {
			// 確認画面のロック対応
			if (form.mode == "confirm") {
				return false;
			}
			thisTable.checkTableField($(this));
		});
		this.find("[id$='sel']").click(function() {
			// 確認画面のロック対応
			if (form.mode == "confirm") {
				return false;
			}
		});
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
	 * @param {Boolean} ck チェック。
	 */
	checkAll(ck) {
		logger.log("checkAll ck=" + ck);
		if (this.tableData != null) {
			var map = {};
			for (var i = 0; i < this.tableData.length; i++) {
				logger.log("checkAll i=" + i);
				var selid = "selectFieldList[" + i + "].sel";
				var sel = this.find("#" + this.selectorEscape(selid));
				var fid = "selectFieldList[" + i + "].fieldId";
				var f = this.find("#" + this.selectorEscape(fid));

				var afid = "selectFieldList[" + i + "].alias";
				var af = this.find("#" + this.selectorEscape(afid));
				var id = f.val();
				if (af.val().length > 0) {
					id = af.val();
				}
				if (map[id] == null) {
					if (ck) {
						sel.val("1");
					} else {
						sel.val("0");
					}
				}
				if (sel.val() == "1") {
					map[id] = true;
				}
			}
		}
	}

}


