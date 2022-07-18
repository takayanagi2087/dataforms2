/**
 * @fileOverview {@link QueryExecutorQueryForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryExecutorQueryForm
 *
 * @extends QueryForm
 */
class QueryExecutorQueryForm extends QueryForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * Queryクラスに対応するSQLを取得します。
	 */
	async getSql() {
		let r = await this.submit("getSql");
		if (r.status == JsonResponse.SUCCESS) {
			this.get("sql").val(r.result);
		}
	}
}


