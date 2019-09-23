/**
 * @fileOverview {@link TableClassNameField}クラスを記述したファイルです。
 */

/**
 * @class TableClassNameField
 *
 * @extends SimpleClassNameField
 */
class TableClassNameField extends SimpleClassNameField {
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
			if (typeof form.getFieldList == "function") {
				form.getFieldList();
			}
		}
	}

}



