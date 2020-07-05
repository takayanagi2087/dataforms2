package test.field;

import dataforms.field.sqltype.NumericField;


/**
 * TestNumericFieldフィールドクラス。
 *
 */
public class TestNumericField extends NumericField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "10進数";
	/**
	 * コンストラクタ。
	 */
	public TestNumericField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public TestNumericField(final String id) {
		super(id, 10, 2);
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
