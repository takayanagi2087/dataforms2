package dataforms.devtool.pageform.gen;

import java.util.Map;

import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;

/**
 * 検索結果フォームのJavaソースジェネレータ。
 */
public class QueryResultFormGenerator extends FormSrcGenerator {

	/**
	 * 問合せ結果フォームのテンプレートを取得します。
	 */
	@Override
	protected Template getTemplate() throws Exception {
		Template tmp = new Template(this.getClass(), "template/QueryResultForm.java.template");
		return tmp;
	}


	/**
	 * 問合せ結果フォームクラス名を取得します。
	 */
	@Override
	protected String getFormClassName(Map<String, Object> data) {
		String formClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_QUERY_RESULT_FORM_CLASS_NAME);
		return formClassName;
	}


	@Override
	protected void setFormComponent(Template tmp, String formClassName, Map<String, Object> data) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
