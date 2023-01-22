package sample.field;

import dataforms.field.sqltype.NumericField;


/**
 * UnitPriceFieldフィールドクラス。
 *
 */
public class UnitPriceField extends NumericField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "単価";
	/**
	 * コンストラクタ。
	 */
	public UnitPriceField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public UnitPriceField(final String id) {
		super(id, 16, 2);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
