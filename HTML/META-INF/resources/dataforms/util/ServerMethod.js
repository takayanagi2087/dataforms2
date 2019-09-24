/**
 * @fileOverview  {@link ServerMethod}クラスを記述したファイルです。
 */


/**
 * @class ServerMethod
 *
 * サーバメソッド基本クラス。
 * <pre>
 * </pre>
 * @param {String} m メソッド名。
 */
class ServerMethod {
	/**
	 * コンストラクタ。
	 * @param {String} m メソッド名。
	 *
	 */
	constructor(m) {
		if (m != null) {
		    this.serverUrl = location.pathname;
		    this.method = m;
		    this.errorMessagesArea = "errorMessages";
		}
	}

	/**
	 * Ajax呼び出しの成功を示します。
	 * @constant ServerMethod.SUCCESS
	 */
	static get SUCCESS() {
		return 0;
	}


	/**
	 * Ajax呼び出しのバリデーションエラーを示します。
	 * @constant ServerMethod.INVALID
	 */
	static get INVALID() {
		return 1;
	}


	/**
	 * アプリケーション例外を示します。
	 * @constant ServerMethod.APPLICATION_EXCEPTION
	 */
	static get APPLICATION_EXCEPTION() {
		return 2;
	}

	/**
	 * アプリケーション例外発生時の処理です。
	 * <pre>
	 * dataは以下の形式のオブジェクトです。
	 * {
	 *   status:サーバの処理結果(ServerMethod.APPLICATION_EXCEPTION).
	 *   result:例外に対応したメッセージ。
	 * }
	 * </pre>
	 * @param {Object} data 返却されたオブジェクト.
	 * @param {Object} type 返却されたオブジェクトタイプ.
	 */
	onCatchApplicationException(data, type) {
		if (data.result.key == "error.auth") {
			window.location.href = currentPage.contextPath + currentPage.errorPage + "?msg=" + data.result.message;
		} else {
			alert(data.result.message);
		}
	}

	/**
	* システム例外発生時の処理を行います。
	* <pre>
	* jQuery.ajaxのerror オプションに指定するメソッドです。
	* </pre>
	* @param {Object} XMLHttpRequest XMLHttpRequestオブジェクト。
	* @param {String} textStatus エラー内容。
	* @param {Object} errorThrown 補足的な例外オブジェクト。
	*/
	onAjaxError(XMLHttpRequest, textStatus, errorThrown) {
		alert(MessagesUtil.getMessage("error.ajax"));
	}

	/**
	* サーバー上のメソッドを呼び出します。
	* @param {String} method メソッド名。
	* @param {String} param パラメータ。
	* @param {Function} success 成功時の応答処理。
	* @param {Boolean} as 非同期フラグ。
	*/
	callMethod(method, param, success, as) {
		if (window.currentPage != null) {
			window.currentPage.lock();
		}
	    if (param == null) {
	        param = "";
	    }
	    param = "dfMethod=" + method + "&" + param;
	    if (currentPage.csrfToken != null) {
	    	param = "csrfToken=" + currentPage.csrfToken + "&" + param;
	    }
	    var me = this;
	    var errorfunc = this.onAjaxError;
	    $.ajax({ url: this.serverUrl
		  , data: param
		  , async: as
		  , dataType: "json"
		  , type: "POST"
		  , contentType: "application/x-www-form-urlencoded"
		  , success: function (data, type) {
//			  logger.info("type=" + type);
			  if (window.currentPage != null) {
				  window.currentPage.unlock();
			  }
			  // TODO:SUCCESS, INVALID以外の値を返した場合の処理に対応できたほうが良い。
			  if (data.status == ServerMethod.SUCCESS || data.status == ServerMethod.INVALID) {
				  success.call(me, data, type);
			  } else if (data.status == ServerMethod.APPLICATION_EXCEPTION) {
				  me.onCatchApplicationException(data, type);
			  }
		  }
		  , error: function (XMLHttpRequest, textStatus, errorThrown) {
			  logger.error(textStatus);
			  if (window.currentPage != null) {
				  window.currentPage.unlock();
			  }
		      errorfunc.call(me, XMLHttpRequest, textStatus, errorThrown);
		  }
	    });
	}

