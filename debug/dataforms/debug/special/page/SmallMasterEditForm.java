package dataforms.debug.special.page;

import java.util.Map;

import dataforms.controller.EditForm;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.debug.special.dao.SmallMasterDao;
import dataforms.debug.special.dao.SmallMasterTable;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.htmltable.EditableHtmlTable;

/**
 * 小規模マスタの編集フォーム。
 *
 */
public class SmallMasterEditForm extends EditForm {

	/**
	 * Logger.
	 */
	//private static Logger logger = Logger.getLogger(SmallMasterEditForm.class);

	/**
	 * コンストラクタ。
	 */
	public SmallMasterEditForm() {
		SmallMasterDao dao = new SmallMasterDao();
		FieldList flist = dao.getMultiRecordQueryKeyList();
		for (Field<?> f: flist) {
			f.setReadonly(true);
		}
		this.addFieldList(flist);
		for (Query q: dao.getMultiRecordQueryList()) {
			FieldList fl = q.getFieldList();
			fl.get(SmallMasterTable.Entity.ID_KEY1).setHidden(true);
			fl.get(SmallMasterTable.Entity.ID_KEY2).setHidden(true);
			EditableHtmlTable rtable = new EditableHtmlTable(q.getListId(), q.getFieldList());
			this.addHtmlTable(rtable);
		}
	}



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
		SmallMasterDao dao = new SmallMasterDao(this);
		return dao.query(data);
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
		Table table = new SmallMasterTable();
		boolean ret = this.isUpdate(table, data);
		return ret;
	}

	/**
	 * データを新規追加します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		SmallMasterDao dao = new SmallMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.insert(data);
	}

	/**
	 * データを更新します。
	 * @param data ポストされたデータ。
	 */
	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		SmallMasterDao dao = new SmallMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.update(data);
	}

	/**
	 * データを削除します。
	 * @param data ポストされたデータ。
	 */
	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		SmallMasterDao dao = new SmallMasterDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.delete(data);
	}

}
