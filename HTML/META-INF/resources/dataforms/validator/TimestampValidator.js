/**
 * @fileOverview {@link TimestampValidator}クラスを記述したファイルです。
 */

/**
 * @class TimestampValidator
 * タイムスタンプバリデータ。
 * @extends DateTimeValidator
 */
class TimestampValidator extends DateTimeValidator {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		this.dateFormatKey = "format.timestampfield";
	}
}

