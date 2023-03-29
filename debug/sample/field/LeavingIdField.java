package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * LeavingIdFieldフィールドクラス。
 *
 */
public class LeavingIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "出庫ID";
	/**
	 * コンストラクタ。
	 */
	public LeavingIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public LeavingIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
