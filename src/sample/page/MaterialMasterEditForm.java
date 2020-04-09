package sample.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.dao.Table;
import dataforms.field.base.Field;
import dataforms.field.common.FileField;
import dataforms.validator.RequiredValidator;
import sample.dao.MaterialMasterDao;
import sample.dao.MaterialMasterTable;

/**
 * 編集フォームクラス。
 */
public class MaterialMasterEditForm extends EditForm {
	/**
	 * コンストラクタ。
	 */
	public MaterialMasterEditForm() {
		MaterialMasterDao dao = new MaterialMasterDao();
		MaterialMasterTable table = dao.getMainTable();
		table.getMaterialCodeField().addValidator(new RequiredValidator());
		table.getMaterialNameField().addValidator(new RequiredValidator());
		table.getMaterialUnitField().addValidator(new RequiredValidator());
		table.getUnitPriceField().addValidator(new RequiredValidator());
		this.addTableFields(table);
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
	 * 新規データの初期値を取得します。
	 * <pre>
	 * 資材コードを生成します。
	 * </pre>
	 */
	@Override
	protected Map<String, Object> queryNewData(Map<String, Object> data) throws Exception {
		Map<String, Object> ret = super.queryNewData(data);
		MaterialMasterDao dao = new MaterialMasterDao(this);
		MaterialMasterTable table = dao.getMainTable();
		String newcode = dao.queryNextCode(table.getMaterialCodeField(), null);
		MaterialMasterTable.Entity e = new MaterialMasterTable.Entity(ret);
		e.setMaterialCode(newcode);
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
		MaterialMasterDao dao = new MaterialMasterDao(this);
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
		Table table = new MaterialMasterTable();
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
		MaterialMasterTable table = new MaterialMasterTable();
		boolean ret = this.isUpdate(table, data);
		return ret;
	}

	/**
	 * データを新規追加します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		MaterialMasterDao dao = new MaterialMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.insert(data);
	}

	/**
	 * データを更新します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		MaterialMasterDao dao = new MaterialMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.update(data);
	}

	/**
	 * データを削除します。
	 * @param data ポストされたデータ。
	 */
	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		MaterialMasterDao dao = new MaterialMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}
}
