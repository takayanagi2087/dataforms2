package dataforms.devtool.field;

import dataforms.dao.Table;

/**
 * テーブルクラス名フィールドクラス。
 *
 */
public class TableFullClassNameField extends SimpleClassNameField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "テーブルクラス名";
	/**
	 * コンストラクタ。
	 */
	public TableFullClassNameField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public TableFullClassNameField(final String id) {
		super(id);
		this.addBaseClass(Table.class);
		this.setComment(COMMENT);
		this.setAutocomplete(false);
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
}
