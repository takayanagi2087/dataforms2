package dataforms.devtool.db.table;

import dataforms.devtool.field.ClassNameField;
import dataforms.field.common.PresenceField;
import dataforms.field.common.RowNoField;
import dataforms.field.sqltype.IntegerField;
import dataforms.field.sqltype.VarcharField;
import dataforms.htmltable.HtmlTable;

/**
 * DBテーブルの一覧を表示するHtmlTable。
 *
 */
public class DbTableListHtmlTable extends HtmlTable {
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public DbTableListHtmlTable(final String id) {
		super(id
			, new ClassNameField("checkedClass")
			, new RowNoField()
			, (new ClassNameField()).setSortable(true)
			, (new VarcharField("tableName", 64)).setSortable(true)
			, (new VarcharField("tableComment", 1024)).setSortable(true)
			, (new VarcharField("indexNames", 1024)).setSortable(true)
			, new PresenceField("status").setSortable(true)
			, new PresenceField("statusVal")
			, new PresenceField("sequenceGeneration").setSortable(true)
			, new PresenceField("difference").setSortable(true)
			, new PresenceField("differenceVal")
			, new PresenceField("backupTable").setSortable(true)
			, new IntegerField("recordCount").setSortable(true)
		);
	}
}
