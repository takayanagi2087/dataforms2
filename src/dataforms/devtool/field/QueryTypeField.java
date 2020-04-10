package dataforms.devtool.field;

import dataforms.field.common.PropertiesSingleSelectField;

/**
 * フィールド選択フィールド。
 *
 */
public class QueryTypeField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public QueryTypeField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public QueryTypeField(final String id) {
		super(id, "querytype", 1);
		this.setComment("問合せパターン");
		this.setHtmlFieldType(HtmlFieldType.RADIO);
	}

}
