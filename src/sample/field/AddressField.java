package sample.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;


/**
 * AddressFieldフィールドクラス。
 *
 */
public class AddressField extends VarcharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 1024;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "住所";
	/**
	 * コンストラクタ。
	 */
	public AddressField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public AddressField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
