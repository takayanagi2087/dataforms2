package dataforms.app.enumtype.dao;

/**
 * 列挙型の選択肢一覧を取得する問合せ。
 *
 */
public class EnumOptionQuery extends EnumTableQuery {
	/**
	 * コンストラクタ。
	 * @param enumTypeId 列挙型ID。
	 */
	public EnumOptionQuery(final Long enumTypeId) {
		super();
		// 親IDがnullのものを検索。
		this.setCondition("m.parent_id=:parent_id");
		EnumTable.Entity p = new EnumTable.Entity();
		p.setEnumId(enumTypeId);
		this.setQueryFormData(p.getMap());
	}

	/**
	 * コンストラクタ。
	 * @param enumCode 列挙型コード。
	 */
	public EnumOptionQuery(final String enumCode) {
		super();
		this.setCondition("m.parent_id in (select enum_id from enum where m.enum_code=:enum_code)");
		EnumTable.Entity p = new EnumTable.Entity();
		p.setEnumCode(enumCode);
		this.setQueryFormData(p.getMap());
	}
}
