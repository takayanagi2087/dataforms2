package sample.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;


/**
 * MaterialNameFieldフィールドクラス。
 *
 */
public class MaterialNameField extends VarcharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 128;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材名称";
	/**
	 * コンストラクタ。
	 */
	public MaterialNameField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MaterialNameField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
