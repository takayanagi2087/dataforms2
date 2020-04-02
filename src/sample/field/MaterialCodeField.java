package sample.field;

import dataforms.field.sqltype.CharField;
import dataforms.validator.MaxLengthValidator;


/**
 * MaterialCodeFieldフィールドクラス。
 *
 */
public class MaterialCodeField extends CharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 6;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材コード";
	/**
	 * コンストラクタ。
	 */
	public MaterialCodeField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MaterialCodeField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
