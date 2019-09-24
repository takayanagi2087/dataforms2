/**
 * @fileOverview {@link QueryExecutorQueryResultForm}クラスを記述したファイルです。
 */

/**
 * @class QueryExecutorQueryResultForm
 *
 * @extends QueryResultForm
 */
class QueryExecutorQueryResultForm extends QueryResultForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		this.headerHtml = null;
		super.attach();
	}

	setQueryResult(queryResult) {
		logger.log("headerHtml=" + queryResult.headerHtml);
		logger.log("dataHtml=" + queryResult.dataHtml);
		logger.log("htmlTable=" + queryResult.htmlTable);
		var table = this.getComponent("queryResult");
		table.fields = [];
		table.initField(queryResult.htmlTable.fieldList);
		table.trLine = queryResult.dataHtml;
		if (this.headerHtml != queryResult.headerHtml) {
			logger.log("updateHeader");
			this.find("#queryResult thead tr").html(queryResult.headerHtml);
			table.setColumnSortEvent();
			this.headerHtml = queryResult.headerHtml;
		}
		super.setQueryResult(queryResult);
	}
}



