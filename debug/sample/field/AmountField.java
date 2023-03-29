package sample.field;

import dataforms.field.sqltype.NumericField;


/**
 * AmountFieldフィールドクラス。
 *
 */
public class AmountField extends NumericField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "数量";
	/**
	 * コンストラクタ。
	 */
	public AmountField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public AmountField(final String id) {
		super(id, 16, 2);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
