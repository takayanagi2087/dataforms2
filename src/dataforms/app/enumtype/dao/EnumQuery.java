package dataforms.app.enumtype.dao;

import dataforms.dao.Query;
import dataforms.dao.TableList;
import dataforms.field.base.FieldList;

/**
 * 列挙型の各種問合せ。
 * <pre>
 * EnumTable,EnumNameTableを結合した問合せです。
 * </pre>
 */
public class EnumQuery extends Query {
	/**
	 * コンストラクタ。
	 */
	public EnumQuery() {
		EnumNameTable ntable = new EnumNameTable();
		ntable.setAlias("en");
		EnumTable table = new EnumTable();
		table.setAlias("e");
		FieldList flist = new FieldList();
		flist.addAll(table.getFieldList());
		flist.addField(ntable.getLangCodeField());
		flist.addField(ntable.getEnumNameField());
		this.setFieldList(flist);
		this.setMainTable(table);
		this.setJoinTableList(new TableList(ntable));
	}
}
