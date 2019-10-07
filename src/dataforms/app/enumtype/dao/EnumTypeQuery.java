package dataforms.app.enumtype.dao;

/**
 * 列挙型の一覧を取得する問合せ。
 * <pre>
 * 親IDがnullの物が列挙型のコード。
 * </pre>
 *
 */
public class EnumTypeQuery extends EnumTableQuery {
	/**
	 * コンストラクタ。
	 */
	public EnumTypeQuery() {
		super();
		this.setCondition("m.parent_id is null");
	}
}
