package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import dataforms.field.common.SortOrderField;
import sample.dao.MaterialMasterTable;
import sample.dao.MaterialOrderItemTable;
import sample.dao.MaterialOrderTable;
import sample.dao.SupplierMasterTable;
import sample.field.AmountField;
import sample.field.MaterialCodeField;
import sample.field.MaterialNameField;
import sample.field.MemoField;
import sample.field.OrderDateField;
import sample.field.OrderNoField;
import sample.field.SupplierCodeField;
import sample.field.SupplierNameField;
import sample.field.UnitPriceField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialOrderQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderQueryForm() {
		this.addField(new OrderNoField(MaterialOrderTable.Entity.ID_ORDER_NO)).setMatchType(MatchType.PART).setComment("発注番号");
		this.addField(new OrderDateField(MaterialOrderTable.Entity.ID_ORDER_DATE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("発注日(from)");
		this.addField(new OrderDateField(MaterialOrderTable.Entity.ID_ORDER_DATE + "To")).setMatchType(MatchType.RANGE_TO).setComment("発注日(to)");
		this.addField(new SortOrderField(MaterialOrderItemTable.Entity.ID_SORT_ORDER + "From")).setMatchType(MatchType.RANGE_FROM).setComment("ソート順(from)");
		this.addField(new SortOrderField(MaterialOrderItemTable.Entity.ID_SORT_ORDER + "To")).setMatchType(MatchType.RANGE_TO).setComment("ソート順(to)");
		this.addField(new MaterialCodeField(MaterialMasterTable.Entity.ID_MATERIAL_CODE)).setMatchType(MatchType.FULL).setComment("資材コード");
		this.addField(new MaterialNameField(MaterialMasterTable.Entity.ID_MATERIAL_NAME)).setMatchType(MatchType.FULL).setComment("資材名称");
		this.addField(new UnitPriceField(MaterialOrderItemTable.Entity.ID_ORDER_PRICE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("単価(from)");
		this.addField(new UnitPriceField(MaterialOrderItemTable.Entity.ID_ORDER_PRICE + "To")).setMatchType(MatchType.RANGE_TO).setComment("単価(to)");
		this.addField(new AmountField(MaterialOrderItemTable.Entity.ID_AMOUNT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("数量(from)");
		this.addField(new AmountField(MaterialOrderItemTable.Entity.ID_AMOUNT + "To")).setMatchType(MatchType.RANGE_TO).setComment("数量(to)");
		this.addField(new MemoField(MaterialOrderItemTable.Entity.ID_ITEM_MEMO)).setMatchType(MatchType.FULL).setComment("メモ");
		this.addField(new SupplierCodeField(SupplierMasterTable.Entity.ID_SUPPLIER_CODE)).setMatchType(MatchType.FULL).setComment("仕入先コード");
		this.addField(new SupplierNameField(SupplierMasterTable.Entity.ID_SUPPLIER_NAME)).setMatchType(MatchType.FULL).setComment("仕入先名称");

	}
}
