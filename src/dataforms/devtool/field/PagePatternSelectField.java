package dataforms.devtool.field;

import dataforms.field.common.PropertiesSingleSelectField;

/**
 * ページパターン選択フィールド。
 */
public class PagePatternSelectField extends PropertiesSingleSelectField {

	/**
	 * プロパティのキー。
	 */
	private static final String KEY = "pagepattern";

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PagePatternSelectField(final String id) {
		super(id, 10, KEY);
	}
}
