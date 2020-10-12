package dataforms.servlet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.util.FileUtil;
import dataforms.util.StringUtil;

/**
 * dataforms2.jarで定義するフィールターのベースクラス。
 *
 */
public class DataFormsFilter {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(DataFormsFilter.class);

	/**
	 * Webリソースのタイムスタンプキャッシュ。
	 */
	private static Map<String, Long> webResourceTimestampCache = Collections.synchronizedMap(new HashMap<String, Long>());

	/**
	 * タイムスタンプキャッシュを取得します。
	 * @return タイムスタンプキャッシュ。
	 */
	public static Map<String, Long> getWebResourceTimestampCache() {
		return webResourceTimestampCache;
	}

	/**
	 * WebリソースのURLを取得します。
	 *
	 * @param req 要求情報。
	 * @param path パス。
	 * @return URL。
	 * @throws Exception 例外。
	 */
	public String getWebResourceUrl(final HttpServletRequest req, final String path) throws Exception {
		URL accessurl = new URL(req.getRequestURL().toString());
		String url = null;
		if (StringUtil.isBlank(DataFormsServlet.getWebResourceUrl())) {
			url = accessurl.getProtocol() + "://" + req.getServerName() + ":" + req.getServerPort() + path;
		} else {
			url = DataFormsServlet.getWebResourceUrl() + path;
		}
		logger.debug("getWebResourceUrl:path={}", path);
		logger.debug("getWebResourceUrl:url={}", url);
		return url;
	}

	/**
	 * Webリソースを読み込みます。
	 * @param req 要求情報。
	 * @param path リソースのパス。
	 * @return Webリソースの文字列。
	 * @throws Exception 例外。
	 */
	protected String readWebResource(final HttpServletRequest req, final String path) throws Exception {
		logger.debug("readWebResource path={}", path);
		URL url = new URL(getWebResourceUrl(req, path));
		String ret = "";
		logger.debug(() -> "webResourceUrl=" + url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		try {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				byte[] buf = FileUtil.readInputStream(is);
				ret = new String(buf, DataFormsServlet.getEncoding());

				long d = conn.getLastModified();
				DataFormsFilter.webResourceTimestampCache.put(path, d);
			}
		} finally {
			conn.disconnect();
		}
		return ret;
	}

	/**
	 * 指定ファイルのタイムスタンプを取得します。
	 * @param path 取得するファイルのパス。
	 * @return タイムスタンプ。
	 */
	protected Long getLastUpdate(final String path) {
		return webResourceTimestampCache.get(path);
	}

}
