/**
 * @fileOverview {@link NumberField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class NumberField
 * 数値フィールドクラス。
 * <pre>
 * 各種数値フィールドの基底クラスです。
 * </pre>
 * @extends Field
 */
class NumberField extends Field {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * focus, blurイベント処理を登録し、カンマの付け外しを行います。
	 * </pre>
	 */
	attach() {
		super.attach();
		var thisField = this;
		var el = this.get();
		// 右寄せに設定.
		el.css("text-align", "right");
		// focusイベント処理を登録
		el.focus(function() {
			if (thisField.commaFormat) {
				var v = el.val();
				v = thisField.delComma(v);
				el.val(v);
			}
		});
		// blurイベント処理を登録.
		el.blur(function() {
			var v = el.val();
			var v = StringUtil.fullToHalf(v);
			v = thisField.adjustScale(v);
			el.val(v);
			if (thisField.commaFormat) {
				var v = el.val();
				v = thisField.addComma(v);
				el.val(v);
			}
		});
		this.backupStyle();
	}
	/**
	 * 3桁ごとにカンマを追加するします。
	 * @param {String} v 数値文字列。
	 * @returns {String} カンマを追加した文字列。
	 */
	addComma(v) {
		return NumberUtil.addComma(v);
	}

	/**
	 * カンマを削除します。
	 * @param {String} v カンマを含む文字列。
	 * @returns {String} カンマを削除した文字列。
	 */
	delComma(v) {
		return NumberUtil.delComma(v);
	}

	/**
	 * 数値フィールドの初期化を行います。
	 *
	 */
	init() {
		super.init(this);
	}


	/**
	 * 小数点以下の桁数を調節します。
	 * @param {String} v 値。
	 * @return {String} 小数点以下の桁数を調整した文字列。
	 */
	adjustScale(v) {
		// 数値に変換できなければ、そのままの文字列.
		if (v == null || v == "" || isNaN(Number(v))) {
			return v;
		}
		var value = v.toString();
		if (this.scale > 0) {
			if (value.indexOf(".") < 0) {
				value += ".";
			}
			for (var i = 0; i < this.scale; i++) {
				value += "0";
			}
			var pp = value.indexOf(".");
			if (pp >= 0) {
				value = value.substring(0, pp + this.scale + 1);
			}
		} else {
			var pp = value.indexOf(".");
			if (pp >= 0) {
				value = value.substring(0, pp);
			}
		}
		return value;
	}

	/**
	 * 値を設定します。
	 * @param {String} value 設定値。
	 */
	setValue(value) {
		var v = value;
		v = this.adjustScale(v);
		if (this.commaFormat) {
			v = this.addComma(v);
		}
		super.setValue(v);
	}

	/**
	 * 値を取得します。
	 * @return {Number} 値。
	 */
	getValue() {
		var ret = super.getValue();
		var r = parseFloat(this.delComma(ret));
		if (isNaN(r)) {
			return null;
		} else {
			return r;
		}
	}

	/**
	 * マップ中の対応フィールドを比較します。
	 * @param a {Object} 比較対象のマップ1。
	 * @param b {Object} 比較対象のマップ2。
	 * @returns {Number} 比較結果。
	 */
	comp(a, b) {
		var ret = 0;
		if (parseFloat(a[this.id].toString()) < parseFloat(b[this.id].toString())) {
			ret = -1;
		} else if (parseFloat(a[this.id].toString()) > parseFloat(b[this.id].toString())) {
			ret = 1;
		}
		return ret;
	}

}







