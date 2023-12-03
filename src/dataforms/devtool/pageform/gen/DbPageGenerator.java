package dataforms.devtool.pageform.gen;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.devtool.javasrc.JavaSrcGenerator;

/**
 * ページジェネレータ。
 */
public class DbPageGenerator extends JavaSrcGenerator {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(DbPageGenerator.class);

	/**
	 * コンストラクタ。
	 */
	public DbPageGenerator() {

	}

	@Override
	public void generage(Form form, Map<String, Object> data) throws Exception {
		logger.debug("Generate DB access page ");
	}
}
