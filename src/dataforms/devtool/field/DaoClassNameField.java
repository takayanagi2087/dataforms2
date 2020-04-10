package dataforms.devtool.field;

import dataforms.dao.QuerySetDao;
import dataforms.devtool.validator.ClassNameValidator;

/**
 * テーブルクラス名フィールドクラス。
 *
 */
public class DaoClassNameField extends SimpleClassNameField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "DAOクラス名";
	/**
	 * コンストラクタ。
	 */
	public DaoClassNameField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public DaoClassNameField(final String id) {
		super(id);
		this.addBaseClass(QuerySetDao.class);
		this.setComment(COMMENT);
		this.setAutocomplete(false);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new ClassNameValidator("Dao"));
	}
}
