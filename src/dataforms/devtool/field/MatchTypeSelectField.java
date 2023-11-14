package dataforms.devtool.field;

import dataforms.field.common.PropertiesSingleSelectField;

/**
 * マッチタイプ選択フィールドクラス。
 */
public class MatchTypeSelectField extends PropertiesSingleSelectField {

	/**
	 * フィールド長。
	 */
	private static int LENGTH = 16;

	/**
	 * PROPERTIESファイルのKEY。
	 */
	private static String KEY = "matchtype";

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MatchTypeSelectField(final String id) {
		super(id, LENGTH, KEY);
	}
}
