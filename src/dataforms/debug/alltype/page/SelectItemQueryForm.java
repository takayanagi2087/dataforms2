package dataforms.debug.alltype.page;

import dataforms.controller.QueryForm;
import dataforms.debug.alltype.dao.SingleSelectTable;
import dataforms.field.base.Field.MatchType;
import dataforms.field.common.PropertiesMultiSelectField;
import dataforms.field.common.PropertiesSingleSelectField;



/**
 * 問い合わせフォームクラス。
 */
public class SelectItemQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SelectItemQueryForm() {
		SingleSelectTable table = new SingleSelectTable();
		this.addField(table.getCharItemField()).setMatchType(MatchType.FULL);
		this.addField(table.getVarcharItemField()).setMatchType(MatchType.FULL);
		this.addField(table.getSmallintItemField()).setMatchType(MatchType.FULL);
		this.addField(table.getIntegerIetmField()).setMatchType(MatchType.FULL);
		this.addField(table.getBigintItemField()).setMatchType(MatchType.FULL);
		this.addField(table.getPresenceItemField()).setMatchType(MatchType.FULL);
		this.addField(new PropertiesSingleSelectField("psel", "presence", 1));
		this.addField(new PropertiesMultiSelectField("pmsel", "presence", 1));
	}
}
