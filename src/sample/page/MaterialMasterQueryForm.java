package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.dao.MaterialMasterTable;
import sample.field.MemoField;
import sample.field.UnitPriceField;
import sample.field.MaterialCodeField;
import sample.field.MaterialUnitField;
import sample.field.OrderPointField;
import sample.field.MaterialNameField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterQueryForm() {
		this.addField(new MaterialCodeField(MaterialMasterTable.Entity.ID_MATERIAL_CODE)).setMatchType(MatchType.FULL).setComment("資材コード");
		this.addField(new MaterialNameField(MaterialMasterTable.Entity.ID_MATERIAL_NAME)).setMatchType(MatchType.FULL).setComment("資材名称");
		this.addField(new MaterialUnitField(MaterialMasterTable.Entity.ID_MATERIAL_UNIT)).setMatchType(MatchType.FULL).setComment("資材在庫単位");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("単価(from)");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "To")).setMatchType(MatchType.RANGE_TO).setComment("単価(to)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("発注点(from)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("発注点(to)");
		this.addField(new MemoField(MaterialMasterTable.Entity.ID_MEMO)).setMatchType(MatchType.FULL).setComment("メモ");

	}
}
