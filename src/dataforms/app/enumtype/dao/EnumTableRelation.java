package dataforms.app.enumtype.dao;

import java.util.ArrayList;
import java.util.List;

import dataforms.dao.Table;
import dataforms.dao.TableRelation;

/**
 * EnumTableの関係を定義するクラスです。
 *
 */
public class EnumTableRelation extends TableRelation {

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
		// foreignKeyList.add(new ForeignKey("fkEnumTable01", EnumTable.Entity.ID_HOGE_ID, HogeTable.class));
	}

	@Override
	public List<ForeignKey> getForeignKeyList() {
		return foreignKeyList;
	}

	/**
	 * コンストラクタ。
	 * @param table 対象テーブル。
	 */
	public EnumTableRelation(final Table table) {
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
