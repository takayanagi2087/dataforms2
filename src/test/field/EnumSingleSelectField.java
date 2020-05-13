package test.field;

import dataforms.field.common.EnumOptionSingleSelectField;


/**
 * EnumSingleSelectFieldフィールドクラス。
 *
 */
public class EnumSingleSelectField extends EnumOptionSingleSelectField {
	/**
	 * 列挙名称。
	 */
	private static final String ENUM_TYPE = "overwriteMode"; // 適切に修正して使用してください。

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "列挙型単一選択";

	/**
	 * コンストラクタ。
	 */
	public EnumSingleSelectField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public EnumSingleSelectField(final String id) {
		super(id, ENUM_TYPE);
		this.setComment(COMMENT);
		this.setBlankOption(true);
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
