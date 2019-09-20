/**
 * @fileOverview {@link PageScrollHtmlTable}クラスを記述したファイルです。
 */

/**
 * @class PageScrollHtmlTable
 * ページスクロールHTMLテーブルクラス。
 * <pre>
 * 行の追加、削除、ドラックによる順序変更をサポートします。
 * </pre>
 * @extends HtmlTable
 */
class PageScrollHtmlTable extends HtmlTable {
	/**
	 * エレメントとの対応付け.
	 */
	attach() {
		this.sortOrder = "";
		var thisTable = this;
		super.attach();
		thisTable.get().before(this.additionalHtmlText);
		thisTable.parent.find(":input").each(function() {
			if ($(this).attr("name") == null) {
				$(this).attr("name", $(this).attr("id"));
			}
		});
		this.sortOrder = this.getSortOrder();
	}


	getSortOrder() {
		var flist = this.getSortFieldList();
		var sortOrder = "";
		for (var i = 0; i < flist.length; i++) {
			if (sortOrder.length > 0) {
				sortOrder += ",";
			}
			var f = flist[i];
			sortOrder += (f.id + ":" + f.currentSortOrder);
		}
		return sortOrder;
	}

	/**
	 * ソートを行います。
	 * @param co {jQuery} ラベルのエレメント.
	 * @return {Array} ソート結果リスト。
	 *
	 */
	sortTable(col) {
		var thisTable = this;
		this.changeSortMark(col);
		this.sortOrder = this.getSortOrder();
		for (var i = 0; i < this.fields.length; i++) {
			var f = this.fields[i];
			f.sortOrder = f.currentSortOrder;
		}

		this.parent.find("#pageNo").val("0");
		this.parent.changePage();
	}
}


