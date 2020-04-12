package dataforms.devtool.query.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.dao.Table;
import dataforms.devtool.field.FieldFullClassNameField;
import dataforms.devtool.field.FieldIdField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.devtool.field.TableFullClassNameField;
import dataforms.field.base.Field;
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

	/**
	 * fieldListに対応した表示データを作成します。
	 * @param flist フィールドリスト。
	 * @return テーブルデータ。
	 */
	public static List<Map<String, Object>> getTableData(final FieldList flist) {
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


	/**
	 *
	 * キーフィールドを設定します。
	 * @param list テーブルデータ。
	 * @param keyFieldList キーフィールドリスト。
	 * @return テーブルデータ。
	 *
	 */
	public static List<Map<String, Object>> selectKey(final List<Map<String, Object>> list, final FieldList keyFieldList) {
		if (keyFieldList != null) {
			for (Field<?> f: keyFieldList) {
				String fid = f.getId();
				for (Map<String, Object> m: list) {
					String fieldId = (String) m.get("fieldId");
					if (fid.equals(fieldId)) {
						m.put("sel", "1");
					}
				}
			}
		}
		return list;
	}
}
