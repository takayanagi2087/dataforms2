package test.page;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import dataforms.field.base.FieldList;
import dataforms.report.ExportDataFile;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.util.WebClient;
import dataforms.util.WebClient.Convert;
import dataforms.validator.ValidationError;
import test.dao.TestCode1Query;
import test.dao.TestMultiRecDao;
import test.field.Code1Field;


/**
 * 問い合わせフォームクラス。
 */
public class TestMultiRecQueryForm extends QueryForm {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(TestMultiRecQueryForm.class);

	/**
	 * コンストラクタ。
	 */
	public TestMultiRecQueryForm() {
		this.addField(new Code1Field(TestCode1Query.Entity.ID_CODE1)).setMatchType(MatchType.FULL).setComment("コード1");

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
		TestMultiRecDao dao = new TestMultiRecDao(this);
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
		TestMultiRecDao dao = new TestMultiRecDao(this);
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
			ret = new JsonResponse(JsonResponse.SUCCESS, data);
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/


	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response testApi(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			WebClient wc = new WebClient("http://localhost:11080/dataforms2/test/page/TestApi.df", WebClient.METHOD_GET);
			Object r = wc.call(null, /*"err=1", */Convert.TEXT);
			logger.debug(() -> "status=" + wc.getHttpStatus());
			logger.debug(() -> "content-type=" + wc.getResponseContentType());
			logger.debug(() -> "object=" + r.getClass().getName());
			logger.debug(() -> "text=" + r.toString());
			ret = new JsonResponse(JsonResponse.SUCCESS, r);
//			Object r = wc.call(null, Convert.BINARY);
//			logger.debug(() -> "object=" + r.getClass().getName());
//			ret = new BinaryResponse((byte[]) r);
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}

}
