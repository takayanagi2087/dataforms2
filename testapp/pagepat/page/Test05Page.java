package pagepat.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import pagepat.dao.Test05Dao;


/**
 * ページクラス。
 */
public class Test05Page extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public Test05Page() {
		this.addForm(new Test05EditForm());

	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/pagepat";
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return Test05Dao.class;
	}


	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 *
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		// TODO:何らかの処理を行い、応答情報を作成します。
		Object obj = p; // 作成したオブジェクト
		Response ret = new JsonResponse(JsonResponse.SUCCESS, obj);
		return ret;
	}
*/

}
