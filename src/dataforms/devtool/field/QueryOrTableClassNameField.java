package dataforms.devtool.field;

import dataforms.dao.Table;

/**
 * テーブルまたは問合せクラス名フィールド。
 *
 */
public class QueryOrTableClassNameField extends QueryClassNameField {

	/**
	 * コンストラクタ。
	 */
	public QueryOrTableClassNameField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public QueryOrTableClassNameField(final String id) {
		super(id);
		this.addBaseClass(Table.class);
	}

}
