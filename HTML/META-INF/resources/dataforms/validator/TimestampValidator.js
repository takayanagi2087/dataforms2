/**
 * @fileOverview {@link TimestampValidator}クラスを記述したファイルです。
 */

/**
 * @class TimestampValidator
 * タイムスタンプバリデータ。
 * <pre>
 * </pre>
 * @extends DateTimeValidator
 */
// TimestampValidator = createSubclass("TimestampValidator", {dateFormatKey: "format.timestampfield"}, "DateTimeValidator");

class TimestampValidator extends DateTimeValidator {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		this.dateFormatKey = "format.timestampfield";
	}
}

