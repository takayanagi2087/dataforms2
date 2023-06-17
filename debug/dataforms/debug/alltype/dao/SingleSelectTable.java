package dataforms.debug.alltype.dao;

import java.util.Map;
import dataforms.dao.Table;
import dataforms.debug.alltype.field.SingleSelectIdField;
import dataforms.debug.alltype.field.CharItemField;
import dataforms.debug.alltype.field.VarcharItemField;
import dataforms.debug.alltype.field.SmallintItemField;
import dataforms.debug.alltype.field.IntegerIetmField;
import dataforms.debug.alltype.field.BigintItemField;
import dataforms.field.common.PresenceField;


/**
 * SingleSelectTableクラス。
 *
 */
public class SingleSelectTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public SingleSelectTable() {
		this.setAutoIncrementId(true);
		this.setComment("SingleSelectTable");
		this.addPkField(new SingleSelectIdField()); //レコードID
		this.addField(new CharItemField()); //Charフィールド
		this.addField(new VarcharItemField()); //Varcharフィールド
		this.addField(new SmallintItemField()); //Smallintフィールド
		this.addField(new IntegerIetmField()); //Integerフィールド
		this.addField(new BigintItemField()); //Bigintフィールド
		this.addField(new PresenceField("presenceItem")); //有無フィールド

	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		SingleSelectTableRelation r = new SingleSelectTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** レコードIDのフィールドID。 */
		public static final String ID_SINGLE_SELECT_ID = "singleSelectId";
		/** CharフィールドのフィールドID。 */
		public static final String ID_CHAR_ITEM = "charItem";
		/** VarcharフィールドのフィールドID。 */
		public static final String ID_VARCHAR_ITEM = "varcharItem";
		/** SmallintフィールドのフィールドID。 */
		public static final String ID_SMALLINT_ITEM = "smallintItem";
		/** IntegerフィールドのフィールドID。 */
		public static final String ID_INTEGER_IETM = "integerIetm";
		/** BigintフィールドのフィールドID。 */
		public static final String ID_BIGINT_ITEM = "bigintItem";
		/** 有無フィールドのフィールドID。 */
		public static final String ID_PRESENCE_ITEM = "presenceItem";

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
		public java.lang.Long getSingleSelectId() {
			return (java.lang.Long) this.getMap().get(Entity.ID_SINGLE_SELECT_ID);
		}

		/**
		 * レコードIDを設定します。
		 * @param singleSelectId レコードID。
		 */
		public void setSingleSelectId(final java.lang.Long singleSelectId) {
			this.getMap().put(Entity.ID_SINGLE_SELECT_ID, singleSelectId);
		}

		/**
		 * Charフィールドを取得します。
		 * @return Charフィールド。
		 */
		public java.lang.String getCharItem() {
			return (java.lang.String) this.getMap().get(Entity.ID_CHAR_ITEM);
		}

		/**
		 * Charフィールドを設定します。
		 * @param charItem Charフィールド。
		 */
		public void setCharItem(final java.lang.String charItem) {
			this.getMap().put(Entity.ID_CHAR_ITEM, charItem);
		}

		/**
		 * Varcharフィールドを取得します。
		 * @return Varcharフィールド。
		 */
		public java.lang.String getVarcharItem() {
			return (java.lang.String) this.getMap().get(Entity.ID_VARCHAR_ITEM);
		}

		/**
		 * Varcharフィールドを設定します。
		 * @param varcharItem Varcharフィールド。
		 */
		public void setVarcharItem(final java.lang.String varcharItem) {
			this.getMap().put(Entity.ID_VARCHAR_ITEM, varcharItem);
		}

		/**
		 * Smallintフィールドを取得します。
		 * @return Smallintフィールド。
		 */
		public java.lang.Short getSmallintItem() {
			return (java.lang.Short) this.getMap().get(Entity.ID_SMALLINT_ITEM);
		}

		/**
		 * Smallintフィールドを設定します。
		 * @param smallintItem Smallintフィールド。
		 */
		public void setSmallintItem(final java.lang.Short smallintItem) {
			this.getMap().put(Entity.ID_SMALLINT_ITEM, smallintItem);
		}

		/**
		 * Integerフィールドを取得します。
		 * @return Integerフィールド。
		 */
		public java.lang.Integer getIntegerIetm() {
			return (java.lang.Integer) this.getMap().get(Entity.ID_INTEGER_IETM);
		}

		/**
		 * Integerフィールドを設定します。
		 * @param integerIetm Integerフィールド。
		 */
		public void setIntegerIetm(final java.lang.Integer integerIetm) {
			this.getMap().put(Entity.ID_INTEGER_IETM, integerIetm);
		}

		/**
		 * Bigintフィールドを取得します。
		 * @return Bigintフィールド。
		 */
		public java.lang.Long getBigintItem() {
			return (java.lang.Long) this.getMap().get(Entity.ID_BIGINT_ITEM);
		}

		/**
		 * Bigintフィールドを設定します。
		 * @param bigintItem Bigintフィールド。
		 */
		public void setBigintItem(final java.lang.Long bigintItem) {
			this.getMap().put(Entity.ID_BIGINT_ITEM, bigintItem);
		}

		/**
		 * 有無フィールドを取得します。
		 * @return 有無フィールド。
		 */
		public java.lang.Object getPresenceItem() {
			return (java.lang.Object) this.getMap().get(Entity.ID_PRESENCE_ITEM);
		}

		/**
		 * 有無フィールドを設定します。
		 * @param presenceItem 有無フィールド。
		 */
		public void setPresenceItem(final java.lang.Object presenceItem) {
			this.getMap().put(Entity.ID_PRESENCE_ITEM, presenceItem);
		}


	}
	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public SingleSelectIdField getSingleSelectIdField() {
		return (SingleSelectIdField) this.getField(Entity.ID_SINGLE_SELECT_ID);
	}

	/**
	 * Charフィールドフィールドを取得します。
	 * @return Charフィールドフィールド。
	 */
	public CharItemField getCharItemField() {
		return (CharItemField) this.getField(Entity.ID_CHAR_ITEM);
	}

	/**
	 * Varcharフィールドフィールドを取得します。
	 * @return Varcharフィールドフィールド。
	 */
	public VarcharItemField getVarcharItemField() {
		return (VarcharItemField) this.getField(Entity.ID_VARCHAR_ITEM);
	}

	/**
	 * Smallintフィールドフィールドを取得します。
	 * @return Smallintフィールドフィールド。
	 */
	public SmallintItemField getSmallintItemField() {
		return (SmallintItemField) this.getField(Entity.ID_SMALLINT_ITEM);
	}

	/**
	 * Integerフィールドフィールドを取得します。
	 * @return Integerフィールドフィールド。
	 */
	public IntegerIetmField getIntegerIetmField() {
		return (IntegerIetmField) this.getField(Entity.ID_INTEGER_IETM);
	}

	/**
	 * Bigintフィールドフィールドを取得します。
	 * @return Bigintフィールドフィールド。
	 */
	public BigintItemField getBigintItemField() {
		return (BigintItemField) this.getField(Entity.ID_BIGINT_ITEM);
	}

	/**
	 * 有無フィールドフィールドを取得します。
	 * @return 有無フィールドフィールド。
	 */
	public PresenceField getPresenceItemField() {
		return (PresenceField) this.getField(Entity.ID_PRESENCE_ITEM);
	}


}
