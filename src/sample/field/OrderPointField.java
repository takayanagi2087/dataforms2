package sample.field;

import dataforms.field.sqltype.NumericField;


/**
 * OrderPointFieldフィールドクラス。
 *
 */
public class OrderPointField extends NumericField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "発注点";
	/**
	 * コンストラクタ。
	 */
	public OrderPointField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public OrderPointField(final String id) {
		super(id, 16, 2);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
