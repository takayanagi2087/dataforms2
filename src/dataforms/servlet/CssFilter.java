package dataforms.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import dataforms.util.FileUtil;
import dataforms.util.StringUtil;

/**
 * CSSのフィルター。
 * <pre>
 *
 * Hoge.cssxをアクセスすると、Hoge.cssを読み込み変換して出力するフィルターです。
 *
 * 今のところVariables.cssに定義した変数を展開したcssを作成する機能しかありません。
 * この機能は標準cssではできない以下の記述に対応する為に作りました。
 *
 * @@media screen and (max-width: var(--sp-screen-max))
 *
 * そのうちcssの記述性を上げる機能を追加するかもしれません。
 *
 * </pre>
 */
@WebFilter(filterName="css-filter", urlPatterns = "*.cssx")
public class CssFilter implements Filter {
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(CssFilter.class);

	/**
	 * CSS Map.
	 */
	private static Map<String, String> cssMap = new HashMap<String, String>();

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
		logger.debug("getWebResourceUrl:path=" + path);
		logger.debug("getWebResourceUrl:url=" + url);
		return url;
	}


	/**
	 * Webリソースを読み込みます。
	 * @param req 要求情報。
	 * @param path リソースのパス。
	 * @return Webリソースの文字列。
	 * @throws Exception 例外。
	 */
	private String readWebResource(final HttpServletRequest req, final String path) throws Exception {
		logger.debug("readWebResource path=" + path);
		String css = CssFilter.cssMap.get(path);
		if (css != null) {
			return css;
		}
		URL url = new URL(getWebResourceUrl(req, path));
		String ret = "";
		logger.debug("webResourceUrl=" + url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		try {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				byte[] buf = FileUtil.readInputStream(is);
				ret = new String(buf, DataFormsServlet.getEncoding());
				CssFilter.cssMap.put(path, ret);
			}
		} finally {
			conn.disconnect();
		}
		return ret;
	}

	/**
	 * 変数マップ。
	 */
	private static Map<String, String> varMap = null;

	/**
	 * css内の:rootのカスタムプロパティを取得します。
	 * @param css CSSの文字列。
	 */
	private void parseVar(final String css) {
		logger.debug("parseVar");
		if (CssFilter.varMap != null) {
			return;
		}
		Pattern p = Pattern.compile(":root[\\s\\S]\\{([\\s\\S]*?)\\}", Pattern.MULTILINE);
		Matcher m = p.matcher(css);
		if (m.find()) {
			CssFilter.varMap = new HashMap<String, String>();
			String g = m.group(1);
			String[] lines = g.split("[\r\n]");
			Pattern vp = Pattern.compile("(--.*):(.*);");
			for (String line : lines) {
				line = line.replaceAll("/\\*.*\\*/", "");
				Matcher vm = vp.matcher(line);
				if (vm.find()) {
					logger.debug("var=" + vm.group(1) + "," + vm.group(2));
					CssFilter.varMap.put(vm.group(1), vm.group(2));
				}
			}
		}
	}

	/**
	 * 変数の置き換えを行います。
	 * @param css スタイルシートのテキスト。
	 * @return 変数を置き換えたcss。
	 */
	private String replaceVar(final String css) {
		if (CssFilter.varMap != null) {
			String ret = css;
			for (String key: CssFilter.varMap.keySet()) {
				String p = "var\\s*\\(\\s*" + key + "\\s*\\)";
				String v = CssFilter.varMap.get(key);
				ret = ret.replaceAll(p, v);
			}
			return ret;
		} else {
			return css;
		}

	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest sreq = (HttpServletRequest) req;
			try {
				String contents = this.readWebResource(sreq, sreq.getRequestURI().replaceAll("\\.cssx$", ".css"));
				this.parseVar(contents);
				contents = this.replaceVar(contents);
				resp.setContentType("text/css; charset=utf-8");
				try (PrintWriter out = resp.getWriter()) {
					out.print(contents);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
