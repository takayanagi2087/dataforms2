package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * IncomincIdFieldフィールドクラス。
 *
 */
public class IncomincIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材入庫ID";
	/**
	 * コンストラクタ。
	 */
	public IncomincIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public IncomincIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
