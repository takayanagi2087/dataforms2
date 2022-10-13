package dataforms.devtool.field;

import dataforms.devtool.validator.UpdateSqlValidator;
import dataforms.field.sqltype.ClobField;

/**
 * 更新SQLフィールド。
 *
 */
public class UpdateSqlField extends ClobField {
	/**
	 * コンストラクタ。
	 */
	public UpdateSqlField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public UpdateSqlField(final String id) {
		super(id);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new UpdateSqlValidator());
	}
}
