package sample.field;

import dataforms.field.common.RecordIdField;


/**
 * SupplierIdFieldフィールドクラス。
 *
 */
public class SupplierIdField extends RecordIdField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "仕入先ID";
	/**
	 * コンストラクタ。
	 */
	public SupplierIdField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SupplierIdField(final String id) {
		super(id);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
