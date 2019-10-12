package dataforms.exception;

import dataforms.controller.Page;

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
	public ConstraintViolationException(final Page page, final String constraintName) {
		super(page, "error.constraintviolation", constraintName);
		this.constraintName = constraintName;
	}

	/**
	 * 制約名称。
	 * @return 例外。
	 */
	public String getConstraintName() {
		return constraintName;
	}

}
