/**
 * @fileOverview {@link MaterialOrderPage}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class MaterialOrderPage
 *
 * @extends BasePage
 */
class MaterialOrderPage extends BasePage {
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


	// 問合せモードに移行時に独自の処理を行う場合は、以下のメソッドを有効にしてください。
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

	// 編集モードに移行時に独自の処理を行う場合は、以下のメソッドを有効にしてください。
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

	// ウインドウリサイズ時に独自の処理を行う場合は、以下のonResizeメソッドを有効にして、処理を記述してください。
	/**
	 * ウインドウリサイズ時の処理。
	 */
/*
	onResize() {
		super.onResize();
		// TODO:ウインドウのリサイズ時の処理を記述してください。
	}
*/

	// 独自のWebメソッドを呼び出す場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドの呼び出しサンプル。
	 *
	 */
/*
	callWebMethod() {
		// ページの初期化.
		var method = new ServerMethod("webMethod");
		method.execute("p1=v1&p2=v2", (r) => {
			// TODO:応答情報を適切に処理してください。
			logger.dir(r);
		});
	}
*/
}

