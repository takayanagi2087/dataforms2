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
	getSql() {
		var thisForm = this;
		this.submit("getSql", function(r) {
			if (r.status == ServerMethod.SUCCESS) {
				thisForm.get("sql").val(r.result);
			}
		});
	}
}


