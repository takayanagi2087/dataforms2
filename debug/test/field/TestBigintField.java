package test.field;

import dataforms.field.sqltype.BigintField;


/**
 * TestBigintFieldフィールドクラス。
 *
 */
public class TestBigintField extends BigintField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "8バイト整数";
	/**
	 * コンストラクタ。
	 */
	public TestBigintField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public TestBigintField(final String id) {
		super(id);
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
		Object obj = p;
		Response resp = new JsonResponse(JsonResponse.SUCCESS, obj);
		return resp;
	}
*/

}
