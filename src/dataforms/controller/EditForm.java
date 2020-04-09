package dataforms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataforms.annotation.WebMethod;
import dataforms.dao.Table;
import dataforms.exception.ApplicationException;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.response.JsonResponse;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;
import dataforms.validator.ValidationError;


/**
 * 1レコード編集フォームクラス。
 * <pre>
 * テーブルの1レコードを編集するためのFormクラスです。
 * </pre>
 *
 */
public abstract class EditForm extends Form {
	/**
	 * Logger.
	 */
//	private static Logger logger = Logger.getLogger(EditForm.class.getName());


	/**
	 * PKフィールドリスト。
	 */
	private List<String> pkFieldIdList = null;



	/**
	 * コンストラクタ。
	 */
	public EditForm() {
		this(DataForms.ID_EDIT_FORM);
	}

	/**
	 * コンストラクタ。
	 * @param id フォームID。
	 */
	public EditForm(final String id) {
		super(id);
		this.addField(new TextField("saveMode")).setHidden(true);

	}

	/**
	 * 主キーフィールドリストを取得します。
	 * @return 主キーフィールドリスト。
	 */
	public List<String> getPkFieldIdList() {
		return pkFieldIdList;
	}


	/**
	 * 主キーフィールドリストを設定します。
	 * @param pkFieldList 主キーフィールドリスト。
	 */
	public void setPkFieldIdList(final FieldList pkFieldList) {
		this.pkFieldIdList = new ArrayList<String>();
		for (Field<?> f: pkFieldList) {
			this.pkFieldIdList.add(f.getId());
		}
	}


	/**
	 * 主キーフィールドリストを設定します。
	 * @param list 主キーIDリスト。
	 */
	public void setPkFieldIdList(final List<String> list) {
		this.pkFieldIdList = list;
	}

	@Override
	public Map<String, Object> getProperties() throws Exception {
		Map<String, Object> ret = super.getProperties();
		if (this.pkFieldIdList != null) {
			ret.put("pkFieldIdList", this.pkFieldIdList);
		}
		return ret;
	}


	/**
	 * {@inheritDoc}
	 * <pre>
	 * 指定しれたテーブルの主キーフィールドリストの設定も行います。
	 * </pre>
	 */
	@Override
	protected void addTableFields(final Table table) {
		super.addTableFields(table);
		this.setPkFieldIdList(table.getPkFieldList());
	}

	/**
	 * 保存時のメッセージキーを取得します。
	 * <pre>
	 * "message.saved"を返します。
	 * 保存時のメッセージを指定する場合、オーバーライドします。
	 * </pre>
	 * @param data 保存したデータ。
	 * @return 保存時のメッセージ。
	 */
	protected String getSavedMessage(final Map<String, Object> data) {
		return MessagesUtil.getMessage(this.getPage(), "message.saved");
	}

	/**
	 * 編集対象のデータを取得します。
	 * @param data 編集対象を特定するためのデータ。
	 * @return 問い合わせ結果。
	 * @throws Exception 例外。
	 */
	protected abstract Map<String, Object> queryData(final Map<String, Object> data) throws Exception;

	/**
	 * 選択されたデータを取得します。
	 *
	 * @param param パラメータ。
	 * @return 取得したデータ。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse getData(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.convertToServerData(param);
		JsonResponse result = new JsonResponse(JsonResponse.SUCCESS, this.convertToClientData(this.queryData(data)));
		return result;
	}

	/**
	 * 新規データを取得します。
	 * @param param パラメータ。
	 * @return 新規データ。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse getNewData(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.queryNewData(this.convertToServerData(param));
		JsonResponse result = new JsonResponse(JsonResponse.SUCCESS, this.convertToClientData(data));
		return result;
	}

	/**
	 * 新規データを取得します。
	 * <pre>
	 * フォームの初期化処理を実行し、そのフォームデータを返します。
	 * デフォルト値の設定が必要な場合、独自に実装します。
	 * </pre>
	 * @param data 参照対象を特定するためのデータ。
	 * @return 参照登録用データ。
	 * @throws Exception 例外。
	 */
	protected Map<String, Object> queryNewData(final Map<String, Object> data) throws Exception {
		this.init();
		return this.getFormDataMap();
	}


	/**
	 * 参照用データを取得する。
	 * @param data 参照対象を特定するためのデータ。
	 * @return 参照登録用データ。
	 * @throws Exception 例外。
	 */
	protected Map<String, Object> queryReferData(final Map<String, Object> data) throws Exception {
		throw new ApplicationException(this.getPage(), "error.notimplemetmethod");
	}

