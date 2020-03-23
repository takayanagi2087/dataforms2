/**
 * @fileOverview {@link UserAttributeTypeField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class UserAttributeTypeField
 * ユーザ属性フィールドクラス。
 * <pre>
 * </pre>
 * @extends EnumTypeSingleSelectField
 */
class UserAttributeTypeField extends EnumTypeSingleSelectField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * ユーザ属性を変更されたタイミングで、ユーザ属性値の選択肢を変更します。
	 * </pre>
	 */
	attach() {
		super.attach();
		var thisField = this;
		this.get().change(function() {
			thisField.setUserAttributeValueOption($(this).attr(thisField.getIdAttribute()), $(this).val());
		});
	}

	/**
	 * ユーザ属性値の選択肢を設定します。
	 * @param {String} id ユーザ属性のフィールドID。
	 * @param type ユーザ属性名称。
	 */
	setUserAttributeValueOption(id, type) {
		var vid = id.replace("userAttributeType", "userAttributeValue");
		var val = this.parent.getComponent(vid);
		val.setUserAttributeType(type);
	}

	/**
	 * 値の設定を行ないます。
	 * <pre>
	 * 同時にユーザ属性値の選択肢も更新します。
	 * </pre>
	 * @param {String} v 値。
	 *
	 */
	setValue(v) {
		super.setValue(v);
	}
}


