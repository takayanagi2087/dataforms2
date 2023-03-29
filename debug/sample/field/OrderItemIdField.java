package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * OrderItemIdFieldフィールドクラス。
 *
 */
public class OrderItemIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "発注明細ID";
	/**
	 * コンストラクタ。
	 */
	public OrderItemIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public OrderItemIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
