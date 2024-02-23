package pagepat.page;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import pagepat.dao.TestTable;
import java.util.List;
import dataforms.field.base.FieldList;
import pagepat.field.Code1Field;
import java.util.Map;
import dataforms.report.ExportDataFile;

import pagepat.dao.Test07Dao;


/**
 * 検索→複数レコード編集用問合せフォームクラス。
 */
public class Test07QueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public Test07QueryForm() {
		this.addField(new Code1Field(TestTable.Entity.ID_CODE1)).setMatchType(MatchType.PART).setComment("コード1");

	}

	@Override
	public void init() throws Exception {
		super.init();
		// フィールドに初期値を設定する場合は以下の様にしてください。
		// this.setFormData("fieldId", "初期値");
	}

	/**
	 * エクスポートデータのフィールドリストを作成します。
	 * @return フィールドリスト。
	 */
	@Override
	protected FieldList getExportDataFieldList(final Map<String, Object> data) throws Exception {
		Test07Dao dao = new Test07Dao(this);
		return dao.getListQuery().getFieldList();
	}

	/**
	 * エクスポートデータファイルのインスタンスを返します。
	 * @return エクスポートデータファイルのインスタンス。
	 */
	@Override
	protected ExportDataFile getExportDataFile() {
		ExportDataFile file = super.getExportDataFile();
		file.setFileName("export.xlsx");
		return file;
	}

	/**
	 * エクスポートするデータを返します。
	 * @param data 条件データ。
	 * @return エクスポートデータ。
	 */
	@Override
	protected List<Map<String, Object>> queryExportData(final Map<String, Object> data) throws Exception {
		Test07Dao dao = new Test07Dao(this);
		return dao.query(data, this.getFieldList());
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
			ret = new JsonResponse(JsonResponse.SUCCESS, data);	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
