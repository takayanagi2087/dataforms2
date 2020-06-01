package dataforms.devtool.query.page;

import dataforms.devtool.field.DbColumnSelectField;
import dataforms.devtool.field.FieldLengthField;
import dataforms.devtool.field.QueryFieldIdField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.SortOrderField;
import dataforms.htmltable.EditableHtmlTable;

/**
 * 選択フィールドHtmlテーブルクラス。
 *
 */
public class SqlFieldHtmlTable extends EditableHtmlTable {
	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public SqlFieldHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new SortOrderField()
			, new QueryFieldIdField("fieldId")
			, new DbColumnSelectField("fieldClassName")
			, new FieldLengthField()
			, new TextField("sql")
			, new TextField("comment")
		);
		this.setFieldList(flist);
		this.setFixedColumns(4);
	}

	/**
	 * fieldListに対応した表示データを作成します。
	 * @param flist フィールドリスト。
	 * @return テーブルデータ。
	 */
/*	public static List<Map<String, Object>> getTableData(final FieldList flist) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (Field<?> f: flist) {
			Map<String, Object> ent = new HashMap<String, Object>();
			Table table = f.getTable();
			ent.put("selectTableClass", table.getClass().getName());
			ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getName());
			ent.put("selectTableClassName", table.getClass().getSimpleName());
			ent.put("sel", "0");
			ent.put("fieldId", f.getId());
			ent.put("fieldClassName", f.getClass().getName());
			ent.put("comment", f.getComment());
			ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getSimpleName());
			ret.add(ent);
		}
		return ret;
	}
*/
}
