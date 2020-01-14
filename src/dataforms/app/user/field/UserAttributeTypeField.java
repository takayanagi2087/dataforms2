package dataforms.app.user.field;

import dataforms.app.enumtype.dao.EnumDao;
import dataforms.field.common.EnumTypeSingleSelectField;

/**
 * ユーザ属性フィールドクラス。
 *
 */
public class UserAttributeTypeField extends EnumTypeSingleSelectField {
	/**
	 * コンストラクタ。
	 */
	public UserAttributeTypeField() {
		super(null, "userAttribute");
		this.setComment("ユーザ属性");
	}


	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public UserAttributeTypeField(final String id) {
		super(id, "userAttribute");
		this.setComment("ユーザ属性");
	}

	/*
	@Override
	protected void onBind() {
		Form form = this.getParentForm();
		if (form instanceof UserEditForm) {
			this.addValidator(new RequiredValidator());
		}
	}*/

	@Override
	public void init() throws Exception {
		super.init();
		EnumDao dao = new EnumDao(this);
		String lang = this.getPage().getCurrentLanguage();
		this.setOptionList(dao.getTypeList(this.getEnumGroupCode(), lang), true);
	}

}
