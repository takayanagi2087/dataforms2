/**
 * @fileOverview {@link DateTimeField}クラスを記述したファイルです。
 */

/**
 * @class DateTimeField
 *
 * 日付/時刻フィールドクラス。
 * <pre>
 * 日付/時刻フィールドの基底クラスです。
 * </pre>
 * @extends Field
 */
class DateTimeField extends Field {
	/**
	 * 日付の表示フォーマットを指定します。
	 * @param {String} displayFormat 表示時のフォーマット。
	 * @param {String} editFormat 編集時のフォーマット。
	 */
	setFormat(displayFormat, editFormat) {
		this.get().focus(function() {
			var v = $(this).val();
			var fmt = new SimpleDateFormat(displayFormat);
			var efmt = new SimpleDateFormat(editFormat);
			var ev = fmt.parse(v);
			if (ev != null) {
				$(this).val(efmt.format(ev));
			}
		});
		this.get().blur(function() {
			var v = $(this).val();
			var fmt = new SimpleDateFormat(displayFormat);
			var efmt = new SimpleDateFormat(editFormat);
			var ev = efmt.parse(v);
			if (ev != null) {
				$(this).val(fmt.format(ev));
			}
		});
	};

	/**
	 * 値を取得します。
	 * @returns {Date} 値(日付形式)。
	 */
	getValue() {
		var displayFormat = MessagesUtil.getMessage("format.timestampfield");
		var editFormat = MessagesUtil.getMessage("editformat.timestampfield");
		var v = super.getValue();
		if (v != null && v.length > 0) {
			var dfmt = new SimpleDateFormat(displayFormat);
			var ret = dfmt.parse(v);
			if (ret == null) {
				var efmt = new SimpleDateFormat(editFormat);
				ret = efmt.parse(v);
			}
			return ret;
		} else {
			return null;
		}
	}
}

