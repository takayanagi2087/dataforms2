package test.field;

import dataforms.field.common.BlobStoreAudioField;


/**
 * BlobAudioFieldフィールドクラス。
 *
 */
public class BlobAudioField extends BlobStoreAudioField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "BLOB音声";
	/**
	 * コンストラクタ。
	 */
	public BlobAudioField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public BlobAudioField(final String id) {
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
