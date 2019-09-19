/**
 * @fileOverview {@link AlertDialog}クラスを記述したファイルです。
 */

/**
 * @class AlertDialog
 *
 * @extends Dialog
 */
// AlertDialog = createSubclass("AlertDialog", {title:null, message:null, okFunc: null}, "Dialog");
class AlertDialog extends Dialog {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.title = null;
		this.message = null;
		this.okFunc = null;
	}
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisDialog = this;
		this.find("#alertOkButton").click(function() {
			thisDialog.close();
			if (thisDialog.okFunc != null) {
				thisDialog.okFunc.call(this);
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
		this.find("#alertMessage").html(this.message);
		super.show(modal, p);
	}
}


