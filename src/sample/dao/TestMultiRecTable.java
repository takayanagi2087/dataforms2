package sample.dao;

import java.util.Map;
import dataforms.dao.Table;
import sample.field.TestMultiRecIdField;
import dataforms.field.common.SortOrderField;
import sample.field.Code1Field;
import sample.field.Code2Field;
import sample.field.ContentsField;


/**
 * 複数レコード編集テストテーブルクラス。
 *
 */
public class TestMultiRecTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public TestMultiRecTable() {
		this.setAutoIncrementId(true);
		this.setComment("複数レコード編集テストテーブル");
		this.addPkField(new TestMultiRecIdField()); //レコードID
		this.addField(new SortOrderField()); //ソート順
		this.addField(new Code1Field()); //コード1
		this.addField(new Code2Field()); //コード2
		this.addField(new ContentsField()); //内容
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		TestMultiRecTableRelation r = new TestMultiRecTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** レコードIDのフィールドID。 */
		public static final String ID_TEST_MULTI_REC_ID = "testMultiRecId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** コード1のフィールドID。 */
		public static final String ID_CODE1 = "code1";
		/** コード2のフィールドID。 */
		public static final String ID_CODE2 = "code2";
		/** 内容のフィールドID。 */
		public static final String ID_CONTENTS = "contents";

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
		 * レコードIDを取得します。
		 * @return レコードID。
		 */
		public java.lang.Long getTestMultiRecId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_TEST_MULTI_REC_ID);
		}

		/**
		 * レコードIDを設定します。
		 * @param testMultiRecId レコードID。
		 */
		public void setTestMultiRecId(final java.lang.Long testMultiRecId) {
			this.getMap().put(Entity.ID_TEST_MULTI_REC_ID, testMultiRecId);
		}

		/**
		 * ソート順を取得します。
		 * @return ソート順。
		 */
		public java.lang.Short getSortOrder() {
			return (java.lang.Short) this.getMap().get(Entity.ID_SORT_ORDER);
		}

		/**
		 * ソート順を設定します。
		 * @param sortOrder ソート順。
		 */
		public void setSortOrder(final java.lang.Short sortOrder) {
			this.getMap().put(Entity.ID_SORT_ORDER, sortOrder);
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
		 * コード2を取得します。
		 * @return コード2。
		 */
		public java.lang.String getCode2() {
			return (java.lang.String) this.getMap().get(Entity.ID_CODE2);
		}

		/**
		 * コード2を設定します。
		 * @param code2 コード2。
		 */
		public void setCode2(final java.lang.String code2) {
			this.getMap().put(Entity.ID_CODE2, code2);
		}

		/**
		 * 内容を取得します。
		 * @return 内容。
		 */
		public java.lang.String getContents() {
			return (java.lang.String) this.getMap().get(Entity.ID_CONTENTS);
		}

		/**
		 * 内容を設定します。
		 * @param contents 内容。
		 */
		public void setContents(final java.lang.String contents) {
			this.getMap().put(Entity.ID_CONTENTS, contents);
		}


	}
	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public TestMultiRecIdField getTestMultiRecIdField() {
		return (TestMultiRecIdField) this.getField(Entity.ID_TEST_MULTI_REC_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
	}

	/**
	 * コード1フィールドを取得します。
	 * @return コード1フィールド。
	 */
	public Code1Field getCode1Field() {
		return (Code1Field) this.getField(Entity.ID_CODE1);
	}

	/**
	 * コード2フィールドを取得します。
	 * @return コード2フィールド。
	 */
	public Code2Field getCode2Field() {
		return (Code2Field) this.getField(Entity.ID_CODE2);
	}

	/**
	 * 内容フィールドを取得します。
	 * @return 内容フィールド。
	 */
	public ContentsField getContentsField() {
		return (ContentsField) this.getField(Entity.ID_CONTENTS);
	}


}
