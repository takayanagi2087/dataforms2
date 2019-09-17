/**
 * @fileOverview {@link RegexpValidator}クラスを記述したファイルです。
 */

/**
 * @class RegexpValidator
 * 正規表現パターンバリデータ。
 * <pre>
 * </pre>
 * @extends FieldValidator
 */
//RegexpValidator = createSubclass("RegexpValidator", {}, "FieldValidator");
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
		var regex = new RegExp(this.pattern);
	    if (regex.test(v)) {
	    	return true;
	    }
	    return false;
	}
}

