package sample.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import dataforms.field.sqlfunc.CountField;
import dataforms.field.sqlfunc.AliasField;




/**
 * 問い合わせクラスです。
 *
 */
public class TestQuery extends Query {
	/**
	 * 資材マスタ。
	 */
	private MaterialMasterTable materialMasterTable = null;

	/**
	 * 資材マスタを取得します。
	 * @return 資材マスタ。
	 */
	public MaterialMasterTable getMaterialMasterTable() {
		return this.materialMasterTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestQuery() {
		this.setComment("test");
		this.setDistinct(false);
		this.materialMasterTable = new MaterialMasterTable();
		this.materialMasterTable.setAlias("m");

		this.setFieldList(new FieldList(
			new CountField("cnt", this.materialMasterTable.getMaterialIdField())
			, new AliasField("u", materialMasterTable.getMaterialUnitField())
		));
		this.setMainTable(materialMasterTable);

	}
}