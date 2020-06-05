/**
 * @fileOverview {@link TableOrSubQueryClassNameField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class TableOrSubQueryClassNameField
 *
 * @extends SimpleClassNameField
 */
class TableOrSubQueryClassNameField extends SimpleClassNameField {
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
	}

	/**
	 * テーブルクラス名の更新時の処理。
	 */
	onUpdateRelationField() {
		super.onUpdateRelationField();
		if (this.get().val().length != 0) {
			var form = this.getParentForm();
			if (typeof form.getFieldList == "function") {
				form.getFieldList();
			}
		}
	}

	// 独自のWebメソッドを呼び出す場合は、以下のコメントを参考にしてください。
	/**
	 * Webメソッドの呼び出しサンプル。
	 *
	 */
/*
	callWebMethod() {
		// ページの初期化.
		var method = this.getServerMethod("webMethod");
		method.execute(this.id + "=" + this.getValue(), (r) => {
			// TODO:応答情報を適切に処理してください。
			logger.dir(r);
		});
	}
*/

	// フィールドの各種動作をカスタマイズするには以下のメソッドをオーバーライドしてください。
	/**
	 * autocompleteの選択時の処理を記述します。
	 */
/*
	onAutocompleteSelected() {
		super.onAutocompleteSelected();
	}
*/

	/**
	 * 関連データの更新後に呼び出されるメソッドです。
	 */
/*
	onUpdateRelationField() {
		super.onUpdateRelationField();
		// このフィールドが配置されたフォームのメソッドを呼び出す場合は以下の様にします。
		var form = this.getParentForm();
		if (typeof(form.hogeFunc) == "function") {
			form.hogeFunc();
		}
	}
*/

}

