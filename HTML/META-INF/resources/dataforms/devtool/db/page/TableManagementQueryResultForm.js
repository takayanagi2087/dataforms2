/**
 *
 * @fileOverview {@link TableManagementQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class TableManagementQueryResultForm
 * テーブル問い合わせ結果フォーム。
 * <pre>
 * テーブル管理の問い合わせ結果を表示するフォームです。
 * </pre>
 * @extends QueryResultForm
 */
class TableManagementQueryResultForm extends QueryResultForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var systemName = MessagesUtil.getMessage("message.systemname");
		var thisForm = this;
		this.get("selectAllButton").click(function() {
			$("[name='checkedClass']").each(function() {
				$(this).prop("checked", true);
			});
			thisForm.controlButton();
		});
		this.get("selectNothingButton").click(function() {
			for (var i = 0;;i++) {
				var id = "queryResult[" + i + "].statusVal";
				var st = thisForm.get(id);
				if (st.length == 0) {
					break;
				}
				if (st.val() == "0") {
					var cbid = "queryResult[" + i + "].checkedClass";
					thisForm.get(cbid).prop("checked", true);
				}
			}
			thisForm.controlButton();
		});
		this.get("selectDiffButton").click(function() {
			for (var i = 0;;i++) {
				var id = "queryResult[" + i + "].differenceVal";
				var st = thisForm.get(id);
				if (st.length == 0) {
					break;
				}
				if (st.val() == "1") {
					var cbid = "queryResult[" + i + "].checkedClass";
					thisForm.get(cbid).prop("checked", true);
				}
			}
			thisForm.controlButton();
		});
		this.get("unselectAllButton").click(function() {
			$("[name='checkedClass']").each(function() {
				$(this).prop("checked", false);
			});
			thisForm.controlButton();
		});
		this.get("initTableButton").click(function() {
			currentPage.confirm(systemName, MessagesUtil.getMessage("message.initTableConfirm"), function() {
				thisForm.submit("initTable", function(result) {
					thisForm.updateTableInfoList(result);
				});
			})
		});

		this.get("updateTableButton").click(function() {
			currentPage.confirm(systemName, MessagesUtil.getMessage("message.updateTableConfirm"), function() {
				thisForm.submit("updateTable", function(result) {
					thisForm.updateTableInfoList(result);
				});
			});
		});

		this.get("dropTableButton").click(function() {
			currentPage.confirm(systemName, MessagesUtil.getMessage("message.dropTableConfirm"), function() {
				thisForm.submit("dropTable", function(result) {
					thisForm.updateTableInfoList(result);
				});
			});
		});

		this.get("exportAsInitialDataButton").click(function() {
			currentPage.confirm(systemName, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"), function() {
				thisForm.submit("exportTableAsInitialData", function(result) {
					if (result.status == ServerMethod.SUCCESS) {
						var path = result.result;
						currentPage.alert(systemName, MessagesUtil.getMessage("message.exportInitialDataResult", path));
					}
				});
			});
		});

		this.get("exportTableButton").click(function() {
			currentPage.confirm(systemName, MessagesUtil.getMessage("message.dexportTableConfirm"), function() {
				thisForm.submit("exportTable", function(result) {
					if (result.status == ServerMethod.SUCCESS) {
						var path = result.result;
						currentPage.alert(systemName, MessagesUtil.getMessage("message.exportInitialDataResult", path));
					}
				});
			});
		});
		this.get("importTableButton").click(function() {
			var dlg = thisForm.parent.getComponent("importDataDialog");
			dlg.showModal();
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
			var list = HtmlTable.prototype.sortTable.call(this, col);
			this.parent.setTableEventHandler(list);
		};

		this.controlButton();
	}

	/**
	 * ボタンのenable/disable制御を行います。
	 */
	controlButton() {
		var tr = this.find("#queryResult>tbody>tr");
		if (tr.length > 0) {
			this.get("selectAllButton").prop("disabled", false);
			this.get("selectNothingButton").prop("disabled", false);
			this.get("selectDiffButton").prop("disabled", false);
			this.get("unselectAllButton").prop("disabled", false);
		} else {
			this.get("selectAllButton").prop("disabled", true);
			this.get("selectNothingButton").prop("disabled", true);
			this.get("selectDiffButton").prop("disabled", true);
			this.get("unselectAllButton").prop("disabled", true);
		}
		var ckcb = this.find("[name='checkedClass']:checked");
		if (ckcb.length > 0) {
			this.get("updateTableButton").prop("disabled", false);
			this.get("initTableButton").prop("disabled", false);
			this.get("dropTableButton").prop("disabled", false);
			this.get("exportAsInitialDataButton").prop("disabled", false);
			this.get("exportTableButton").prop("disabled", false);
			this.get("importTableButton").prop("disabled", false);
		} else {
			this.get("updateTableButton").prop("disabled", true);
			this.get("initTableButton").prop("disabled", true);
			this.get("dropTableButton").prop("disabled", true);
			this.get("exportAsInitialDataButton").prop("disabled", true);
			this.get("exportTableButton").prop("disabled", true);
			this.get("importTableButton").prop("disabled", true);
		}
	}

	/**
	 * 結果テーブルへイベントハンドラを設定します。
	 * @param queryResult {Array} 検索結果。
	 */
	setTableEventHandler(queryResult) {
		var thisForm = this;
		if (queryResult != null) {
			for (var i = 0; i < queryResult.length; i++) {
				var id = "queryResult[" + i + "].className";
				this.get(id).click(function() {
					var clsname = $(this).html();
					var qs="className=" + clsname;
					var method = thisForm.getServerMethod("getTableInfo");
					method.execute(qs, function(sqllist) {
						if (sqllist.status == ServerMethod.SUCCESS) {
							thisForm.showTableInfo(sqllist.result);
						}
					});
				});
//				var sid = "queryResult[" + i + "].statusVal";
			}
			this.find("[name='checkedClass']").each(function() {
				$(this).click(function() {
					thisForm.controlButton();
				});
			});
			for (var i = 0; i < queryResult.length; i++) {
				logger.log("statusVal=" + queryResult[i].statusVal + ",differenceVal=" + queryResult[0].differenceVal);
				if (queryResult[i].differenceVal == "1") {
					this.find("#queryResult tbody tr:eq(" + i + ")").addClass("warnTr");
				} else {
					this.find("#queryResult tbody tr:eq(" + i + ")").removeClass("warnTr");
				}
				if (queryResult[i].statusVal == "0") {
					this.find("#queryResult tbody tr:eq(" + i + ")").addClass("errorTr");
				} else {
					this.find("#queryResult tbody tr:eq(" + i + ")").removeClass("errorTr");

				}
			}
			this.find("[id$='\.tableName']").click(function () {
				thisForm.showQueryForm($(this));
			});
		}
		this.controlButton();
	}


	showQueryForm(lnk) {
		var table = lnk.text();
		logger.log("tableName=" + table);
		var url = currentPage.contextPath + "/dataforms/devtool/query/page/QueryExecutorPage.df?t=" + table;
		window.open(url, "_blank")
	}

	/**
	 * 問い合わせの結果を設定します。
	 * <pre>
	 * 問い合わせ結果をHTMLテーブルに設定されたソート順に従ってソートしてから設定します。
	 * </pre>
	 * @param result 問い合わせ結果。
	 */
	setQueryResult(result) {
		var tbl = this.getComponent("queryResult");
		result.queryResult = tbl.sort(result.queryResult);
		super.setQueryResult(result);
	}

	/**
	 * 問い合わせ結果を表示します。
	 * @param {Object} result 問い合わせ結果。
	 */
	setFormData(result) {
		super.setFormData(result);
		var queryResult = result.queryResult;
		this.setTableEventHandler(queryResult);
	}

	/**
	 * テーブル情報ダイアログを表示します。
	 * @param {Object} result テーブル情報。
	 *
	 */
	showTableInfo(result) {
		var dlg = this.parent.getComponent("tableInfoDialog");
		dlg.getComponent("tableInfoForm").setFormData(result);
		dlg.showModal();
	}

	/**
	 * 検索結果リストのテーブル情報を更新します。
	 * @param {Object} result テーブル情報。
	 */
	updateTableInfo(result) {
		for (var i = 0;;i++) {
			var id = "queryResult[" + i + "].className";
			var clsname = this.get(id);
			if (clsname.length > 0) {
				if (clsname.html() == result.className) {
					result.rowNo = (i + 1);
					var rt = this.getComponent("queryResult");
					rt.updateRowData(i, result);

					if (result.differenceVal == "1") {
						this.find("#queryResult tbody tr:eq(" + i + ")").addClass("warnTr");
					} else {
						this.find("#queryResult tbody tr:eq(" + i + ")").removeClass("warnTr");
					}
					if (result.statusVal == "0") {
						this.find("#queryResult tbody tr:eq(" + i + ")").addClass("errorTr");
					} else {
						this.find("#queryResult tbody tr:eq(" + i + ")").removeClass("errorTr");
					}

				}
			} else {
				break;
			}
		}
	}

	/**
	 * 検索結果リストのテーブル情報を更新します。
	 * @param {Array} result テーブル情報の配列。
	 */
	updateTableInfoList(result) {
		if (result.status == ServerMethod.SUCCESS) {
			var tlist = result.result;
			for (var i = 0; i < tlist.length; i++) {
				this.updateTableInfo(tlist[i]);
			}
		}
	}

	/**
	 * Importを実行します。
	 * @param {String} path インポートデータのパス。
	 */
	importTableData(path) {
		var thisForm = this;
		$(this.convertSelector("#datapath")).val(path);
		thisForm.submit("importTable", function(result) {
			if (result.status == ServerMethod.SUCCESS) {
				thisForm.updateTableInfoList(result);
			}
		});
	}

}


