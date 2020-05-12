/**
 * @fileOverview {@link SupplierMasterQueryForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class SupplierMasterQueryForm
 *
 * @extends QueryForm
 */
class SupplierMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		// 独自のボタン(id="webMethodButton")を追加しサーバサイドのメソッドを呼び出す場合は以下の様にしてください。
/*
		this.get("webMethodButton").click(() => {
			this.callWebMethod();
			return false;
		});
*/


	}

	// 独自のWebメソッドを呼び出す場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドの呼び出しサンプル。
	 *
	 */
/*
	callWebMethod() {
		if (this.validate()) {
			this.submit("webMethod", (r) {
				this.parent.resetErrorStatus();
				if (r.status == ServerMethod.SUCCESS) {
					// TODO:成功時の処理を記述します。
					// 応答情報をログ表示
					logger.dir(r);
				} else {
					this.parent.setErrorInfo(this.getValidationResult(r), this);
				}
			});
		}
	}
*/


	// フォームのデータ設定時に処理を行う場合、以下の処理を参考にしてください。
	/**
	 * 各フィールドにデータを設定します。
	 * <pre>
	 * 新規モードの場合、削除ボタンを隠します。
	 * </pre>
	 * @param {Object} data フォームデータ.
	 *
	 */
/*
	setFormData(data) {
		// TODO:データ設定の前処理を記述します。
		super.setFormData(data);
		// TODO:データ設定の後処理を記述します。
	}
*/

	// フォーム単位のバリデーションを行う場合は以下のメソッドを参考にしてください。
	/**
	 * フォームのバリデーション。
	 */
/*
	validateForm() {
		let list = super.validateForm();
		if (list.length == 0) {
			if (エラー判定) {
				list.push(new ValidationError("fieldId", MessagesUtil.getMessage("error.messagekey")));
			}
		}
		return list;
	}
*/

	// フォームの計算処理を行う場合、以下の処理を参考にしてください。
	/**
	 * 計算イベント処理を行います。
	 * <pre>
	 * 計算イベントフィールドが更新された場合、このメソッドが呼び出されます。
	 * データ入力時の自動計算が必要な場合このメソッドをオーバーライドしてください。
	 * </pre>
	 * @param {jQuery} element イベントが発生した要素。初期表示の時等特定フィールドが要因でない場合はnullが設定されます。
	 *
	 */
/*
	onCalc(element) {
	}
*/

}

