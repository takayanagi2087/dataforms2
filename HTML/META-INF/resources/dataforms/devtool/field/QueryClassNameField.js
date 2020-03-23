/**
 * @fileOverview {@link QueryClassNameField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class QueryClassNameField
 *
 * @extends SimpleClassNameField
 */
class QueryClassNameField extends SimpleClassNameField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	onUpdateRelationField() {
		super.onUpdateRelationField();
		if (this.get().val().length != 0) {
			var form = this.getParentForm();
			if (typeof form.getSql == "function") {
				form.getSql();
			}
		}
	}
}


