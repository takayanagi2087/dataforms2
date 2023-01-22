package sample.field;

import dataforms.field.sqltype.NumericField;


/**
 * QuantityFieldフィールドクラス。
 *
 */
public class QuantityField extends NumericField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "数量";
	/**
	 * コンストラクタ。
	 */
	public QuantityField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public QuantityField(final String id) {
		super(id, 16, 2);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
