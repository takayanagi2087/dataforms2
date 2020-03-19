/**
 * @fileOverview {@link ImportDataForm}クラスを記述したファイルです。
 */

/**
 * @class ImportDataForm
 * テーブル情報フォームクラス。
 * <pre>
 * テーブル情報を表示するためのフォームです。
 * </pre>
 * @extends Form
 */
class ImportDataForm extends Form {
	/**
	 * HTMLエレメントフォームとの対応付けを行います。
	 *
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.find("#importButton").click(function() {
			var rform = currentPage.getComponent("queryResultForm");
			var path = thisForm.getFieldValue("pathName");
			rform.import(path);
			thisForm.parent.close();
		});
	}
}

