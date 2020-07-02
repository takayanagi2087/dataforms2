package test.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import test.field.TestMultiRecIdField;
import java.util.Map;
import test.field.Code1Field;
import dataforms.field.sqlfunc.CountField;




/**
 * 問い合わせクラスです。
 *
 */
public class TestCode1Query extends Query {
	/**
	 * 複数レコード編集テストテーブル。
	 */
	private TestMultiRecTable testMultiRecTable = null;

	/**
	 * 複数レコード編集テストテーブルを取得します。
	 * @return 複数レコード編集テストテーブル。
	 */
	public TestMultiRecTable getTestMultiRecTable() {
		return this.testMultiRecTable;
	}


	/**
	 * コンストラクタ.
	 */
	public TestCode1Query() {
		this.setComment("code1一覧の問合せ");
		this.setDistinct(false);
		this.testMultiRecTable = new TestMultiRecTable();
		this.testMultiRecTable.setAlias("m");

		this.setFieldList(new FieldList(
			this.testMultiRecTable.getCode1Field()
			, new CountField("cnt", this.testMultiRecTable.getTestMultiRecIdField()).setComment("件数")
		));
		this.setMainTable(testMultiRecTable);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** 件数のフィールドID。 */
		public static final String ID_CNT = "cnt";

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
		 * 件数を取得します。
		 * @return 件数。
		 */
		public java.lang.Long getCnt() {
			return (java.lang.Long) this.getMap().get(Entity.ID_CNT);
		}

		/**
		 * 件数を設定します。
		 * @param cnt 件数。
		 */
		public void setCnt(final java.lang.Long cnt) {
			this.getMap().put(Entity.ID_CNT, cnt);
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
	 * 件数フィールドを取得します。
	 * @return 件数フィールド。
	 */
	public TestMultiRecIdField getCntField() {
		return (TestMultiRecIdField) this.getField(Entity.ID_CNT);
	}


}