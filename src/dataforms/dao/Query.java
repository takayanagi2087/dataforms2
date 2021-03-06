package dataforms.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.field.sqlfunc.AliasField;
import dataforms.field.sqlfunc.GroupSummaryField;
import dataforms.field.sqlfunc.SqlField;
import dataforms.util.StringUtil;


/**
 * 問い合わせクラス。
 * <pre>
 * SQLのSELECT文に対応するクラスです。
 * DAOに対して、このクラスのインスタンスを渡すと、DBMSに対応したSqlGeneratorが
 * 適切なSQLを作成し、問い合わせを行います。
 * </pre>
 */
public class Query {
    /**
     * Logger.
     */
//    private static Logger log = Logger.getLogger(Query.class.getName());

	/**
	 * フィールドリスト。
	 * <pre>
	 * 結果リストに格納するフィールドリスト.
	 * </pre>
	 */
	private FieldList fieldList = new FieldList();

	/**
	 * distinctフラグ。
	 * <pre>
	 * trueの場合生成するSQLは"select distinct ..."となります。
	 * </pre>
	 */
	private boolean distinct = false;

	/**
	 * 結合元のテーブル。
	 */
	private Table mainTable = null;
	/**
	 * 内部結合テーブルリスト。
	 */
	private TableList joinTableList = null;
	/**
	 * 左外部結合テーブルリスト。
	 */
	private TableList leftJoinTableList = null;
	/**
	 * 右外部結合テーブルリスト。
	 */
	private TableList rightJoinTableList = null;

	/**
	 * 問い合わせフォームフィールドリスト。
	 *
	 */
	private FieldList conditionFieldList = null;
	/**
	 * 問い合わせフォームの入力データ。
	 */
	private Map<String, Object> conditionData = null;

	/**
	 * ソートフィールドリスト。
	 */
	private FieldList orderByFieldList = null;

	/**
	 * 結合情報リスト。
	 */
	private List<JoinInfo> joinInfoList = null;

	/**
	 * フィールドとテーブル別名とのマップ。
	 */
	private Map<String, String> fieldTableAliasMap = null;

	/**
	 * 削除フラグの有効性。
	 */
	private boolean effectivenessOfDeleteFlag = true;

	/**
	 * 固定検索条件。
	 */
	private String condition = null;

	/**
	 * コメント。
	 */
	private String comment = null;

	/**
	 * コンストラクタ。
	 */
	public Query() {

	}

	/**
	 * コメントを取得します。
	 * @return コメント。
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * コメント。
	 * @param comment コメント。
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}


	/**
	 * 問合せに対応するHtmlTableのIDを取得します。
	 * @return 問合せに対応するHtmlTableのID。
	 */
	public String getListId() {
		Table mt = this.getMainTable();
		String tableName = mt.getTableName();
		String tid = StringUtil.snakeToCamel(tableName) + "List";
		return tid;
	}

	/**
	 * フィールドリストを取得します。
	 * @return フィールドリスト。
	 */
	public FieldList getFieldList() {
		return fieldList;
	}


	/**
	 * フィールドリストを設定します。
	 * @param fieldList フィールドリスト。
	 */
	public void setFieldList(final FieldList fieldList) {
		this.fieldList = fieldList;
	}

	/**
	 * 指定されたIDに対応するフィールドを取得します。
	 * @param id フィールドID。
	 * @return フィールド。
	 */
	public Field<?> getField(final String id) {
		Field<?> field = this.fieldList.get(id);
		if (field instanceof GroupSummaryField) {
			GroupSummaryField<?> f = (GroupSummaryField<?>) field;
			return f.getTargetField();
		} else if (field instanceof SqlField) {
			SqlField f = (SqlField) field;
			return f.getTargetField();
		} else {
			return field;
		}
	}

	/**
	 * 結合元のテーブルを取得します。
	 * @return 結合元のテーブル。
	 */
	public Table getMainTable() {
		return mainTable;
	}

	/**
	 * 結合元のテーブルを設定します。
	 * @param mainTable 結合元のテーブル。
	 */
	public void setMainTable(final Table mainTable) {
		this.mainTable = mainTable;
	}

	/**
	 * 内部結合するテーブルリストを取得します。
	 * @return 内部結合するテーブルリスト。
	 * @deprecated {@link #getJoinInfoList()}に置き換えてください。
	 */
	@Deprecated
	public TableList getJoinTableList() {
		return joinTableList;
	}

