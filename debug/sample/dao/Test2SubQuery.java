package sample.dao;

import dataforms.dao.SubQuery;
import dataforms.dao.Table;

/**
 * Test2Queryの副問合せ。
 *
 */
public class Test2SubQuery extends SubQuery {
	/**
	 * コンストラクタ。
	 */
	public Test2SubQuery() {
		super(new Test2Query());
		this.setComment(this.getQuery().getClass().getSimpleName() + "の副問合せ");
	}

	/**
	 * 問合せを取得します。
	 * @return 問合せ。
	 */
	public Test2Query getTest2Query() {
		return (Test2Query) this.getQuery();
	}

	/**
	 * 他テーブルとのリンク条件を作成します。
	 * <pre>
	 * 他のテーブルとの結合を行う場合を以下のコメント部分を参考に実装してください。
	 * </pre>
	 * @param joinTable 結合するテーブルのインスタンス。
	 * @param alias 結合するテーブルの別名。
	 */
	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
	/*
		if (joinTable instanceof HogeTable) {
			return this.getLinkFieldCondition(TestQuery.Entity.ID_HOGE_ID, joinTable, alias, HogeTable.Entity.ID_HOGE_ID);
		}
	*/
		return super.getJoinCondition(joinTable, alias);
	}
}
