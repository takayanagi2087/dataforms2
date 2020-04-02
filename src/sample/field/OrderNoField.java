package sample.field;

import dataforms.field.sqltype.CharField;
import dataforms.validator.MaxLengthValidator;


/**
 * OrderNoFieldフィールドクラス。
 *
 */
public class OrderNoField extends CharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 10;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "発注番号";
	/**
	 * コンストラクタ。
	 */
	public OrderNoField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public OrderNoField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}
}
