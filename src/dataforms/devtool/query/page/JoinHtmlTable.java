package dataforms.devtool.query.page;

import dataforms.devtool.field.AliasNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.JoinTypeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.field.base.FieldList;
import dataforms.field.sqltype.VarcharField;
import dataforms.htmltable.EditableHtmlTable;

/**
 * Joinテーブルを編集するためのHTMLテーブルクラスです。
 *
 */
public class JoinHtmlTable extends EditableHtmlTable {
	/**
	 * テーブルクラス名フィールドID。
	 */
	private static final String ID_TABLE_CLASS_NAME = "tableClassName";

	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public JoinHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new JoinTypeField()
			, new FunctionSelectField()
			, new PackageNameField()
			, new TableClassNameField(ID_TABLE_CLASS_NAME)
			, (new AliasNameField()).setCalcEventField(true)
			, (new VarcharField("joinCondition", 1024)).setReadonly(true)
		);
		flist.get(ID_TABLE_CLASS_NAME).setAutocomplete(true).setRelationDataAcquisition(true);
		this.setFieldList(flist);
	}
}
