package test.field;

import dataforms.field.common.PropertiesSingleSelectField;
import dataforms.validator.MaxLengthValidator;


/**
 * PropSingleSelectFieldフィールドクラス。
 *
 */
public class PropSingleSelectField extends PropertiesSingleSelectField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 8;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "プロパティ単一選択";

	/**
	 * プロパティのキー。
	 */
	private static final String PROPERTY_KEY = "onoroff"; // 適切に修正して使用してください。

	/**
	 * コンストラクタ。
	 */
	public PropSingleSelectField() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PropSingleSelectField(final String id) {
		super(id, LENGTH, PROPERTY_KEY);
		this.setComment(COMMENT);
		this.setBlankOption(true);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

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
