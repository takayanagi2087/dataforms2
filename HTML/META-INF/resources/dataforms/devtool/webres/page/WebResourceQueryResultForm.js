/**
 * @fileOverview {@link WebResourceQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class WebResourceQueryResultForm
 * Webリソース検索フォームクラス。
 * <pre>
 * 指定されたページ中のWebコンポーネントを検索するためのフォームです。
 * </pre>
 * @extends QueryResultForm
 */
class WebResourceQueryResultForm extends QueryResultForm {
	/**
	 * HTMLエレメントフォームとの対応付けを行います。
	 *
	 */
	attach() {
		super.attach();
	}

	/**
	 * 問い合わせ結果を表示します。
	 * @param {Object} result 問い合わせ結果。
	 */
	setFormData(result) {
		super.setFormData(result);
		let thisForm = this;
		let queryResult = result.queryResult;
		let table = this.getComponent("queryResult");
		if (queryResult != null) {
			for (let i = 0; i < queryResult.length; i++) {
				let id = "queryResult[" + i + "].className";
				this.get(id).click((ev) => {
					let classname = table.getSameRowField($(ev.target), "className").html();
					let webComponentType = table.getSameRowField($(ev.target), "webComponentType").val();
					let htmlStatus = table.getSameRowField($(ev.target), "htmlStatus").val();
					let javascriptStatus = table.getSameRowField($(ev.target), "javascriptStatus").val();
					let javascriptClass = table.getSameRowField($(ev.target), "javascriptClass").val();
					let data = {};
					data.className = classname;
					data.webComponentType = webComponentType;
					data.htmlStatus = htmlStatus;
					data.javascriptStatus = javascriptStatus;
					data.javascriptClass = javascriptClass;
					let dlg = thisForm.parent.getComponent("webResourceDialog");
					let f = dlg.getComponent("webResourceForm");
					data.webSourcePath = f.getFieldValue("webSourcePath");
					f.setFormData(data);
					dlg.showModal({width: 850});
				});
			}
		}
	}
}



