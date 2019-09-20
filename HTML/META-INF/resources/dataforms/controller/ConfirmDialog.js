/**
 * @fileOverview {@link ConfirmDialog}クラスを記述したファイルです。
 */

/**
 * @class ConfirmDialog
 *
 * @extends Dialog
 */
class ConfirmDialog extends Dialog {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.title = null;
		this.message = null;
		this.okFunc =  null;
		this.cancelFunc = null;
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisDialog = this;
		this.find("#confirmOkButton").click(function() {
			thisDialog.close();
			if (thisDialog.okFunc != null) {
				thisDialog.okFunc.call(this);
			}
			return false;
		});
		this.find("#confirmCancelButton").click(function() {
			thisDialog.close();
			if (thisDialog.cancelFunc != null) {
				thisDialog.cancelFunc.call(this);
			}
			return false;
		});
	}

	/**
	 * ダイアログを表示します。
	 * @param {Boolean} modal モーダル表示の場合true。
	 * @param {Object} p 追加プロパティ。
	 *
	 */
	show(modal, p) {
		this.find("#confirmMessage").html(this.message);
		super.show(modal, p);
	}
}


