package dataforms.debug.alltype.field;

import dataforms.field.common.FolderStoreImageField;


/**
 * StaticFolderImageFileFieldフィールドクラス。
 *
 */
public class StaticFolderImageFileField extends FolderStoreImageField {

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "";
	/**
	 * コンストラクタ。
	 */
	public StaticFolderImageFileField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public StaticFolderImageFileField(final String id) {
		super(id);
		this.setComment(COMMENT);
		this.setBaseFolder("C:/home/admin/PLADB.data/testdata02.zip/Image");
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
