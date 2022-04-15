/**
 * @fileOverview {@link QueryGeneratorQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryGeneratorQueryResultForm
 *
 * @extends QueryResultForm
 */
class QueryGeneratorQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * SubQueryの生成を行います。
	 * @param {jQuery} btn ボタン。
	 */
	async generateSubQuery(btn) {
		let queryResult = this.getComponent("queryResult");
		let queryClassName = queryResult.getSameRowField(btn, "fullClassName").text();
		logger.log("queryClassName=" + queryClassName);
		let m = this.getWebMethod("generateSubQuery");
		let r = await m.execute("queryClass=" + queryClassName);
		if (r.status == ServerMethod.SUCCESS) {
			currentPage.alert(null, r.result);
		}
		this.changePage();
	}

	/**
	 * 問合せ結果にデフォルトイベント処理を設定します。
	 */
	setQueryResultEventHandler() {
		super.setQueryResultEventHandler();
		let queryResult = this.getComponent("queryResult");
		// リスト中のボタンに対してイベント処理を追加。
		this.find("[id$='\.generateSubQueryButton']").click(async (ev) => {
			let sq = queryResult.getSameRowField($(ev.target), "subQuery").text();
			if (sq.length > 0) {
				let msg = MessagesUtil.getMessage("message.confirmsubquery");
				if (await currentPage.confirm(null, msg)) {
					this.generateSubQuery($(ev.target));
				};
			} else {
				this.generateSubQuery($(ev.target));
			}
		});
	}
}

