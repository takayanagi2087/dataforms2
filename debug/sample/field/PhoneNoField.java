package sample.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;


/**
 * PhoneNoFieldフィールドクラス。
 *
 */
public class PhoneNoField extends VarcharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 16;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "電話番号";
	/**
	 * コンストラクタ。
	 */
	public PhoneNoField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PhoneNoField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
