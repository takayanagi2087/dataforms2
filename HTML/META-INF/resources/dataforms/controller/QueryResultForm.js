/**
 * @fileOverview {@link QueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryResultForm
 *
 * 問い合わせ結果フォームクラス。
 *  * <pre>
 * 問い合わせ結果を表示するフォームです。
 * </pre>
 * @extends Form
 */
class QueryResultForm extends Form {
	/**
	 * HTMLエレメントへの対応付けを行います。
	 * <pre>
	 * 以下のコンポーネントが存在した場合、イベント処理を登録します。
	 * #linesPerPage ... 1ページの行数指定。
	 * #pageNo ... ページ番号指定。
	 * #topPageButton ... 先頭ページボタン。
	 * #bottomPageButton ... 末尾ページボタン。
	 * #prevPageButton ... 前ページボタン。
	 * #nextPageButton ... 次ページボタン。
	 * </pre>
	 */
	attach() {
		super.attach();
		this.queryResult = null;
		var thisForm = this;
		this.get("linesPerPage").change(function() {
			thisForm.get("pageNo").val(0);
			thisForm.changePage();
		});
		this.get("pageNo").change(function() {
			thisForm.changePage();
		});

		this.get("topPageButton").click(function() {
			thisForm.topPage();
			return false;
		});

		this.get("bottomPageButton").click(function() {
			thisForm.bottomPage();
			return false;
		});

		this.get("prevPageButton").click(function() {
			thisForm.prevPage();
			return false;
		});


		this.get("nextPageButton").click(function() {
			thisForm.nextPage();
			return false;
		});
		this.controlPager();
	}


	/**
	 * 先頭ページに遷移します。
	 */
	topPage() {
		this.get("pageNo").val(0);
		this.changePage();
	}


	/**
	 * 末尾ページに遷移します。
	 */
	bottomPage() {
		var thisForm = this;
		var v = thisForm.find("#pageNo>option:last").val();
		thisForm.get("pageNo").val(v);
		thisForm.changePage();
	}

	/**
	 * 前ページに遷移します。
	 */
	prevPage() {
		var thisForm = this;
		var v = parseInt(thisForm.get("pageNo").val(), 10);
		var idx = v - 1;
		if (idx < 0){
			idx = 0;
		}
		thisForm.get("pageNo").val(idx);
		thisForm.changePage();
	}

	/**
	 * 次ページに遷移します。
	 */
	nextPage() {
		var thisForm = this;
		var max = parseInt(thisForm.find("#pageNo>option:last").val(), 10);
		var v = parseInt(thisForm.get("pageNo").val(), 10);
		var idx = v + 1;
		if (idx > max){
			idx = max;
		}
		thisForm.get("pageNo").val(idx);
		thisForm.changePage();
	}

