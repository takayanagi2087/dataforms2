/**
 * @fileOverview {@link WebResourceQueryForm}クラスを記述したファイルです。
 */

/**
 * @class WebResourceQueryForm
 * Webリソース検索フォームクラス。
 * <pre>
 * 指定されたページ中のWebコンポーネントを検索するためのフォームです。
 * </pre>
 * @extends QueryForm
 */
class WebResourceQueryForm extends QueryForm {
	/**
	 * HTMLエレメントフォームとの対応付けを行います。
	 *
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.find("#checkAllTypeButton").click(function() {
			thisForm.find("[name='webComponentTypeList']").each(function() {
				$(this).prop("checked", true);
			});
		});
		this.find("#uncheckAllTypeButton").click(function() {
			thisForm.find("[name='webComponentTypeList']").each(function() {
				$(this).prop("checked", false);
			});
		});
	}
}

