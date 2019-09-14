/**
 * @fileOverview  {@link MessagesUtil}クラスを記述したファイルです。
 */
/**
 * @class MessagesUtil
 * メッセージユーティリティ。
 * <pre>
 * 各種メッセージを取得します。
 * ClientMessages.properties, &lt;Page&gt;.properties中のメッセージは初期化時にサーバから取得します。
 * 以下の仕様は同期通信を使用するため削除。
 * <span style="text-decoration: line-through;">Messages.propertiesのメッセージは、要求された時にサーバから取得します。</span>
 * </pre>
 */
class MessagesUtil {
	/**
	 * メッセージマップのgetterです。
	 * @returns メッセージマップ。
	 */
	static get messageMap() {
		return window._df_messageMap;
	}

	/**
	 * メッセージマップのsetterです。
	 * @param {Object} map メッセージマップ。
	 */
	static set messageMap(map) {
		window._df_messageMap = map;
	}

	/**
	 * ClientMessages.properties, &lt;Page&gt;.propertiesに指定されたメッセージを読み込みます。
	 * @param {Object} map メッセージマップ。
	 */
	static init(map) {
		MessagesUtil.messageMap = map;
	}

	/**
	 * メッセージを取得します。
	 * <pre>
	 * 以下の仕様は同期通信を使用するため削除。
	 * <span style="text-decoration: line-through;">
	 * initメソッドで読み込んでいないメッセージは、ajaxで取得します。
	 * </span>
	 * </pre>
	 * @param {String} key メッセージキー.
	 * @param {String} [arg] メッセージ引数(複数指定可).
	 * @returns {String} メッセージ.
	 */
	static getMessage() {
		var msg = MessagesUtil.messageMap[arguments[0]];
/*		if (msg == null) {
			if (currentPage.clientMessageTransfer == "SEND_AT_ANY_TIME") {
				// 存在しないメッセージはajaxで取得しますが、
				// 同期通信なので、非推奨です。
				var method = new SyncServerMethod("getServerMessage");
				var r = method.execute("key=" + arguments[0]);
				this.messageMap[arguments[0]] = r.result;
				msg = r.result;
			}
		}*/
		if (msg != null) {
			for (var i = 1; i < arguments.length; i++) {
				var rex = RegExp('\\{' + (i - 1) +  '\\}');
				if (msg.match(rex)) {
					var mid = RegExp.lastMatch;
					msg = msg.replace(mid, arguments[i]);
				}
			}
		}
		return msg;
	}
}
