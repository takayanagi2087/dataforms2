package dataforms.devtool.query.page;

import dataforms.devtool.field.AliasNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JoinTypeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.htmltable.EditableHtmlTable;

/**
 * Joinテーブルを編集するためのHTMLテーブルクラスです。
 *
 */
public class JoinHtmlTable extends EditableHtmlTable {
	/**
	 * 結合区分。
	 */
	public static final String ID_JOIN_TYPE = "joinType";

	/**
	 * パッケージ名。
	 */
	public static final String ID_PACKAGE_NAME = "packageName";

	/**
	 * テーブルクラス名フィールドID。
	 */
	public static final String ID_TABLE_CLASS_NAME = "tableClassName";

	/**
	 * 別名フィールドID。
	 */
	public static final String ID_ALIAS_NAME = "aliasName";

	/**
	 * 結合条件フィールドID。
	 */
	public static final String ID_JOIN_CONDITION = "joinCondition";

	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public JoinHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new JoinTypeField(ID_JOIN_TYPE)
			, new FunctionSelectField()
			, new PackageNameField(ID_PACKAGE_NAME)
			, new TableClassNameField(ID_TABLE_CLASS_NAME)
			, (new AliasNameField(ID_ALIAS_NAME)).setCalcEventField(true)
			, (new TextField(ID_JOIN_CONDITION)).setReadonly(true)
		);
		flist.get(ID_TABLE_CLASS_NAME).setAutocomplete(true).setRelationDataAcquisition(true);
		this.setFieldList(flist);
	}
}
