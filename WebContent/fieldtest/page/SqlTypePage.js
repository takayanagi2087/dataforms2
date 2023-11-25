/**
 * @fileOverview {@link SqlTypePage}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class SqlTypePage
 *
 * @extends BasePage
 */
class SqlTypePage extends BasePage {
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

	// 独自のWebメソッドを呼び出す場合は、以下のコメントを参考にしてください。
	/**
	 * Webメソッドの呼び出しサンプル。
	 *
	 */
/*
	async callWebMethod() {
		try {
			let method = this.getWebMethod("webMethod");
			let r = await method.execute("p1=v1&p2=v2");
			// TODO:応答情報を適切に処理してください。
			logger.dir(r);
		} catch (e) {
			currentPage.reportError(e);
		}
	}
*/


	// フォームの各種動作をカスタマイズするには以下のメソッドをオーバーライドしてください。

	/**
	 * ページのモードをクエリモードにします。
	 * <pre>
	 * EditFormを隠し、QueryForm,QueryResultFormを表示します。
	 * </pre>
	 * @reutrns {Boolean} QueryFormが存在しない場合falseを返します。
	 */
/*
	toQueryMode() {
		// TODO:必要に応じて前処理を実装します。
		let ret = super.toQueryMode();
		// TODO:必要に応じて後処理を実装します。
		return ret;
	}
*/

	/**
	 * ページのモードを編集モードにします。
	 * <pre>
	 * EditFormを表示し、QueryForm,QueryResultFormを隠します。
	 * </pre>
	 * @reutrns {Boolean} EditFormが存在しない場合falseを返します。
	 */
/*
	toEditMode() {
		// TODO:必要に応じて前処理を実装します。
		let ret = super.toEditMode();
		// TODO:必要に応じて後処理を実装します。
		return ret;
	}
*/

	/**
	 * ウインドウリサイズ時の処理。
	 */
/*
	onResize() {
		super.onResize();
		// TODO:ウインドウのリサイズ時の処理を記述してください。
	}
*/
}