	/**
	 * 非同期メソッドを呼び出します。
	 * <pre>
	 * 指定されたメソッドを非同期で呼び出し、コールバックメソッドで結果を処理ます。
	 * </pre>
	 *
	 * @param {String} param パラメータ(QueryString形式)。
	 * @param {Function} success 成功時の応答処理 function(data)。
	 * 応答処理メソッドには以下の形式のObjectを渡します。
	 * <pre>
	 * {
	 *   status:サーバの処理結果(ServerMethod.SUCCESS or ServerMethod.INVALID).
	 *   result:サーバが応答したオブジェクト。
	 * }
	 * </pre>
	 *
	 *
	 */
	execute(param, success) {
	    this.callMethod(this.method, param, success, true);
	}

	/**
	 * UPLOADフォームかどうかを判定します。
	 * @param {jQuery} form フォーム。
	 * @returns ファイルアップロードフィールドが存在するフォームの場合trueを返します。
	 *
	 */
	isUploadForm(form) {
		var fileFields = form.find(':file');
		return fileFields.length > 0;
	}

	/**
	 * 指定したフォームにHIDDEN項目を追加します。
	 * @param {jQuery} form フォーム。
	 * @param {String} field フィールドID。
	 * @param {String} val 値。
	 */
	setHiddenField(form, field, val) {
		var hid = form.find('#' + field);
		if (hid.length == 0) {
			form.append("<input type='hidden' id='" + field + "' name='" + field + "' value='" + val + "'>");
			hid = form.find('#' + field);
		}
		hid.val(val);
	}


	/**
	 * ファイルアップロードフィールドが存在する場合のPOSTリクエストを実行します。
	 * <pre>
	 * ファイルが正常に送信できるようにフォームの属性を自動的に設定し、POSTを実行します。
	 * サーバーからの応答は見えないインラインフレーム展開し、処理します。
	 * </pre>
	 * @param {jQuery} form フォーム。
	 * @param {String} method ポスト先のMethod。
	 * @param {Function} success 成功時の応答処理 function(data)。
	 * 応答処理メソッドには以下の形式のObjectを渡します。
	 * <pre>
	 * {
	 *   status:サーバの処理結果(ServerMethod.SUCCESS or ServerMethod.INVALID).
	 *   result:サーバが応答したオブジェクト。
	 * }
	 * </pre>
	 */
	uploadForm(form, method, success) {
		var me = this;
		// 受け取り用iframeが存在したら削除する.
		var uiframe = $('#uploadIFrame').remove();
		// 結果を受け取るIFRAMEを非表示で作成する.
		$('body').append("<iframe style='display:none' id='uploadIFrame' name='uploadIFrame'></iframe>");
		uiframe = $('#uploadIFrame');
		// iframeがロードされた場合のイベント処理を登録する.
		uiframe.on('load', function() {
			var contents = $(uiframe).contents().get(0);
			var data = $(contents).find('body').text();
			try {
				data = window.eval('(' + data + ')');
				// TODO:SUCCESS, INVALID以外の値を返した場合の処理に対応できたほうが良い。
				if (data.status == ServerMethod.SUCCESS || data.status == ServerMethod.INVALID) {
					success.call(me, data);
				} else if (data.status == ServerMethod.APPLICATION_EXCEPTION) {
					me.onCatchApplicationException(data);
					if (window.currentPage !== undefined) {
						window.currentPage.unlock();
					}
				}
			} catch (e) {
				logger.error(e.message);
				if (window.currentPage !== undefined) {
					window.currentPage.unlock();
				}
				me.onAjaxError();
			}
			uiframe.html("");
		});
		form.attr("enctype", "multipart/form-data"); // enctypeをファイルアップロード可能なmultipart/form-dataに設定
		form.attr("method", "POST");
		form.attr("action", this.servletUrl);
		form.attr("target", "uploadIFrame"); // POSTの結果の受け取り先を見えないiframeに設定する.
		this.setHiddenField(form, "dfMethod", method);
		if (currentPage.csrfToken != null) {
			this.setHiddenField(form, "csrfToken", currentPage.csrfToken);
		}
		if (window.location.search != null && window.location.search.length > 1) {
			this.setHiddenField(form, "dfQueryString", window.location.search.substring(1));
		}
		form.submit();
		form.find("#dfMethod").remove();
		if (currentPage.csrfToken != null) {
			form.find("#csrfToken").remove();
		}
		if (window.location.search != null && window.location.search.length > 1) {
			this.find("#dfQueryString").remove();
		}
	}

