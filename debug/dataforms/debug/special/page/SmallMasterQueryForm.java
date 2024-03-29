package dataforms.debug.special.page;

import dataforms.controller.QueryForm;
import dataforms.debug.special.field.Key1Field;
import dataforms.debug.special.field.Key2Field;
import dataforms.validator.RequiredValidator;

/**
 * 小規模マスタ問い合わせフォーム。
 *
 */
public class SmallMasterQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SmallMasterQueryForm() {
		this.addField(new Key1Field()).addValidator(new RequiredValidator());
		this.addField(new Key2Field()).addValidator(new RequiredValidator());
	}
}
