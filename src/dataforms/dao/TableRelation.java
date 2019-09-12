package dataforms.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dataforms.util.StringUtil;

/**
 * テーブル関係を定義するクラスです。
 * <pre>
 * ver 1.0xではHogeTable#getJoinConditionメソッドにテーブルの関係を記述していましたが、
 * 開発ツールでTableクラスを修正した場合、この定義を上書きしてしまうということがありました。
 * ver1.1xではその対策として、HogeTableRelationクラスを用意し、このクラスでテーブルの
 * 関係を定義するように修正しました。 
 * </pre>
 */
public class TableRelation {
	
	
	/**
	 * 外部キー制約クラス。
	 *
	 */
	public static class ForeignKey {
		
		/**
		 * Logger.
		 */
		private static Logger logger = Logger.getLogger(ForeignKey.class);

		/**
		 * 制約名。
		 */
		private String constraintName = null;
		/**
		 * 参照元テーブルのインスタンス。
		 */
		private Table table = null;
		/**
		 * 参照元テーブルのフィールドIDリスト。
		 */
		private String[] fieldIdList = null;
		/**
		 * 参照先テーブルのクラス。
		 */
		private Class<? extends Table> referenceTableClass = null;
		/**
		 * 参照フィールドIDリスト。
		 */
		private String[] referenceFieldIdList = null;
		
		/**
		 * コンストラクタ。
		 * @param constraintName 制約名。
		 * @param fieldIdList フィールドIDのリスト。
		 * @param refTableClass 参照テーブルクラス。
		 * @param refFieldIdList 参照フィールドIDリスト。
		 */
		public ForeignKey(final String constraintName, final String [] fieldIdList, final Class<? extends Table> refTableClass, final String[]  refFieldIdList) {
			this.constraintName = constraintName;
			this.fieldIdList = fieldIdList;
			this.referenceTableClass = refTableClass;
			this.referenceFieldIdList = refFieldIdList;
		}

		/**
		 * コンストラクタ。
		 * <pre>
		 * 参照元フィールドIDのリストと参照先フィールドIDのリストが同じ場合のコンストラクタ。
		 * </pre>
		 * @param constraintName 制約名。
		 * @param fieldIdList フィールドIDのリスト。
		 * @param refTableClass 参照テーブルクラス。
		 */
		public ForeignKey(final String constraintName, final String [] fieldIdList, final Class<? extends Table> refTableClass) {
			this.constraintName = constraintName;
			this.fieldIdList = fieldIdList;
			this.referenceTableClass = refTableClass;
			this.referenceFieldIdList = fieldIdList;
		}

		/**
		 * コンストラクタ。
		 * <pre>
		 * フィールド数が1件の場合。
		 * </pre>
		 * @param constraintName 制約名。
		 * @param fieldId フィールドID。
		 * @param refTableClass 参照先テーブルクラス。
		 * @param refFieldId 参照フィールドID。
		 */
		public ForeignKey(final String constraintName, final String fieldId, final Class<? extends Table> refTableClass, final String refFieldId) {
			this.constraintName = constraintName;
			String [] fieldIdList = new String[1];
			fieldIdList[0] = fieldId;
			this.fieldIdList = fieldIdList;
			this.referenceTableClass = refTableClass;
			String [] refFieldIdList = new String[1];
			refFieldIdList[0] = refFieldId;
			this.referenceFieldIdList = refFieldIdList;
		}
		
		/**
		 * コンストラクタ。
		 * <pre>
		 * フィールド数が1件でかつ参照先フィールドIDと同じ場合。
		 * </pre>
		 * @param constraintName 制約名。
		 * @param fieldId フィールドID。
		 * @param refTableClass 参照先テーブルクラス。
		 */
		public ForeignKey(final String constraintName, final String fieldId, final Class<? extends Table> refTableClass) {
			this.constraintName = constraintName;
			String [] fieldIdList = new String[1];
			fieldIdList[0] = fieldId;
			this.fieldIdList = fieldIdList;
			this.referenceTableClass = refTableClass;
			String [] refFieldIdList = new String[1];
			refFieldIdList[0] = fieldId;
			this.referenceFieldIdList = refFieldIdList;
		}
		
		/**
		 * 制約名称を取得します。
		 * @return 制約名称。
		 */
		public String getConstraintName() {
			return constraintName;
		}

		/**
		 * 制約名称を設定します。
		 * @param constraintName 制約名称。
		 */
		public void setConstraintName(final String constraintName) {
			this.constraintName = constraintName;
		}

		/**
		 * 参照元テーブルを取得します。
		 * @return テーブル。
		 */
		public Table getTable() {
			return table;
		}

		/**
		 * 参照元テーブルを設定します。
		 * @param table テーブル。
		 */
		public void setTable(final Table table) {
			this.table = table;
		}

		/**
		 * 参照元フィールドIDのリストを取得します。
		 * @return リンク元フィールドIDのリスト。
		 */
		public String[] getFieldIdList() {
			return fieldIdList;
		}

		/**
		 * 参照元フィールドIDのリストを設定します。
		 * @param fieldIdList リンク元フィールドIDのリスト。
		 */
		public void setFieldIdList(final String[] fieldIdList) {
			this.fieldIdList = fieldIdList;
		}

		/**
		 * 参照先テーブルのクラスを取得します。
		 * @return 参照テーブルのクラス。
		 */
		public Class<? extends Table> getReferenceTableClass() {
			return referenceTableClass;
		}