	/**
	 * 指定したformを指定したメソッドに対してPostします。
	 * <pre>
	 * 指定されたformの内容をサーバーメソッドに対してpostします。
	 * サーバーメソッドの応答内容は、通常json形式です。
	 * 通常はフォームの内容をform.serialize()メソッドで$.ajax()メソッドに渡します。
	 * フォーム中に&lt;input type=&quot;file&quot; ...&gt;が存在する場合、
	 * formのenctype,action,methodを適切に設定し、targetを非表示の&lt;iframe&gt;にして
	 * submitを行うことにより、ファイル送信を実現しています。
	 * </pre>
	 *
	 * @param {jQuery} form FROM。
	 * @param {Function} func 応答処理 function(data)。
	 * 応答処理メソッドには以下の形式のObjectを渡します。
	 * <pre>
	 * {
	 *   status:サーバの処理結果(ServerMethod.SUCCESS or ServerMethod.INVALID).
	 *   result:サーバが応答したオブジェクト。
	 * }
	 * </pre>
	 */
	submit(form, func) {
		if (!this.isUploadForm(form)) {
			this.submitWithoutFile(form, func);
		} else {
			this.submitWithFile(form, func);
		}
	}

	/**
	 * 指定したformを指定したメソッドに対してPostします、ファイルは送信されません。
	 * <pre>
	 * 指定されたformの内容をサーバーメソッドに対してpostします。
	 * サーバーメソッドの応答内容は、通常json形式です。
	 * フォームの内容をform.serialize()メソッドで$.ajax()メソッドに渡します。
	 * </pre>
	 * @param {jQuery} form FROM。
	 * @param {Function} func 応答処理 function(data)。
	 * 応答処理メソッドには以下の形式のObjectを渡します。
	 * <pre>
	 * {
	 *   status:サーバの処理結果(ServerMethod.SUCCESS or ServerMethod.INVALID).
	 *   result:サーバが応答したオブジェクト。
	 * }
	 * </pre>
	 *
	 */
	submitWithoutFile(form, func) {
		form.find("#dfMethod").remove();
		var data = form.serialize();
		form.find(':file').each(function() {
			if (data.length > 0) {
				data += "&";
			}
			data += $(this).attr("name") + "=" + encodeURIComponent($(this).val());
		});
		logger.log("data=" + data);
		this.callMethod(this.method, data, func, true);
	}

	/**
	 * 指定したformを指定したメソッドに対してPostします、ファイルは送信されません。
	 * <pre>
	 * 指定されたformの内容をサーバーメソッドに対してpostします。
	 * サーバーメソッドの応答内容は、通常json形式です。
	 * formのenctype,action,methodを適切に設定し、targetを非表示の&lt;iframe&gt;にして
	 * submitを行うことにより、ファイル送信を実現しています。
	 * </pre>
	 * @param {jQuery} form FROM。
	 * @param {Function} func 応答処理 function(data)。
	 * 応答処理メソッドには以下の形式のObjectを渡します。
	 * <pre>
	 * {
	 *   status:サーバの処理結果(ServerMethod.SUCCESS or ServerMethod.INVALID).
	 *   result:サーバが応答したオブジェクト。
	 * }
	 * </pre>
	 */
	submitWithFile(form, func) {
		if (window.currentPage !== undefined) {
			window.currentPage.lock();
		}
		var rfunc = function(data) {
			if (window.currentPage !== undefined) {
				window.currentPage.unlock();
			}
			func(data);
		};
		this.uploadForm(form, this.method, rfunc);
	}
}





