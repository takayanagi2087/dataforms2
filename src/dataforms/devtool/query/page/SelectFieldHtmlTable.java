package dataforms.devtool.query.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.devtool.field.FieldDisplaySelectField;
import dataforms.devtool.field.FieldFullClassNameField;
import dataforms.devtool.field.MatchTypeSelectField;
import dataforms.devtool.field.QueryFieldIdField;
import dataforms.devtool.field.SummerySelectField;
import dataforms.devtool.field.TableFullClassNameField;
import dataforms.devtool.field.TableOrSubQueryClassNameField;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.FlagField;
import dataforms.field.common.RecordIdField;
import dataforms.field.common.SortOrderField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.util.StringUtil;
import dataforms.validator.RequiredValidator;

/**
 * 選択フィールドHtmlテーブルクラス。
 *
 */
public class SelectFieldHtmlTable extends EditableHtmlTable {

	/**
	 * 選択フラグ。
	 */
	public static final String ID_SEL = "sel";

	/**
	 * テーブルのフルクラス名。
	 */
	public static final String ID_TABLE_FULL_CLASS_NAME = "tableFullClassName";

	/**
	 * フィールドID。
	 */
	public static final String ID_FIELD_ID = "fieldId";

	/**
	 * フィールドクラス名。
	 */
	public static final String ID_FIELD_CLASS_NAME = "fieldClassName";

	/**
	 * 一覧フォームのフィールド表示設定。
	 */
	private static final String ID_LIST_FIELD_DISPLAY = "listFieldDisplay";

	/**
	 * 編集キー。
	 */
	private static final String ID_EDIT_KEY = "editKey";

	/**
	 * 編集フォームのフィールド表示設定。
	 */
	private static final String ID_EDIT_FIELD_DISPLAY = "editFieldDisplay";

	/**
	 * テーブルクラス名。
	 */
	public static final String ID_TABLE_CLASS_NAME = "tableClassName";

	/**
	 * マッチタイプ。
	 */
	private static final String ID_MATCH_TYPE = "matchType";

	/**
	 * フィールド別名。
	 */
	public static final String ID_ALIAS = "alias";

	/**
	 * テーブル別名。
	 */
	public static final String ID_TABLE_ALIAS = "tableAlias";

	/**
	 * コメント。
	 */
	public static final String ID_COMMENT = "comment";


	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public SelectFieldHtmlTable(final String id) {
		this(id, false);
	}

	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 * @param daoflg Daoフラグ。
	 */
	public SelectFieldHtmlTable(final String id, final boolean daoflg) {
		super(id);
		FieldList flist = new FieldList(
			(daoflg ? new FlagField(ID_SEL): new SummerySelectField(ID_SEL))
			, new SortOrderField()
			, new QueryFieldIdField(ID_FIELD_ID).addValidator(new RequiredValidator()).setReadonly(daoflg)
			, new TextField(ID_ALIAS)
			, new FieldFullClassNameField(ID_FIELD_CLASS_NAME).setReadonly(true)
			, new TableFullClassNameField(ID_TABLE_FULL_CLASS_NAME).setReadonly(true)
			, new TableOrSubQueryClassNameField(ID_TABLE_CLASS_NAME).setReadonly(true)
			, new MatchTypeSelectField(ID_MATCH_TYPE)
			, new FieldDisplaySelectField(ID_LIST_FIELD_DISPLAY)
			, new FlagField(ID_EDIT_KEY)
			, new FieldDisplaySelectField(ID_EDIT_FIELD_DISPLAY)
			, new TextField(ID_TABLE_ALIAS ).setReadonly(true)
			, new TextField(ID_COMMENT)
		);
		this.setFieldList(flist);
		if (!daoflg) {
			this.setFixedColumns(5);
		}
	}

	/**
	 * 指定されたクラスのフィールドリストを取得します。
	 * @param className QueryまたはTableのクラス名。
	 * @return 主テーブルのインスタンス。
	 * @throws Exception 例外。
	 */
	public static FieldList getFieldList(final String className) throws Exception {
		FieldList fieldList = null;
		if (!StringUtil.isBlank(className)) {
			Class<?> qclass = Class.forName(className);
			Object obj = qclass.getConstructor().newInstance();
			if (obj instanceof Query) {
				Query q = (Query) obj;
				fieldList = q.getFieldList();
			} else if (obj instanceof Table){
				Table t = (Table) obj;
				fieldList = t.getFieldList();
			}
		}
		return fieldList;
	}

	/**
	 * 一覧フォームのフィールド表示設定。
	 * @param f フィールド。
	 * @return フィールド表示情報。
	 */
	private static FieldDisplaySelectField.FieldDisplay getListFieldDisplay(final Field<?> f) {
		FieldDisplaySelectField.FieldDisplay ret = FieldDisplaySelectField.FieldDisplay.NONE;
		if (f.isHidden()) {
			ret = FieldDisplaySelectField.FieldDisplay.INPUT_HIDDEN;
		} else {
			ret = FieldDisplaySelectField.FieldDisplay.SPAN;
		}
		return ret;
	}

	/**
	 * 編集フォームのフィールド表示設定。
	 * @param f フィールド。
	 * @return フィールド表示情報。
	 */
	private static FieldDisplaySelectField.FieldDisplay getEditFieldDisplay(final Field<?> f) {
		FieldDisplaySelectField.FieldDisplay ret = FieldDisplaySelectField.FieldDisplay.NONE;
		if (f.isHidden()) {
			ret = FieldDisplaySelectField.FieldDisplay.INPUT_HIDDEN;
		} else {
			ret = FieldDisplaySelectField.FieldDisplay.INPUT;
		}
		return ret;
	}


	/**
	 * fieldListに対応した表示データを作成します。
	 * @param flist フィールドリスト。
	 * @param alias 別名。
	 * @return テーブルデータ。
	 */
	public static List<Map<String, Object>> getTableData(final FieldList flist, final String alias) {
		boolean pk = false;
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (Field<?> f: flist) {
			Map<String, Object> ent = new HashMap<String, Object>();
			Table table = f.getTable();
			ent.put(ID_TABLE_FULL_CLASS_NAME, table.getClass().getName());
			ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getSimpleName());
			ent.put(ID_SEL, "0");
			ent.put(ID_FIELD_ID, f.getId());
			ent.put(ID_FIELD_CLASS_NAME, f.getClass().getName());
			ent.put(ID_TABLE_ALIAS, alias);
			ent.put(ID_COMMENT, f.getComment());
			ent.put(ID_MATCH_TYPE, f.getDefaultMatchType());
			// 先頭のrecord idに編集キーを設定する。
			if (pk == false && f instanceof RecordIdField) {
				ent.put(ID_EDIT_KEY, "1");
				pk = true;
			} else {
				ent.put(ID_EDIT_KEY, "0");
			}
			ent.put(ID_LIST_FIELD_DISPLAY, SelectFieldHtmlTable.getListFieldDisplay(f));
			ent.put(ID_EDIT_FIELD_DISPLAY, SelectFieldHtmlTable.getEditFieldDisplay(f));

			// ent.put(JoinHtmlTable.ID_TABLE_CLASS_NAME, table.getClass().getSimpleName());
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
					String fieldId = (String) m.get(ID_FIELD_ID);
					if (fid.equals(fieldId)) {
						m.put(ID_SEL, "1");
					}
				}
			}
		}
		return list;
	}
}
