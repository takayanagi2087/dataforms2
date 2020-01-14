package dataforms.app.user.field;

import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MailAddressValidator;

/**
 * メールアドレスフィールドクラス。
 *
 */
public class MailAddressField extends VarcharField {
	/**
	 * 項目長。
	 */
	private static final int LENGTH = 64;
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "メールアドレス";

	/**
	 * コンストラクタ。
	 */
	public MailAddressField() {
		super(null, LENGTH);
		this.setComment(COMMENT);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MailAddressField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		this.addValidator(new MailAddressValidator());
/*		Form form = this.getParentForm();
		if (form instanceof UserEditForm) {
			this.addValidator(new RequiredValidator());
		}*/
	}
}
