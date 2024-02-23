package dataforms.devtool.pageform.page;

import dataforms.controller.Dialog;

/**
 * フィールドリスト設定ダイアログ。
 */
public class FieldListDialog extends Dialog {
	/**
	 * コンストラクタ。
	 */
	public FieldListDialog() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id ダイアログID。
	 */
	public FieldListDialog(final String id) {
		super(id);
		this.addForm(new FieldListForm());
	}
}
