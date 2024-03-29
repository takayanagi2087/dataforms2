package fieldtest.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import fieldtest.dao.SqlTypeDao;


/**
 * ページクラス。
 */
public class SqlTypePage extends BasePage {
	/**
	 * コンストラクタ。
	 */
	public SqlTypePage() {
		this.addForm(new SqlTypeQueryForm());
		this.addForm(new SqlTypeQueryResultForm());
		this.addForm(new SqlTypeEditForm());

	}

	/**
	 * Pageが配置されるパスを返します。
	 *
	 * @return Pageが配置されるパス。
	 */
	public String getFunctionPath() {
		return "/fieldtest";
	}

	/**
	 * テーブルを操作するためのDaoクラスを取得します。
	 * <pre>
	 * ページjavaクラス作成用のメソッドです。
	 * </pre>
	 * @return Daoクラス。
	 */
	public Class<? extends Dao> getDaoClass() {
		return SqlTypeDao.class;
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
