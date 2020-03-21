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
		this.displayFormat = displayFormat;
		this.editFormat = editFormat;
		var thisForm = this;
		this.get().focus(function() {
			thisForm.toEditFormat($(this));
		});
		this.get().blur(function() {
			thisForm.toDisplayFormat($(this));
		});
	}

	/**
	 * 日付を編集フォーマットに変更します。
	 * @param {jQuery} f テキストフィールド。
	 */
	toEditFormat(f) {
		var v = f.val();
		var fmt = new SimpleDateFormat(this.displayFormat);
		var efmt = new SimpleDateFormat(this.editFormat);
		var ev = fmt.parse(v);
		if (ev != null) {
			f.val(efmt.format(ev));
		}
	}


	/**
	 * 日付を表示フォーマットに変更します。
	 * @param {jQuery} f テキストフィールド。
	 */
	toDisplayFormat(f) {
		var v = f.val();
		var fmt = new SimpleDateFormat(this.displayFormat);
		var efmt = new SimpleDateFormat(this.editFormat);
		var ev = efmt.parse(v);
		if (ev != null) {
			f.val(fmt.format(ev));
		}
	}

	/**
	 * 親フォームのonCalcメソットを呼び出します。
	 * @param {jQuery} f フィールド。
	 */
	callOnCalc(f) {
		this.toDisplayFormat(f);
		super.callOnCalc(f);
	}


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

