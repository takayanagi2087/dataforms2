/**
 * @fileOverview {@link FieldListHtmlTable}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class FieldListHtmlTable
 *
 * @extends EditableHtmlTable
 */
class FieldListHtmlTable extends EditableHtmlTable {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * 行追加時に呼び出されるメソッドです。
	 *
	 * @param {String} rowid 設定する行のID('tableid[idx]'形式)。
	 */
	onAddTr(rowid) {
		super.onAddTr(rowid);
		logger.log("rowid=" + rowid);
		var form = this.parent;
		var tpkgname = form.get("packageName").val();
		var pkg = this.get(rowid + ".packageName");
		var spkg = this.get(rowid + ".superPackageName");
		if (pkg.val().length == 0) {
			pkg.val(tpkgname.replace(".dao", ".field"));
		}
		if (spkg.val().length == 0) {
			spkg.val("dataforms");
		}
	}
}


