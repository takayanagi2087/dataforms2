package sample.dao;

import java.util.ArrayList;
import java.util.List;

import dataforms.dao.ForeignKey;
import dataforms.dao.Table;
import dataforms.dao.TableRelation;

/**
 * MeterialIncomincTableの関係を定義するクラスです。
 *
 */
public class MeterialIncomincTableRelation extends TableRelation {

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
		foreignKeyList.add(new ForeignKey("fkMeterialIncomincTable01", MeterialIncomincTable.Entity.ID_ORDER_ITEM_ID, MaterialOrderItemTable.class));
	}

	@Override
	public List<ForeignKey> getForeignKeyList() {
		return foreignKeyList;
	}

	/**
	 * コンストラクタ。
	 * @param table 対象テーブル。
	 */
	public MeterialIncomincTableRelation(final Table table) {
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
