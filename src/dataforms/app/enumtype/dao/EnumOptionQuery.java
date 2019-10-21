package dataforms.app.enumtype.dao;

/**
 * 列挙型選択肢の問合せ。
 *
 */
public class EnumOptionQuery extends EnumQuery {
	/**
	 * コンストラクタ。
	 * @param enumId 列挙型ID。
	 * @param langCode 言語コード。
	 */
	public EnumOptionQuery(final Long enumId, final String langCode) {
		super();
		// 親IDがnullのものを検索。
		this.setCondition("e.parent_id=:parent_id and en.lang_code=:lang_code");
		EnumTable.Entity p = new EnumTable.Entity();
		p.setParentId(enumId);
		EnumNameTable.Entity np = new EnumNameTable.Entity(p.getMap());
		np.setLangCode(langCode);
		this.setQueryFormData(p.getMap());
	}

	/**
	 * コンストラクタ。
	 * @param enumCode 列挙型コード。
	 * @param langCode 言語コード。
	 */
	public EnumOptionQuery(final String enumCode, final String langCode) {
		super();
		this.setCondition("e.parent_id in (select enum_id from enum where enum_code=:enum_code) and en.lang_code=:lang_code");
		EnumTable.Entity p = new EnumTable.Entity();
		p.setEnumCode(enumCode);
		EnumNameTable.Entity np = new EnumNameTable.Entity(p.getMap());
		np.setLangCode(langCode);
		this.setQueryFormData(p.getMap());
	}


	/**
	 * コンストラクタ。
	 * @param enumCode 列挙型コード。
	 * @param optionCode 選択肢コード。
	 * @param langCode 言語コード。
	 */
	public EnumOptionQuery(final String enumCode, final String optionCode, final String langCode) {
		super();
		this.setCondition("e.enum_code=:option_code and e.parent_id in (select enum_id from enum where enum_code=:enum_code) and en.lang_code=:lang_code");
		EnumTable.Entity p = new EnumTable.Entity();
		p.setEnumCode(enumCode);
		p.getMap().put("optionCode", optionCode);
		EnumNameTable.Entity np = new EnumNameTable.Entity(p.getMap());
		np.setLangCode(langCode);
		this.setQueryFormData(p.getMap());
	}

}
