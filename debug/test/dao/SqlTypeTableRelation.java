package test.dao;

import java.util.List;
import java.util.ArrayList;
import dataforms.dao.ForeignKey;
import dataforms.dao.Table;
import dataforms.dao.TableRelation;

/**
 * SqlTypeTableの関係を定義するクラスです。
 *
 */
public class SqlTypeTableRelation extends TableRelation {

	/**
	 * 外部キーリスト。
	 */
	private static List<ForeignKey> foreignKeyList = null;

	/**
	 * 外部キーリストの定義。
	 * <pre>
	 * この初期化処理で外部キーを定義することにより、自動的に外部キーが設定されます。
	 * </pre>
	 */
	static {
		foreignKeyList = new ArrayList<ForeignKey>();
		// foreignKeyList.add(new ForeignKey("fkSqlTypeTable01", SqlTypeTable.Entity.ID_HOGE_ID, HogeTable.class));
	}

	@Override
	public List<ForeignKey> getForeignKeyList() {
		return foreignKeyList;
	}

	/**
	 * コンストラクタ。
	 * @param table 対象テーブル。
	 */
	public SqlTypeTableRelation(final Table table) {
		super(table);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		return super.getJoinCondition(joinTable, alias);
	}
}
