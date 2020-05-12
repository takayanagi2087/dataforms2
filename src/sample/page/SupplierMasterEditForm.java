package sample.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.dao.Table;
import sample.dao.SupplierMasterDao;

/**
 * 編集フォームクラス。
 */
public class SupplierMasterEditForm extends EditForm {
	/**
	 * コンストラクタ。
	 */
	public SupplierMasterEditForm() {
		SupplierMasterDao dao = new SupplierMasterDao();
		this.addFields(dao);
	}

	/**
	 * フォームの初期化を行います。
	 * <pre>
	 * DBを使用した初期化処理はここに記述します。
	 * </pre>
	 */
	@Override
	public void init() throws Exception {
		super.init();
		// フィールドに初期値を設定する場合は以下の様にしてください。
		// this.setFormData("fieldId", "初期値");
	}

	/**
	 * 編集対象のデータを取得します。
	 * <pre>
	 * 問い合わせ結果フォームに表示されたデータを選択した際に呼び出されます。
	 * dataには最低編集対象レコードのPKのマップが入ってきます。
	 * </pre>
	 * @param data 取得するデータのPKの値が入ってきます。
	 * @return 編集対象データ。
	 */
	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		SupplierMasterDao dao = new SupplierMasterDao(this);
		return dao.query(data);
	}

	/**
	 * 参照登録対象対象のデータを取得します。
	 * <pre>
	 * queryDataから取得したデータから、PK項目を削除します。
	 * </pre>
	 * @param data 取得するデータのPKの値が入ってきます。
	 * @return 編集対象データ。
	 */
	@Override
	protected Map<String, Object> queryReferData(final Map<String, Object> data) throws Exception {
		Map<String, Object> ret = this.queryData(data);
		SupplierMasterDao dao = new SupplierMasterDao(this);
		removeKeyData(dao, ret);
		return ret;
	}

	/**
	 * ポストされたデータが更新するのか新規追加するのかを判定します。
	 * <pre>
	 * 編集対象データにPKの入力があった場合、更新すべきと判断します。
	 * </pre>
	 * @param data 入力データ。
	 * @return 更新対象データの場合true。
	 */
	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		SupplierMasterDao dao = new SupplierMasterDao(this);
		Table table = dao.getMainTable();
		boolean ret = this.isUpdate(table, data);
		return ret;
	}

	/**
	 * データを新規追加します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		SupplierMasterDao dao = new SupplierMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.insert(data);
	}

	/**
	 * データを更新します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		SupplierMasterDao dao = new SupplierMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.update(data);
	}

	/**
	 * データを削除します。
	 * @param data ポストされたデータ。
	 */
	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		SupplierMasterDao dao = new SupplierMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}

	// フォームの各フィールドの関連チェックを行う場合は、以下のvalidateFormメソッドを実装してください。
	/**
	 * フォームのデータをチェックします。
	 * @param p パラメータ。
	 * @return 判定結果リスト。
	 * @throws Exception 例外。
	 */
/*
	@Override
	protected List<ValidationError> validateForm(final Map<String, Object> data) throws Exception {
		List<ValidationError> list = super.validateForm(data);
		if (list.size() == 0) {
			if ( エラー判定 ) {
				list.add(new ValidationError(HogeTable.Entity.ID_FIELD_ID, MessagesUtil.getMessage(this.getPage(), "error.messagekey")));
			}
		}
		return list;
	}
*/


	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			Map<String, Object> data = this.convertToServerData(p);
			ret = null;	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
