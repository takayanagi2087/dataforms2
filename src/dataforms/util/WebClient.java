package dataforms.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.arnx.jsonic.JSON;

/**
 * httpアクセス用のクラス。
 *
 */
public class WebClient {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(WebClient.class);

	/**
	 * Getメソッド。
	 */
	public static final String METHOD_GET = "GET";
	/**
	 * Postメソッド。
	 */
	public static final String METHOD_POST = "POST";

	/**
	 * URL.
	 */
	private String url = null;

	/**
	 * HTTPメソッド。
	 */
	private String httpMethod = METHOD_GET;

	/**
	 * 送信するデータ形式。
	 * application/x-www-form-urulencoded または application/json
	 */
	private String contentType = null;

	/**
	 * コンストラクタ。
	 * @param url URL。
	 */
	public WebClient(final String url) {
		this.url = url;
		this.httpMethod = METHOD_GET;
	}

	/**
	 * コンストラクタ。
	 * @param url URL。
	 * @param method HTTP method。
	 */
	public WebClient(final String url, final String method) {
		this.url = url;
		this.httpMethod = method;
	}

	/**
	 * HTTPのメソッドを取得します。
	 * @return HTTPのメソッド。
	 */
	public String getHttpMethod() {
		return httpMethod;
	}

	/**
	 * HTTPのメソッドを設定します。
	 * @param httpMethod HTTPのメソッド。
	 */
	public void setHttpMethod(final String httpMethod) {
		this.httpMethod = httpMethod;
	}

	/**
	 * 送信データのcontent-typeを取得します。
	 * @return 送信データのcontent-type。
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 送信データのcontent-typeを設定します。
	 * @param contentType 送信データのcontent-type。
	 */
	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	/**
	 * パラメータを解析します。
	 * @param p パラメータ。
	 * @return 送信データ。
	 */
	protected String parseSendData(final Object p) {
		if (p == null) {
			return null;
		}
		if (p instanceof String) {
			// 文字列の場合はqueryStringの形式。
			this.setContentType("application/x-www-form-urulencoded");
			return (String) p;
		} else {
			// 文字列以外はJSON形式でPOST
			this.setHttpMethod(METHOD_POST);
			this.setContentType("application/json");
			return JSON.encode(p);
		}
	}

	/**
	 * POSTの場合のデータ送信。
	 * @param conn URLConnection。
	 * @param senddata 送信データ。
	 * @throws Exception 例外。
	 */
	private void sendData(HttpURLConnection conn, String senddata) throws Exception {
		if (METHOD_POST.equals(this.httpMethod)) {
			if (senddata != null) {
				logger.debug(() -> "senddata=" + senddata);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", this.contentType);
				try (OutputStream os = conn.getOutputStream()) {
					FileUtil.writeOutputStream(senddata.getBytes("utf-8"), os);
				}
			}
		}
	}

	/**
	 * 応答情報を読み込みます。
	 * @param conn URLConnection。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	private Object readResponse(HttpURLConnection conn) throws Exception, UnsupportedEncodingException, IOException {
		Object ret = null;
		try (InputStream is = conn.getInputStream()) {
			String encoding = conn.getContentEncoding();
			if (null == encoding) {
				encoding = "utf-8";
			}
			byte[] buf = FileUtil.readInputStream(is);
			ret = new String(buf, encoding);
			String contentType = conn.getContentType().toLowerCase();
			if (contentType != null) {
				if (contentType.indexOf("application/json") >= 0) {
					ret = JSON.decode((String) ret, HashMap.class);
				}
			}
		}
		return ret;
	}

	/**
	 * APIを呼び出します。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	public Object call(final Object p) throws Exception {
		Object ret = null;
		String senddata = this.parseSendData(p);
		String url = this.url;
		if (METHOD_GET.equals(this.httpMethod)) {
			// GET
			if (senddata != null) {
				url += "?" + senddata;
			}
		}
		logger.debug("url={}", url);
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setRequestMethod(this.httpMethod);
		this.sendData(conn, senddata);
		conn.connect();
		try {
			// HTTPレスポンスコード
			final int status = conn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				ret = this.readResponse(conn);
			}
		} finally {
			conn.disconnect();
		}
		return ret;
	}
}
