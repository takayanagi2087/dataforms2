/**
 * @fileOverview {@link PageScrollHtmlTable}クラスを記述したファイルです。
 */

'use strict';

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
		thisTable.parent.find("div.pageController :input").each(function() {
			let id = $(this).attr(thisTable.getIdAttribute());
			if ($(this).attr("name") == null) {
				$(this).attr("name", id);
			}
		});
		if (currentPage.useUniqueId) {
			// TODO: ページャーの構造は見直した方がよいかも。
			// ページャー関連のユニークIDを設定します。
			this.setPageControllerRealId("hitCount");
			this.setPageControllerRealId("pageNo");
			this.setPageControllerRealId("linesPerPage");
		}
		this.sortOrder = this.getSortOrder();
	}

	/**
	 * ページャー関連のコンポートネントにユニークなIDを設定します。
	 * @param {String} fid フィールドID。
	 */
	setPageControllerRealId(fid) {
		var comp = this.parent.getComponent(fid);
		var jq = this.parent.find("[" + this.getIdAttribute() + "='" + fid + "']");
		jq.attr("id", comp.realId);
	}

	/**
	 * ソート順の情報を取得します。
	 * @return {String} ソート順情報。
	 */
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


