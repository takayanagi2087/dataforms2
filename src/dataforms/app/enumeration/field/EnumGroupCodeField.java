package dataforms.app.enumeration.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;

/**
 * 列挙型グループコードクラス。
 *
 */
public class EnumGroupCodeField extends VarcharField {

	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 16;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "列挙型グループコード.";

	/**
	 * コンストラクタ。
	 */
	public EnumGroupCodeField() {
		super(null, LENGTH);
		this.setComment(COMMENT);
//		this.addValidator(new RequiredValidator());
		this.addValidator(new MaxLengthValidator(LENGTH));
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドのID。
	 */
	public EnumGroupCodeField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
//		this.addValidator(new RequiredValidator());
		this.addValidator(new MaxLengthValidator(LENGTH));
	}
}