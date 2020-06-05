/**
 * @fileOverview {@link QueryGeneratorQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryGeneratorQueryResultForm
 *
 * @extends QueryResultForm
 */
class QueryGeneratorQueryResultForm extends QueryResultForm {
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


	// 問合せ結果リスト中に独自のボタンを追加した場合、以下のコメントを参考にしてイベント処理を実装してください。
	/**
	 * 問合せ結果にデフォルトイベント処理を設定します。
	 */
	setQueryResultEventHandler() {
		super.setQueryResultEventHandler();
		var queryResult = this.getComponent("queryResult");
		for (var i = 0; i < this.formData.queryResult.length; i++) {
			var subQuery = this.formData.queryResult[i].subQuery;
			if (subQuery != null && subQuery.length > 0) {
				this.get("queryResult[" + i + "].generateSubQueryButton").hide();
			} else {
				this.get("queryResult[" + i + "].generateSubQueryButton").show();
			}
		}
		var thisForm = this;
		// リスト中のボタンに対してイベント処理を追加。
		this.find("[id$='\.generateSubQueryButton']").click(function() {
			var queryClassName = queryResult.getSameRowField($(this), "fullClassName").text();
			logger.log("queryClassName=" + queryClassName);
			var m = thisForm.getServerMethod("generateSubQuery");
			m.execute("queryClass=" + queryClassName, function(r) {
				if (r.status == ServerMethod.SUCCESS) {
					currentPage.alert(null, r.result);
				}
				thisForm.changePage();
			});
		});
	}

	// フォームの各種動作をカスタマイズするには以下のメソッドをオーバーライドしてください。

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

	/**
	 * 問い合わせ結果を表示します。
	 * @param {Object} queryResult 問い合わせ結果。
	 */
/*
	setQueryResult(queryResult) {
		super.setQueryResult(queryResult);
	}
*/

	/**
	 * 先頭ページに遷移します。
	 */
/*
	topPage() {
		super.topPage();
	}
*/
	/**
	 * 末尾ページに遷移します。
	 */
/*
	bottomPage() {
		super.bottomPage();
	}
*/

	/**
	 * 前ページに遷移します。
	 */
/*
	prevPage() {
		super.prevPage();
	}
*/
	/**
	 * 次ページに遷移します。
	 */
/*
	nextPage() {
		super.nextPage();
	}
*/

	/**
	 * ページの更新を行います。
	 */
/*
	changePage() {
		super.changePage();
	}
*/

	/**
	 * 選択データを更新します。
	 */
/*
	updateData() {
		super.updateData();
	}
*/

	/**
	 * 選択データをコピーした新規データを登録します。
	 */
/*
	referData() {
		super.referData();
	}
*/
	/**
	 * 選択データの削除を行います。
	 */
/*
	deleteData() {
		super.deleteData();
	}
*/

	/**
	 * ページ関連情報を設定します。
	 * @param {Object} queryResult 問い合わせ結果。
	 */
/*
	setPagerInfo(queryResult) {
		super.setPagerInfo(queryResult);
	}
*/

	/**
	 * ページ関連情報を制御します。
	 */
/*
	controlPager() {
		super.controlPager();
	}
*/
}

