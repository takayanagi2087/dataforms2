/**
 * @fileOverview {@link FileField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class FileField
 * ファイルフィールドクラス。
 * <pre>
 * 各種ファイルフィールドの基底クラスです。
 * </pre>
 * @extends Field
 */
class FileField extends Field {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 削除チェックボックス、ダウンロードリンクなどの設定を行います。
	 * </pre>
	 */
	attach() {
		var comp = this.get();
		this.addElements(comp);
		super.attach();
		var thisComp = this;
		var selid = this.id + "_sel"; // 選択ボタンID.
		var selfileid = this.id + "_selfile"; // 選択ボタンID.
		var linkid = this.id + "_link"; // ダウンロードリンク.
		var fnid = this.id + "_fn"; // ファイル名のリンク.
		var delid = this.id + "_del"; // ファイル削除のチェックボックス.
		var ckid = this.id + "_ck"; // ファイル削除のチェックボックス.
		var selfile = this.parent.get(selfileid);
		var fnlink = this.parent.get(linkid);
		var fnhidden = this.parent.find("[name='" + this.selectorEscape(fnid) + "']");
		this.parent.get(selid).click(function() {
			comp.click();
		});
		this.parent.get(delid).click(function() {
			thisComp.parent.get(ckid).click();
			$(this).hide();
		});
		this.parent.get(ckid).click(function() {
			thisComp.delFile($(this));
		});
		comp.change(function() {
			selfile.text($(this).val());
			fnlink.html(fnlink.attr("data-value"));
			fnhidden.val(fnlink.attr("data-value"));
			thisComp.id = $(this).attr(thisComp.getIdAttribute());
			thisComp.showDelCheckbox();
		});
		if (this.readonly) {
			this.lock(true);
		} else {
			this.lock(false);
		}
		comp.hide();
	}

	/**
	 * 削除チェックボックスの処理を行います。
	 */
	delFile(ck) {
		var comp = this.get();
		var linkid = this.id + "_link"; // ダウンロードリンク.
		var selfileid = this.id + "_selfile"; // 選択ボタンID.
		var fnid = this.id + "_fn"; // ファイル名のリンク.
		var selfile = this.parent.get(selfileid);
		var fnlink = this.parent.get(linkid);
		var fnhidden = this.parent.find("[name='" + this.selectorEscape(fnid) + "']");
		//logger.log("checked=" + ck.prop("checked"));
		if (ck.prop("checked")) {
			selfile.html("");
			fnhidden.val("");
			fnlink.html("");
			fnlink.attr("data-value", "");
			fnlink.attr("data-size", "");
			fnlink.attr("data-dlparam", "");
			comp.val("");
		} else {
			fnlink.html(fnlink.attr("data-value"));
			fnhidden.val(fnlink.attr("data-value"));
		}
	}


	/**
	 * 削除チェックボックスを表示します。
	 */
	showDelCheckbox() {
		var delid = this.id + "_del";
		this.parent.get(delid).show();
		var ckid = this.id + "_ck";
		var delcheck = this.parent.get(ckid);
		delcheck.prop("checked", false);
		delcheck.hide();
	}

	/**
	 * 削除チェックボックスを隠します。
	 */
	hideDelCheckbox() {
		var delid = this.id + "_del";
		this.parent.get(delid).hide();
		var ckid = this.id + "_ck";
		var delcheck = this.parent.get(ckid);
		delcheck.hide();
	}

	/**
	 * ファイルフィールドに付随する各種コンポーネントを配置します。
	 * @param comp ファイルフィールド。
	 */
	addElements(comp) {
		var htmlstr = this.additionalHtmlText;
		var html = htmlstr.replace(/\$\{fieldId\}/g, this.id);
		var tag = comp.prop("tagName");
		var type = comp.prop("type");
		if ("INPUT" == tag && type == "file") {
			comp.after(html);
		} else if (tag == "DIV") {
			comp.html(html);
		}
	}

	/**
	 * 値を設定します。
	 *
	 * @param {Object} value 値。
	 */
	setValue(value) {
		var comp = this.get();
		var tag = comp.prop("tagName");
		var type = comp.prop("type");
		var linkid = this.id + "_link";
		var selfileid = this.id + "_selfile";
		var fnid = this.id + "_fn";
		var ckid = this.id + "_ck";

		// 選択ファイル名をリセット
		var selfile = this.parent.get(selfileid);
		selfile.html("");
		// 削除フラグのリセット
		var delcheck = this.parent.get(ckid);
		delcheck.prop("checked", false);
		if (value != null) {
			var form = this.getParentForm();
			var url = location.pathname + "?dfMethod=" + encodeURIComponent(this.getUniqId()) + ".download"  + "&" + value.downloadParameter;
			if (currentPage.csrfToken != null) {
				url += "&csrfToken=" + currentPage.csrfToken;
			}
			var fnlink = this.parent.get(linkid);
			fnlink.attr("href", url);
			var fnhidden = this.parent.find("[name='" + this.selectorEscape(fnid) + "']");
			fnlink.html(value.fileName);
			fnlink.attr("data-value", value.fileName);
			fnlink.attr("data-size", value.size);
			fnlink.attr("data-dlparam", value.downloadParameter);
			fnhidden.val(value.fileName);
			if (this.readonly) {
				this.hideDelCheckbox();
			} else {
				var tag = comp.prop("tagName");
				var type = comp.prop("type");
				if ("INPUT" == tag && type == "file") {
					this.showDelCheckbox();
				}
			}
			this.hideDelCheckbox();
			delcheck.attr("checked", false);
		} else {
			var fnlink = this.parent.get(linkid);
			fnlink.attr("href", "");
			var fnhidden = this.parent.find("[name='" + this.selectorEscape(fnid) + "']");
			fnlink.html("");
			fnlink.attr("data-value", "");
			fnlink.attr("data-size", "");
			fnlink.attr("data-dlparam", "");
			fnhidden.val("");
			this.hideDelCheckbox();
			delcheck.attr("checked", false);
		}
		if ("INPUT" == tag) {
			comp.val("");
		}
	}

	/**
	 * 値を取得します。
	 * @return {String} 値。
	 */
	getValue() {
		var ret = this.get().val();
		if (ret.length == 0) {
			var fnid = this.id + "_link";
			ret = this.parent.get(fnid).text();
		}
		return ret;
	}

	/**
	 * フィールドの検証を行ないます。
	 * <pre>
	 * 各フィールドのバリデータを呼び出します。
	 * 追加のチェックが必要な場合、このメソッドをオーバーライドします。
	 * </pre>
	 * @returns {ValidationError} 検証結果。問題が発生しなければnullを返します。
	 */
	validate() {
		var val = this.getValue();
		this.value = val;
		if (this.validators != null) {
			for (var i = 0; i < this.validators.length; i++) {
				var v = this.validators[i];
				if (v.validate(val) == false) {
					var msg = v.getMessage(this.label);
					return new ValidationError(this.id, msg);
				}
			}
		}
		return null;
	}
}





