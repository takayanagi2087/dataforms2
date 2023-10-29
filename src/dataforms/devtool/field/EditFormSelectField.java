package dataforms.devtool.field;
import dataforms.field.common.PropertiesSingleSelectField;

/**
 * 編集Formの種類。
 */
public class EditFormSelectField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public EditFormSelectField() {
		super(null, 1, "daoandpagegenerator.editform");
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public EditFormSelectField(final String id) {
		super(id, 1, "daoandpagegenerator.editform");
	}
}

