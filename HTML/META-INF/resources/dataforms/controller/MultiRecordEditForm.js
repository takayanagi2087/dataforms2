/**
 * @fileOverview  {@link MultiRecordEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class MultiRecordEditForm
 *
 * データ編集フォーム。
 * <pre>
 * データ編集を行うフォームです。
 * </pre>
 * @extends TableUpdateForm
 *
 * @prop {String} mode "edit"(フォームが編集可能な状態)または"confirm"(フォーム全体が編集不可の状態)の値を取ります。
 * @prop {Boolean} multiRecord 複数レコード編集モードの場合はtrue。このクラスではtrueに設定されます。
 * @prop {Object} keyMap QueryFormで編集対象を限定した場合、その指定内容を保存します。
 *
 */
class MultiRecordEditForm extends TableUpdateForm {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.mode = "edit";
		this.multiRecord = true;
		this.keyMap = {};
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 以下のボタンが存在した場合、イベント処理を登録します。
	 * #confirmButton ... 「確認」ボタンの処理.
	 * #saveButton ... 「保存」ボタンの処理.
	 * #resetButton ... 「リセット」ボタンの処理.
	 * #backButton ... 「戻る」ボタンの処理.
	 * </pre>
	 */
	attach() {
		super.attach();
		var list = this.getComponent("list");
		list.onAddTr = (rowid) => {
			EditableHtmlTable.prototype.onAddTr.call(list, rowid);
			this.setKeyValue(rowid);
		}
		this.toEditMode();
	}

	/**
	 * QueryFormで指定されたキー情報を設定します。
	 * @param {String} rowid 行ID。
	 */
	setKeyValue(rowid) {
		for (var k in this.keyMap) {
			var id = rowid + "." + k;
			this.setFieldValue(id, this.keyMap[k]);
		}
	}

	/**
	 * QueryFormで指定された条件でレコードを抽出し、そのレコードを編集対象にします。
	 */
	updateData(qs) {
		this.keyMap = QueryStringUtil.parse(qs);
		var title = MessagesUtil.getMessage("message.editformtitle.update");
		this.get("editFormTitle").text(title);
		var form = this;
		var data = qs;
		logger.log("qs=" + data);
		var method = new ServerMethod("editForm.getDataByQueryFormCondition");
		method.execute(data, function(result) {
			form.parent.resetErrorStatus();
			if (result.status == ServerMethod.SUCCESS) {
				form.parent.toEditMode();
				form.setFormData(result.result);
				form.toEditMode();
				form.parent.pushEditModeStatus();
			} else {
				form.parent.setErrorInfo(form.getValidationResult(result), form);
			}
		});
	}
}

