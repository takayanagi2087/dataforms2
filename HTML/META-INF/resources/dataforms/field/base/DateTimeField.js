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
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.displayFormat = null;
		this.editFormat = null;
	}
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
	 * 値を設定します。
	 * @param {String} date 値。
	 */
	setValue(date) {
		if (date instanceof Date) {
			var fmt = new SimpleDateFormat(this.displayFormat);
			super.setValue(fmt.format(date));
		} else {
			super.setValue(date);
		}
	}

	/**
	 * 値を取得します。
	 * @returns {Date} 値(日付形式)。
	 */
	getValue() {
		var v = super.getValue();
		if (v != null && v.length > 0) {
			logger.log("getValue()=" + v);
			var dfmt = new SimpleDateFormat(this.displayFormat);
			var ret = dfmt.parse(v);
			if (ret == null) {
				var efmt = new SimpleDateFormat(this.editFormat);
				ret = efmt.parse(v);
			}
			return ret;
		} else {
			return null;
		}
	}
}

