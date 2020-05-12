package sample.page;

import dataforms.controller.QueryForm;
import sample.field.SupplierKanaNameField;
import sample.field.SupplierCodeField;
import sample.field.SupplierNameField;
import dataforms.field.base.Field.MatchType;
import java.util.List;
import dataforms.field.base.FieldList;
import sample.dao.SupplierMasterTable;
import java.util.Map;
import sample.field.FaxNoField;
import sample.field.AddressField;
import dataforms.field.common.ZipCodeField;
import sample.field.PhoneNoField;
import dataforms.report.ExportDataFile;

import sample.dao.SupplierMasterDao;


/**
 * 問い合わせフォームクラス。
 */
public class SupplierMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterQueryForm() {
		this.addField(new SupplierCodeField(SupplierMasterTable.Entity.ID_SUPPLIER_CODE)).setMatchType(MatchType.FULL).setComment("仕入先コード");
		this.addField(new SupplierNameField(SupplierMasterTable.Entity.ID_SUPPLIER_NAME)).setMatchType(MatchType.FULL).setComment("仕入先名称");
		this.addField(new SupplierKanaNameField(SupplierMasterTable.Entity.ID_SUPPLIER_KANA_NAME)).setMatchType(MatchType.FULL).setComment("仕入先カナ名称");
		this.addField(new PhoneNoField(SupplierMasterTable.Entity.ID_PHONE_NO)).setMatchType(MatchType.FULL).setComment("電話番号");
		this.addField(new FaxNoField(SupplierMasterTable.Entity.ID_FAX_NO)).setMatchType(MatchType.FULL).setComment("FAX番号");
		this.addField(new ZipCodeField(SupplierMasterTable.Entity.ID_ZIP_CODE)).setMatchType(MatchType.FULL).setComment("郵便番号");
		this.addField(new AddressField(SupplierMasterTable.Entity.ID_ADDRESS)).setMatchType(MatchType.FULL).setComment("住所");

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
		SupplierMasterDao dao = new SupplierMasterDao(this);
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
		SupplierMasterDao dao = new SupplierMasterDao(this);
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
			ret = null;	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
