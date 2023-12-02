package dataforms.devtool.javasrc;

import java.io.InputStream;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;

/**
 * Javaソースジェネレータの基本クラス。
 */
public abstract class JavaSrcGenerator {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(JavaSrcGenerator.class);

	/**
	 * 指定された文字列リソースを取得します。
	 * @param cls クラス。
	 * @param path リソースパス。
	 * @return 文字列。
	 * @throws Exception 例外。
	 */
	protected String getStringResourse(final Class<?> cls, final String path) throws Exception {
		logger.info("getResource:" + cls.getName() + "," + path);
		InputStream is = cls.getResourceAsStream(path);
		String text = new String(FileUtil.readInputStream(is), DataFormsServlet.getEncoding());
		return text;
	}

	/**
	 * ソースを生成します。
	 * @param form フォーム。
	 * @param data POSTされたデータ。
	 * @throws Exception 例外。
	 */
	public abstract void generage(final Form form, final Map<String, Object> data) throws Exception;
}
