package dataforms.devtool.query.page;

import dataforms.devtool.field.AliasNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.TableFullClassNameField;
import dataforms.field.base.FieldList;
import dataforms.field.sqltype.VarcharField;
import dataforms.htmltable.EditableHtmlTable;

/**
 * Joinテーブルを編集するためのHTMLテーブルクラスです。
 *
 */
public class JoinHtmlTable extends EditableHtmlTable {
	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public JoinHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new FunctionSelectField()
			, new PackageNameField()
			, new TableFullClassNameField("tableClassName")
			, (new AliasNameField()).setCalcEventField(true)
			, (new VarcharField("joinCondition", 1024)).setReadonly(true)
		);
		flist.get("tableClassName").setAutocomplete(true).setRelationDataAcquisition(true);
		this.setFieldList(flist);
	}
}
