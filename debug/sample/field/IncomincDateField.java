package sample.field;

import dataforms.field.sqltype.DateField;


/**
 * IncomincDateFieldフィールドクラス。
 *
 */
public class IncomincDateField extends DateField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "入庫日";
	/**
	 * コンストラクタ。
	 */
	public IncomincDateField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public IncomincDateField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
