package test.field;

import dataforms.field.common.PropertiesMultiSelectField;


/**
 * PropMultiSelectFieldフィールドクラス。
 *
 */
public class PropMultiSelectField extends PropertiesMultiSelectField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "プロパティ複数選択";

	/**
	 * プロパティのキー。
	 */
	private static final String PROPERTY_KEY = "onoroff"; // 適切に修正して使用してください。

	/**
	 * コンストラクタ。
	 */
	public PropMultiSelectField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PropMultiSelectField(final String id) {
		super(id, PROPERTY_KEY);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();

	}

	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p 入力パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Object obj = p; // TODO:必要な処理を行い、結果オブジェクトを作成してください。
		Response resp = new JsonResponse(JsonResponse.SUCCESS, obj);
		return resp;
	}
*/

}
