package dataforms.devtool.field;

import dataforms.dao.Table;
import dataforms.devtool.validator.ClassNameValidator;

/**
 * テーブルクラス名フィールドクラス。
 *
 */
public class DaoClassNameField extends SimpleClassNameField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "DAOクラス名";
	/**
	 * コンストラクタ。
	 */
	public DaoClassNameField() {
		this.setBaseClass(Table.class);
		this.setComment(COMMENT);
		this.setAutocomplete(false);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public DaoClassNameField(final String id) {
		super(id);
		this.setBaseClass(Table.class);
		this.setComment(COMMENT);
		this.setAutocomplete(false);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new ClassNameValidator("Dao"));
	}
}
