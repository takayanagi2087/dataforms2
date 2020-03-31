package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.dao.MaterialMasterTable;
import sample.field.UnitPriceField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterQueryForm() {
		MaterialMasterTable table = new MaterialMasterTable();
		this.addField(table.getMaterialNameField()).setMatchType(MatchType.FULL);
		this.addField(table.getMaterialUnitField()).setMatchType(MatchType.FULL);
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("単価(from)");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "To")).setMatchType(MatchType.RANGE_TO).setComment("単価(to)");
		this.addField(table.getMemoField()).setMatchType(MatchType.FULL);
	}
}
