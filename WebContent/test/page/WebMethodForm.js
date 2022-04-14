/**
 * @fileOverview {@link WebMethodForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class WebMethodForm
 *
 * @extends Form
 */
class WebMethodForm extends Form {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

	/**
	 * ServerMethiodの異常呼び出し。
	 */
	async callServerMethodError() {
		let method = this.getServerMethod("applicationExceptionMethod");
		let formData = new FormData(this.get().get(0));
		method.execute(formData, (data) => {
			logger.log("methid.contentType=" + method.contentType);
			logger.dir(data);
		});
	}

	/**
	 * WebMethod異常呼び出し。
	 */
	async callWebMethodError() {
		let method = this.getWebMethod("applicationExceptionMethod");
		let formData = new FormData(this.get().get(0));
		let data = await method.execute(formData);
		logger.log("methid.contentType=" + method.contentType);
		logger.dir(data);
	}

	/**
	 * WebMethod正常呼び出し。
	 */
	async callExecuteJsonButton() {
		let method = this.getWebMethod("jsonMethod");
		let formData = new FormData(this.get().get(0));
		logger.dir(formData);
		let data = await method.execute(formData);
		logger.log("methid.contentType=" + method.contentType);
		logger.dir(data);
	}

	/**
	 * downloadFile異常呼び出し。
	 */
	async callExecuteDownload() {
		let method = this.getWebMethod("downloadFile");
		let formData = new FormData(this.get().get(0));
		let data = await method.execute(formData);
		logger.dir(data);
		if (data instanceof Blob) {
			this.downloadBlob(method, data);
		}
	}

	/**
	 * submitでのjson受信
	 */
	async callSubmitJson() {
		let result = await this.submit("jsonMethod");
		if (result != null) {
			logger.log("result=");
			logger.dir(result);
			this.parent.resetErrorStatus();
			if (result.status == WebMethod.SUCCESS) {
				alert("OK");
			} else {
				this.parent.setErrorInfo(this.getValidationResult(result), this);
			}
		}
	}

	/**
	 * submitでのダウンロード。
	 */
	async callSubmitDownload() {
		let result = await this.submit("downloadFile");
		if (result != null) {
			logger.log("result=");
			logger.dir(result);
			this.parent.resetErrorStatus();
			if (result.status == WebMethod.SUCCESS) {
				alert("OK");
			} else {
				this.parent.setErrorInfo(this.getValidationResult(result), this);
			}
		}
	}


	/**
	 * submitでのjson受信
	 */
	async callSubmitWithoutFileJson() {
		let result = await this.submitWithoutFile("jsonMethod");
		if (result != null) {
			logger.log("result=");
			logger.dir(result);
			this.parent.resetErrorStatus();
			if (result.status == WebMethod.SUCCESS) {
				alert("OK");
			} else {
				this.parent.setErrorInfo(this.getValidationResult(result), this);
			}
		}
	}

	/**
	 * submitでのダウンロード。
	 */
	async callSubmitWithoutFileDownload() {
		let result = await this.submitWithoutFile("downloadFile");
		if (result != null) {
			logger.log("result=");
			logger.dir(result);
			this.parent.resetErrorStatus();
			if (result.status == WebMethod.SUCCESS) {
				alert("OK");
			} else {
				this.parent.setErrorInfo(this.getValidationResult(result), this);
			}
		}
	}


	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		// 独自のボタン(id="webMethodButton")を追加しサーバサイドのメソッドを呼び出す場合は以下の様にしてください。
		this.get("serverMethodErrorButton").click(() => {
			this.callServerMethodError();
		});
		this.get("webMethodErrorButton").click(() => {
			this.callWebMethodError();
		});
		this.get("executeJsonButton").click(() => {
			this.callExecuteJsonButton();
		});
		this.get("executeDownloadButton").click(() => {
			this.callExecuteDownload();
		});
		this.get("submitJsonButton").click(() => {
			this.callSubmitJson();
		});
		this.get("submitDownloadButton").click(() => {
			this.callSubmitDownload();
		});


		this.get("submitWithoutFileJsonButton").click(() => {
			this.callSubmitWithoutFileJson();
		});
		this.get("submitWithoutFileDownloadButton").click(() => {
			this.callSubmitWithoutFileDownload();
		});

	}

	// 独自のWebメソッドを呼び出す場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドの呼び出しサンプル。
	 *
	 */
/*
	callWebMethod() {
		if (this.validate()) {
			this.submit("webMethod", (r) => {
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
		super.setFormData(data);
	}
*/

	// フォーム単位のバリデーションを行う場合は以下のコメントを参考に実装してください。
	/**
	 * フォームのバリデーション。
	 * <pre>
	 * フォーム内のフィールド関連チェックを実装します。
	 * </pre>
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

