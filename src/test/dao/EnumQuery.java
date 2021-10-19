package test.dao;

import dataforms.field.base.FieldList;
import dataforms.dao.Query;
import dataforms.app.enumtype.field.EnumIdField;
import dataforms.field.common.CreateTimestampField;
import dataforms.app.enumtype.field.ParentIdField;
import dataforms.field.common.UpdateUserIdField;
import dataforms.field.common.CreateUserIdField;
import dataforms.app.enumtype.field.EnumGroupCodeField;
import dataforms.util.NumberUtil;
import java.util.Map;
import dataforms.field.common.SortOrderField;
import dataforms.app.enumtype.field.EnumCodeField;
import dataforms.app.enumtype.field.MemoField;
import dataforms.field.sqlfunc.AliasField;
import dataforms.field.common.UpdateTimestampField;

import dataforms.app.enumtype.dao.EnumTable;



/**
 * 問い合わせクラスです。
 *
 */
public class EnumQuery extends Query {
	/**
	 * 列挙型テーブル。
	 */
	private EnumTable enumTable = null;

	/**
	 * 列挙型テーブルを取得します。
	 * @return 列挙型テーブル。
	 */
	public EnumTable getEnumTable() {
		return this.enumTable;
	}

	/**
	 * 列挙型テーブル。
	 */
	private EnumTable enumTableJ0 = null;

	/**
	 * 列挙型テーブルを取得します。
	 * @return 列挙型テーブル。
	 */
	public EnumTable getEnumTableJ0() {
		return this.enumTableJ0;
	}


