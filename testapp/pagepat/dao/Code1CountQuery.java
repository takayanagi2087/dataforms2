package pagepat.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import dataforms.field.sqltype.BigintField;
import pagepat.field.Code1Field;
import dataforms.util.NumberUtil;
import java.util.Map;
import dataforms.field.sqlfunc.CountField;



/**
 * Code1件数問合せクラスです。
 *
 */
public class Code1CountQuery extends Query {
	/**
	 * コード12テーブルを取得します。
	 * @return コード12テーブル。
	 */
	public Code12Table getCode12Table() {
		return (Code12Table) this.getTable(Code12Table.class, "m");
	}


	/**
	 * コンストラクタ.
	 */
	public Code1CountQuery() 	{
		this.setComment("Code1件数問合せ");
		this.setDistinct(false);
		Code12Table code12Table = new Code12Table();
		code12Table.setAlias("m");

		this.setFieldList(new FieldList(
			code12Table.getCode1Field()
			, new CountField("code2Count", code12Table.getCode2Field()).setComment("コード1の件数")
		));
		this.setMainTable(code12Table);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** コード1の件数のフィールドID。 */
		public static final String ID_CODE2_COUNT = "code2Count";

		/**
		 * コンストラクタ。
		 */
		public Entity() {

		}
		/**
		 * コンストラクタ。
		 * @param map 操作対象マップ。
		 */
		public Entity(final Map<String, Object> map) {
			super(map);
		}
		/**
		 * コード1を取得します。
		 * @return コード1。
		 */
		public java.lang.String getCode1() {
			return (java.lang.String) this.getMap().get(Entity.ID_CODE1);
		}

		/**
		 * コード1を設定します。
		 * @param code1 コード1。
		 */
		public void setCode1(final java.lang.String code1) {
			this.getMap().put(Entity.ID_CODE1, code1);
		}

		/**
		 * コード1の件数を取得します。
		 * @return コード1の件数。
		 */
		public java.lang.Long getCode2Count() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_CODE2_COUNT));
		}

		/**
		 * コード1の件数を設定します。
		 * @param code2Count コード1の件数。
		 */
		public void setCode2Count(final java.lang.Long code2Count) {
			this.getMap().put(Entity.ID_CODE2_COUNT, code2Count);
		}


	}

	/**
	 * コード1フィールドを取得します。
	 * @return コード1フィールド。
	 */
	public Code1Field getCode1Field() {
		return (Code1Field) this.getField(Entity.ID_CODE1);
	}

	/**
	 * コード1の件数フィールドを取得します。
	 * @return コード1の件数フィールド。
	 */
	public BigintField getCode2CountField() {
		return (BigintField) this.getField(Entity.ID_CODE2_COUNT);
	}



}