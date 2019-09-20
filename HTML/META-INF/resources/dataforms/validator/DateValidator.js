/**
 * @fileOverview {@link DateValidator}クラスを記述したファイルです。
 */

/**
 * @class DateValidator
 * 日付バリデータクラス。
 * <pre>
 * </pre>
 * @extends DateTimeValidator
 */
class DateValidator extends DateTimeValidator {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		this.dateFormatKey = "format.datefield";
	}
}