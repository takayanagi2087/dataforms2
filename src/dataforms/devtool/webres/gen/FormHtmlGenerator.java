package dataforms.devtool.webres.gen;

import java.util.ArrayList;
import java.util.List;

import dataforms.controller.Form;
import dataforms.controller.Page;
import dataforms.controller.WebComponent;
import dataforms.devtool.webres.page.RangeFieldPair;
import dataforms.devtool.webres.page.TableFieldSetGenerator;
import dataforms.field.base.Field;
import dataforms.field.base.Field.MatchType;
import dataforms.field.common.DeleteFlagField;
import dataforms.field.common.FileField;
import dataforms.field.common.FlagField;
import dataforms.field.common.MultiSelectField;
import dataforms.field.common.SingleSelectField;
import dataforms.field.sqltype.ClobField;
import dataforms.field.sqltype.DateField;
import dataforms.htmltable.HtmlTable;

/**
 * フォームHTMLジェネレータ。
 *
 */
public class FormHtmlGenerator extends HtmlGenerator {
	/**
	 * 対象フォーム。
	 */
	private Form form = null;

	/**
	 * フィールドのレイアウト指定。
	 */
	public enum FieldLayout {
		/**
		 * tableタグを使用したレイアウト。
		 */
		TABLE_LAYOUT
		/**
		 * display: gridを指定したDIV。
		 */
		, GRID_LAYOUT
		/**
		 * display: flexを指定したDIV。
		 */
		, FLEX_LAYOUT
	}

	/**
	 * フィールドレイアウト。
	 */
	private FieldLayout fieldLayout = FieldLayout.GRID_LAYOUT;

	/**
	 * コンストラクタ。
	 * @param form 対象フォーム。
	 * @param indent 段付けのtab数。
	 */
	public FormHtmlGenerator(final Form form, final int indent) {
		super(indent);
		this.form = form;

	}

	/**
	 * フィールドレイアウトを取得します。
	 * @return フィールドレイアウト。
	 */
	public FieldLayout getFieldLayout() {
		return fieldLayout;
	}

	/**
	 * フィールドレイアウトを設定します。
	 * @param fieldLayout フィールドレイアウト。
	 */
	public void setFieldLayout(final FieldLayout fieldLayout) {
		this.fieldLayout = fieldLayout;
	}

	/**
	 * フィールドセットジェネレータを取得します。
	 * @return フィールドセットジェネレータ。
	 */
	public FieldSetGenerator getFieldSetGenerator() {
		FieldLayout fl = this.getFieldLayout();
		if (fl == FieldLayout.TABLE_LAYOUT) {
			return new TableFieldSetGenerator();
		} else if (fl == FieldLayout.GRID_LAYOUT) {
			return new GridFieldSetGenerator();
		} else if (fl == FieldLayout.FLEX_LAYOUT) {
			return new FlexFieldSetGenerator();
		}
		return null;
	}


	/**
	 * 作成対象フォームを取得します。
	 * @return 作成対象フォーム。
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * 指定されたクラスに対応したフォームHTMLジェネレータを作成します。
	 * @param form フォーム。
	 * @param indent 段付けのtab数。
	 * @return フォームHTMLジェネレータ。
	 */
	public static FormHtmlGenerator newFormHtmlGenerator(final Form form, final int indent) {
		FormHtmlGenerator gen = null;
		if (Page.ID_QUERY_FORM.equals(form.getId())) {
			gen = new QueryFormHtmlGenerator(form, indent);
		} else if (Page.ID_QUERY_RESULT_FORM.equals(form.getId())) {
			gen = new QueryResultFormHtmlGenerator(form, indent);
		} else if (Page.ID_EDIT_FORM.equals(form.getId())) {
			gen = new EditFormHtmlGenerator(form, indent);
		} else {
			gen = new FormHtmlGenerator(form, indent);
		}
		return gen;
	}


	/**
	 * 作成対象コンポーネントかどうか確認します。
	 * @param c 確認するコンポーネント。
	 * @return 作成対象の場合true。
	 */
	protected boolean isTargetedForGeneration(final WebComponent c) {
		return true;
	}

	/**
	 * フォームのタイトルを返す。
	 * @return フォームのタイトル。
	 */
	protected String getFormTitle() {
		return "xxx";
	}

	/**
	 * フォームのボタンのHTMLを取得します。
	 * @return フォームのボタンのHTML。
	 */
	protected String getFormButtionHtml() {
		return "";
	}

	/**
	 * 隠しフィールドを生成する。
	 * @param f フォーム。
	 * @param sb 出力先文字列バッファ。
	 */
	private void generateHiddenField(final Form f, final StringBuilder sb) {
		FieldSetGenerator gen = this.getFieldSetGenerator();
		String tabs = getTabs();
		for (WebComponent c: f.getComponentList()) {
			if (c instanceof Field) {
				Field<?> field = (Field<?>) c;
				if (!field.isHtmlGeneration()) {
					continue;
				}
				if (!this.isTargetedForGeneration(c)) {
					continue;
				}
				if (c instanceof DeleteFlagField || field.isHidden()) {
					sb.append(gen.getHiddenFieldHtml(field, tabs));
				}
			}
		}
	}


