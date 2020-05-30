package dataforms.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.util.FileUtil;


/**
 * BABEL変換スクリプトフィルター。
 *
 */
@WebFilter(filterName="babel-filter", urlPatterns = "*.jsb")
public class BabelFilter extends DataFormsFilter implements Filter {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(BabelFilter.class);

	/**
	 * ES6をIEで実行できるように変換する。
	 * @param sreq 要求情報。
	 * @param fname ファイル名。
	 * @return ES5への変換結果。
	 * @throws Exception 例外。
	 */
	private String convert(HttpServletRequest sreq, String fname) throws Exception {
		String babel = DataFormsServlet.getBabelCommand();
		Process p = Runtime.getRuntime().exec(babel);
		String es6js = this.readWebResource(sreq, fname);
		try (OutputStream os = p.getOutputStream()) {
			FileUtil.writeOutputStream(es6js.getBytes(DataFormsServlet.getEncoding()), os);
		}
		String contents = null;
		try (InputStream is = p.getInputStream()) {
			byte [] buf = FileUtil.readInputStream(is);
			contents = new String(buf, DataFormsServlet.getEncoding());
		}
		return contents;
	}

	/**
	 * JS Map.
	 */
	private static Map<String, String> jsMap = new HashMap<String, String>();


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest sreq = (HttpServletRequest) req;
			try {
				String fname = sreq.getRequestURI().replaceAll("\\.jsb$", ".js");
				logger.debug(() -> "filename=" + fname);
				String contents = BabelFilter.jsMap.get(fname);
				if (contents == null) {
					contents = convert(sreq, fname);
					BabelFilter.jsMap.put(fname, convert(sreq, fname));
				}
				resp.setContentType("text/css; charset=utf-8");
				try (PrintWriter out = resp.getWriter()) {
					out.print(contents);
				}
			} catch (Exception e) {
				logger.error(() -> e.getMessage(), e);
			}
		}
	}

}
