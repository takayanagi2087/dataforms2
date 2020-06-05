package dataforms.devtool.query.page;

import dataforms.devtool.field.DbColumnSelectField;
import dataforms.devtool.field.FieldLengthField;
import dataforms.devtool.field.QueryFieldIdField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.SortOrderField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.validator.RequiredValidator;

/**
 * 選択フィールドHtmlテーブルクラス。
 *
 */
public class SqlFieldHtmlTable extends EditableHtmlTable {
	/**
	 * フィールドIDフィールドID。
	 */
	public static final String ID_FIELD_ID = "fieldId";
	/**
	 * フィールドクラス名フィールドID。。
	 */
	public static final String ID_FIELD_CLASS_NAME = "fieldClassName";
	/**
	 * フィールド長のフィールドID。
	 */
	public static final String ID_FIELD_LENGTH = "fieldLength";
	/**
	 * SQLフィールドID。。
	 */
	public static final String ID_SQL = "sql";
	/**
	 * コメントフィールドID。
	 */
	public static final String ID_COMMENT = "comment";

	/**
	 * コンストラクタ。
	 * @param id デーブルID。
	 */
	public SqlFieldHtmlTable(final String id) {
		super(id);
		FieldList flist = new FieldList(
			new SortOrderField()
			, (new QueryFieldIdField(ID_FIELD_ID)).addValidator(new RequiredValidator())
			, (new DbColumnSelectField(ID_FIELD_CLASS_NAME)).addValidator(new RequiredValidator())
			, (new FieldLengthField())
			, new TextField(ID_SQL).addValidator(new RequiredValidator())
			, new TextField(ID_COMMENT)
		);
		this.setFieldList(flist);
		this.setFixedColumns(4);
	}
}
