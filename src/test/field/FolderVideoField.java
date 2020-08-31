package test.field;

import dataforms.field.common.FolderStoreVideoField;


/**
 * FolderVideoFieldフィールドクラス。
 *
 */
public class FolderVideoField extends FolderStoreVideoField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "FOLDER動画";
	/**
	 * コンストラクタ。
	 */
	public FolderVideoField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public FolderVideoField(final String id) {
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
