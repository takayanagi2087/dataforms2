package dataforms.devtool.pageform.gen;

import java.util.Map;

import dataforms.controller.Form;
import dataforms.devtool.javasrc.JavaSrcGenerator;
import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;

/**
 * フォームソースジェネレータの基本クラス。
 */
public abstract class FormSrcGenerator extends JavaSrcGenerator {

	/**
	 * テンプレートを取得します。
	 * @return テンプレート。
	 */
	protected abstract Template getTemplate() throws Exception;

	/**
	 * フォームクラス名を取得します。
	 * @param data POSTデータ。
	 * @return フォームクラス名。
	 */
	protected abstract String getFormClassName(final Map<String, Object> data);

	/**
	 * フォームの種類毎のコンポーネントの配置処理を実装します。
	 * @param tmp テンプレート。
	 * @param formClassName フォームクラス名。
	 * @param data POSTデータ。
	 * @throws Exception 例外。
	 */
	protected abstract void setFormComponent(Template tmp, String formClassName, Map<String, Object> data) throws Exception;

	/**
	 * メソッドのテンプレートを設定する。
	 * @param tmp ソーステンプレレート。
	 * @throws Exception 例外。
	 */
	protected void setMethodTemplate(Template tmp) throws Exception {
		Template vfTmp = new Template(this.getClass(), "template/validateForm.java.template");
		Template wmTmp = new Template(this.getClass(), "template/webMethod.java.template");
		tmp.replace("validateForm", vfTmp.getSource());
		tmp.replace("webMethod", wmTmp.getSource());
	}

	@Override
	public void generage(Form form, Map<String, Object> data) throws Exception {
		Template tmp = this.getTemplate();
		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String pageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME);
		String daoPackageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_PACKAGE_NAME);
		String daoClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME);
		String formClassName = this.getFormClassName(data);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME, packageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_NAME, pageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME, daoClassName);
		String daocls = daoPackageName + "." + daoClassName;
		tmp.replace("daoClassFullName", daocls);
		this.setFormComponent(tmp, formClassName, data);
		this.setMethodTemplate(tmp);
		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String formclass = packageName + "." + formClassName;
		String srcPath = path + "/" + formclass.replaceAll("\\.", "/") + ".java";
		FileUtil.writeTextFileWithBackup(srcPath, tmp.getSource(), DataFormsServlet.getEncoding());
	}

}
