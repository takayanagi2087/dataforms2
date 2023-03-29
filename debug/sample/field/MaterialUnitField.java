package sample.field;

import dataforms.field.common.EnumOptionSingleSelectField;


/**
 * MaterialUnitFieldフィールドクラス。
 *
 */
public class MaterialUnitField extends EnumOptionSingleSelectField {
	/**
	 * 列挙名称。
	 */
	private static final String ENUM_TYPE = "materialUnit";

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材在庫単位";

	/**
	 * コンストラクタ。
	 */
	public MaterialUnitField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MaterialUnitField(final String id) {
		super(id, ENUM_TYPE);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}
}
