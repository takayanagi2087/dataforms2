package dataforms.devtool.field;
import dataforms.field.common.PropertiesSingleSelectField;

/**
 * リストFormの種類。
 */
public class ListFormSelectField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public ListFormSelectField() {
		super(null, 1, "daoandpagegenerator.listform");
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public ListFormSelectField(final String id) {
		super(id, 1, "daoandpagegenerator.listform");
	}
}

