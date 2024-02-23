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
 * 単純なページジェネレータ。
 */
public class SimplePageGenerator extends JavaSrcGenerator {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(SimplePageGenerator.class);

	@Override
	protected Template getTemplate() throws Exception {
		Template tmp = new Template(this.getClass(), "simpletemplate/Page.java.template");
		return tmp;
	}

	/**
	 * ページのソースを作成します。
	 * @param data POSTされたデータ。
	 * @throws Exception 例外。
	 */
	private void generatePageClass(final Map<String, Object> data) throws Exception {
		Template tmp = this.getTemplate(); // new Template(this.getClass(), "simpletemplate/Page.java.template");
		logger.debug("page src=" + tmp.getSource());
		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String pageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME);
		String pageClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_CLASS_NAME);
		String functionPath = (String) data.get(DaoAndPageGeneratorEditForm.ID_FUNCTION_SELECT);
		String formClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_FORM_CLASS_NAME);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME, packageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_NAME, pageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_CLASS_NAME, pageClassName);
		tmp.replace("functionPath", functionPath);
		tmp.replace("formList", "\t\tthis.addForm(new " + formClassName + "());");
		logger.debug("page=" + tmp.getSource());

		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String pageclass = packageName + "." + pageClassName;
		String srcPath = path + "/" + pageclass.replaceAll("\\.", "/") + ".java";
		logger.debug("srcPath=" + srcPath);
		FileUtil.writeTextFileWithBackup(srcPath, tmp.getSource(), DataFormsServlet.getEncoding());

	}

	/**
	 * ソースの生成処理。
	 */
	@Override
	public void generage(final Form form, final Map<String, Object> data) throws Exception {
		logger.info("generate simple page.");
		this.generatePageClass(data);
	}
}
