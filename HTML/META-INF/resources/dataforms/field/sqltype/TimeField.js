
/**
 * @fileOverview {@link TimeField}クラスを記述したファイルです。
 */

/**
 * @class TimeField
 * Time型フィールドクラス。
 * <pre>
 * </pre>
 * @extends DateTimeField
 */
//TimeField = createSubclass("TimeField", {}, "DateTimeField");
class TimeField extends DateTimeField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var comp = this.get();
		if (!comp.prop("readonly")) {
			var displayFormat = MessagesUtil.getMessage("format.timefield");
			var editFormat = MessagesUtil.getMessage("editformat.timefield");
			this.setFormat(displayFormat, editFormat);
		}
		this.backupStyle();
	};
}

