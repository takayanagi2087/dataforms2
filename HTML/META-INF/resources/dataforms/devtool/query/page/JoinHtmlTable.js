/**
 * @fileOverview {@link JoinHtmlTable}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class JoinHtmlTable
 *
 * @extends EditableHtmlTable
 */
class JoinHtmlTable extends EditableHtmlTable {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * 各行のid中のインデックスを整列する.
	 */
	resetIdIndex() {
		super.resetIdIndex();
		for (var i = 0; i < this.getRowCount(); i++) {
			var af = this.getRowField(i, "aliasName");
			var alias = af.getValue();
			if (alias.match(/j[0-9]+/)) {
				af.setValue("");
			}
		}
		for (var i = 0; i < this.getRowCount(); i++) {
			var af = this.getRowField(i, "aliasName");
			var alias = af.getValue();
			if (alias == null || alias.length == 0) {
				af.setValue("j" + i);
			}
		}
	}
}

