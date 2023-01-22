package sample.field;

import dataforms.field.sqltype.DateField;


/**
 * OrderDateFieldフィールドクラス。
 *
 */
public class OrderDateField extends DateField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "発注日";
	/**
	 * コンストラクタ。
	 */
	public OrderDateField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public OrderDateField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
