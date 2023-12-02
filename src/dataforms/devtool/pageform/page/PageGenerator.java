package dataforms.devtool.pageform.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.devtool.javasrc.JavaSrcGenerator;

/**
 * ページジェネレータ。
 */
public abstract class PageGenerator extends JavaSrcGenerator {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(PageGenerator.class);

	/**
	 * コンストラクタ。
	 */
	public PageGenerator() {

	}
}
