package sample.field;

import dataforms.field.sqltype.DateField;


/**
 * LimitDateFieldフィールドクラス。
 *
 */
public class LimitDateField extends DateField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "使用期限";
	/**
	 * コンストラクタ。
	 */
	public LimitDateField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public LimitDateField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
