package test.field;

import dataforms.field.sqltype.DateField;


/**
 * SqlDateFieldフィールドクラス。
 *
 */
public class SqlDateField extends DateField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "Dateフィールド";
	/**
	 * コンストラクタ。
	 */
	public SqlDateField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SqlDateField(final String id) {
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
