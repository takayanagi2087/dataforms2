/**
 * @fileOverview {@link QueryForm}クラスを記述したファイルです。
 */


/**
 * @class QueryForm
 *
 * 問い合わせフォームクラス。
 * <pre>
 * 問い合わせの条件を入力するためのフォームです。
 * </pre>
 * @extends Form
 */
class QueryForm extends Form {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 以下のボタンが存在した場合、イベント処理を登録します。
	 * #queryButton ... 「検索」ボタンの処理.
	 * #resetButton ... 「リセット」ボタンの処理.
	 * </pre>
	 */
	attach() {
		super.attach();
		var queryForm = this;
		this.find('#queryButton').click(function() {
			queryForm.query();
			return false;
		});
		this.find('#resetButton').click(function() {
			queryForm.reset();
			return false;
		});
		this.find('#exportButton').click(function() {
			queryForm.exportData();
			return false;
		});
	}

	/**
	 * クエリの実行を行います。
	 */
	query() {
		var queryForm = this;
		if (queryForm.validate()) {
			queryForm.submit("query", function(result) {
				queryForm.parent.resetErrorStatus();
				if (result.status == ServerMethod.SUCCESS) {
					var resultForm = queryForm.parent.componentMap["queryResultForm"];
					if (resultForm != null) {
						queryForm.showQueryResultForm();
					} else {
						queryForm.showEditForm();
					}
				} else {
					queryForm.parent.setErrorInfo(queryForm.getValidationResult(result), queryForm);
				}
			});
		}
	}
	/**
	 * 問い合わせ結果フォームに入力された検索条件を設定し、検索を行い結果を表示します。
	 */
	showQueryResultForm() {
		var resultForm = this.parent.componentMap["queryResultForm"];
		if (resultForm != null) {
			this.parent.find("#queryResultForm").show();
			var condition = this.get().serialize();
			resultForm.condition = condition;
			resultForm.changePage();
		}
	}

	/**
	 * 編集フォームに入力された検索条件を設定し、対象データを編集します。
	 */
	showEditForm() {
		var editForm = this.parent.componentMap["editForm"];
		if (editForm != null) {
			var condition = this.get().serialize();
			editForm.updateData(condition);
		}
	}

	/**
	 * 編集モードに移行します。
	 */
	toEditMode() {
		this.lockFields(false);
		this.find('#queryButton').show();
		this.find('#resetButton').show();
		this.find('#newButton').show();
	}


	/**
	 * 確認モードに移行します。
	 */
	toConfirmMode() {
		this.lockFields(true);
		this.find('#queryButton').hide();
		this.find('#resetButton').hide();
		this.find('#newButton').hide();
	}

	/**
	 * 問合せ結果をエクスポートします。
	 */
	exportData() {
		var queryForm = this;
		if (queryForm.validate()) {
			var sortOrder = this.getSortOrder();
			this.setHiddenField("sortOrder", sortOrder);
			queryForm.submitForDownload("exportData", function(result) {
				queryForm.parent.resetErrorStatus();
				if (result.status == ServerMethod.INVALID) {
					queryForm.parent.setErrorInfo(queryForm.getValidationResult(result), queryForm);
				}
			});
			this.find("#sortOrder").remove();
		}
	}


	/**
	 * QueryResultFormのソート順を取得します。
	 */
	getSortOrder() {
		var ret = null;
		var qrf = currentPage.getComponent("queryResultForm");
		if (qrf != null) {
			var list = qrf.getComponent("queryResult");
			if (list != null) {
				ret = list.sortOrder;
			}
		}
		logger.log("sort order=" + ret);
		return ret;
	}

}

