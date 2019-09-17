
/**
 * @fileOverview {@link TimestampField}クラスを記述したファイルです。
 */

/**
 * @class TimestampField
 * Timestamp型フィールドクラス。
 * <pre>
 * </pre>
 * @extends DateTimeField
 */
//TimestampField = createSubclass("TimestampField", {}, "DateTimeField");
class TimestampField extends DateTimeField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var comp = this.get();
		if (!comp.prop("readonly")) {
			var displayFormat = MessagesUtil.getMessage("format.timestampfield");
			var editFormat = MessagesUtil.getMessage("editformat.timestampfield");
			this.setFormat(displayFormat, editFormat);
		}
		this.backupStyle();
	}
}