	/**
	 * "From","To"を除いたフィールドIDが一致するかを確認。
	 * @param from Fromフィールド。
	 * @param to Toフィールド。
	 * @return 一致する場合true。
	 */
	private boolean checkFieldId(final Field<?> from, final Field<?> to) {
		String fromId = from.getId().replaceAll("From$", "");
		String toId = to.getId().replaceAll("To$", "");
		boolean ret = fromId.equals(toId);
		return ret;
	}


	/**
	 * フォームの表示フィールドのリストを取得します。
	 * @param f フォーム。
	 * @return 表示フィールドのリスト。
	 */
	private List<WebComponent> getVisibleFieldList(final Form f) {
		List<WebComponent> list = new ArrayList<WebComponent>();
		RangeFieldPair pair = null;
		for (WebComponent c: f.getComponentList()) {
			if (c instanceof Field) {
				Field<?> field = (Field<?>) c;
				if (field.isHidden()) {
					continue;
				}
				if (!field.isHtmlGeneration()) {
					continue;
				}
				if (!this.isTargetedForGeneration(c)) {
					continue;
				}
				if (c instanceof DeleteFlagField) {
					continue;
				}
				if (field.getMatchType() == MatchType.RANGE_FROM) {
					pair = new RangeFieldPair();
					pair.setFrom(field);
				} else if (field.getMatchType() == MatchType.RANGE_TO) {
					if (pair != null && this.checkFieldId(pair.getFrom(), field)) {
						// toの直前にfromがあった場合
						pair.setTo(field);
						list.add(pair);
						pair = null;
					} else {
						if (pair != null) {
							list.add(pair.getFrom());
							pair = null;
						}
						list.add(field);
					}
				} else {
					if (pair != null) {
						// Fromの後にTOが来ない場合
						list.add(pair.getFrom());
						pair = null;
					}
					list.add(c);
				}
			}
		}
		return list;
	}

	/**
	 * 表示フィールドを生成する。
	 * @param f フォーム。
	 * @param sb 出力先文字列バッファ。
	 */
	private void generateVisibleField(final Form f, final StringBuilder sb) {
		FieldSetGenerator gen = this.getFieldSetGenerator();
		String tabs = getTabs();
		for (WebComponent c: this.getVisibleFieldList(f)) {
			if (c instanceof RangeFieldPair) {
				sb.append(gen.getRangeFieldPair(tabs, (RangeFieldPair) c));
			} else if (c instanceof Field) {
				Field<?> field = (Field<?>) c;
				if (c instanceof SingleSelectField) {
					SingleSelectField<?> msf = (SingleSelectField<?>) c;
					if (msf.getHtmlFieldType() == SingleSelectField.HtmlFieldType.SELECT) {
						// selectを展開
						sb.append(gen.getSelectFieldHtml(field, tabs));
					} else {
						sb.append(gen.getRadioFieldHtml(field, tabs));
					}
				} else if (c instanceof MultiSelectField) {
					MultiSelectField<?> msf = (MultiSelectField<?>) c;
					if (msf.getHtmlFieldType() == MultiSelectField.HtmlFieldType.CHECKBOX) {
						// checkboxを展開
						sb.append(gen.getCheckboxArrayFieldHtml(field, tabs));
					} else {
						// マルチ選択リストを展開
						sb.append(gen.getMultiSelectFieldHtml(field, tabs));
					}
				} else if (c instanceof FlagField) {
					// checkboxを展開
					sb.append(gen.getCheckboxFlagFieldHtml(field, tabs));
				} else if (c instanceof ClobField) {
					// textareaを展開
					sb.append(gen.getTextareaFieldHtml(field, tabs));
				} else if (c instanceof FileField) {
					// fileを展開
					sb.append(gen.getFileFieldHtml(field, tabs));
				} else if (c instanceof DateField) {
					sb.append(gen.getDateFieldHtml(field, tabs));
				} else {
					// 通常はテキストボックス。
					sb.append(gen.getTextFieldHtml(field, tabs));
				}
			}
		}
	}


	/**
	 * テーブルタグを出力します。
	 * @param f フォーム。
	 * @param sb 出力先文字列バッファ。
	 */
	protected void generateTable(final Form f, final StringBuilder sb) {
		for (WebComponent c: f.getComponentList()) {
			if (c instanceof HtmlTable) {
				HtmlTable table = (HtmlTable) c;
				TableHtmlGenerator gen = TableHtmlGenerator.newTableHtmlGenerator(table, this.getIndent());
				sb.append(gen.generateTableHtml());
			}
		}
	}

	/**
	 * フォームのHTMLを作成します。
	 * @param outputFormHtml Form別ファイル出力フラグ。
	 * @return フォームのHTML。
	 */
	public String generateFormHtml(final String outputFormHtml) {
		FieldSetGenerator gen = this.getFieldSetGenerator();
		String tabs = this.getTabs();
		Form f = this.getForm();
		StringBuilder sb = new StringBuilder();
		sb.append(tabs + "<form id=\"" + f.getId() + "\">\n");
		if ("0".equals(outputFormHtml)) {
			sb.append(tabs + "\t<div class=\"formHeader\">" + this.getFormTitle() + "</div>\n");
			this.generateHiddenField(f, sb);
			sb.append(gen.getStartTag(tabs));
			this.generateVisibleField(f, sb);
			sb.append(gen.getEndTag(tabs));
			this.generateTable(f, sb);
			sb.append(this.getFormButtionHtml());
		}
		sb.append(tabs + "</form>\n");
		return sb.toString();
	}
}
