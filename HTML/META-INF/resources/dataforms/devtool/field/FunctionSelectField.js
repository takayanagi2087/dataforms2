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

	/**
	 * パッケージ名称を設定します。
	 * @param {String} jq パッケージ名を設定するフィールド。
	 */
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

	/**
	 * パッケージ名から機能を設定します。
	 * @param {String} packageName パッケージ名。
	 */
	selectPackage(packageName) {
		for (let i = 0; i < this.optionList.length; i++) {
			let path = this.optionList[i].value;
			let pkg = path.substring(1).replace("/", ".");
			if (packageName != null && packageName.length > 0) {
				if (packageName.indexOf(pkg) == 0) {
					this.get().val(path);
				}
			}
		}
	}
}


