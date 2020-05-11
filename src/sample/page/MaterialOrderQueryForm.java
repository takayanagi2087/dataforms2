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
		this.addField(new OrderNoField(MaterialOrderTable.Entity.ID_ORDER_NO)).setMatchType(MatchType.FULL).setComment("発注番号");
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

	@Override
	public void init() throws Exception {
		super.init();
		// フィールドに初期値を設定する場合は以下の様にしてください。
		// this.setFormData("fieldId", "初期値");
	}

	// フォームの各フィールドの関連チェックを行う場合は、以下のvalidateFormメソッドを実装してください。
	/**
	 * フォームのデータをチェックします。
	 * @param p パラメータ。
	 * @return 判定結果リスト。
	 * @throws Exception 例外。
	 */
/*
	@Override
	protected List<ValidationError> validateForm(final Map<String, Object> data) throws Exception {
		List<ValidationError> list = super.validateForm(data);
		if (list.size() == 0) {
			if ( エラー判定 ) {
				list.add(new ValidationError(HogeTable.Entity.ID_FIELD_ID, MessagesUtil.getMessage(this.getPage(), "error.messagekey")));
			}
		}
		return list;
	}
*/


	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			Map<String, Object> data = this.convertToServerData(p);
			ret = null;	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
