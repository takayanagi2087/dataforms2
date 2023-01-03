package dataforms.devtool.field;

import dataforms.devtool.validator.SqlValidator;
import dataforms.field.sqltype.ClobField;

/**
 * 更新SQLフィールド。
 *
 */
public class SqlField extends ClobField {
	/**
	 * コンストラクタ。
	 */
	public SqlField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SqlField(final String id) {
		super(id);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new SqlValidator());
	}
}