	/**
	 * 選択されたデータをコピーした新規データを作成します。
	 * @param param パラメータ。
	 * @return コピーした新規データ。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse getReferData(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.convertToServerData(param);
		JsonResponse result = new JsonResponse(JsonResponse.SUCCESS,
				this.convertToClientData(this.queryReferData(data)));
		return result;
	}

	/**
	 * 編集フォームの確認処理を行います。
	 * <pre>
	 * バリデーションを行いその結果を返します。
	 * </pre>
	 * @param param パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse confirm(final Map<String, Object> param) throws Exception {
		List<ValidationError> err = this.validate(param);
		JsonResponse result = null;
		if (err.size() > 0) {
			result = new JsonResponse(JsonResponse.INVALID, err);
		} else {
			result = new JsonResponse(JsonResponse.SUCCESS, "");
		}
		return result;
	}

	/**
	 * 更新すべきかどうかを判定します。
	 * <pre>
	 * 主キー指定がない場合、新規と判定するように実装します。
	 * </pre>
	 * @param data パラメータ。
	 * @return 更新の場合はtrue。
	 * @throws Exception 例外。
	 */
	protected abstract boolean isUpdate(final Map<String, Object> data) throws Exception;

	/**
	 * 更新すべきかどうかを判定します。
	 * <pre>
	 * 主キーを自動生成するテーブルの場合、data中に、指定されたテーブルの主キー指定がない場合新規と判定します。
	 * 主キーの自動生成を行わないデーブルの場合、saveModeの指定で判定します。
	 * </pre>
	 * @param table 更新テーブル。
	 * @param data パラメータ。
	 * @return 更新すべき場合、trueを返します。
	 */
	protected boolean isUpdate(final Table table, final Map<String, Object> data) {
		if (table.isAutoIncrementId()) {
			boolean ret = true;
			for (Field<?> f:table.getPkFieldList()) {
				if (StringUtil.isBlank(data.get(f.getId()))) {
					ret = false; // この場合は新規。
				}
			}
			return ret;
		} else {
			String saveMode = (String) data.get("saveMode");
			return "update".equals(saveMode);
		}
	}

	/**
	 * データの追加を行ないます。
	 * <pre>
	 * テーブルに対してデータを挿入する処理を実装します。
	 * </pre>
	 * @param data パラメータ。
	 * @throws Exception 例外。
	 *
	 */
	protected abstract void insertData(final Map<String, Object> data) throws Exception;

	/**
	 * データの更新を行ないます。
	 * <pre>
	 * テーブル中のデータを更新する処理を実装します。
	 * </pre>
	 * @param data パラメータ。
	 * @throws Exception 例外。
	 */
	protected abstract void updateData(final Map<String, Object> data) throws Exception;

	/**
	 * 登録ボタンの処理を行います。
	 *
	 * <pre>
	 * データ挿入または更新を行います。
	 * </pre>
	 *
	 * @param param パラメータ。
	 * @return 登録結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse save(final Map<String, Object> param) throws Exception {
		List<ValidationError> err = this.validate(param);
		JsonResponse result = null;
		if (err.size() > 0) {
			result = new JsonResponse(JsonResponse.INVALID, err);
		} else {
			Map<String, Object> data = this.convertToServerData(param);
			if (this.isUpdate(data)) {
				// this.setTablePrimaryKey(data);
				this.updateData(data);
			} else {
				this.insertData(data);
			}
			result = new JsonResponse(JsonResponse.SUCCESS, this.getSavedMessage(data));
		}
		return result;
	}

	/**
	 * 削除時のメッセージキーを取得します。
	 * <pre>
	 * nullを返します。
	 * 削除時のメッセージを指定する場合、オーバーライドします。
	 * </pre>
	 * @param data 削除したデータ。
	 * @return 削除時のメッセージ。
	 */
	protected String getDeletedMessage(final Map<String, Object> data) {
		return null;
	}


	/**
	 * 指定データの削除を行います。
	 *
	 * @param param パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse delete(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.convertToServerData(param);
		this.deleteData(data);
		JsonResponse result = new JsonResponse(JsonResponse.SUCCESS, this.getDeletedMessage(data));
		return result;
	}

	/**
	 * データの削除を行います。
	 * <pre>
	 * テーブル中のデータを削除する処理を実装します。
	 * </pre>
	 * @param data パラメータ。
	 * @throws Exception 例外。
	 */
	public abstract void deleteData(final Map<String, Object> data) throws Exception;
}
