package dataforms.devtool.field;
import dataforms.field.common.PropertiesSingleSelectField;

/**
 * 編集タイプFormの種類。
 */
public class EditTypeSelectField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public EditTypeSelectField() {
		super(null, 1, "daoandpagegenerator.edittype");
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public EditTypeSelectField(final String id) {
		super(id, 1, "daoandpagegenerator.edittype");
	}
}

