/**
 * @fileOverview {@link ImportDataForm}クラスを記述したファイルです。
 */

'use strict';

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
		this.get("importButton").click(() => {
			var rform = currentPage.getComponent("queryResultForm");
			var path = this.getFieldValue("pathName");
			rform.importTableData(path);
			this.parent.close();
		});
	}
}