		/**
		 * 参照先テーブルのクラスを設定します。
		 * @param referenceTableClass 参照テーブルのクラス。
		 */
		public void setReferenceTableClass(final Class<? extends Table> referenceTableClass) {
			this.referenceTableClass = referenceTableClass;
		}

		/**
		 * 参照先フィールドIDのリストを取得します。
		 * @return 参照先フィールドID。
		 */
		public String[] getReferenceFieldIdList() {
			return referenceFieldIdList;
		}

		/**
		 * 参照先フィールドIDのリストを設定します。
		 * @param referenceFieldIdList 参照先フィールドIDのリスト。
		 */
		public void setReferenceFieldIdList(final String[] referenceFieldIdList) {
			this.referenceFieldIdList = referenceFieldIdList;
		}
		
		/**
		 * 外部キーのフィールドリストを取得します。
		 * @param constname 外部キー制約名称。
		 * @param dbfklist テーブルに付随するDBの外部キー情報。
		 * @return 外部キーのフィールドリスト。
		 */
		private List<Map<String, Object>> getForeignKeyFieldList(final String constname, final List<Map<String, Object>> dbfklist) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m: dbfklist) {
				String fkname = (String) m.get("fkName");
				if (constname.equalsIgnoreCase(fkname)) {
					list.add(m);
				}
			}
			return list;
		}
		
		/**
		 * ソース上の外部キーとDB上の外部キーの違いを検出します。
		 * @param dbfklist DB上の外部キー。
		 * @return 異なっている場合false
		 * @throws Exception 例外。
		 */
		public boolean structureAccords(final List<Map<String, Object>> dbfklist) throws Exception {
			String constname = StringUtil.camelToSnake(this.getConstraintName());
			String tblname = this.getReferenceTableClass().newInstance().getTableName();
			String[] flist = this.getFieldIdList();
			String[] rflist = this.getReferenceFieldIdList();
			
			List<Map<String, Object>> fldlist = this.getForeignKeyFieldList(constname, dbfklist);
			logger.debug("fldlist.size()=" + fldlist.size());
			if (flist.length != fldlist.size()) {
				logger.info("foreign key " + constname + " field count missmatch.");
				return false;
			}
			if (rflist.length != fldlist.size()) {
				logger.info("foreign key " + constname + " field count missmatch.");
				return false;
			}
			int fidx = 0;
			for (Map<String, Object> m: fldlist) {
				String fld = StringUtil.camelToSnake(flist[fidx]);
				String rfld = StringUtil.camelToSnake(rflist[fidx]);
				fidx++;
				String pktable = (String) m.get("pktableName");
				String fkcol = (String) m.get("fkcolumnName");
				String pkcol = (String) m.get("pkcolumnName");
				if (!tblname.equalsIgnoreCase(pktable)) {
					logger.info("foreign key " + constname + " tabe name missmatch.(" + tblname + "," + pktable +  ")");
					return false;
				}
				if (!fld.equalsIgnoreCase(fkcol)) {
					logger.info("foreign key " + constname + " fk column name missmatch.(" + fld + "," + fkcol +  ")");
					return false;
				}
				if (!rfld.equalsIgnoreCase(pkcol)) {
					logger.info("foreign key " + constname + " pk column name missmatch.(" + rfld + "," + pkcol +  ")");
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 関係を定義するテーブルのインスタンス。
	 */
	private Table table = null;
	
	/**
	 * コンストラクタ。
	 * @param table 関係を定義するテーブルのインスタンス。
	 */
	public TableRelation(final Table table) {
		this.table = table;
		List<ForeignKey> fklist = this.getForeignKeyList();
		for (ForeignKey fk: fklist) {
			fk.setTable(table);
		}
	}

	/**
	 * 関係を定義するテーブルのインスタンスを取得します。
	 * @return 関係を定義するテーブルのインスタンス。
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * 外部キーのリストを取得します。
	 * @return 外部キーのリスト。
	 */
	public List<ForeignKey> getForeignKeyList() {
		return new ArrayList<ForeignKey>();
	}
	
	
	/**
	 * テーブルの結合条件を取得します。
	 * <pre>
	 * このテーブルとjoinTableとの結合条件を作成します。
	 * 通常は指定されたjoinTableのクラスをinstansof演算子で判定し結合条件を作成します。
	 * 一回の問い合わせで、同じテーブルを複数回結合する場合は、それぞれのテーブルを
	 * 別インスタンスで作成し別のaliasを設定し、そのaliasで判定します。
	 * </pre>
	 * @param joinTable 結合対象テーブル。
	 * @param alias 結合対象テーブルの別名。
	 * @return 結合条件。
	 */
	public String getJoinCondition(final Table joinTable, final String alias) {
		List<ForeignKey> fklist = this.getForeignKeyList();
		for (ForeignKey fk: fklist) {
			Class<? extends Table> tc = fk.getReferenceTableClass();
			if (tc.isInstance(joinTable)) {
				StringBuilder sb = new StringBuilder();
				String[] flist = fk.getFieldIdList();
				String[] rflist = fk.getReferenceFieldIdList();
				for (int i = 0; i < flist.length; i++) {
					if (sb.length() > 0) {
						sb.append(" and ");
					}
					String cnd = this.getTable().getLinkFieldCondition(flist[i], joinTable, alias, rflist[i]);
					sb.append(cnd);
				}
				return sb.toString();
			}
		}
		return null;
	}
}
