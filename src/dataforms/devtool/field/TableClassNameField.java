package dataforms.devtool.field;

import dataforms.dao.Table;
import dataforms.devtool.validator.ClassNameValidator;

/**
 * テーブルクラス名フィールドクラス。
 *
 */
public class TableClassNameField extends SimpleClassNameField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "テーブルクラス名";
	/**
	 * コンストラクタ。
	 */
	public TableClassNameField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public TableClassNameField(final String id) {
		super(id);
		this.addBaseClass(Table.class);
		this.setComment(COMMENT);
		this.setAutocomplete(false);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new ClassNameValidator("Table"));
	}
}
