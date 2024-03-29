package dataforms.devtool.table.page;

import dataforms.devtool.field.FieldClassNameField;
import dataforms.devtool.field.FieldIdField;
import dataforms.devtool.field.FieldLengthField;
import dataforms.devtool.field.OverwriteModeField;
import dataforms.devtool.field.PackageNameField;
import dataforms.field.base.FieldList;
import dataforms.field.common.FlagField;
import dataforms.field.sqltype.VarcharField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.validator.RequiredValidator;

/**
 * フィールドリストテーブルクラス。
 *
 */
public class FieldListHtmlTable extends EditableHtmlTable {
	/**
	 * コンストラクタ。
	 */
	public FieldListHtmlTable() {
		super("fieldList");
		FieldList flist = new FieldList(
			 (new PackageNameField("superPackageName")).addValidator(new RequiredValidator()).setComment("基本パッケージ名").setCalcEventField(true)
			, (new FieldClassNameField("superSimpleClassName")).setPackageNameFieldId("superPackageName").addValidator(new RequiredValidator()).setComment("基本クラス名").setCalcEventField(true)
			, (new PackageNameField()).setComment("パッケージ名").addValidator(new RequiredValidator()).setCalcEventField(true)
			, (new FieldClassNameField()).setComment("クラス名").addValidator(new RequiredValidator()).setCalcEventField(true)
			, new FieldIdField()
			, new FieldLengthField()
			, (new FlagField("pkFlag")).setComment("主キー")
			, (new FlagField("notNullFlag")).setComment("Not Null")
			, (new VarcharField("comment", 256)).setComment("コメント")
			, new FlagField("isDataformsField")
			, new OverwriteModeField()
		);
		this.setFieldList(flist);
	}
}
