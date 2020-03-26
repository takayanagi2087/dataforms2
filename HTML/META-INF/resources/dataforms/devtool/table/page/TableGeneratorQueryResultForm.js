/**
 * @fileOverview {@link TableGeneratorQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class TableGeneratorQueryResultForm
 *
 * @extends QueryResultForm
 */
class TableGeneratorQueryResultForm extends QueryResultForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.get("printButton").click(function() {
			thisForm.print();
		});
		var tbl = this.getComponent("queryResult");
		// ソート結果の行番号を修正。
		tbl.getSortedList = function() {
			var list = HtmlTable.prototype.getSortedList.call(this);
			for (var i = 0; i < list.length; i++) {
				list[i].rowNo = (i + 1);
			}
			return list;
		};
		// ソート時のイベントハンドラ設定。
		tbl.sortTable = function(col) {
			logger.log("sort");
			thisForm.queryResult.queryResult = HtmlTable.prototype.sortTable.call(this, col);
			this.parent.setQueryResultEventHandler();
		};
	}

	/**
	 * 問い合わせ結果を表示します。
	 * @param {Object} result 問い合わせ結果。
	 */
	setFormData(result) {
		super.setFormData(result);
		var thisForm = this;
		var queryResult = result.queryResult;
		if (queryResult != null) {
			for (var i = 0; i < queryResult.length; i++) {
				logger.log("statusVal=" + queryResult[i].statusVal + ",differenceVal=" + queryResult[0].differenceVal);
				if (queryResult[i].differenceVal == "1") {
					this.find("#queryResult tbody tr:eq(" + i + ")").addClass("warnTr");
				}
				if (queryResult[i].statusVal == "0") {
					this.find("#queryResult tbody tr:eq(" + i + ")").addClass("errorTr");
				}
			}
		}
	}

	/**
	 * テーブル定義書を印刷します。
	 */
	print() {
		var thisForm = this;
		thisForm.parent.resetErrorStatus();
		thisForm.submitForDownload("print");
	}

}