	/**
	 * コンストラクタ.
	 */
	public EnumQuery() {
		this.setComment("列挙型問合せ");
		this.setDistinct(false);
		this.enumTable = new EnumTable();
		this.enumTable.setAlias("m");
		this.enumTableJ0 = new EnumTable();
		this.enumTableJ0.setAlias("j0");

		this.setFieldList(new FieldList(
			this.enumTable.getEnumIdField()
			, this.enumTable.getParentIdField()
			, this.enumTable.getSortOrderField()
			, this.enumTable.getEnumCodeField()
			, this.enumTable.getEnumGroupCodeField()
			, this.enumTable.getMemoField()
			, this.enumTable.getCreateUserIdField()
			, this.enumTable.getCreateTimestampField()
			, this.enumTable.getUpdateUserIdField()
			, this.enumTable.getUpdateTimestampField()
			, new AliasField("parentCode", this.enumTableJ0.getEnumCodeField())
		));
		this.setMainTable(enumTable);
		this.addInnerJoin(enumTableJ0);

	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 列挙型IDのフィールドID。 */
		public static final String ID_ENUM_ID = "enumId";
		/** 親IDフィールドのフィールドID。 */
		public static final String ID_PARENT_ID = "parentId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** 列挙型コードのフィールドID。 */
		public static final String ID_ENUM_CODE = "enumCode";
		/** 列挙型グループコード.のフィールドID。 */
		public static final String ID_ENUM_GROUP_CODE = "enumGroupCode";
		/** メモのフィールドID。 */
		public static final String ID_MEMO = "memo";
		/** 作成者IDのフィールドID。 */
		public static final String ID_CREATE_USER_ID = "createUserId";
		/** 作成日時のフィールドID。 */
		public static final String ID_CREATE_TIMESTAMP = "createTimestamp";
		/** 更新者IDのフィールドID。 */
		public static final String ID_UPDATE_USER_ID = "updateUserId";
		/** 更新日時のフィールドID。 */
		public static final String ID_UPDATE_TIMESTAMP = "updateTimestamp";
		/** 列挙型コードのフィールドID。 */
		public static final String ID_PARENT_CODE = "parentCode";

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
		 * 列挙型IDを取得します。
		 * @return 列挙型ID。
		 */
		public java.lang.Long getEnumId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_ENUM_ID));
		}

		/**
		 * 列挙型IDを設定します。
		 * @param enumId 列挙型ID。
		 */
		public void setEnumId(final java.lang.Long enumId) {
			this.getMap().put(Entity.ID_ENUM_ID, enumId);
		}

		/**
		 * 親IDフィールドを取得します。
		 * @return 親IDフィールド。
		 */
		public java.lang.Long getParentId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_PARENT_ID));
		}

		/**
		 * 親IDフィールドを設定します。
		 * @param parentId 親IDフィールド。
		 */
		public void setParentId(final java.lang.Long parentId) {
			this.getMap().put(Entity.ID_PARENT_ID, parentId);
		}

		/**
		 * ソート順を取得します。
		 * @return ソート順。
		 */
		public java.lang.Short getSortOrder() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SORT_ORDER));
		}

		/**
		 * ソート順を設定します。
		 * @param sortOrder ソート順。
		 */
		public void setSortOrder(final java.lang.Short sortOrder) {
			this.getMap().put(Entity.ID_SORT_ORDER, sortOrder);
		}

		/**
		 * 列挙型コードを取得します。
		 * @return 列挙型コード。
		 */
		public java.lang.String getEnumCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_ENUM_CODE);
		}

		/**
		 * 列挙型コードを設定します。
		 * @param enumCode 列挙型コード。
		 */
		public void setEnumCode(final java.lang.String enumCode) {
			this.getMap().put(Entity.ID_ENUM_CODE, enumCode);
		}

		/**
		 * 列挙型グループコード.を取得します。
		 * @return 列挙型グループコード.。
		 */
		public java.lang.String getEnumGroupCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_ENUM_GROUP_CODE);
		}

		/**
		 * 列挙型グループコード.を設定します。
		 * @param enumGroupCode 列挙型グループコード.。
		 */
		public void setEnumGroupCode(final java.lang.String enumGroupCode) {
			this.getMap().put(Entity.ID_ENUM_GROUP_CODE, enumGroupCode);
		}

		/**
		 * メモを取得します。
		 * @return メモ。
		 */
		public java.lang.String getMemo() {
			return (java.lang.String) this.getMap().get(Entity.ID_MEMO);
		}

		/**
		 * メモを設定します。
		 * @param memo メモ。
		 */
		public void setMemo(final java.lang.String memo) {
			this.getMap().put(Entity.ID_MEMO, memo);
		}

		/**
		 * 作成者IDを取得します。
		 * @return 作成者ID。
		 */
		public java.lang.Long getCreateUserId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_CREATE_USER_ID));
		}

		/**
		 * 作成者IDを設定します。
		 * @param createUserId 作成者ID。
		 */
		public void setCreateUserId(final java.lang.Long createUserId) {
			this.getMap().put(Entity.ID_CREATE_USER_ID, createUserId);
		}

		/**
		 * 作成日時を取得します。
		 * @return 作成日時。
		 */
		public java.sql.Timestamp getCreateTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_CREATE_TIMESTAMP);
		}

		/**
		 * 作成日時を設定します。
		 * @param createTimestamp 作成日時。
		 */
		public void setCreateTimestamp(final java.sql.Timestamp createTimestamp) {
			this.getMap().put(Entity.ID_CREATE_TIMESTAMP, createTimestamp);
		}

		/**
		 * 更新者IDを取得します。
		 * @return 更新者ID。
		 */
		public java.lang.Long getUpdateUserId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_UPDATE_USER_ID));
		}

		/**
		 * 更新者IDを設定します。
		 * @param updateUserId 更新者ID。
		 */
		public void setUpdateUserId(final java.lang.Long updateUserId) {
			this.getMap().put(Entity.ID_UPDATE_USER_ID, updateUserId);
		}

		/**
		 * 更新日時を取得します。
		 * @return 更新日時。
		 */
		public java.sql.Timestamp getUpdateTimestamp() {
			return (java.sql.Timestamp) this.getMap().get(Entity.ID_UPDATE_TIMESTAMP);
		}

		/**
		 * 更新日時を設定します。
		 * @param updateTimestamp 更新日時。
		 */
		public void setUpdateTimestamp(final java.sql.Timestamp updateTimestamp) {
			this.getMap().put(Entity.ID_UPDATE_TIMESTAMP, updateTimestamp);
		}

		/**
		 * 列挙型コードを取得します。
		 * @return 列挙型コード。
		 */
		public java.lang.String getParentCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_PARENT_CODE);
		}

		/**
		 * 列挙型コードを設定します。
		 * @param parentCode 列挙型コード。
		 */
		public void setParentCode(final java.lang.String parentCode) {
			this.getMap().put(Entity.ID_PARENT_CODE, parentCode);
		}


	}

	/**
	 * 列挙型IDフィールドを取得します。
	 * @return 列挙型IDフィールド。
	 */
	public EnumIdField getEnumIdField() {
		return (EnumIdField) this.getField(Entity.ID_ENUM_ID);
	}

	/**
	 * 親IDフィールドフィールドを取得します。
	 * @return 親IDフィールドフィールド。
	 */
	public ParentIdField getParentIdField() {
		return (ParentIdField) this.getField(Entity.ID_PARENT_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
	}

	/**
	 * 列挙型コードフィールドを取得します。
	 * @return 列挙型コードフィールド。
	 */
	public EnumCodeField getEnumCodeField() {
		return (EnumCodeField) this.getField(Entity.ID_ENUM_CODE);
	}

	/**
	 * 列挙型グループコード.フィールドを取得します。
	 * @return 列挙型グループコード.フィールド。
	 */
	public EnumGroupCodeField getEnumGroupCodeField() {
		return (EnumGroupCodeField) this.getField(Entity.ID_ENUM_GROUP_CODE);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}

	/**
	 * 作成者IDフィールドを取得します。
	 * @return 作成者IDフィールド。
	 */
	public CreateUserIdField getCreateUserIdField() {
		return (CreateUserIdField) this.getField(Entity.ID_CREATE_USER_ID);
	}

	/**
	 * 作成日時フィールドを取得します。
	 * @return 作成日時フィールド。
	 */
	public CreateTimestampField getCreateTimestampField() {
		return (CreateTimestampField) this.getField(Entity.ID_CREATE_TIMESTAMP);
	}

	/**
	 * 更新者IDフィールドを取得します。
	 * @return 更新者IDフィールド。
	 */
	public UpdateUserIdField getUpdateUserIdField() {
		return (UpdateUserIdField) this.getField(Entity.ID_UPDATE_USER_ID);
	}

	/**
	 * 更新日時フィールドを取得します。
	 * @return 更新日時フィールド。
	 */
	public UpdateTimestampField getUpdateTimestampField() {
		return (UpdateTimestampField) this.getField(Entity.ID_UPDATE_TIMESTAMP);
	}

	/**
	 * 列挙型コードフィールドを取得します。
	 * @return 列挙型コードフィールド。
	 */
	public EnumCodeField getParentCodeField() {
		return (EnumCodeField) this.getField(Entity.ID_PARENT_CODE);
	}


}