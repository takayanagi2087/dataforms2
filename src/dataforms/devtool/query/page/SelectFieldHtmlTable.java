package dataforms.devtool.query.page;

import dataforms.devtool.field.FieldFullClassNameField;
import dataforms.devtool.field.FieldIdField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.devtool.field.TableFullClassNameField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.FlagField;
import dataforms.field.common.SortOrderField;
import dataforms.htmltable.EditableHtmlTable;

/**
 * 選択フィールドHtmlテーブルクラス。
 *
 */
public class SelectFieldHtmlTable extends EditableHtmlTable {
	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public SelectFieldHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new FlagField("sel")
			, new SortOrderField()
			, (new FieldIdField()).setReadonly(true)
			, new FieldFullClassNameField("fieldClassName")
			, new TableFullClassNameField("selectTableClassName")
			, new TableClassNameField()
			, new TextField("comment"));
		this.setFieldList(flist);
	}

}