	/**
	 * 内部結合するテーブルリストを設定します。
	 * @param joinTableList 内部結合するテーブリスト。
	 * @deprecated {@link #addInnerJoin(Table, JoinConditionInterface)}
	 */
	@Deprecated
	public void setJoinTableList(final TableList joinTableList) {
		this.joinTableList = joinTableList;
	}

	/**
	 * 左外部結合するテーブルリストを取得します。
	 * @return 左外部結合するテーブルリスト。
	 * @deprecated {@link #getJoinInfoList()}に置き換えてください。
	 */
	@Deprecated
	public TableList getLeftJoinTableList() {
		return leftJoinTableList;
	}

	/**
	 * 左外部結合するテーブルリストを設定します。
	 * @param leftJoinTableList 左外部結合するテーブルリスト。
	 * @deprecated {@link #addLeftJoin(Table, JoinConditionInterface)}
	 */
	@Deprecated
	public void setLeftJoinTableList(final TableList leftJoinTableList) {
		this.leftJoinTableList = leftJoinTableList;
	}

	/**
	 * 右外部結合するテーブルリストを取得します。
	 * @return 右外部結合するテーブルリスト。
	 * @deprecated {@link #getJoinInfoList()}に置き換えてください。
	 */
	@Deprecated
	public TableList getRightJoinTableList() {
		return rightJoinTableList;
	}

	/**
	 * 右外部結合するテーブルリストを設定します。
	 * @param rightJoinTableList 右外部結合するテーブルリスト。
	 * @deprecated {@link #addRightJoin(Table, JoinConditionInterface)}
	 */
	@Deprecated
	public void setRightJoinTableList(final TableList rightJoinTableList) {
		this.rightJoinTableList = rightJoinTableList;
	}


	/**
	 * テーブル結合情報。
	 *
	 */
	public static class JoinInfo {
		/**
		 * 内部結合。
		 */
		public static final String INNER_JOIN = " inner join ";
		/**
		 * 左結合。
		 */
		public static final String LEFT_JOIN = " left join ";
		/**
		 * 右結合。
		 */
		public static final String RIGHT_JOIN = " right join ";

		/**
		 * 結合タイプ。
		 */
		private String joinType = null;
		/**
		 * 結合するテーブル。
		 */
		private Table joinTable = null;
		/**
		 * 結合条件関数インターフェース。
		 */
		private JoinConditionInterface joinCondition = null;

		/**
		 * 生成された結合条件式。
		 */
		private String generatedCondition = null;


		/**
		 * コンストラクタ。
		 * @param joinType 結合タイプ。
		 * @param joinTable 結合するテーブル。
		 * @param joinCondition 結合条件関数インターフェース。
		 */
		public JoinInfo(final String joinType, final Table joinTable, final JoinConditionInterface joinCondition) {
			this.joinType = joinType;
			this.joinTable = joinTable;
			this.joinCondition = joinCondition;
		}
		/**
		 * 結合タイプを取得します。
		 * @return 結合タイプ。
		 */
		public String getJoinType() {
			return joinType;
		}

		/**
		 * 結合テーブルを取得します。
		 * @return 結合タイプ。
		 */
		public Table getJoinTable() {
			return joinTable;
		}

		/**
		 * 結合条件関数インターフェースを取得します。
		 * @return 結合条件関数インターフェース。
		 */
		public JoinConditionInterface getJoinCondition() {
			return joinCondition;
		}


		/**
		 * 生成された条件式を取得します。
		 * @return 生成された条件式。
		 */
		public String getGeneratedCondition() {
			return generatedCondition;
		}

		/**
		 * 生成された条件式を設定します。
		 * @param generatedCondition 生成された条件式。
		 */
		public void setGeneratedCondition(final String generatedCondition) {
			this.generatedCondition = generatedCondition;
		}

	}


	/**
	 * 結合情報リストを追加します。
	 * @param joinInfo 結合情報リスト。
	 */
	protected void addJoinInfo(final JoinInfo joinInfo) {
		if (this.joinInfoList == null) {
			this.joinInfoList = new ArrayList<JoinInfo>();
		}
		this.joinInfoList.add(joinInfo);
	}

