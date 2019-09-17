/**
 * @fileOverview  {@link WebComponent}クラスを記述したファイルです。
 */

/**
 * @class WebComponent
 *
 * ウエブコンポーネントクラス。
 * <pre>
 * HTML中の各要素の情報を保持するオブジェクトの基本クラスです。
 * ID、親の要素へのポインタと子要素のマップを持ちます。
 * </pre>
 *
 * @prop id {String} コンポーネントのID。
 * @prop componentMap {Object} 所有するコンポーネントのマップです。this.componentMap[id]でIDを指定し、対応するコンポーネントを取得することができます。
 * @prop parent {WebComponent} 親となるコンポーネントです。
 *
 */
class WebComponent {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		this.id = null;
		this.classMap = {};
		this.componentMap = {};
		this.parent = null;
	}


	/**
	 * 親フォームを取得します。
	 * @returns {Form} 親フォーム。
	 */
	getParentForm() {
		var f = this;
		while (!(f instanceof Form)) {
			f = f.parent;
		}
		return f;
	}


	/**
	 * 親となるページまたはダイアログを取得します。
	 * @returns {DataForms} 親となるページまたはダイアログ。
	 */
	getParentDataForms() {
		var f = this;
		while (!(f instanceof DataForms)) {
			f = f.parent;
		}
		return f;
	}

	/**
	 * QueryStringを取得します。
	 * @returns {Object} QueryStringを展開したオブジェクト。
	 */
	getQueryString = function() {
		return QueryStringUtil.parse(window.location.search);
	}


	/**
	 * 同期サーバメソッドを取得します。
	 * @param {String} method メソッド名。
	 * @returns {SyncServerMethod} 同期サーバメソッド。
	 * @deprecated
	 */
	getSyncServerMethod(method) {
		return new SyncServerMethod(this.getUniqId() + "." + method);
	};

	/**
	 * 非同期サーバメソッドを取得すします。
	 * @param {String} method メソッド名。
	 * @returns {SyncServerMethod} 同期サーバメソッド。
	 */
	getAsyncServerMethod(method) {
		return new AsyncServerMethod(this.getUniqId() + "." + method);
	}

	/**
	 * jQueryオブジェクトの検索を行います。
	 * <pre>
	 * jQueryセレクタを指定して、セレクタに合致する子を検索します。
	 * </pre>
	 * @param {String} q jQueryのセレクタ。
	 * @returns {jQuery} jQueryオブジェクト。
	 */
	find(q) {
		return this.get().find(q);
	}

	/**
	 * jQueryオブジェクトを取得します。
	 * <pre>
	 * コンポーネントに対応したjQueryオブジェクトを取得します。
	 * </pre>
	 * @returns {jQuery} jQueryオブジェクト。
	 */
	get() {
		var ret = null;
		if (this.parent == null) {
			ret = $('#' + this.selectorEscape(this.id));
		} else {
			var sel = this.getUniqSelector();
			ret = $(sel);
		}
		return ret;
	}

	/**
	 * サーバから送信されたクラス情報から、そのクラスのインスタンスを作成します。
	 * <pre>
	 * scriptタグで読み込まれていない場合は、jspathで指定したスクリプトを読み込み
	 * クラスのインスタンスを作成します。
	 * </pre>
	 * @param {Object} clszz クラス情報。
	 * @returns {WebComponent} 作成されたインスタンス。
	 */
	newInstance(clazz) {
		var classname = clazz.jsClass;
		var jspath = clazz.jsPath;
		var obj = null;
		if (this.classMap[classname] != null) {
			eval("var obj = new " + classname + "(this);");
		} else {
			if (classname in window) {
				eval("var obj = new " + classname + "(this);");
			} else {
				// この処理は転送されていないJavascriptクラスを読み込むための処理です。
				// 基本的にgetHtmlで<script></script>タグに展開されるので、現在は呼ばれないはずです。
				logger.log("jspath=" + jspath);
				var getJs = new SyncServerMethod("getJs");
				var scriptstr = getJs.execute("parts=" + escape(jspath));
				if (scriptstr.result != null) {
					eval(scriptstr.result);
					// 生成したクラスをグローバルにする.
					//eval("window." + classname + "=" + classname + ";");
				}
				eval("var obj = new " + classname + "(this);");
				this.classMap[classname] = {};
			}
		}
		for (var key in clazz) {
			obj[key] = clazz[key];
		}
		obj.parent = this;
		this.componentMap[obj.id] = obj;
		return obj;
	}

	/**
	 * HtmlTable中の要素のIDかどうかをチェックします。
	 * @param {String} id 判定するID。
	 * @returns {Boolean} HtmlTable中の要素の場合true.
	 */
	isHtmlTableElementId(id) {
		var ret = false;
		if (id.match(/^[A-Za-z0-9]+\[[0-9]+\]\.[A-Za-z0-9]+$/)) {
			ret = true;
		}
		return ret;
	}

	/**
	 * テーブルのID部分を取得します。
	 * @param {String} id HtmlTable中の各要素のID。
	 * @returns {String} テーブルのID.
	 */
	getHtmlTableId(id) {
		if (this.isHtmlTableElementId(id)) {
			var sp = id.split(/[\[\]\.]/);
			return sp[0];
		} else {
			return null;
		}
	}

	/**
	 * テーブルのカラムID部分を取得します。
	 * @param {String} id HtmlTable中の各要素のID。
	 * @returns {String} テーブルのカラムID。
	 */
	getHtmlTableColumnId(id) {
		if (this.isHtmlTableElementId(id)) {
			var sp = id.split(/[\[\]\.]/);
			var lidx = sp.length - 1;
			if (lidx >= 0) {
				return sp[lidx];
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 所有オブジェクトのインスタンスを取得します。
	 * @param {String} id 所有オブジェクトのID。
	 * @returns {WebComponent} 所有オブジェクトのインスタンス。
	 */
	getComponent(id) {
		var tblid = this.getHtmlTableId(id);
		if (tblid != null) {
			var colid = this.getHtmlTableColumnId(id);
			var tbl = this.componentMap[tblid];
			for (var i = 0; i <  tbl.fields.length; i++) {
				if (tbl.fields[i].id == colid) {
					var tblfield = $.extend(true, {}, tbl.fields[i]);
					tblfield.id = id;
					return tblfield;
				}
			}
		} else {
			return this.componentMap[id];
		}
	}

	/**
	 * jqueryのセレクタをエスケープします。
	 * @param {String} val エスケープする文字列。
	 * @returns {String} エスケープされた文字列。
	 */
	selectorEscape(val){
	    return val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, '\\$&');
	}

	/**
	 * コンポーネントの親子関係から、インデント文字列を作成します。
	 * <pre>
	 * ログ出力用の機能です。
	 * </pre>
	 * @returns {String} インデント文字列。
	 */
	getIndent() {
		var t = "";
		var p = this;
		while (p != null) {
			t += "\t";
			p = p.parent;
		}
		return t;
	}

	/**
	 * オブジェクトの初期化を行います。
	 * <pre>
	 * </pre>
	 */
	init() {
	}

	/**
	 * エレメントとの対応付けを行います。
	 * <pre>
	 * 各オブジェクトとHTMLの各エレメントへの対応付けを行い、イベント登録等の設定を行います。
	 * </pre>
	 */
	attach() {
		for (var id in this.componentMap) {
			this.componentMap[id].attach();
		}
	}

	/**
	 * Cookieを取得します。
	 * @param {String} name Cookie名称。
	 * @returns {String} Cookie値。
	 */
	getCookie(name) {
	    var result = null;
	    var cookieName = name + '=';
	    var allcookies = document.cookie;
		logger.log("getCookie():allcookies = " + allcookies);
	    var sp = allcookies.split(";");
	    for (var i = 0; i < sp.length; i++) {
	        var c = sp[i].trim();
	    	if (c.indexOf(cookieName) == 0) {
	    		result = c.substring(cookieName.length);
	    		break;
	    	}
	    }
	    return result == null ? result : decodeURIComponent(result);
	}

	/**
	 * Cookieを設定します。
	 * @param {String} name Cookie名称。
	 * @param {String} val Cookie値。
	 */
	setCookie(name, val) {
		var now = new Date();
		var expires = new Date();
		expires.setTime(now.getTime() + 365*24*60*60*1000);
		var x = name + "=" + encodeURIComponent(val) + "; expires=" + expires.toGMTString() + "; path=" + currentPage.contextPath + ";";
		logger.log("setCookie():cookie x = " + x);
		document.cookie = x;
		logger.log("setCookie():document.cookie=" + document.cookie);
	}

	/**
	 * 一意に対応するjQueryを作成します。
	 * <pre>
	 * コンポーネントの階層を辿り、一意になるjQueryセレクタを作成します。
	 * </pre>
	 * @returns {String} jQueryセレクタ。
	 */
	getUniqSelector() {
		var t = this;
		var sel = "";
		while (!(t instanceof DataForms)) {
			sel = "#" + this.selectorEscape(t.id) + " " + sel;
			if (t instanceof Form) {
				if (t.parentDivId != null) {
					sel = "#" + this.selectorEscape(t.parentDivId) + " " + sel;
					return sel;
				}
			}
			t = t.parent;
		}
		sel = "#" + this.selectorEscape(t.id) + " " + sel;
//		var ret = sel.trim();
		var ret = $.trim(sel);
		return ret;
	}

	/**
	 * 一意なIDを取得します。
	 * <pre>
	 * コンポーネントの階層を辿り、各コンポーネントのidを"."で繋げた文字列を返します。
	 * </pre>
	 *  * @returns {String} 一意なID。
	 */
	getUniqId() {
		var t = this;
		var sel = "";
		if (t instanceof HtmlTable) {
			while (!(t instanceof DataForms)) {
				if (sel.length > 0) {
					sel = "." + sel;
				}
				sel = t.id + sel;
				t = t.parent;
			}
		} else {
			while (!(t instanceof DataForms)) {
				if (!(t instanceof HtmlTable)) {
					if (sel.length > 0) {
						sel = "." + sel;
					}
					sel = t.id + sel;
				}
				t = t.parent;
			}
		}
		if (t instanceof Dialog) {
			if (sel.length > 0) {
				sel = "." + sel;
			}
			sel = t.id + sel;
		}
		return sel;
	}

	/**
	 * ブラウザの言語コードを取得します。
	 * @returns {String} ブラウザの言語コード。
	 */
	getLanguage = function() {
		return window.navigator.userLanguage || window.navigator.language || window.navigator.browserLanguage;
	}
}
