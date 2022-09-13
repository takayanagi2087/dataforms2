/**
 * @fileOverview {@link RegexpValidator}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class RegexpValidator
 * 正規表現パターンバリデータ。
 * <pre>
 * </pre>
 * @extends FieldValidator
 */
class RegexpValidator extends FieldValidator {
	/**
	 * バリデーションを行ないます。
	 * @param {String} v 値。
	 * @returns {Boolean} バリデーション結果。
	 */
	validate(v) {
		if (this.isBlank(v)) {
			return true;
		}
		if (this.multiline) {
			let regex = new RegExp(this.pattern, "m");
			if (regex.test(v)) {
				return true;
			}
		} else {
			let regex = new RegExp(this.pattern);
			if (regex.test(v)) {
				return true;
			}
		}
	    return false;
	}
}

