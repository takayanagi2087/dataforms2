package dataforms.dao;

/**
 * Joinの条件生成インターフェース。
 *
 */
@FunctionalInterface
public interface JoinConditionInterface {
	/**
	 * Joinの結合条件を生成します。
	 * @param table 結合元のテーブル。
	 * @param joinTable 木都合するテーブル。
	 * @return Joinの結合条件。
	 */
	String getJoinCondition(final Table table, final Table joinTable);
}
