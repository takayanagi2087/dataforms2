package sample.field;

import java.util.List;
import java.util.Map;

import dataforms.annotation.WebMethod;
import dataforms.dao.SingleTableQuery;
import dataforms.field.sqltype.CharField;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.validator.MaxLengthValidator;
import sample.dao.SupplierMasterTable;


/**
 * SupplierCodeFieldフィールドクラス。
 *
 */
public class SupplierCodeField extends CharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 6;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "仕入先コード";
	/**
	 * コンストラクタ。
	 */
	public SupplierCodeField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SupplierCodeField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}

	/**
	 * 関連フィールドリスト。
	 */
	private static final String[] IDLIST = {
		// このフィールドのIDを指定します
		SupplierMasterTable.Entity.ID_SUPPLIER_CODE
		// 確定時にフォームに設定するフィールドIDを複数しています。
		, SupplierMasterTable.Entity.ID_SUPPLIER_NAME
		, SupplierMasterTable.Entity.ID_SUPPLIER_ID
	};
	/**
	 * Autocomplete用の一覧を取得するメソッドを実装します。
	 * @param data パラメータ。
	 * @return 関連データのマップ。
	 * @throws Exception 例外。
	 */
	@Override
	protected List<Map<String, Object>> queryAutocompleteSourceList(final Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new SupplierMasterTable()); // 一覧を取得する問合せを作成。
		List<Map<String, Object>> list = this.queryAutocompleteSourceList(
			data					// フォームのデータ
			, query					// 問合せ
			, (Map<String, Object> map, String ... ids) -> {
				return (String) map.get(ids[0]) + ":" + (String) map.get(ids[1]);
			}						// ラベルの構築処理を指定します。
			, IDLIST
		);
		return list;
	}

	/**
	 * 関連データを取得します。
	 * @param data パラメータ。
	 * @return 関連データのマップ。
	 * @throws Exception 例外。
	 */
	@Override
	protected Map<String, Object> queryRelationData(final Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new SupplierMasterTable()); // 関連データを取得する問合せを作成。
		Map<String, Object> ret = this.queryRelationData(
			data					// フォームのデータ
			, query					// 問合せ
			, IDLIST
		);
		return ret;
	}

	/**
	 * Webメソッドのサンプル。
	 * @param p 入力パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Object obj = p; // TODO:必要な処理を行い、結果オブジェクトを作成してください。
		Response resp = new JsonResponse(JsonResponse.SUCCESS, obj);
		return resp;
	}

}
