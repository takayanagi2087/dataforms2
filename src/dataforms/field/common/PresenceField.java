package dataforms.field.common;

import dataforms.dao.sqldatatype.SqlVarchar;


/**
 * 有無フィールドクラス。
 */
public class PresenceField extends PropertiesSingleSelectField implements SqlVarchar {
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PresenceField(final String id) {
		super(id, 1, "presence");
	}
}
