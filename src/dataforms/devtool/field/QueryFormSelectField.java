package dataforms.devtool.field;
import dataforms.field.common.PropertiesSingleSelectField;

/**
 * 問い合わせ条件Formの種類。
 */
public class QueryFormSelectField extends PropertiesSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public QueryFormSelectField() {
		super(null, 1, "daoandpagegenerator.queryform");
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public QueryFormSelectField(final String id) {
		super(id, 1, "daoandpagegenerator.queryform");
	}
}

