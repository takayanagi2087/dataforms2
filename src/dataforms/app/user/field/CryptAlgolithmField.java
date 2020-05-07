package dataforms.app.user.field;

import dataforms.field.common.PropertiesSingleSelectField;

/**
 * 暗号化アルゴリズム選択フィールド。
 *
 */
public class CryptAlgolithmField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public CryptAlgolithmField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public CryptAlgolithmField(final String id) {
		super(id, 4, "cryptalgolithm");
	}
}
