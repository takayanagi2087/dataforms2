package dataforms.devtool.pageform.gen;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.devtool.javasrc.JavaSrcGenerator;
import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;

/**
 * 単純なフォームジェネレータ。
 */
public class SimpleFormGenerator extends JavaSrcGenerator {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(SimpleFormGenerator.class);

	/**
	 * フォームのソースを作成します。
	 * @param data POSTされたデータ。
	 * @throws Exception 例外。
	 */
	private void generateFormClass(final Map<String, Object> data) throws Exception {
		Template tmp = new Template(this.getClass(), "simpletemplate/Form.java.template");
		logger.debug("page src=" + tmp.getSource());
		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String pageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME);
		String formClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_FORM_CLASS_NAME);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME, packageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_NAME, pageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_FORM_CLASS_NAME, formClassName);
		logger.debug("page=" + tmp.getSource());

		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String formclass = packageName + "." + formClassName;
		String srcPath = path + "/" + formclass.replaceAll("\\.", "/") + ".java";
		logger.debug("srcPath=" + srcPath);
		FileUtil.writeTextFileWithBackup(srcPath, tmp.getSource(), DataFormsServlet.getEncoding());
	}


	/**
	 * ソースの生成処理。
	 */
	@Override
	public void generage(final Form form, final Map<String, Object> data) throws Exception {
		logger.info("generate simple form.");
		this.generateFormClass(data);
	}
}
