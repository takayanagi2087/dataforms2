package sample.dao;

import dataforms.dao.SubQuery;

/**
 * TestQueryの副問合せ。
 *
 */
public class TestSubQuery extends SubQuery {
	/**
	 * コンストラクタ。
	 */
	public TestSubQuery() {
		super(new TestQuery());
	}

	/**
	 * 問合せを取得します。
	 * @return 問合せ。
	 */
	public TestQuery getTestQuery() {
		return (TestQuery) this.getQuery();
	}
}
