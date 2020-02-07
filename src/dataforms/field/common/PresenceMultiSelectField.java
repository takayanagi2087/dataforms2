package dataforms.field.common;

import dataforms.dao.sqldatatype.SqlVarchar;


/**
 * 有無フィールドクラス。
 */
public class PresenceMultiSelectField extends PropertiesMultiSelectField implements SqlVarchar {
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PresenceMultiSelectField(final String id) {
		super(id, "presence", 1);
	}
}