	/**
	 * 内部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addInnerJoin(final Table table, final String alias, final JoinConditionInterface joinCondition) {
		if (alias != null) {
			table.setAlias(alias);
		}
		this.addJoinInfo(new JoinInfo(JoinInfo.INNER_JOIN, table, joinCondition));
	}

	/**
	 * 内部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addInnerJoin(final Table table, final JoinConditionInterface joinCondition) {
		this.addInnerJoin(table, null, joinCondition);
	}

	/**
	 * 内部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 */
	public void addInnerJoin(final Table table, final String alias) {
		this.addInnerJoin(table, alias, null);
	}

	/**
	 * 内部結合を追加します。
	 * @param table 結合するテーブル。
	 */
	public void addInnerJoin(final Table table) {
		this.addInnerJoin(table, null, null);
	}

	/**
	 * 左外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addLeftJoin(final Table table, final String alias, final JoinConditionInterface joinCondition) {
		if (alias != null) {
			table.setAlias(alias);
		}
		this.addJoinInfo(new JoinInfo(JoinInfo.LEFT_JOIN, table, joinCondition));
	}

	/**
	 * 左外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addLeftJoin(final Table table, final JoinConditionInterface joinCondition) {
		this.addLeftJoin(table, null, joinCondition);
	}

	/**
	 * 左外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 */
	public void addLeftJoin(final Table table, final String alias) {
		this.addLeftJoin(table, alias, null);
	}

	/**
	 * 左外部結合を追加します。
	 * @param table 結合するテーブル。
	 */
	public void addLeftJoin(final Table table) {
		this.addLeftJoin(table, null, null);
	}


	/**
	 * 右外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addRightJoin(final Table table, final String alias, final JoinConditionInterface joinCondition) {
		if (alias != null) {
			table.setAlias(alias);
		}
		this.addJoinInfo(new JoinInfo(JoinInfo.RIGHT_JOIN, table, joinCondition));
	}


	/**
	 * 右外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param joinCondition 結合条件関数インターフェース。
	 */
	public void addRightJoin(final Table table, final JoinConditionInterface joinCondition) {
		this.addRightJoin(table, null, joinCondition);
	}

	/**
	 * 右外部結合を追加します。
	 * @param table 結合するテーブル。
	 * @param alias 別名。
	 */
	public void addRightJoin(final Table table, final String alias) {
		this.addRightJoin(table, alias, null);
	}


	/**
	 * 右外部結合を追加します。
	 * @param table 結合するテーブル。
	 */
	public void addRightJoin(final Table table) {
		this.addRightJoin(table, null, null);
	}

	/**
	 * 旧形式のjoinTableListをjoinInfoListに展開する。
	 * @param type 結合タイプ。
	 * @param tlist テーブルリスト。
	 */
	private void addJoinTableList(final String type, final TableList tlist) {
		if (tlist != null) {
			for (Table t: tlist) {
				this.addJoinInfo(new JoinInfo(type, t, null));
			}
		}
	}

	/**
	 * setXXXJoinTableListで設定した、結合情報をjoinInfoListに転記する。
	 */
	public void buildJoinInfoList() {
		this.addJoinTableList(JoinInfo.INNER_JOIN, this.joinTableList);
		this.addJoinTableList(JoinInfo.LEFT_JOIN, this.leftJoinTableList);
		this.addJoinTableList(JoinInfo.RIGHT_JOIN, this.rightJoinTableList);
		this.joinTableList = null;
		this.leftJoinTableList = null;
		this.rightJoinTableList = null;
	}

	/**
	 * 結合情報リストを取得します。
	 * @return 結合情報リスト。
	 */
	public List<JoinInfo> getJoinInfoList() {
		return joinInfoList;
	}

