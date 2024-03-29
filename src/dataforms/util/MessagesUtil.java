package dataforms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Page;
import dataforms.controller.WebComponent;
import dataforms.controller.WebEntryPoint;
import dataforms.exception.ApplicationError;
import dataforms.field.common.SelectField;


/**
 * メッセージユーティリティクラス。
 *
 */
public final class MessagesUtil {
    /**
     * Log.
     */
	private static Logger logger = LogManager.getLogger(MessagesUtil.class.getName());

	/**
	 * クライアント用メッセージプロパティ。
	 */
	private static String clientMessagesName = null;

	/**
	 * クライアント用メッセージプロパティ(アプリケーション独自)。
	 */
	private static String appClientMessagesName = null;

	/**
	 * メッセージプロパティ。
	 */
	private static String messagesName = null;

	/**
	 * メッセージプロパティ(アプリケーション独自)。
	 */
	private static String appMessagesName = null;

	/**
	 * クライアントメッセージ送信モード。
	 *
	 */
	public static enum ClientMessageTransfer {
		/**
		 * ページに関する全メッセージリソースをクライアントに送信します。
		 */
		ALL,
		/**
		 * client-message,app-client-messageとXXXPage.propertiesのみ送信します。
	 	 * message, app-messageはJavascriptで使用不可となります。
		 */
		CLIENT_ONLY,
		/**
	 	 * 旧バージョンとの互換性を維持するためのモードです。
		 * 初期化時にclient-message,app-client-messageとXXXPage.propertiesのみ送信します。
	 	 * message, app-messageは必要な時に送信されますが、その通信は同期通信を使用するため
	 	 * 将来的に使用できなくなる可能性があります。
		 */
		@Deprecated
		SEND_AT_ANY_TIME
	}


	/**
	 * クライアントメッセージモード。
	 */
	private static ClientMessageTransfer clientMessageTransfer = null;


	/**
	 * クライアントメッセージモードを取得します。
	 * @return クライアントメッセージモード。
	 */
	public static ClientMessageTransfer getClientMessageTransfer() {
		return clientMessageTransfer;
	}


	/**
	 * クライアントメッセージモードを設定します。
	 * @param clientMessageTransfer クライアントメッセージモード。
	 */
	public static void setClientMessageTransfer(final ClientMessageTransfer clientMessageTransfer) {
		MessagesUtil.clientMessageTransfer = clientMessageTransfer;
	}


	/**
     * コンストラクタ。
     */
    private MessagesUtil() {

    }


    /**
     * クライアント用のメッセージリソースを設定します。
     * @param clientMessagesName クライアント用のメッセージリソース。
     */
    public static void setClientMessagesName(final String clientMessagesName) {
		MessagesUtil.clientMessagesName = clientMessagesName;
	}



    /**
     * サーバ用のメッセージリソースを設定します。
     * @param messagesName サーバ用のメッセージリソース。
     */
	public static void setMessagesName(final String messagesName) {
		MessagesUtil.messagesName = messagesName;
	}



    /**
     * クライアント用のメッセージリソース(アプリケーション独自)を設定します。
     * @param appClientMessagesName クライアント用のメッセージリソース(アプリケーション独自)。
     */
	public static void setAppClientMessagesName(final String appClientMessagesName) {
		MessagesUtil.appClientMessagesName = appClientMessagesName;
	}


    /**
     * サーバ用のメッセージリソース(アプリケーション独自)を設定します。
     * @param appMessagesName サーバ用のメッセージリソース(アプリケーション独自)。
     */
	public static void setAppMessagesName(final String appMessagesName) {
		MessagesUtil.appMessagesName = appMessagesName;
	}


	/**
	 * 順序付プロパティファイルを取得します。
	 * <pre>
	 * 指定されたパスのプロパティファイルを順序情報を含めて読み込みます。
	 * 基本的にFunction.properties用のメソッドです。
	 * </pre>
	 * @param epoint ページ情報。言語の判定などに使用します。
	 * @param path パス。
	 * @return プロパティ。
	 *
	 */
	public static SequentialProperties getProperties(final WebEntryPoint epoint, final String path) {
		WebComponent comp = (WebComponent) epoint;
		SequentialProperties prop = new SequentialProperties();
		try {
			String proppath = comp.getAppropriatePath(path + ".properties", epoint.getRequest());
			String proptext = comp.getWebResource(proppath);
			prop.loadText(proptext);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ApplicationError(e);
		}
		return prop;
	}

	/**
	 * 指定されたプロパティファイルからメッセージを取得ます。
	 *
	 * @param epoint ページ。
	 * @param messageKey メッセージキー。
	 * @param prop プロパティファイルのパス。
	 * @return メッセージ。
	 */
	public static String getMessageFromPropfile(final WebEntryPoint epoint, final String messageKey, final String prop) {
		String msg = null;
		Properties appClientMessages = getProperties(epoint, prop);
		if (appClientMessages.containsKey(messageKey)) {
			msg = appClientMessages.getProperty(messageKey);
		}
		return msg;
	}

