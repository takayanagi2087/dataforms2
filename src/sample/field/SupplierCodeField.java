package sample.field;

import dataforms.field.sqltype.CharField;
import dataforms.validator.MaxLengthValidator;


/**
 * SupplierCodeFieldフィールドクラス。
 *
 */
public class SupplierCodeField extends CharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 6;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "仕入先コード";
	/**
	 * コンストラクタ。
	 */
	public SupplierCodeField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SupplierCodeField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
