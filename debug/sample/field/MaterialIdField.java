package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * MaterialIdFieldフィールドクラス。
 *
 */
public class MaterialIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材ID";
	/**
	 * コンストラクタ。
	 */
	public MaterialIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MaterialIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
