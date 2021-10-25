package test.dao;

import java.util.Map;

import dataforms.app.enumtype.dao.EnumNameTable;
import dataforms.app.enumtype.dao.EnumTable;
import dataforms.app.enumtype.field.EnumCodeField;
import dataforms.app.enumtype.field.EnumGroupCodeField;
import dataforms.app.enumtype.field.EnumIdField;
import dataforms.app.enumtype.field.EnumNameField;
import dataforms.app.enumtype.field.MemoField;
import dataforms.app.enumtype.field.ParentIdField;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.field.base.FieldList;
import dataforms.field.common.CreateTimestampField;
import dataforms.field.common.CreateUserIdField;
import dataforms.field.common.LangCodeField;
import dataforms.field.common.SortOrderField;
import dataforms.field.common.UpdateTimestampField;
import dataforms.field.common.UpdateUserIdField;
import dataforms.field.sqlfunc.AliasField;
import dataforms.util.NumberUtil;



/**
 * 問い合わせクラスです。
 *
 */
public class EnumQuery extends Query {

	/**
	 * Logger.
	 */
//	private Logger logger = LogManager.getLogger(EnumQuery.class);

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
	private EnumTable enumTablePe = null;

	/**
	 * 列挙型テーブルを取得します。
	 * @return 列挙型テーブル。
	 */
	public EnumTable getEnumTablePe() {
		return this.enumTablePe;
	}

	/**
	 * 列挙型名称テーブル。
	 */
	private EnumNameTable enumNameTableNm = null;

	/**
	 * 列挙型名称テーブルを取得します。
	 * @return 列挙型名称テーブル。
	 */
	public EnumNameTable getEnumNameTableNm() {
		return this.enumNameTableNm;
	}

	/**
	 * 列挙型名称テーブル。
	 */
	private EnumNameTable enumNameTablePnm = null;

	/**
	 * 列挙型名称テーブルを取得します。
	 * @return 列挙型名称テーブル。
	 */
	public EnumNameTable getEnumNameTablePnm() {
		return this.enumNameTablePnm;
	}


	/**
	 * コンストラクタ.
	 */
	public EnumQuery() {
		this.setComment("列挙型問合せ");
		this.setDistinct(false);
		this.enumTable = new EnumTable();
		this.enumTable.setAlias("m");
		this.enumTablePe = new EnumTable();
		this.enumTablePe.setAlias("pe");
		this.enumNameTableNm = new EnumNameTable();
		this.enumNameTableNm.setAlias("nm");
		this.enumNameTablePnm = new EnumNameTable();
		this.enumNameTablePnm.setAlias("pnm");

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
			, this.enumNameTableNm.getLangCodeField()
			, this.enumNameTableNm.getEnumNameField()
			, new AliasField("parentLangCode", this.enumNameTablePnm.getLangCodeField().setComment(""))
			, new AliasField("parentName", this.enumNameTablePnm.getEnumNameField().setComment(""))
			, new AliasField("pid", this.enumTablePe.getParentIdField())
			, new AliasField("parentCode", this.enumTablePe.getEnumCodeField())
		));
		this.setMainTable(enumTable);
		this.addInnerJoin(enumTablePe);
		this.addInnerJoin(enumNameTableNm);
		this.addInnerJoin(enumNameTablePnm, (final Table joinTable) -> {
			return this.enumTablePe.getLinkFieldCondition(EnumTable.Entity.ID_ENUM_ID, joinTable);
		});

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
		/** 言語コードのフィールドID。 */
		public static final String ID_LANG_CODE = "langCode";
		/** 列挙型名称のフィールドID。 */
		public static final String ID_ENUM_NAME = "enumName";
		/** のフィールドID。 */
		public static final String ID_PARENT_LANG_CODE = "parentLangCode";
		/** のフィールドID。 */
		public static final String ID_PARENT_NAME = "parentName";
		/** 親IDフィールドのフィールドID。 */
		public static final String ID_PID = "pid";
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
		 * 言語コードを取得します。
		 * @return 言語コード。
		 */
		public java.lang.String getLangCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_LANG_CODE);
		}

		/**
		 * 言語コードを設定します。
		 * @param langCode 言語コード。
		 */
		public void setLangCode(final java.lang.String langCode) {
			this.getMap().put(Entity.ID_LANG_CODE, langCode);
		}

		/**
		 * 列挙型名称を取得します。
		 * @return 列挙型名称。
		 */
		public java.lang.String getEnumName() {
			return (java.lang.String) this.getMap().get(Entity.ID_ENUM_NAME);
		}

		/**
		 * 列挙型名称を設定します。
		 * @param enumName 列挙型名称。
		 */
		public void setEnumName(final java.lang.String enumName) {
			this.getMap().put(Entity.ID_ENUM_NAME, enumName);
		}

		/**
		 * 言語コードを取得します。
		 * @return 言語コード。
		 */
		public java.lang.String getParentLangCode() {
			return (java.lang.String) this.getMap().get(Entity.ID_PARENT_LANG_CODE);
		}

		/**
		 * 言語コードを設定します。
		 * @param parentLangCode 言語コード。
		 */
		public void setParentLangCode(final java.lang.String parentLangCode) {
			this.getMap().put(Entity.ID_PARENT_LANG_CODE, parentLangCode);
		}

		/**
		 * 列挙型名称を取得します。
		 * @return 列挙型名称。
		 */
		public java.lang.String getParentName() {
			return (java.lang.String) this.getMap().get(Entity.ID_PARENT_NAME);
		}

		/**
		 * 列挙型名称を設定します。
		 * @param parentName 列挙型名称。
		 */
		public void setParentName(final java.lang.String parentName) {
			this.getMap().put(Entity.ID_PARENT_NAME, parentName);
		}

		/**
		 * 親IDフィールドを取得します。
		 * @return 親IDフィールド。
		 */
		public java.lang.Long getPid() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_PID));
		}

		/**
		 * 親IDフィールドを設定します。
		 * @param pid 親IDフィールド。
		 */
		public void setPid(final java.lang.Long pid) {
			this.getMap().put(Entity.ID_PID, pid);
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
	 * 言語コードフィールドを取得します。
	 * @return 言語コードフィールド。
	 */
	public LangCodeField getLangCodeField() {
		return (LangCodeField) this.getField(Entity.ID_LANG_CODE);
	}

	/**
	 * 列挙型名称フィールドを取得します。
	 * @return 列挙型名称フィールド。
	 */
	public EnumNameField getEnumNameField() {
		return (EnumNameField) this.getField(Entity.ID_ENUM_NAME);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public LangCodeField getParentLangCodeField() {
		return (LangCodeField) this.getField(Entity.ID_PARENT_LANG_CODE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public EnumNameField getParentNameField() {
		return (EnumNameField) this.getField(Entity.ID_PARENT_NAME);
	}

	/**
	 * 親IDフィールドフィールドを取得します。
	 * @return 親IDフィールドフィールド。
	 */
	public ParentIdField getPidField() {
		return (ParentIdField) this.getField(Entity.ID_PID);
	}

	/**
	 * 列挙型コードフィールドを取得します。
	 * @return 列挙型コードフィールド。
	 */
	public EnumCodeField getParentCodeField() {
		return (EnumCodeField) this.getField(Entity.ID_PARENT_CODE);
	}


}