	/**
	 * ページの更新を行います。
	 */
	changePage() {
		var queryResultForm = this;
		var lpp = this.get("linesPerPage");
		var lines = "";
		if (lpp.prop("disabled")) {
			this.get("linesPerPage").find("option").each(function() {
				if ($(this).attr("selected") == "selected") {
					lines = "&linesPerPage=" + $(this).val();
				}
			});
		}
		var rt = this.getComponent("queryResult");
		logger.log("sortOrder=" + rt.sortOrder);
		var param = this.condition + lines +  "&" + this.get().serialize() + "&sortOrder=" + rt.sortOrder;
		logger.log("param=" + param);
		var method = this.getServerMethod("changePage");
		method.execute(param, function(result) {
			queryResultForm.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				queryResultForm.setQueryResult(result.result);
			} else {
				queryResultForm.parent.setErrorInfo(queryResultForm.getValidationResult(result), queryResultForm);
			}
		});
	}

	/**
	 * 選択データを更新します。
	 */
	updateData() {
		var queryResultForm = this;
		var editForm = this.parent.getComponent("editForm");
		if (editForm != null) {
			editForm.updateData();
		} else {
			if (queryResultForm.parent instanceof Dialog) {
				queryResultForm.parent.close();
			}
		}
	}

	/**
	 * 選択データをコピーした新規データを登録します。
	 */
	referData() {
		var queryResultForm = this;
		var editForm = this.parent.getComponent("editForm");
		if (editForm != null) {
			editForm.referData();
		}
	}

	/**
	 * 選択データの表示します。
	 */
	viewData() {
		var queryResultForm = this;
		var editForm = this.parent.getComponent("editForm");
		if (editForm != null) {
			editForm.viewData();
		}
	}

	/**
	 * 選択データの削除を行います。
	 */
	async deleteData() {
		let systemName = MessagesUtil.getMessage("message.systemname");
		let msg = MessagesUtil.getMessage("message.deleteconfirm");
		if (await currentPage.confirm(systemName, msg)) {
			logger.log("selectedQueryString=" + this.selectedQueryString);
			let method = this.getWebMethod("delete");
			let result = await method.execute(this.selectedQueryString);
			this.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				this.changePage();
			} else {
				this.parent.setErrorInfo(this.getValidationResult(result), this);
			}
		}
	}

	/**
	 * ページ関連情報を設定します。
	 * @param {Object} queryResult 問い合わせ結果。
	 */
	setPagerInfo(queryResult) {
		var hitCount = queryResult.hitCount;
		var linesPerPage = queryResult.linesPerPage;
		var pageSelector = this.find("select[id='pageNo']");
		if (pageSelector.length) {
			var max = Math.floor(hitCount / linesPerPage);
			if (hitCount % linesPerPage != 0) {
				max ++;
			}
			pageSelector.empty();
			for (var i = 0; i < max; i++) {
				pageSelector.append('<option value="' + i + '">' + (i + 1) + '</option>');
			}
		}
	}

	/**
	 * ページ関連情報を制御します。
	 */
	controlPager() {
		if (this.find("#queryResult>tbody>tr").length == 0) {
			this.get("linesPerPage").prop("disabled", true);
			this.get("topPageButton").prop("disabled", true);
			this.get("prevPageButton").prop("disabled", true);
			this.get("pageNo").prop("disabled", true);
			this.get("nextPageButton").prop("disabled", true);
			this.get("bottomPageButton").prop("disabled", true);
		} else {
			this.get("linesPerPage").prop("disabled", false);
			this.get("topPageButton").prop("disabled", false);
			this.get("prevPageButton").prop("disabled", false);
			this.get("pageNo").prop("disabled", false);
			this.get("nextPageButton").prop("disabled", false);
			this.get("bottomPageButton").prop("disabled", false);
			var minPage = 0;
			var maxPage = parseInt(this.find("#pageNo>option:last").val(), 10);
			var pageNo = parseInt(this.get("pageNo").val(), 10);
			if (pageNo == minPage) {
				this.get("topPageButton").prop("disabled", true);
				this.get("prevPageButton").prop("disabled", true);
			}
			if (pageNo == maxPage) {
				this.get("nextPageButton").prop("disabled", true);
				this.get("bottomPageButton").prop("disabled", true);
			}
		}
	}

	/**
	 * 各種操作をするためのキーを設定します。
	 * <pre>
	 * 更新等のイベント処理時に更新対象のキー情報を適切に設定する処理です。
	 * </pre>
	 * @param {jQuery} comp イベントの発生したコンポーネント。
	 * @return {Boolean} 基本的にtrueを返す。
	 */
	setSelectedKey(comp) {
		// クリックされたボタンと同一行にあるキー項目の値を取得する.
		this.selectedQueryString = "";
		var tbl = this.getComponent("queryResult");
		var ridx = tbl.getRowIndex(comp);
		for (var i = 0; i < this.pkFieldList.length; i++) {
			var id = this.pkFieldList[i];
			var v = this.queryResult.queryResult[ridx][id];
			// 処理対象を指定するキーフィールドに値を設定する.
			if (this.selectedQueryString.length > 0) {
				this.selectedQueryString += "&"
			}
			this.selectedQueryString += (id + "=" + v);
			var editForm = this.parent.getComponent("editForm");
			if (editForm != null) {
				editForm.setFieldValue(id, v);
			}
		}
		return true;
	}

	/**
	 * 選択データを設定します。
	 * @param {jQuery} comp イベントの発生したコンポーネント。
	 */
	setSelectedData(comp) {
		var table = this.getComponent("queryResult");
		var idx = table.getRowIndex(comp);
		var seldata = this.queryResult.queryResult[idx];
		var dlg = this.getParentDataForms();
		dlg.data = seldata;
	}

	/**
	 * 問合せ結果にデフォルトイベント処理を設定します。
	 */
	setQueryResultEventHandler() {
		var thisForm = this;
		this.find("[id$='\.viewButton']").click(function() {
			if (thisForm.setSelectedKey($(this))) {
				thisForm.viewData();
			}
		});
		//
		this.find("[id$='\.updateButton']").click(function() {
			if (thisForm.setSelectedKey($(this))) {
				// データ検索ダイアログように、選択されたデータを設定する。
				thisForm.setSelectedData($(this));
				thisForm.updateData();
			}
		});
		this.find("[id$='\.referButton']").click(function() {
			if (thisForm.setSelectedKey($(this))) {
				thisForm.referData();
			}
		});
		this.find("[id$='\.deleteButton']").click(function() {
			if (thisForm.setSelectedKey($(this))) {
				thisForm.deleteData();
			}
		});

		var editForm = this.parent.getComponent("editForm");
		if (editForm == null) {
			this.find(".deleteColumn").hide();
		}
	}

	/**
	 * 問い合わせ結果を表示します。
	 * <pre>
	 * 各結果行に以下のボタンが存在した場合、それぞれのイベント処理を登録します。
	 * [id$='\.viewButton'] ... 表示ボタン。
	 * [id$='\.updateButton'] ... 更新ボタン。
	 * [id$='\.referButton'] ... 参照登録ボタン。
	 * [id$='\.deleteButton'] ... 削除ボタン。
	 *
	 * </pre>
	 * @param {Object} queryResult 問い合わせ結果。
	 */
	setQueryResult(queryResult) {
		this.queryResult = queryResult;
		this.setPagerInfo(queryResult);
		this.setFormData(queryResult);
		// 各リンクのイベント処理を登録.
		var thisForm = this;
		this.controlPager();
		// テーブルのイベント処理を追加する。
		this.setQueryResultEventHandler();
	}

}


