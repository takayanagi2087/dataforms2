package dataforms.devtool.pageform.gen;

import java.util.Map;

import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;

/**
 * 検索結果フォームのJavaソースジェネレータ。
 */
public class EditFormGenerator extends FormSrcGenerator {

	/**
	 * 問合せ結果フォームのテンプレートを取得します。
	 */
	@Override
	protected Template getTemplate() throws Exception {
		Template tmp = new Template(this.getClass(), "template/EditForm.java.template");
		return tmp;
	}


	/**
	 * 問合せ結果フォームクラス名を取得します。
	 */
	@Override
	protected String getFormClassName(Map<String, Object> data) {
		String formClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_FORM_CLASS_NAME);
		return formClassName;
	}


	@Override
	protected void setFormComponent(Template tmp, String formClassName, Map<String, Object> data) throws Exception {
		tmp.replace(DaoAndPageGeneratorEditForm.ID_EDIT_FORM_CLASS_NAME, formClassName);
	}

}
