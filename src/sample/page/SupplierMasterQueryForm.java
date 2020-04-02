package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.dao.SupplierMasterTable;



/**
 * 問い合わせフォームクラス。
 */
public class SupplierMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterQueryForm() {
		SupplierMasterTable table = new SupplierMasterTable();
		this.addField(table.getSupplierCodeField()).setMatchType(MatchType.PART);
		this.addField(table.getSupplierNameField()).setMatchType(MatchType.PART);
		this.addField(table.getSupplierKanaNameField()).setMatchType(MatchType.PART);
	}
}
