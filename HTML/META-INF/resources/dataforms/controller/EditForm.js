/**
 * @fileOverview  {@link EditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class EditForm
 *
 * データ編集フォーム。
 * <pre>
 * データ編集を行うフォームです。
 * </pre>
 * @extends TableUpdateForm
 *
 * @prop {String} mode "edit"(フォームが編集可能な状態)または"confirm"(フォーム全体が編集不可の状態)の値を取ります。
 * @prop {String} saveMode "new"(新規データの入力中)または"update"(既存データの編集中)の値を取ります。
 * @prop {Boolean} multiRecord 複数レコード編集モードの場合はtrue。このクラスではfalseに設定。
 *
 *
 */
class EditForm extends TableUpdateForm {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.mode = "edit";
		this.saveMode =  "new";
		this.multiRecord = false;
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 以下のボタンが存在した場合、イベント処理を登録します。
	 * #confirmButton ... 「確認」ボタンの処理.
	 * #saveButton ... 「保存」ボタンの処理.
	 * #resetButton ... 「リセット」ボタンの処理.
	 * #deleteButton ... 「削除」ボタンの処理.
	 * #backButton ... 「戻る」ボタンの処理.
	 * </pre>
	 */
	attach() {
		super.attach();
		var form = this;
		form.get('deleteButton').click(function() {
			form.del();
			return false;
		});
		form.toEditMode();
	}
	/**
	 * 更新モードの時にPKをロックします。
	 *
	 */
	lockPkFields() {
		var lk = false;
		if (this.saveMode == "new") {
			lk = false;
		} else {
			lk = true;
		}
		if (this.pkFieldIdList != null) {
			for (var i = 0; i < this.pkFieldIdList.length; i++) {
				var f = this.getComponent(this.pkFieldIdList[i]);
				if (f != null) {
					f.lock(lk);
				}
			}
		}
	}

	/**
	 * 編集モードにします。
	 * <pre>
	 * 各フィールドを編集可能状態にします。
	 * </pre>
	 */
	toEditMode() {
		super.toEditMode();
		this.lockPkFields();
	}


	/**
	 * 新規登録モードにします。
	 * <pre>
	 * 対応するEditFormのgetNewDataを呼び出し、初期データを取得します。
	 * 各フィールドに取得データを設定し、編集モードにします。
	 * </pre>
	 */
	newData() {
		var title = MessagesUtil.getMessage("message.editformtitle.new");
		this.get("editFormTitle").text(title);
		var form = this;
		form.submitWithoutFile("getNewData", function(result) {
			form.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				form.saveMode = "new";
				form.setFormData(result.result);
				form.toEditMode();
				form.parent.pushEditModeStatus();
			} else {
				form.parent.setErrorInfo(form.getValidationResult(result), form);
			}
		});
	}

	/**
	 * 更新登録モードにします。
	 * <pre>
	 * 対応するEditFormのgetDataを呼び出し、編集対象データを取得します。
	 * 各フィールドに取得データを設定し、編集モードにします。
	 * </pre>
	 */
	updateData() {
		var title = MessagesUtil.getMessage("message.editformtitle.update");
		this.get("editFormTitle").text(title);
		var form = this;
		form.submitWithoutFile("getData", function(result) {
			form.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				form.parent.toEditMode();
				form.saveMode = "update";
				form.setFormData(result.result);
				form.toEditMode();
				form.parent.pushEditModeStatus();
			} else {
				form.parent.setErrorInfo(form.getValidationResult(result), form);
			}
		});
	}

	/**
	 * データを参照登録します。
	 * <pre>
	 * 対応するEditFormのgetReferDataを呼び出し、参照対象データを取得します。
	 * 各フィールドに取得データを設定し、編集モードにします。
	 * </pre>
	 *
	 */
	referData() {
		var title = MessagesUtil.getMessage("message.editformtitle.refer");
		this.get("editFormTitle").text(title);
		var form = this;
		form.submitWithoutFile("getReferData", function(result) {
			form.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				form.parent.toEditMode();
				form.saveMode = "new";
				form.setFormData(result.result);
				form.toEditMode();
				form.parent.pushEditModeStatus();
			} else {
				form.parent.setErrorInfo(form.getValidationResult(result), form);
			}
		});
	}

	/**
	 * データを参照します。
	 * <pre>
	 * 対応するEditFormのgetDataを呼び出し、参照対象データを取得します。
	 * 各フィールドに取得データを設定し、参照モードにします。
	 * </pre>
	 */
	viewData() {
		var title = MessagesUtil.getMessage("message.editformtitle.view");
		this.get("editFormTitle").text(title);
		var form = this;
		form.submitWithoutFile("getData", function(result) {
			form.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				form.parent.toEditMode();
				form.saveMode = "update";
				form.setFormData(result.result);
				form.lockFields(true);
				form.get("confirmButton").hide();
				form.get("saveButton").hide();
				form.get("resetButton").hide();
				form.parent.pushConfirmModeStatus();
			} else {
				form.parent.setErrorInfo(form.getValidationResult(result), form);
			}
		});
	}

	/**
	 * 各フィールドにデータを設定します。
	 * <pre>
	 * 新規モードの場合、削除ボタンを隠します。
	 * </pre>
	 * @param {Object} data フォームデータ.
	 *
	 */
	setFormData(data) {
		super.setFormData(data);
		if (this.saveMode == "new") {
			this.get('deleteButton').hide();
		} else {
			this.get('deleteButton').show();
		}
	}

	/**
	 * 保存ボタンのイベント処理を行います。
	 * <pre>
	 * 対応するEditFormのdeleteメソッドを呼び出し、保存処理を行います。
	 * </pre>
	 */
	del() {
		var systemName = MessagesUtil.getMessage("message.systemname");
		var msg = MessagesUtil.getMessage("message.deleteconfirm");
		var form = this;
		currentPage.confirm(systemName, msg, function() {
			form.submit("delete", function(result) {
				form.parent.resetErrorStatus();
				if (result.status == ServerMethod.SUCCESS) {
					if (result.result != null && result.result.length > 0) {
						currentPage.alert(null, result.result, function() {
							form.changeStateForAfterUpdate();
						});
					} else {
						form.changeStateForAfterUpdate();
					}
				}
			});
		});
	}
}





