package sample.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import sample.dao.MaterialMasterTable;
import sample.field.MaterialCodeField;
import sample.field.MaterialNameField;
import sample.field.MaterialUnitField;
import sample.field.MemoField;
import sample.field.OrderPointField;
import sample.field.UnitPriceField;



/**
 * 問い合わせフォームクラス。
 */
public class MaterialMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterQueryForm() {
		this.addField(new MaterialCodeField(MaterialMasterTable.Entity.ID_MATERIAL_CODE)).setMatchType(MatchType.PART).setComment("資材コード");
		this.addField(new MaterialNameField(MaterialMasterTable.Entity.ID_MATERIAL_NAME)).setMatchType(MatchType.PART).setComment("資材名称");
		this.addField(new MaterialUnitField(MaterialMasterTable.Entity.ID_MATERIAL_UNIT)).setMatchType(MatchType.PART).setComment("資材在庫単位");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("単価(from)");
		this.addField(new UnitPriceField(MaterialMasterTable.Entity.ID_UNIT_PRICE + "To")).setMatchType(MatchType.RANGE_TO).setComment("単価(to)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("発注点(from)");
		this.addField(new OrderPointField(MaterialMasterTable.Entity.ID_ORDER_POINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("発注点(to)");
		this.addField(new MemoField(MaterialMasterTable.Entity.ID_MEMO)).setMatchType(MatchType.PART).setComment("メモ");

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
			ret = null;
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
