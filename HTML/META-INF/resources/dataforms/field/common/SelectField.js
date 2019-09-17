/**
 * @fileOverview {@link SelectField}クラスを記述したファイルです。
 */

/**
 * @class SelectField
 * 選択肢フィールドクラス。
 * <pre>
 * 各種選択肢フィールドの基底クラスです。
 * </pre>
 * @extends Field
 */
// SelectField = createSubclass("SelectField", {}, "Field");
class SelectField extends Field {
	/**
	 * HTMLの要素との対応付けを行ないます。
	 * <pre>
	 * setOptionListを呼び出し、選択肢を設定します。
	 * </pre>
	 */
	attach() {
		super.attach();
		this.setOptionList();
	}

	/**
	 * 対応するHTMLの要素を取得する.
	 * @returns {jQuery} 対応するHTMLエレメント。
	 */
	get() {
		var el = super.get();
		if (el.length == 0) {
			el = this.parent.find("[id^='" + this.selectorEscape(this.id + "[") + "']");
		}
		if (el.length == 0) {
			el = this.parent.find("[name='" + this.selectorEscape(this.id) + "']");
		}
		return el;
	}


	/**
	 * SPAN等の表示用タグへ値を設定します。
	 * <pre>
	 * 選択肢の場合は値ではなく名称を設定します。
	 * </pre>
	 * @param {jQuery} comp コンポーネント。
	 * @param {String} value 値。
	 */
	setTextValue(comp, value) {
		if (this.optionList != null) {
			if (value == null) {
				comp.text("");
				return;
			}
			for (var i = 0; i < this.optionList.length; i++) {
				var opt = this.optionList[i];
				var ov = (opt.value == null ? "" : opt.value.toString());
				var v = value.toString();
				if (ov == v) {
					comp.text(opt.name);
					break;
				}
			}
		} else {
			if (value != null) {
				comp.text(value);
			} else {
				comp.text("");
			}
		}
	}

	/**
	 * 選択肢を対応する要素に設定します。
	 * @param {Array} opt 選択肢のリスト。
	 */
	setOptionList(opt) {
		if (opt != null) {
			this.optionList = opt;
		}
		if (this.optionList == null) {
			return;
		}
		var el = this.get();
		if (el.length > 0) {
			if (el.prop("tagName") == "SELECT") {
				var opthtml = "";
				for (var i = 0; i < this.optionList.length; i++) {
					var opt = this.optionList[i];
					opthtml += "<option value='" + opt.value + "'>" + opt.name + "</option>";
				}
				el.html(opthtml);
			} else if (el.prop("tagName") == "INPUT") {
				if (el.attr("type").toLowerCase() == "radio" ) {
					var pl = el.parent();
					pl.html("");
					var opthtml = "";
					for (var i = 0; i < this.optionList.length; i++) {
						var opt = this.optionList[i];
						opthtml +=
							"<input type='radio' id='" + this.id + "[" + i + "]' name='" + this.id + "' value='" + opt.value + "'/>"
								+ "<label for='" + this.id + "[" + i + "]'>" + opt.name + "</label>&nbsp;";
					}
					pl.html(opthtml);
				} else if (el.attr("type").toLowerCase() == "checkbox" ) {
					var pl = el.parent();
					pl.html("");
					var opthtml = "";
					for (var i = 0; i < this.optionList.length; i++) {
						var opt = this.optionList[i];
						opthtml +=
							"<input type='checkbox' id='" + this.id + "[" + i + "]' name='" + this.id + "' value='" + opt.value + "'/>"
								+ "<label for='" + this.id + "[" + i + "]'>" + opt.name + "</label>&nbsp;";
					}
					pl.html(opthtml);
				}
			}
		}
	}
}




