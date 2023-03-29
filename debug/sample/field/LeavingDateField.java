package sample.field;

import dataforms.field.sqltype.DateField;


/**
 * LeavingDateFieldフィールドクラス。
 *
 */
public class LeavingDateField extends DateField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "出庫日";
	/**
	 * コンストラクタ。
	 */
	public LeavingDateField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public LeavingDateField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
