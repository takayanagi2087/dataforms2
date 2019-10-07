package dataforms.app.enumtype.page;

import dataforms.app.enumtype.dao.EnumTable;
import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;



/**
 * 問い合わせフォームクラス。
 */
public class EnumQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public EnumQueryForm() {
		EnumTable table = new EnumTable();
		this.addField(table.getEnumCodeField()).setMatchType(MatchType.PART);
		this.addField(table.getMemoField()).setMatchType(MatchType.PART);
	}
}
