package dataforms.devtool.field;

import dataforms.controller.Page;

/**
 * ページクラス名フィールドクラス。
 *
 */
public class PageClassNameField extends SimpleClassNameField {
	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "ページクラス名";
	/**
	 * コンストラクタ。
	 */
	public PageClassNameField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public PageClassNameField(final String id) {
		super(id);
		this.addBaseClass(Page.class);
		this.setComment(COMMENT);
	}

	@Override
	protected String getClassNameSuffix() {
		return "Page";
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

}
