package sample.page;

import dataforms.controller.QueryForm;
import sample.dao.MaterialMasterTable;
import dataforms.field.base.Field.MatchType;
import sample.field.UnitPriceField;
import sample.field.OrderPointField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterQueryForm() {
		MaterialMasterTable table = new MaterialMasterTable();
		this.addField(table.getMaterialCodeField()).setMatchType(MatchType.FULL);
		this.addField(table.getMaterialNameField()).setMatchType(MatchType.FULL);
		this.addField(table.getMaterialUnitField()).setMatchType(MatchType.FULL);
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("単価(from)");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "To")).setMatchType(MatchType.RANGE_TO).setComment("単価(to)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("発注点(from)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("発注点(to)");
		this.addField(table.getMemoField()).setMatchType(MatchType.FULL);

	}
}
