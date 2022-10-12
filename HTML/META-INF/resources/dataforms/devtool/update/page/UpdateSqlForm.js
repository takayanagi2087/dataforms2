/**
 * @fileOverview {@link UpdateSqlForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class UpdateSqlForm
 *
 * @extends Form
 */
class UpdateSqlForm extends Form {
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
		this.get("generateSqlButton").click(() => {
			this.generateSql();
			return false;
		});
	}

	/**
	 * SQL作成用バリデーション。
	 * @return {Boolean}
	 */
	validateForGenerateSql() {
		let pname = this.get("packageName").val();
		let clsname = this.get("tableClassName").val();
		if (StringUtil.isBlank(pname) || StringUtil.isBlank(clsname)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * SQLの作成処理。
	 */
	async generateSql() {
		if (this.validateForGenerateSql()) {
			let r = await this.submit("generateSql");
			this.parent.resetErrorStatus();
			if (r.status == JsonResponse.SUCCESS) {
				logger.dir(r);
				this.get("sql").val(r.result);
			} else {
				this.parent.setErrorInfo(this.getValidationResult(r), this);
			}
		} else {
			let msg = MessagesUtil.getMessage("error.requiredtableclass");
			await currentPage.alert(null, msg);
		}
	}


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

