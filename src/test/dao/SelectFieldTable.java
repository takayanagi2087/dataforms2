package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import test.field.EnumMultiSelectField;
import test.field.SelectIdField;
import dataforms.util.NumberUtil;
import dataforms.field.common.SortOrderField;
import test.field.VarcharSelectField;
import test.field.IntegerSelectField;
import test.field.PropSingleSelectField;
import test.field.EnumSingleSelectField;
import test.field.PropMultiSelectField;


/**
 * 選択肢フィールドテーブルクラス。
 *
 */
public class SelectFieldTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public SelectFieldTable() {
		this.setAutoIncrementId(true);
		this.setComment("選択肢フィールドテーブル");
		this.addPkField(new SelectIdField()); //選択肢レコードID
		this.addField(new SortOrderField()); //ソート順
		this.addField(new VarcharSelectField()); //Varchar選択肢
		this.addField(new IntegerSelectField()); //整数選択肢
		this.addField(new EnumSingleSelectField()); //列挙型単一選択
		this.addField(new EnumMultiSelectField()); //列挙型複数選択
		this.addField(new PropSingleSelectField()); //プロパティ単一選択
		this.addField(new PropMultiSelectField()); //プロパティ複数選択
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		SelectFieldTableRelation r = new SelectFieldTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** 選択肢レコードIDのフィールドID。 */
		public static final String ID_SELECT_ID = "selectId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** Varchar選択肢のフィールドID。 */
		public static final String ID_VARCHAR_SELECT = "varcharSelect";
		/** 整数選択肢のフィールドID。 */
		public static final String ID_INTEGER_SELECT = "integerSelect";
		/** 列挙型単一選択のフィールドID。 */
		public static final String ID_ENUM_SINGLE_SELECT = "enumSingleSelect";
		/** 列挙型複数選択のフィールドID。 */
		public static final String ID_ENUM_MULTI_SELECT = "enumMultiSelect";
		/** プロパティ単一選択のフィールドID。 */
		public static final String ID_PROP_SINGLE_SELECT = "propSingleSelect";
		/** プロパティ複数選択のフィールドID。 */
		public static final String ID_PROP_MULTI_SELECT = "propMultiSelect";

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
		 * 選択肢レコードIDを取得します。
		 * @return 選択肢レコードID。
		 */
		public java.lang.Long getSelectId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_SELECT_ID));
		}

		/**
		 * 選択肢レコードIDを設定します。
		 * @param selectId 選択肢レコードID。
		 */
		public void setSelectId(final java.lang.Long selectId) {
			this.getMap().put(Entity.ID_SELECT_ID, selectId);
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
		 * Varchar選択肢を取得します。
		 * @return Varchar選択肢。
		 */
		public java.lang.String getVarcharSelect() {
			return (java.lang.String) this.getMap().get(Entity.ID_VARCHAR_SELECT);
		}

		/**
		 * Varchar選択肢を設定します。
		 * @param varcharSelect Varchar選択肢。
		 */
		public void setVarcharSelect(final java.lang.String varcharSelect) {
			this.getMap().put(Entity.ID_VARCHAR_SELECT, varcharSelect);
		}

		/**
		 * 整数選択肢を取得します。
		 * @return 整数選択肢。
		 */
		public java.lang.Integer getIntegerSelect() {
			return NumberUtil.integerValueObject(this.getMap().get(Entity.ID_INTEGER_SELECT));
		}

		/**
		 * 整数選択肢を設定します。
		 * @param integerSelect 整数選択肢。
		 */
		public void setIntegerSelect(final java.lang.Integer integerSelect) {
			this.getMap().put(Entity.ID_INTEGER_SELECT, integerSelect);
		}

		/**
		 * 列挙型単一選択を取得します。
		 * @return 列挙型単一選択。
		 */
		public java.lang.String getEnumSingleSelect() {
			return (java.lang.String) this.getMap().get(Entity.ID_ENUM_SINGLE_SELECT);
		}

		/**
		 * 列挙型単一選択を設定します。
		 * @param enumSingleSelect 列挙型単一選択。
		 */
		public void setEnumSingleSelect(final java.lang.String enumSingleSelect) {
			this.getMap().put(Entity.ID_ENUM_SINGLE_SELECT, enumSingleSelect);
		}

		/**
		 * 列挙型複数選択を取得します。
		 * @return 列挙型複数選択。
		 */
		public java.util.List<?> getEnumMultiSelect() {
			return (java.util.List<?>) this.getMap().get(Entity.ID_ENUM_MULTI_SELECT);
		}

		/**
		 * 列挙型複数選択を設定します。
		 * @param enumMultiSelect 列挙型複数選択。
		 */
		public void setEnumMultiSelect(final java.util.List<?> enumMultiSelect) {
			this.getMap().put(Entity.ID_ENUM_MULTI_SELECT, enumMultiSelect);
		}

		/**
		 * プロパティ単一選択を取得します。
		 * @return プロパティ単一選択。
		 */
		public java.lang.String getPropSingleSelect() {
			return (java.lang.String) this.getMap().get(Entity.ID_PROP_SINGLE_SELECT);
		}

		/**
		 * プロパティ単一選択を設定します。
		 * @param propSingleSelect プロパティ単一選択。
		 */
		public void setPropSingleSelect(final java.lang.String propSingleSelect) {
			this.getMap().put(Entity.ID_PROP_SINGLE_SELECT, propSingleSelect);
		}

		/**
		 * プロパティ複数選択を取得します。
		 * @return プロパティ複数選択。
		 */
		public java.util.List<?> getPropMultiSelect() {
			return (java.util.List<?>) this.getMap().get(Entity.ID_PROP_MULTI_SELECT);
		}

		/**
		 * プロパティ複数選択を設定します。
		 * @param propMultiSelect プロパティ複数選択。
		 */
		public void setPropMultiSelect(final java.util.List<?> propMultiSelect) {
			this.getMap().put(Entity.ID_PROP_MULTI_SELECT, propMultiSelect);
		}


	}
	/**
	 * 選択肢レコードIDフィールドを取得します。
	 * @return 選択肢レコードIDフィールド。
	 */
	public SelectIdField getSelectIdField() {
		return (SelectIdField) this.getField(Entity.ID_SELECT_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
	}

	/**
	 * Varchar選択肢フィールドを取得します。
	 * @return Varchar選択肢フィールド。
	 */
	public VarcharSelectField getVarcharSelectField() {
		return (VarcharSelectField) this.getField(Entity.ID_VARCHAR_SELECT);
	}

	/**
	 * 整数選択肢フィールドを取得します。
	 * @return 整数選択肢フィールド。
	 */
	public IntegerSelectField getIntegerSelectField() {
		return (IntegerSelectField) this.getField(Entity.ID_INTEGER_SELECT);
	}

	/**
	 * 列挙型単一選択フィールドを取得します。
	 * @return 列挙型単一選択フィールド。
	 */
	public EnumSingleSelectField getEnumSingleSelectField() {
		return (EnumSingleSelectField) this.getField(Entity.ID_ENUM_SINGLE_SELECT);
	}

	/**
	 * 列挙型複数選択フィールドを取得します。
	 * @return 列挙型複数選択フィールド。
	 */
	public EnumMultiSelectField getEnumMultiSelectField() {
		return (EnumMultiSelectField) this.getField(Entity.ID_ENUM_MULTI_SELECT);
	}

	/**
	 * プロパティ単一選択フィールドを取得します。
	 * @return プロパティ単一選択フィールド。
	 */
	public PropSingleSelectField getPropSingleSelectField() {
		return (PropSingleSelectField) this.getField(Entity.ID_PROP_SINGLE_SELECT);
	}

	/**
	 * プロパティ複数選択フィールドを取得します。
	 * @return プロパティ複数選択フィールド。
	 */
	public PropMultiSelectField getPropMultiSelectField() {
		return (PropMultiSelectField) this.getField(Entity.ID_PROP_MULTI_SELECT);
	}


}
