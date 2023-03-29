package test.page;

import dataforms.app.base.page.BasePage;
import dataforms.dao.Dao;
import test.dao.SqlTypeDao;


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
		return "/test";
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
		Object obj = p; // 作成したオブジェクト
		Response ret = new JsonResponse(JsonResponse.SUCCESS, obj);
		return ret;
	}
*/

}
