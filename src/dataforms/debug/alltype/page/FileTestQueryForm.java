package dataforms.debug.alltype.page;

import dataforms.controller.QueryForm;
import dataforms.debug.alltype.field.FileCommentField;
import dataforms.field.base.Field.MatchType;

/**
 * 問い合わせフォームクラス。
 */
public class FileTestQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public FileTestQueryForm() {
		this.addField(new FileCommentField()).setMatchType(MatchType.PART);
	}
}
