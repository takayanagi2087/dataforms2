/**
 * @fileOverview  {@link DataForms}クラスを記述したファイルです。
 */


/**
 * @class DataForms
 * フォームコンテナクラス。
 * <pre>
 * 複数のフォームをまとめ、制御するクラスです。
 * DataFormsのサブクラスはPageとDialogです。
 * Pageは複数のFormと複数のDialogを持つことができます。
 * Dialogはjquery-uiのdialogで実装し、複数のFormオブジェクトを持つことができます。
 * QueryForm,QueryResultForm,EditFormを部品として持つ場合、
 * それぞれのFormを適切に制御します。
 * </pre>
 * @extends WebComponent
 */
DataForms = createSubclass("DataForms",{}, "WebComponent");

/**
 * フォーム情報の初期化を行います。
 * @param {Object} formMap フォームマップ.
 */
DataForms.prototype.initForm = function(formMap) {
	for (var key in formMap) {
		var f = formMap[key];
		var form = this.newInstance(f);
		form.init();
	}
};

/**
 * 初期化処理を行います。
 *
 */
DataForms.prototype.init = function() {
	WebComponent.prototype.init.call(this);
};


/**
 * HTMLエレメントとの対応付けを行います。
 * <pre>
 * QueryForm,QueryResultForm,EditFormの存在をチェックし、適切適切な状態を設定します。
 * </pre>
 */
DataForms.prototype.attach = function() {
	var editMode = true;
	var qf = this.find("#queryForm");
	if (qf.length > 0) {
		qf.show();
		editMode = false;
	}
	var rf = this.find("#queryResultForm");
	if (rf.length > 0) {
		rf.hide();
		editMode = false;
	}
	var ef = this.find("#editForm");
	if (editMode) {
		this.replaceState("editMode", "editMode", location.href);
		ef.show();
	} else {
		this.replaceState("queryMode", "queryMode", location.href);
		ef.hide();
	}
	WebComponent.prototype.attach.call(this);
	// エラーメッセージ領域が無い場合自動的に追加する.
	if (this.find("#errorMessages").length == 0) {
		var f = this.find("form:first");
		f.before('<div class="errorMessages" id="errorMessages"><!--エラーメッセージ領域--></div>');
	}
	if (ef.length == 0) {
		// 編集フォームがない場合
		this.find("#newButton").hide();
	}
};

/**
 * エラー状態をリセットする.
 */
DataForms.prototype.resetErrorStatus = function() {
	var area = this.find('#errorMessages');
	area.html("");
	var ef = this.find('.errorField');
	ef.each(function() {
		$(this).removeClass('errorField');
	});

};



/**
 * エラー情報を表示します。
 * @param {Array} errors エラー情報の配列。
 * <pre>
 * 配列の各要素は以下のような形式のオブジェクトです。
 * {
 * 		message: エラーメッセージ.
 * 		fieldId: エラーの発生したフィールドID.
 * }
 * </pre>
 * @param {Form} form エラーの発生したフォーム。
 */
DataForms.prototype.setErrorInfo = function(errors, form) {
	var area = this.find('#errorMessages');
	this.resetErrorStatus();
	for (var i = 0; i < errors.length; i++) {
		area.append(errors[i].message + "<br/>");
		form.find('#' + this.selectorEscape(errors[i].fieldId)).addClass("errorField");
	}
};


/**
 * ページのモードをクエリモードにします。
 * <pre>
 * EditFormを隠し、QueryForm,QueryResultFormを表示します。
 * </pre>
 * @reutrns {Boolean} QueryFormが存在しない場合falseを返します。
 */
DataForms.prototype.toQueryMode = function() {
	var qf = this.find("#queryForm");
	var qrf = this.find("#queryResultForm");
	if (qf.length > 0 || qrf.length > 0) {
		var queryForm = this.getComponent("queryForm");
		if (queryForm != null) {
			qf.show();
			queryForm.toEditMode();
		}
		var queryResultForm = this.getComponent("queryResultForm");
		if (queryResultForm != null) {
			var rf = this.find("#queryResultForm");
			if (queryResultForm.queryResult != null) {
				rf.show();
			} /*else {
				rf.hide();
			}*/
		}
		var editForm = this.getComponent("editForm");
		if (editForm != null) {
			var ef = this.find("#editForm");
			ef.hide();
		}
		return true;
	} else {
		return false;
	}
};

/**
 * ページのモードを編集モードにします。
 * <pre>
 * EditFormを表示し、QueryForm,QueryResultFormを隠します。
 * </pre>
 * @reutrns {Boolean} EditFormが存在しない場合falseを返します。
 */
DataForms.prototype.toEditMode = function() {
	var queryForm = this.getComponent("queryForm");
	if (queryForm != null) {
		//constructor.name
		var editForm = this.getComponent("editForm");
		if (editForm != null && editForm.multiRecord == true) {
			var qf = this.find("#queryForm");
			qf.show();
			queryForm.toConfirmMode();
		} else {
			var qf = this.find("#queryForm");
			qf.hide();
		}
	}
	var rf = this.find("#queryResultForm");
	rf.hide();
	var ef = this.find("#editForm");
	ef.show();
	return (ef.length > 0);
};


/**
 * history.pushStateを呼び出すためのメソッドです。
 * <pre>
 * このメソッドはDataFormsクラスでは何もしませんが、Pageクラスではhistory.pushStateを呼び出します。
 * </pre>
 * @param {Object} state 状態。
 * @param {String} title タイトル。
 * @param {String} url タイトル。
 * 
 */
DataForms.prototype.pushState = function(state, title, url) {
};

/**
 * history.replaceStateを呼び出すためのメソッドです。
 * <pre>
 * このメソッドはDataFormsクラスでは何もしませんが、Pageクラスではhistory.replaceStateを呼び出します。
 * </pre>
 * @param {Object} state 状態。
 * @param {String} title タイトル。
 * @param {String} url タイトル。
 * 
 */
DataForms.prototype.replaceState = function(state, title, url) {
};

/**
 * 編集モードへの画面遷移履歴を登録します。
 */
DataForms.prototype.pushEditModeStatus = function() {
	this.pushState("editMode", "editMode", location.href);
};

/**
 * 確認モードへの画面遷移履歴を登録します。
 */
DataForms.prototype.pushConfirmModeStatus = function() {
	this.pushState("confirmMode", "confirmMode", location.href);
};

/**
 * ブラウザの戻るボタンでフォームの制御を行うかどうかを返します。
 * @returns {Boolean} DataFormsクラスでは常にfalseを返します。
 */
DataForms.prototype.isBrowserBackEnabled = function() {
	return false;
};