	/**
	 * 条件フィールドリストを設定します。
	 * <pre>
	 * 検索条件に使用するフィールドリスト指定します。
	 * このフィールドリストに存在しかつ問い合わせフォームの入力データが存在した場合、
	 * 検索の条件式を生成します。
	 * このリスト中のフィールドにはMatchTypeを指定し、検索条件式の生成を制御できます。
	 * </pre>
	 *　
	 *　<table>
	 *  	<caption>MatchType一覧</caption>
	 *		<thead>
	 *			<tr>
	 *				<th>条件タイプ</th><th>意味</th><th>生成条件式</th><th>データ編集</th>
	 *			</tr>
	 *		</thead>
	 *		<tbody>
	 *			<tr>
	 *				<td>FULL</td><td>完全一致</td><td>field_id = :field_id</td><td></td>
	 *				<td>PART</td><td>部分一致</td><td>field_id like :field_id</td><td>'%入力値%'</td>
	 *				<td>BEGIN</td><td>先頭一致</td><td>field_id like :field_id</td><td>'入力値%'</td>
	 *				<td>END</td><td>末尾一致</td><td>field_id like :field_id</td><td>'%入力値'</td>
	 *				<td>RANGE_FROM</td><td>範囲開始</td><td>field_id &gt;= :field_id</td><td></td>
	 *				<td>RANGE_TO</td><td>範囲終了</td><td>field_id &lt;= :field_id</td><td></td>
	 *			</tr>
	 *		</tbody>
	 *  </table>
	 *
	 * @param conditionFieldList 条件フィールドリスト。
	 */
	public void setConditionFieldList(final FieldList conditionFieldList) {
		this.conditionFieldList = conditionFieldList;
		// 無条件に条件式を生成するように、仮パラメータを設定.
		Map<String, Object> cond = new HashMap<String, Object>();
		for (Field<?> f : conditionFieldList) {
			cond.put(f.getId(), new Object());
		}
		this.setConditionData(cond);
	}

	/**
	 * 問い合わせフォームのフィールドリストを設定します。
	 * <pre>
	 * 検索条件に使用するフィールドリスト指定します。
	 * このフィールドリストに存在しかつ問い合わせフォームの入力データが存在した場合、
	 * 検索の条件式を生成します。
	 * このリスト中のフィールドにはMatchTypeを指定し、検索条件式の生成を制御できます。
	 * </pre>
	 *　
	 *　<table>
	 *  	<caption>MatchType一覧</caption>
	 *		<thead>
	 *			<tr>
	 *				<th>条件タイプ</th><th>意味</th><th>生成条件式</th><th>データ編集</th>
	 *			</tr>
	 *		</thead>
	 *		<tbody>
	 *			<tr>
	 *				<td>FULL</td><td>完全一致</td><td>field_id = :field_id</td><td></td>
	 *				<td>PART</td><td>部分一致</td><td>field_id like :field_id</td><td>'%入力値%'</td>
	 *				<td>BEGIN</td><td>先頭一致</td><td>field_id like :field_id</td><td>'入力値%'</td>
	 *				<td>END</td><td>末尾一致</td><td>field_id like :field_id</td><td>'%入力値'</td>
	 *				<td>RANGE_FROM</td><td>範囲開始</td><td>field_id &gt;= :field_id</td><td></td>
	 *				<td>RANGE_TO</td><td>範囲終了</td><td>field_id &lt;= :field_id</td><td></td>
	 *			</tr>
	 *		</tbody>
	 *  </table>
	 *
	 * @param queryFormFieldList 条件フィールドリスト。
	 * @deprecated 別のメソッドに置き換えられました {@link #setConditionFieldList(FieldList)}
	 */
	@Deprecated
	public void setQueryFormFieldList(final FieldList queryFormFieldList) {
		this.setConditionFieldList(queryFormFieldList);
	}

	/**
	 * 条件フィールドリストを取得します。
	 * @return 条件フィールドリスト。
	 */
	public FieldList getConditionFieldList() {
		return conditionFieldList;
	}

	/**
	 * 問い合わせフォームのフィールドリストを取得します。
	 * @return 条件フィールドリスト。
	 * @deprecated 別のメソッドに置き換えられました {@link #getConditionFieldList()}
	 */
	@Deprecated
	public FieldList getQueryFormFieldList() {
		return conditionFieldList;
	}


	/**
	 * 条件データを取得します。
	 * @return 問い合わせフォームの入力データ。
	 */
	public Map<String, Object> getConditionData() {
		return conditionData;
	}

	/**
	 * 問い合わせフォームの入力データを取得します。
	 * @return 問い合わせフォームの入力データ。
	 * @deprecated {@link #getConditionData()}を使用してください。
	 */
	@Deprecated
	public Map<String, Object> getQueryFormData() {
		return conditionData;
	}

	/**
	 * 条件データを設定します。
	 * @param conditionData 問い合わせフォームの入力データ。
	 */
	public void setConditionData(final Map<String, Object> conditionData) {
		this.conditionData = conditionData;
	}

	/**
	 * 問い合わせフォームの入力データを設定します。
	 * @param queryFormData 問い合わせフォームの入力データ。
	 * @deprecated {@link #setConditionData(Map)}を使用してください。
	 */
	@Deprecated
	public void setQueryFormData(final Map<String, Object> queryFormData) {
		this.conditionData = queryFormData;
	}

