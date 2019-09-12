package dataforms.field.common;

import dataforms.field.sqltype.TimestampField;

/**
 * レコード更新日時フィールドクラス。
 * <pre>
 * DBテーブル中のレコードの更新日時のフィールドです。
 * 楽観ロックチェックの時参照します。
 * </pre>
 */
public class UpdateTimestampField extends TimestampField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "更新日時";

	/**
	 * コンストラクタ.
	 */
	public UpdateTimestampField() {
		super(null);
		this.getValidatorList().clear();
		this.setDateFormat("format.updatetimestampfield");
		this.setComment(COMMENT);
		this.setHidden(true);
	}

	/**
	 * コンストラクタ.
	 * @param id フィールドID.
	 */
	public UpdateTimestampField(final String id) {
		super(id);
		this.getValidatorList().clear();
		this.setDateFormat("format.updatetimestampfield");
		this.setComment(COMMENT);
		this.setHidden(true);
	}
}
