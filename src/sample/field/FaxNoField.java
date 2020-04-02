package sample.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;


/**
 * FaxNoFieldフィールドクラス。
 *
 */
public class FaxNoField extends VarcharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 16;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "FAX番号";
	/**
	 * コンストラクタ。
	 */
	public FaxNoField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public FaxNoField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
