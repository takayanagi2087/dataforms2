package dataforms.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * BABEL変換スクリプトフィルター。
 *
 */
@WebFilter(filterName="babel-filter", urlPatterns = "*.jsb")
public class BabelFilter implements Filter {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(BabelFilter.class);

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest sreq = (HttpServletRequest) req;
			try {
				String fname = sreq.getRequestURI().replaceAll("\\.jsb$", ".js");
				logger.debug(() -> "filename=" + fname);
/*				String contents = this.readWebResource(sreq, fname);
				resp.setContentType("text/css; charset=utf-8");
				try (PrintWriter out = resp.getWriter()) {
					out.print(contents);
				}*/
			} catch (Exception e) {
				logger.error(() -> e.getMessage(), e);
			}
		}
	}
}
