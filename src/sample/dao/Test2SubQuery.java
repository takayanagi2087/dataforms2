package sample.dao;

import dataforms.dao.SubQuery;

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
}
