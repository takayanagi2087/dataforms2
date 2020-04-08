package sample.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.common.FileField;
import dataforms.htmltable.EditableHtmlTable;
import sample.dao.MaterialMasterTable;
import sample.dao.MaterialOrderDao;
import sample.dao.MaterialOrderTable;
import sample.dao.SupplierMasterTable;

/**
 * 編集フォームクラス。
 */
public class MaterialOrderEditForm extends EditForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialOrderEditForm() {
		MaterialOrderDao dao = new MaterialOrderDao();
		FieldList flist = dao.getMainQuery().getFieldList();
		flist.get(SupplierMasterTable.Entity.ID_SUPPLIER_CODE).setAutocomplete(true).setRelationDataAcquisition(true);
		flist.get(SupplierMasterTable.Entity.ID_SUPPLIER_NAME).setAutocomplete(true).setRelationDataAcquisition(true);
		this.addFieldList(flist);
		for (Query q: dao.getRelationQueryList()) {
			EditableHtmlTable rtable = new EditableHtmlTable(q.getListId(), q.getFieldList());
			if ("materialOrderItemList".equals(q.getListId())) {
				q.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_CODE).setAutocomplete(true).setRelationDataAcquisition(true);
				q.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_NAME).setAutocomplete(true).setRelationDataAcquisition(true);
				q.getFieldList().get(MaterialMasterTable.Entity.ID_UNIT_PRICE).setReadonly(true);
				q.getFieldList().get(MaterialMasterTable.Entity.ID_MATERIAL_UNIT).setReadonly(true);
			}
			this.addHtmlTable(rtable);
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

	@Override
	protected Map<String, Object> queryNewData(Map<String, Object> data) throws Exception {
		Map<String, Object> ret = super.queryNewData(data);
		MaterialOrderDao dao = new MaterialOrderDao(this);
		MaterialOrderTable table = dao.getMainTable();
		String newcode = dao.queryNextCode(table.getOrderNoField(), null);
		MaterialOrderTable.Entity e = new MaterialOrderTable.Entity(ret);
		e.setOrderNo(newcode);
		java.sql.Date today = new java.sql.Date((new java.util.Date()).getTime());
		e.setOrderDate(today);
		return ret;
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
		Table table = new MaterialOrderTable();
		Map<String, Object> ret = this.queryData(data);
		for (Field<?> f: table.getPkFieldList()) {
			ret.remove(f.getId());
		}
		for (Field<?> f: table.getFieldList()) {
			if (f instanceof FileField) {
				ret.remove(f.getId());
			}
		}
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
		Table table = new MaterialOrderTable();
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
