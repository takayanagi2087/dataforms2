/**
 * @fileOverview {@link ClassNameValidator}クラスを記述したファイルです。
 */

/**
 * @class ClassNameValidator
 *
 * @extends RegexpValidator
 */

class ClassNameValidator extends RegexpValidator {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
	}

	/**
	 * エラーメッセージを取得します。
	 * @returns {String} エラーメッセージ。
	 */
	getMessage(dspname) {
		return MessagesUtil.getMessage(this.messageKey, dspname, this.classType);
	}
}