    /**
     * メッセージを取得します。
     * <pre>
     * 指定されたキーのメッセージを読み込み、引数を展開します。
     * サーブレットの初期化パラメータ、"client-messages"に指定されたファイルからメッセージを取得します。
     * 上記に無い場合、"messages"に指定されたファイルからメッセージを取得します。
     * </pre>
	 * @param epoint ページ情報。言語の判定などに使用します。
     * @param messageKey メッセージキー。
     * @param args メッセージの引数。
     * @return メッセージ。
     */
    public static String getMessage(final WebEntryPoint epoint, final String messageKey, final String... args) {
        String msg = getMessage(epoint, messageKey);
        int idx = 0;
        for (String arg : args) {
            msg = msg.replaceAll("\\{" + idx + "\\}", arg);
            idx++;
        }
        return msg;
    }


    /**
     * メッセージマップを取得します。
     * <pre>
     * 指定されたパスのプロパティファイルのメッセージマップを読み込みます。
     * </pre>
	 * @param epoint ページ情報。言語の判定などに使用します。
     * @param prop プロパティファイルパス。
     * @return メッセージマップ。
     */
    public static Map<String, String> getMessageMap(final WebEntryPoint epoint, final String prop) {
        Properties clientMessages = getProperties(epoint, prop);
        Map<String, String> map = new HashMap<String, String>();
        for (Object key : clientMessages.keySet()) {
        	map.put((String) key, clientMessages.getProperty((String) key));
        }
        return map;
    }

	/**
	 * メッセージを取得します。
	 *
	 * <pre>
	 * {@code
	 * 指定されたキーのメッセージを読み込みます。
	 * サーブレットの初期化パラメータ、"client-messages"に指定されたファイルからメッセージを取得します。
	 * 上記に無い場合、"messages"に指定されたファイルからメッセージを取得します。
	 * 上記に無い場合、"<page-path>.properties"に指定されたファイルからメッセージを取得します。
	 * }
	 * </pre>
	 *
	 * @param epoint
	 *            ページ情報。言語の判定などに使用します。
	 * @param messageKey
	 *            メッセージキー。
	 * @return メッセージ。
	 */
	public static String getMessage(final WebEntryPoint epoint, final String messageKey) {

		String clsname = epoint.getClass().getName();
		String pageprop = "/" + clsname.replaceAll("\\.", "/");
		Map<String, String> pageMap = MessagesUtil.getMessageMap(epoint, pageprop);
		String msg =  pageMap.get(messageKey);
		if (msg != null) {
			return msg;
		}
		msg = getMessageFromPropfile(epoint, messageKey, appClientMessagesName);
		if (msg != null) {
			return msg;
		}
		msg = getMessageFromPropfile(epoint, messageKey, appMessagesName);
		if (msg != null) {
			return msg;
		}
		msg = getMessageFromPropfile(epoint, messageKey, clientMessagesName);
		if (msg != null) {
			return msg;
		}
		msg = getMessageFromPropfile(epoint, messageKey, messagesName);
		if (msg != null) {
			return msg;
		}
		return "";
	}



	/**
	 * クライアント用のメッセージマップを取得します。
	 *
	 * <pre>
	 * サーブレットの初期化パラメータ、"client-messages"に指定されたファイルから
	 * メッセージマップを取得します。
	 * このマップはページの初期化時にクライアントに送信され、javascriptから
	 * 通信を行わずに利用できるものになります。
	 * 基本的にクライアントバリデーション用のメッセージマップです。
	 * </pre>
	 *
	 * @param page
	 *            ページ情報。言語の判定などに使用する.
	 * @return クライアント用のメッセージマップ.
	 */
	public static Map<String, String> getClientMessageMap(final Page page) {
		ClientMessageTransfer trans = page.getClientMessageTransfer();
		Map<String, String> ret = new HashMap<String, String>();
		if (trans == ClientMessageTransfer.ALL) {
			Map<String, String> map  = getMessageMap(page, messagesName);
			if (map != null) {
				ret.putAll(map);
			}
		}
		Map<String, String> map0 = getMessageMap(page, clientMessagesName);
		if (map0 != null) {
			ret.putAll(map0);
		}
		if (trans == ClientMessageTransfer.ALL) {
			Map<String, String> map1 = getMessageMap(page, appMessagesName);
			if (map0 != null) {
				ret.putAll(map1);
			}
		}
		Map<String, String> map2 = getMessageMap(page, appClientMessagesName);
		if (map0 != null) {
			ret.putAll(map2);
		}
		String clsname = page.getClass().getName();
		String pageprop = "/" + clsname.replaceAll("\\.", "/");
		Map<String, String> map3 = MessagesUtil.getMessageMap(page, pageprop);
		if (map0 != null) {
			ret.putAll(map3);
		}
		return ret;
	}

	/**
	 * PropertiesXXXSelectFieldの選択肢を取得します。
	 * @param page ページ。
	 * @param pkey propertiesのキー。
	 * @return 選択肢リスト。
	 * @throws Exception 例外。
	 */
	public static List<Map<String, Object>> getSelectFieldOption(final Page page, final String pkey) throws Exception {
		List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
		Map<String, String> map = MessagesUtil.getClientMessageMap(page);
		Set<String> keyset = map.keySet();
		List<String> list = new ArrayList<String>();
		list.addAll(keyset);
		list.sort((String a, String b) -> {
			return a.compareTo(b);
		});
		for (String k: list) {
			if (k.indexOf(pkey) == 0) {
				String value = k.replaceAll(pkey + ".", "");
				String name = map.get(k);
				SelectField.OptionEntity e = new SelectField.OptionEntity();
				e.setValue(value);
				e.setName(name);
				options.add(e.getMap());
			}
		}
		return options;
	}
}
