package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * OrderIdFieldフィールドクラス。
 *
 */
public class OrderIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "発注ID";
	/**
	 * コンストラクタ。
	 */
	public OrderIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public OrderIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
