package dataforms.devtool.field;

import dataforms.field.common.PropertiesSingleSelectField;

/**
 * マッチタイプ選択フィールドクラス。
 */
public class FieldDisplaySelectField extends PropertiesSingleSelectField {

	/**
	 * フィールド長。
	 */
	private static int LENGTH = 16;

	/**
	 * PROPERTIESファイルのKEY。
	 */
	private static String KEY = "fielddisplay";

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public FieldDisplaySelectField(final String id) {
		super(id, LENGTH, KEY);
	}
}
