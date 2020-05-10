package sample.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.field.base.FieldList;
import dataforms.htmltable.EditableHtmlTable;
import sample.dao.MaterialOrderDao;

/**
 * 編集フォームクラス。
 */
public class MaterialOrderEditForm extends EditForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderEditForm() {
		MaterialOrderDao dao = new MaterialOrderDao();
		if (dao.getSingleRecordQuery() != null) {
			dao.getMaterialOrderQuery().getSupplierMasterTable().getSupplierCodeField().setAutocomplete(true).setRelationDataAcquisition(true);
			dao.getMaterialOrderQuery().getSupplierMasterTable().getSupplierNameField().setAutocomplete(true).setRelationDataAcquisition(true);
			FieldList flist = dao.getSingleRecordQuery().getFieldList();
			this.addFieldList(flist);
		}
		if (dao.getMultiRecordQueryList() != null) {
			dao.getMaterialOrderItemQuery().getMaterialMasterTable().getMaterialCodeField().setAutocomplete(true).setRelationDataAcquisition(true);
			dao.getMaterialOrderItemQuery().getMaterialMasterTable().getMaterialNameField().setAutocomplete(true).setRelationDataAcquisition(true);
			for (Query q: dao.getMultiRecordQueryList()) {
				EditableHtmlTable rtable = new EditableHtmlTable(q.getListId(), q.getFieldList());
				this.addHtmlTable(rtable);
			}
		}
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
		MaterialOrderDao dao = new MaterialOrderDao(this);
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
		MaterialOrderDao dao = new MaterialOrderDao(this);
		this.removeKeyData(dao, ret);
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
		MaterialOrderDao dao = new MaterialOrderDao(this);
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
		MaterialOrderDao dao = new MaterialOrderDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.insert(data);
	}

	/**
	 * データを更新します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		MaterialOrderDao dao = new MaterialOrderDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.update(data);
	}

	/**
	 * データを削除します。
	 * @param data ポストされたデータ。
	 */
	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		MaterialOrderDao dao = new MaterialOrderDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}
}
