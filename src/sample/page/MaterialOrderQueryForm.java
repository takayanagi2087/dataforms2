package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.dao.MaterialOrderDao;
import sample.dao.MaterialOrderTable;
import sample.field.OrderDateField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialOrderQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderQueryForm() {
		MaterialOrderDao dao = new MaterialOrderDao();
		MaterialOrderTable table = dao.getMainTable();
		this.addField(table.getOrderNoField()).setMatchType(MatchType.PART);
		this.addField(new OrderDateField(MaterialOrderTable.Entity.ID_ORDER_DATE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("発注日(from)");
		this.addField(new OrderDateField(MaterialOrderTable.Entity.ID_ORDER_DATE + "To")).setMatchType(MatchType.RANGE_TO).setComment("発注日(to)");
		this.addField(table.getMemoField()).setMatchType(MatchType.PART);
	}
}
