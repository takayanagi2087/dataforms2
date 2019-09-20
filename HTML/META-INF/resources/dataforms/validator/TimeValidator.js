/**
 * @fileOverview {@link TimeValidator}クラスを記述したファイルです。
 */

/**
 * @class TimeValidator
 * 時刻バリデータ。
 * <pre>
 * </pre>
 * @extends DateTimeValidator
 */
class TimeValidator extends DateTimeValidator {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		this.dateFormatKey = "format.timefield";
	}
}