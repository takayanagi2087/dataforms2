package dataforms.exception;

import dataforms.controller.WebEntryPoint;
import dataforms.dao.Constraint;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.StringUtil;

/**
 * 制約違反例外クラス。
 *
 */
public class ConstraintViolationException extends ApplicationException {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = -3667231418734583692L;
	/**
	 * 制約名。
	 */
	private String constraintName = null;

	/**
	 * コンストラクタ。
	 * @param page ページ。
	 * @param constraintName 制約名。
	 */
	public ConstraintViolationException(final WebEntryPoint page, final String constraintName) {
		super(page, ConstraintViolationException.getMessageKey(constraintName), constraintName);
		this.constraintName = StringUtil.snakeToCamel(constraintName);
	}


	/**
	 * メッセージキーを取得します。
	 * @param constraintName 制約名称。
	 * @return メッセージキー。
	 */
	static String getMessageKey(final String constraintName) {
		String cname = StringUtil.snakeToCamel(constraintName);
		Constraint cons = DataFormsServlet.getConstraintMap().get(cname);
		if (cons != null) {
			String key = cons.getViolationMessageKey();
			if (key != null) {
				return key;
			}
		}
		return "error.constraintviolation";
	}

	/**
	 * 制約名称。
	 * @return 例外。
	 */
	public String getConstraintName() {
		return constraintName;
	}

}