	/**
	 * 固定検索条件を取得します。
	 * @return 固定検索条件。
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * 固定検索条件を取得します。
	 * @param condition 固定検索条件。
	 */
	public void setCondition(final String condition) {
		this.condition = condition;
	}

	/**
	 * フィールドとテーブル別名とのマップを設定します。
	 * @param fieldTableAliasMap フィールドとテーブル別名とのマップ。
	 */
	public void setFieldTableAliasMap(final Map<String, String> fieldTableAliasMap) {
		this.fieldTableAliasMap = fieldTableAliasMap;
	}

	/**
	 * マッチ対象のフィールドの構文を取得します。
	 * @param field フィールド。
	 * @return フィールド式。
	 */
	public String getMatchFieldSql(final Field<?> field) {
		String fid = field.getMatchFieldId();
		String t = null;
		if (field.getTable() != null) {
			t = field.getTable().getAlias();
		}
		if (t == null) {
			if (this.fieldTableAliasMap != null) {
				t = this.fieldTableAliasMap.get(fid);
			}
		}
		if (t != null) {
			String ret = t + "." + StringUtil.camelToSnake(fid);
			return ret;
		}
		return null;
	}

	/**
	 * order byフィールドのSQLを生成します。
	 * @param field フィールドID。
	 * @return order by フィールドのSQL。
	 */
	public String getOrderByFieldSql(final Field<?> field) {
		StringBuilder sb = new StringBuilder();
		sb.append(field.getTable().getAlias());
		sb.append(".");
		if (field instanceof AliasField) {
			sb.append(StringUtil.camelToSnake(((AliasField) field).getTargetField().getId()));
		} else {
			sb.append(StringUtil.camelToSnake(field.getId()));
		}
		if (field.getSortOrder() == Field.SortOrder.ASC) {
			sb.append(" asc");
		} else {
			sb.append(" desc");
		}
		return sb.toString();
	}

	/**
	 * group byフィールドのSQLを生成します。
	 * @param field フィールドID。
	 * @return group by フィールドのSQL。
	 */
	public String getGroupByFieldSql(final Field<?> field) {
		StringBuilder sb = new StringBuilder();
		sb.append(field.getTable().getAlias());
		sb.append(".");
		if (field instanceof AliasField) {
			sb.append(StringUtil.camelToSnake(((AliasField) field).getTargetField().getId()));
		} else {
			sb.append(StringUtil.camelToSnake(field.getId()));
		}
		return sb.toString();
	}


	/**
	 * distinctフラグを取得します。
	 * @return distinctフラグ。
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * distinctフラグを設定します。
	 * @param distinct distinctフラグ。
	 */
	public void setDistinct(final boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * Group by対象のフィールドリストを取得します。
	 * @return Group by対象のフィールドリスト。
	 */
	public FieldList getGroupByFieldList() {
		FieldList ret = new FieldList();
		boolean flg = false;
		for (Field<?> f : this.fieldList) {
			if (f instanceof GroupSummaryField) {
				flg = true;
				break;
			}
		}
		if (flg) {
			for (Field<?> f : this.fieldList) {
				if (!(f instanceof GroupSummaryField)) {
					ret.add(f);
				}
			}
		}
		return ret;
	}

	/**
	 * ソートするフィールドリストを取得します。
	 * @return ソートするフィールドリスト。
	 */
	public FieldList getOrderByFieldList() {
		return orderByFieldList;
	}

	/**
	 * ソートするフィールドリストを設定します。
	 * @param orderByFieldList ソートするフィールドリスト。
	 */
	public void setOrderByFieldList(final FieldList orderByFieldList) {
		this.orderByFieldList = orderByFieldList;
	}

	/**
	 * 削除フラグの有効性を取得します。
	 * <pre>
	 * trueの場合delete_flag='0'の条件を自動生成します。
	 * </pre>
	 * @return 削除フラグの有効性.
	 */
	public boolean isEffectivenessOfDeleteFlag() {
		return effectivenessOfDeleteFlag;
	}

	/**
	 * 削除フラグの有効性を設定します。
	 * @param effectivenessOfDeleteFlag 有効の場合true。
	 */
	public void setEffectivenessOfDeleteFlag(final boolean effectivenessOfDeleteFlag) {
		this.effectivenessOfDeleteFlag = effectivenessOfDeleteFlag;
	}
}
