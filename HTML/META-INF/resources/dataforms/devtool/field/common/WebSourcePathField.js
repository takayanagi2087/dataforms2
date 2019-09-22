/**
 * @fileOverview {@link WebSourcePathField}クラスを記述したファイルです。
 */

/**
 * @class WebSourcePathField
 *
 * @extends VarcharField
 */
class WebSourcePathField extends VarcharField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		this.get().attr("placeholder", MessagesUtil.getMessage("message.setwebsourcepath"))
	}
}



