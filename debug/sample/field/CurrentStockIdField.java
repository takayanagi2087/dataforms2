package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * CurrentStockIdFieldフィールドクラス。
 *
 */
public class CurrentStockIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "在庫ID";
	/**
	 * コンストラクタ。
	 */
	public CurrentStockIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public CurrentStockIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
