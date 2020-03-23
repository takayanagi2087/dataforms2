/**
 * @fileOverview {@link FunctionSelectField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class FunctionSelectField
 * 機能フィールドクラス。
 * <pre>
 * </pre>
 * @extends SingleSelectField
 */
class FunctionSelectField extends SingleSelectField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 機能を変更したタイミングで、パッケージ名を設定します。
	 * </pre>
	 */
	attach() {
		super.attach();
		var thisField = this;
		this.get().change(function() {
			thisField.setPackageName($(this))
		});
	}

	setPackageName(jq) {
		var form = this.getParentForm();
		var funcname = jq.val();
		if (funcname != null && funcname.length > 0) {
			var packageName = funcname.replace(/\//g, ".").substr(1);
			if (this.packageOption.length > 0) {
				packageName +=  "." + this.packageOption;
			}
			var id = jq.attr(this.getIdAttribute());
			logger.log("functionSelectField id=" + id)
			if (this.isHtmlTableElementId(id)) {
				var a = id.split(".");
				form.find("#" + this.selectorEscape(a[0] + "." + this.packageFieldId)).val(packageName);
			} else {
				form.find("#" + this.selectorEscape(this.packageFieldId)).val(packageName);
			}
		}
	}
}


