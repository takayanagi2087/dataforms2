package dataforms.devtool.webres.page;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.login.page.LoginInfoForm;
import dataforms.app.menu.page.SideMenuForm;
import dataforms.controller.DataForms;
import dataforms.controller.Form;
import dataforms.controller.Page;
import dataforms.controller.WebComponent;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.JavascriptClassField;
import dataforms.devtool.field.PathNameField;
import dataforms.devtool.field.WebComponentTypeField;
import dataforms.devtool.field.WebSourcePathField;
import dataforms.field.base.Field;
import dataforms.field.base.Field.MatchType;
import dataforms.field.common.DeleteFlagField;
import dataforms.field.common.FileField;
import dataforms.field.common.FlagField;
import dataforms.field.common.MultiSelectField;
import dataforms.field.common.PresenceField;
import dataforms.field.common.RowNoField;
import dataforms.field.common.SingleSelectField;
import dataforms.field.sqltype.ClobField;
import dataforms.field.sqltype.DateField;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.htmltable.HtmlTable;
import dataforms.htmltable.PageScrollHtmlTable;
import dataforms.response.JsonResponse;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.ClassNameUtil;
import dataforms.util.FileUtil;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;
import dataforms.validator.ValidationError;

// TODO:validatorの生成の際attachメソッドの生成は不要。
/**
 * Webリソース作成フォームクラス。
 *
 */
public class WebResourceForm extends Form {
	/**
	 * Log.
	 */
	private static Logger logger = LogManager.getLogger(WebResourceForm.class.getName());
	/**
	 * コンストラクタ。
	 */
	public WebResourceForm() {
		super(null);
		this.addField(new WebSourcePathField());
		this.addField(new ClassNameField()).setReadonly(true);
		this.addField(new WebComponentTypeField()).setReadonly(true);
		this.addField(new PathNameField("htmlPath")).setReadonly(true);
		this.addField(new PresenceField("htmlStatus")).setReadonly(true);
		this.addField(new FlagField("outputFormHtml"));
		this.addField(new PathNameField("javascriptPath")).setReadonly(true);
		this.addField(new PresenceField("javascriptStatus")).setReadonly(true);
		this.addField(new JavascriptClassField()).setReadonly(true);
		this.addField(new FlagField("forceOverwrite"));
	}


	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("webSourcePath", DeveloperPage.getWebSourcePath());
	}

	/**
	 * HTMLを作成します。
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse generateHtml(final Map<String, Object> p) throws Exception {
		Map<String, Object> data = this.convertToServerData(p);
		String message = "";
		List<ValidationError> errlist = new ArrayList<ValidationError>();
		String srcpath = generateHtmlFile(data);
		if (srcpath != null) {
			message = MessagesUtil.getMessage(this.getPage(), "message.htmlgenerated", srcpath);
		} else {
			errlist.add(new ValidationError("htmlPath", MessagesUtil.getMessage(this.getPage(), "error.htmlalreadyexists")));
		}
		if (errlist.size() == 0) {
			JsonResponse r = new JsonResponse(JsonResponse.SUCCESS, message);
			return r;
		} else {
			JsonResponse r = new JsonResponse(JsonResponse.INVALID, errlist);
			return r;
		}
	}

	/**
	 * HtmlGeneratorクラス。
	 */
	private static class HtmlGenerator {
		/**
		 * 段付のtab数。
		 */
		private int indent = 0;

		/**
		 * コンストラクタ。
		 * @param indent 段付けのtab数。
		 */
		public HtmlGenerator(final int indent) {
			this.indent = indent;
		}

		/**
		 * 段付けのtab数を取得します。
		 * @return 段付けのtab数。
		 */
		public int getIndent() {
			return indent;
		}

		/**
		 * 段付け用のtabを取得します。
		 * @return 段付け用のtab。
		 */
		protected String getTabs() {
			String tabs = "";
			for (int i = 0; i < this.getIndent(); i++) {
				tabs += "\t";
			}
			return tabs;
		}

		/**
		 * フィールドに対応したラベルを取得します。
		 * <pre>
		 * フィールドのコメントをラベルとします。
		 * フィールドのコメントがnullの場合idをラベルとします。
		 * </pre>
		 * @param field フィールド。
		 * @return ラベル。
		 */
		protected String getFieldLabel(final Field<?> field) {
			String label = field.getComment();
			if (label == null) {
				label = field.getId();
			}
			return label;
		}
	}

	/**
	 * フォームHTMLジェネレータ。
	 *
	 */
	private static class FormHtmlGenerator extends HtmlGenerator {
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
			if (this.fieldLayout == FieldLayout.TABLE_LAYOUT) {
				return new TableFieldSetGenerator();
			} else if (this.fieldLayout == FieldLayout.GRID_LAYOUT) {
				return new GridFieldSetGenerator();
			} else if (this.fieldLayout == FieldLayout.FLEX_LAYOUT) {
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

	/**
	 * テーブルHTMLジェネレータ。
	 *
	 */
	private static class TableHtmlGenerator extends HtmlGenerator {
		/**
		 * 対象テーブル。
		 */
		private HtmlTable table = null;

		/**
		 * 生成されたカラム数。
		 */
		private int columnCount = 0;

		/**
		 * コンストラクタ。
		 * @param table テーブル。
		 * @param indent 段付けのtab数。
		 */
		public TableHtmlGenerator(final HtmlTable table, final int indent) {
			super(indent);
			this.table = table;
		}

		/**
		 * 対象テーブルを取得します。
		 * @return 対象テーブル。
		 */
		public HtmlTable getTable() {
			return table;
		}

		/**
		 * 生成されたカラム数を取得します。
		 * @return 生成されたカラム数。
		 */
		protected int getColumnCount() {
			return columnCount;
		}

		/**
		 * カラム数を設定します。
		 * @param columnCount カラム数。
		 */
		public void setColumnCount(final int columnCount) {
			this.columnCount = columnCount;
		}

		/**
		 * HtmlTableに対応したTableHtmlGeneratorを作成します。
		 * @param table HTMLテーブル。
		 * @param indent 段付けのtab数。
		 * @return HtmlTableに対応したTableHtmlGenerator。
		 */
		public static TableHtmlGenerator newTableHtmlGenerator(final HtmlTable table, final int indent) {
			TableHtmlGenerator ret = null;
			if (table instanceof EditableHtmlTable) {
				ret = new EditableTableHtmlGenerator(table, indent);
			} else if (table instanceof PageScrollHtmlTable) {
				ret = new PageScrollTableHtmlGenerator(table, indent);
			} else {
				ret = new TableHtmlGenerator(table, indent);
			}
			return ret;
		}

		/**
		 * テーブルの開始タグを作成します。
		 * @param t HtmlTableオブジェクト。
		 * @return テーブルの開始タグ。
		 */
		protected String generateStartTableTag(final HtmlTable t) {
			return "<table id=\"" + t.getId() + "\">\n";
		}

		/**
		 * テキストフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getTextFieldHtml(final String tblid, final Field<?> field) {
			return "<input type=\"text\" id=\"" + tblid + "[0]." + field.getId() + "\" />";
		}

		/**
		 * SelectフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getSelectFieldHtml(final String tblid, final Field<?> field) {
			return "<select id=\"" + tblid + "[0]." + field.getId() + "\"></select>";
		}

		/**
		 * ラジオボタンフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getRadioFieldHtml(final String tblid, final Field<?> field) {
			return "<span><input type=\"radio\" id=\"" + tblid + "[0]." + field.getId() + "[0]\" name=\"" +  tblid + "[0]." + field.getId() + "\"/></span>";
		}

		/**
		 * チェックボックスフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getCheckboxArrayFieldHtml(final String tblid, final Field<?> field) {
			return "<span><input type=\"checkbox\" id=\"" + tblid + "[0]." + field.getId() + "[0]\" name=\"" + tblid + "[0]." +field.getId() + "\"/></span>";
		}


		/**
		 * 複数選択リストフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getMultiSelectFieldHtml(final String tblid, final Field<?> field) {
			return "<select id=\"" + tblid + "[0]." +field.getId() + "\" size=\"5\" multiple=\"multiple\"></select>";
		}


		/**
		 * チェックボックスフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getCheckboxFlagFieldHtml(final String tblid, final Field<?> field) {
			return "<input type=\"checkbox\" id=\"" + tblid + "[0]." + field.getId() + "\" value=\"1\"/>";
		}

		/**
		 * テキストエリアフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getTextareaFieldHtml(final String tblid, final Field<?> field) {
			return "<textarea id=\"" + tblid + "[0]." + field.getId() + "\" rows=\"20\" cols=\"80\"></textarea>";
		}


		/**
		 * ファイルフィールドのHTMLを作成します。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドのタグ。
		 */
		private String getFileFieldHtml(final String tblid, final Field<?> field) {
			return "<input type=\"file\" id=\"" + tblid + "[0]." + field.getId() + "\" />";
		}


		/**
		 * 表示フィールドを生成する。
		 * @param tblid テーブルID。
		 * @param field フィールド。
		 * @return フィールドタグ。
		 */
		protected String generateVisibleField(final String tblid, final Field<?> field) {
			Field<?> c = field;
			StringBuilder sb = new StringBuilder();
			if (c.isSpanField()) {
				sb.append("<span id=\"" + tblid + "[0]." + c.getId() + "\"></span>");
			} else if (c instanceof SingleSelectField) {
				SingleSelectField<?> msf = (SingleSelectField<?>) c;
				if (msf.getHtmlFieldType() == SingleSelectField.HtmlFieldType.SELECT) {
					// selectを展開
					sb.append(this.getSelectFieldHtml(tblid, field));
				} else {
					sb.append(this.getRadioFieldHtml(tblid, field));
				}
			} else if (c instanceof MultiSelectField) {
				MultiSelectField<?> msf = (MultiSelectField<?>) c;
				if (msf.getHtmlFieldType() == MultiSelectField.HtmlFieldType.CHECKBOX) {
					// checkboxを展開
					sb.append(this.getCheckboxArrayFieldHtml(tblid, field));
				} else {
					// マルチ選択リストを展開
					sb.append(this.getMultiSelectFieldHtml(tblid, field));
				}
			} else if (c instanceof FlagField) {
				// checkboxを展開
				sb.append(this.getCheckboxFlagFieldHtml(tblid, field));
			} else if (c instanceof ClobField) {
				// textareaを展開
				sb.append(this.getTextareaFieldHtml(tblid, field));
			} else if (c instanceof FileField) {
				// fileを展開
				sb.append(this.getFileFieldHtml(tblid, field));
			} else {
				// 通常はテキストボックス。
				sb.append(this.getTextFieldHtml(tblid, field));
			}
			return sb.toString();
		}

		/**
		 * Hiddenフィールドを展開します。
		 * @param t HTMLテーブル。
		 * @param sb 出力先文字列バッファ。
		 */
		protected void addHiddenFields(final HtmlTable t, final StringBuilder sb) {
			String tabs = getTabs();
			for (Field<?> f: t.getFieldList()) {
				if (f.isHidden()) {
					sb.append(tabs + "\t\t\t\t\t\t<input type=\"hidden\" id=\"" + t.getId() + "[0]." + f.getId() + "\" />\n");
				}
			}
		}

		/**
		 * カラム幅リスト。
		 */
		private List<Integer> columnWidthList = null;


		/**
		 * カラム幅リストを取得します。
		 * @return カラム幅リスト。
		 */
		protected List<Integer> getColumnWidthList() {
			return this.columnWidthList;
		}

		/**
		 * フィールドに対応たヘッダを出力します。
		 * @param t テーブル。
		 * @param sb 出力先文字列バッファ。
		 */
		protected void addFieldHeader(final HtmlTable t, final StringBuilder sb) {
			this.columnWidthList = new ArrayList<Integer>();
			String tabs = getTabs();
			this.columnCount = 0;
			for (Field<?> f: t.getFieldList()) {
				if (f.isHidden() || f instanceof DeleteFlagField) {
					continue;
				}
				this.columnCount++;
				sb.append(tabs + "\t\t\t\t\t<th>\n");
				sb.append(tabs + "\t\t\t\t\t\t" + this.getFieldLabel(f) + "\n");
				sb.append(tabs + "\t\t\t\t\t</th>\n");
				this.columnWidthList.add(Integer.valueOf(f.calcDefaultColumnWidth()));
			}
		}

		/**
		 * テーブルヘッダを生成します。
		 * @param t テーブル。
		 * @return テーブルヘッダ。
		 */
		protected String generateTableHeader(final HtmlTable t) {
			String tabs = getTabs();
			StringBuilder sb = new StringBuilder();
			sb.append(tabs + "\t\t\t<thead>\n");
			sb.append(tabs + "\t\t\t\t<tr>\n");
			this.addFieldHeader(t, sb);
			sb.append(tabs + "\t\t\t\t</tr>\n");
			sb.append(tabs + "\t\t\t</thead>\n");
			return sb.toString();
		}


		/**
		 * テーブルボディを生成します。
		 * @param t テーブル。
		 * @return テーブルヘッダ。
		 */
		protected String generateTableBody(final HtmlTable t) {
			String tabs = getTabs();
			StringBuilder sb = new StringBuilder();
			sb.append(tabs + "\t\t\t<tbody>\n");
			sb.append(tabs + "\t\t\t\t<tr>\n");
			this.addFields(t,  sb);
			sb.append(tabs + "\t\t\t\t</tr>\n");
			sb.append(tabs + "\t\t\t</tbody>\n");
			return sb.toString();
		}

		/**
		 * テーブルフッターを作成します。
		 * @param t テーブル。
		 * @return テーブルフッターの文字列。
		 */
		protected String generateTableFooter(final HtmlTable t) {
			return "";
		}


		/**
		 * テーブルに対応したフィールドを出力します。
		 * @param t テーブル。
		 * @param sb 出力先文字列バッファ。
		 */
		protected void addFields(final HtmlTable t, final StringBuilder sb) {
			String tabs = getTabs();
			boolean first = true;
			int idx = 0;
			for (Field<?> f: t.getFieldList()) {
				if (f.isHidden() || f instanceof DeleteFlagField) {
					continue;
				}
				Integer w = this.columnWidthList.get(idx++);
				if (this.getTable().getFixedColumns() >= 0) {
					sb.append(tabs + "\t\t\t\t\t<td style=\"width: " + w + "px;\">\n");
				} else {
					sb.append(tabs + "\t\t\t\t\t<td>\n");
				}
				sb.append(tabs + "\t\t\t\t\t\t" + this.generateVisibleField(t.getId(), f)+ "\n");
				if (first) {
					this.addHiddenFields(t, sb);
					first = false;
				}
				sb.append(tabs + "\t\t\t\t\t</td>\n");
			}
		}



		/**
		 * HtmlTableよりHTMLを作成します。
		 * @return テーブルのHTML。
		 */
		public String generateTableHtml() {
			String tabs = getTabs();
			HtmlTable t = this.getTable();
			StringBuilder sb = new StringBuilder();
			sb.append(tabs + "\t<div>\n");
			sb.append("\t\t" + tabs + this.generateStartTableTag(t));
			if (t.getCaption() != null) {
				sb.append(tabs + "\t\t\t<caption>" + t.getCaption() + "</caption>\n");
			}
			sb.append(this.generateTableHeader(t));
			sb.append(this.generateTableBody(t));
			sb.append(this.generateTableFooter(t));
			sb.append("\t\t" + tabs + "</table>\n");
			sb.append(tabs + "\t</div>\n");
			return sb.toString();
		}
	}

	/**
	 * ページスクロールテーブルHTMLジェネレータ。
	 *
	 */
	private static class PageScrollTableHtmlGenerator extends TableHtmlGenerator {
		/**
		 * コンストラクタ。
		 * @param table テーブル。
		 * @param indent 段付けのtab数。
		 */
		public PageScrollTableHtmlGenerator(final HtmlTable table, final int indent) {
			super(table, indent);
		}

		@Override
		protected void addFieldHeader(final HtmlTable t, final StringBuilder sb) {
			super.addFieldHeader(t, sb);
			String tabs = this.getTabs();
			sb.append(tabs + "\t\t\t\t\t<th>\n");
			sb.append(tabs + "\t\t\t\t\t\t操作\n");
			sb.append(tabs + "\t\t\t\t\t</th>\n");
			this.setColumnCount(this.getColumnCount() + 1);
		}

		@Override
		protected void addFields(final HtmlTable t, final StringBuilder sb) {
			String tabs = this.getTabs();
			boolean first = true;
			boolean genlink = true;
			int idx = 0;
			for (Field<?> f: t.getFieldList()) {
				if (f.isHidden() || f instanceof DeleteFlagField) {
					continue;
				}
				Integer w = this.getColumnWidthList().get(idx++);
				if (this.getTable().getFixedColumns() >= 0) {
					if (f instanceof RowNoField) {
						sb.append(tabs + "\t\t\t\t\t<td class=\"rowno\">\n");
					} else {
						sb.append(tabs + "\t\t\t\t\t<td style=\"width: " + w + "px;\">\n");
					}
				} else {
					sb.append(tabs + "\t\t\t\t\t<td>\n");
				}
				if (first) {
					sb.append(tabs + "\t\t\t\t\t\t" + this.generateVisibleField(t.getId(), f) + "\n");
					this.addHiddenFields(t, sb);
					first = false;
				} else if (genlink && !first) {
					sb.append(tabs + "\t\t\t\t\t\t" +
							"<a id=\"" + t.getId() + "[0].updateButton\" href=\"javascript:void(0);\">" +
							this.generateVisibleField(t.getId(), f) +
							"</a>" +
							 "\n");
					genlink = false;
				} else {
					sb.append(tabs + "\t\t\t\t\t\t" + this.generateVisibleField(t.getId(), f)+ "\n");
				}
				sb.append(tabs + "\t\t\t\t\t</td>\n");
			}
			if (this.getTable().getFixedColumns() >= 0) {
				sb.append(tabs + "\t\t\t\t\t<td style=\"width: 164px;\">\n");
			} else {
				sb.append(tabs + "\t\t\t\t\t<td>\n");
			}
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + "[0].viewButton\" value=\"表示\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + "[0].referButton\" value=\"参照登録\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + "[0].deleteButton\" value=\"削除\">\n");
			sb.append(tabs + "\t\t\t\t\t</td>\n");
		}
	}



	/**
	 * 編集可能テーブルHTMLジェネレータ。
	 *
	 */
	private static class EditableTableHtmlGenerator extends TableHtmlGenerator {
		/**
		 * コンストラクタ。
		 * @param table テーブル。
		 * @param indent 段付けのtab数。
		 */
		public EditableTableHtmlGenerator(final HtmlTable table, final int indent) {
			super(table, indent);
		}

		@Override
		protected String generateStartTableTag(final HtmlTable t) {
			return "<table id=\"" + t.getId() + "\" class=\"editableTable\">\n";
		}

		@Override
		protected void addFieldHeader(final HtmlTable t, final StringBuilder sb) {
			String tabs = this.getTabs();
			sb.append(tabs + "\t\t\t\t\t<th colspan=\"2\" nowrap>\n");
			sb.append(tabs + "\t\t\t\t\t\t\n");
			sb.append(tabs + "\t\t\t\t\t</th>\n");
			sb.append(tabs + "\t\t\t\t\t<th nowrap>\n");
			sb.append(tabs + "\t\t\t\t\t\tNo.\n");
			sb.append(tabs + "\t\t\t\t\t</th>\n");
			super.addFieldHeader(t, sb);
			this.setColumnCount(this.getColumnCount() + 3);
		}

		@Override
		protected void addFields(final HtmlTable t, final StringBuilder sb) {
			String tabs = this.getTabs();
			sb.append(tabs + "\t\t\t\t\t<td class=\"buttonColumn\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + "[0].addButton\" value=\"+\"/>\n");
			sb.append(tabs + "\t\t\t\t\t</td>\n");
			sb.append(tabs + "\t\t\t\t\t<td class=\"buttonColumn\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + "[0].deleteButton\" value=\"-\"/>\n");
			sb.append(tabs + "\t\t\t\t\t</td>\n");
			sb.append(tabs + "\t\t\t\t\t<td style=\"text-align: right;\" class=\"rowno\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<span id=\"" + t.getId() + "[0].no\"></span>\n");
			sb.append(tabs + "\t\t\t\t\t</td>\n");
			super.addFields(t, sb);

		}

		@Override
		protected String generateTableFooter(final HtmlTable t) {
			StringBuilder sb = new StringBuilder();
			String tabs = this.getTabs();
			sb.append(tabs + "\t\t\t<tfoot>\n");
			sb.append(tabs + "\t\t\t\t<tr>\n");
			sb.append(tabs + "\t\t\t\t\t<th class=\"buttonColumn\">\n");
			sb.append(tabs + "\t\t\t\t\t\t<input type=\"button\" id=\"" + t.getId() + ".addButton\" value=\"+\"/>\n");
			sb.append(tabs + "\t\t\t\t\t</th>\n");
			sb.append(tabs + "\t\t\t\t\t<th colspan=\"" + (this.getColumnCount() - 1) + "\">\n");
			sb.append(tabs + "\t\t\t\t\t</th>\n");
			sb.append(tabs + "\t\t\t\t</tr>\n");
			sb.append(tabs + "\t\t\t</tfoot>\n");
			return sb.toString();
		}

		@Override
		public String generateTableHtml() {
			StringBuilder sb = new StringBuilder();
//			String tabs = this.getTabs();
//			sb.append(tabs + "<div style=\"overflow-x:scroll;width:100%\">\n");
			sb.append(super.generateTableHtml());
//			sb.append(tabs + "</div>\n");
			return sb.toString();
		}
	}

	/**
	 * 問い合わせフォームHTMLジェネレータ。
	 *
	 */
	private static class QueryFormHtmlGenerator extends FormHtmlGenerator {
		/**
		 * コンストラクタ。
		 *
		 * @param form フォーム。
		 * @param indent 段付けのtab数。
		 */
		public QueryFormHtmlGenerator(final Form form, final int indent) {
			super(form, indent);
		}

		@Override
		protected String getFormTitle() {
			return "検索条件";
		}

		@Override
		protected String getFormButtionHtml() {
			String tabs = this.getTabs();
			String ret = tabs + "\t<input type=\"submit\" id=\"queryButton\" class=\"largeButton\" value=\"検索\">\n" +
					tabs + "\t<input type=\"button\" id=\"exportButton\" class=\"largeButton\" value=\"エクスポート\">\n" +
					tabs + "\t<input type=\"button\" id=\"resetButton\" class=\"largeButton\" value=\"リセット\">&nbsp;\n" +
					tabs + "\t<input type=\"button\" id=\"newButton\" class=\"largeButton\" value=\"新規登録\">\n";
			return ret;
		}
	}


	/**
	 * 問い合わせ結果フォームHTMLジェネレータ。
	 *
	 */
	private static class QueryResultFormHtmlGenerator extends FormHtmlGenerator {
		/**
		 * 指定されたクラスに対応したフォームHTMLジェネレータを作成します。
		 * @param form フォーム。
		 * @param indent 段付けのtab数。
		 */
		public QueryResultFormHtmlGenerator(final Form form, final int indent) {
			super(form, indent);

		}

		@Override
		protected boolean isTargetedForGeneration(final WebComponent c) {
			if ("hitCount".equals(c.getId()) || "pageNo".equals(c.getId()) || "linesPerPage".equals(c.getId()) || "sortOrder".equals(c.getId())) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		protected String getFormTitle() {
			return "検索結果";
		}

	}

	/**
	 * 問い合わせフォームHTMLジェネレータ。
	 *
	 */
	private static class EditFormHtmlGenerator extends FormHtmlGenerator {
		/**
		 * コンストラクタ。
		 *
		 * @param form フォーム。
		 * @param indent 段付けのtab数。
		 */
		public EditFormHtmlGenerator(final Form form, final int indent) {
			super(form, indent);

		}

		/**
		 * フォーム直下にフィールドを検索します。
		 * @param form フォーム。
		 * @param field 検索するフィールド。
		 * @return 見つかったフィールド。
		 */
		private Field<?> findField(final Form form, final Field<?> field) {
			for (WebComponent f: form.getComponentList()) {
				if (f instanceof Field) {
					if (f.getClass().getName().equals(field.getClass().getName())) {
						if (f.getId().equals(field.getId())) {
							return (Field<?>) f;
						}
					}
				}
			}
			return null;
		}


		/**
		 * テーブル中のフィールドと同じフィールドがフォーム直下に存在した場合、そのフィールドをhiddenに設定します。
		 * @param form フォーム。
		 * @param table テーブル。
		 */
		private void hideDuplicateField(final Form form, final EditableHtmlTable table) {
			for (Field<?> f: table.getFieldList()) {
				Field<?> ff = this.findField(form, f);
				if (ff != null) {
					f.setHidden(true);
				}
			}
		}

		/**
		 * テーブル中のフィールドの可視性を設定します。
		 * @param form フォーム。
		 */
		private void setTableFieldVisibility(final Form form) {
			for (WebComponent c: form.getComponentList()) {
				if (c instanceof EditableHtmlTable) {
					EditableHtmlTable table = (EditableHtmlTable) c;
					this.hideDuplicateField(form, table);
				}
			}
		}

		@Override
		protected void generateTable(final Form f, final StringBuilder sb) {
			this.setTableFieldVisibility(f);
			super.generateTable(f, sb);
		}

		@Override
		protected String getFormTitle() {
			return "<span id=\"editFormTitle\"></span>";
		}

		@Override
		protected String getFormButtionHtml() {
			String tabs = this.getTabs();
			String ret = tabs + "\t<input type=\"button\" id=\"confirmButton\" class=\"largeButton\" value=\"確認\"/>\n" +
					tabs + "\t<input type=\"button\" id=\"saveButton\" class=\"largeButton\" value=\"登録\"/>\n" +
					tabs + "\t<input type=\"button\" id=\"resetButton\" class=\"largeButton\" value=\"リセット\"/>\n" +
					tabs + "\t<input type=\"button\" id=\"deleteButton\" class=\"largeButton\" value=\"削除\"/>\n" +
					tabs + "\t<input type=\"button\" id=\"backButton\" class=\"largeButton\" value=\"戻る\"/>\n";
			return ret;
		}

	}

	/**
	 * Pageクラスに対応したhtmlを作成する。
	 * @param className クラス名。
	 * @param sourcePath 出力先フォルダ。
	 * @param outputFormHtml Form別ファイル出力フラグ。
	 * @return HTMLテキスト。
	 * @throws Exception 例外。
	 */
	private String getDataformsHtml(final String className, final String sourcePath, final String outputFormHtml) throws Exception {
		String src = this.getStringResourse("template/Page.html.template");
		Class<?> pageClass = Class.forName(className);
		DataForms page = (DataForms) pageClass.getDeclaredConstructor().newInstance();
		StringBuilder sb = new StringBuilder();
		List<WebComponent> clist = page.getComponentList();
		for (WebComponent c: clist) {
			if (c instanceof Form) {
				Form f = (Form) c;
				if (isCommonForm(f)) {
					continue;
				}
				if ("0".equals(outputFormHtml)) {
					String srcpath = sourcePath + "/" + f.getClass().getName().replaceAll("\\.", "/") + ".html";
					File srcfile = new File(srcpath);
					srcfile.delete();
				}
				FormHtmlGenerator gen = FormHtmlGenerator.newFormHtmlGenerator(f, 3);
				sb.append(gen.generateFormHtml(outputFormHtml));
			}
		}
		src = src.replaceAll("\\$\\{forms\\}", sb.toString());
		return src;
	}

	/**
	 * フォームを出力します。
	 * @param f フォーム。
	 * @param sourcePath ファイルの出力先。
	 * @param forceOverwrite 強制上書きフラグ。
	 * @return 出力ファイル。
	 * @throws Exception 例外。
	 */
	private String outputFormHtml(final Form f, final String sourcePath, final String forceOverwrite) throws Exception {
		String src = this.getStringResourse("template/Form.html.template");
		FormHtmlGenerator gen = FormHtmlGenerator.newFormHtmlGenerator(f, 2);
		StringBuilder sb = new StringBuilder();
		sb.append(gen.generateFormHtml("0"));
		String gensrc = src.replaceAll("\\$\\{form\\}", sb.toString());
		String srcpath = sourcePath + "/" + f.getClass().getName().replaceAll("\\.", "/") + ".html";
		File file = new File(srcpath);
		if ((!file.exists()) || "1".equals(forceOverwrite)) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
		} else {
			srcpath = null;
		}
		return srcpath;
	}
	/**
	 * ページまたはダイアログクラス中のフォームを別HTMLに出力します。
	 * @param className ページクラス名。
	 * @param sourcePath ファイルの出力パス。
	 * @param forceOverwrite 強制上書きフラグ。
	 * @throws Exception 例外。
	 */
	private void outputForms(final String className, final String sourcePath, final String forceOverwrite) throws Exception {
		Class<?> pageClass = Class.forName(className);
		DataForms page = (DataForms) pageClass.getDeclaredConstructor().newInstance();
		List<WebComponent> clist = page.getComponentList();
		for (WebComponent c: clist) {
			if (c instanceof Form) {
				Form f = (Form) c;
				if (isCommonForm(f)) {
					continue;
				}
				this.outputFormHtml(f, sourcePath, forceOverwrite);
			}
		}
	}

	/**
	 * 生成対象外の共通フォームかどうかを判定します。
	 * @param f フォーム。
	 * @return 生成対象外の共通フォームの場合true。
	 */
	private boolean isCommonForm(final Form f) {
		return f instanceof LoginInfoForm || f instanceof SideMenuForm;
	}


	/**
	 * HTMLファイルを作成します。
	 * @param data データ。
	 * @return 出力されたファイル。
	 * @throws Exception 例外。
	 */
	private String generateHtmlFile(final Map<String, Object> data) throws Exception {
		String webComponentType = (String) data.get("webComponentType");
		String outputFormHtml = (String) data.get("outputFormHtml");
		String forceOverwrite = (String) data.get("forceOverwrite");
		String sourcePath = (String) data.get("webSourcePath");
		String fullClassName = (String) data.get("className");
		String gensrc = "";
		if ("page".equals(webComponentType) || "dialog".equals(webComponentType)) {
			gensrc = this.getDataformsHtml(fullClassName, sourcePath, outputFormHtml);
			if ("1".equals(outputFormHtml)) {
				this.outputForms(fullClassName, sourcePath, forceOverwrite);
			}
			String srcpath = sourcePath + "/" + fullClassName.replaceAll("\\.", "/") + ".html";
			File f = new File(srcpath);
			if ((!f.exists()) || "1".equals(forceOverwrite)) {
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
				return srcpath;
			} else {
				return null;
			}
		} else if ("form".equals(webComponentType)) {
			Class<?> fcls = Class.forName(fullClassName);
			Form f = (Form) fcls.getConstructor().newInstance();
			return 	this.outputFormHtml(f, sourcePath, forceOverwrite);
		}
		return null;
	}


	/**
	 * Javascriptを作成します。
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外
	 */
	@WebMethod
	public JsonResponse generateJavascript(final Map<String, Object> p) throws Exception {
		Map<String, Object> data = this.convertToServerData(p);
		String message = "";
		List<ValidationError> errlist = new ArrayList<ValidationError>();
		String srcpath = generateJavascriptFile(data);
		if (srcpath != null) {
			message = MessagesUtil.getMessage(this.getPage(), "message.javascriptgenerated", srcpath);
		} else {
			errlist.add(new ValidationError("javascriptPath", MessagesUtil.getMessage(this.getPage(), "error.javascriptalreadyexists")));
		}
		if (errlist.size() == 0) {
			JsonResponse r = new JsonResponse(JsonResponse.SUCCESS, message);
			return r;
		} else {
			JsonResponse r = new JsonResponse(JsonResponse.INVALID, errlist);
			return r;
		}
	}

	/**
	 * テンプレートファイルを取得します。
	 * @param name リソース名。
	 * @return テンプレートの内容。
	 * @throws Exception 例外。
	 */
	private String getTemplate(final String name) throws Exception {
		String ret = "";
		Class<?> cls = this.getClass();
		InputStream is = cls.getResourceAsStream(name);
		if (is != null) {
			try {
				ret = new String(FileUtil.readInputStream(is), DataFormsServlet.getEncoding());
			} finally {
				is.close();
			}
		}
		return ret;
	}

	/**
	 * Javascriptのソーステンプレートを取得します。
	 * @param cls Javaクラス。
	 * @return テンプレートの文字列。
	 * @throws Exception 例外。
	 */
	private String getTemplate(final Class<?> cls) throws Exception {
		String template = null;
		Class<?> superClass = cls.getSuperclass();
		while (true) {
			String classname = superClass.getSimpleName();
			logger.debug(() -> "template class=" + classname);
			String src = this.getTemplate("template/" + classname + ".js.template");
			logger.debug(() -> "src=" + src);
			if (!StringUtil.isBlank(src)) {
				template = src;
				break;
			}
			superClass = superClass.getSuperclass();
		}
		return template;
	}

	/**
	 * javascriptファイルを作成します。
	 * @param data データ。
	 * @return 出力されたファイル。
	 * @throws Exception 例外。
	 *
	 */
	private String generateJavascriptFile(final Map<String, Object> data) throws Exception {

		String setFormData = this.getTemplate("template/setFormData.js.template");
		String validateForm = this.getTemplate("template/validateForm.js.template");
		String buttonHandler = this.getTemplate("template/buttonHandler.js.template");
		String callWebMethod = this.getTemplate("template/callWebMethod.js.template");
		String onCalc = this.getTemplate("template/onCalc.js.template");

		String forceOverwrite = (String) data.get("forceOverwrite");
		String sourcePath = (String) data.get("webSourcePath");
		String fullClassName = (String) data.get("className");
		Class<?> cls = Class.forName(fullClassName);
		cls.getSuperclass().getSimpleName();
		String superClassName = cls.getSuperclass().getSimpleName();
		String className = ClassNameUtil.getSimpleClassName(fullClassName);
		String src = this.getTemplate(cls);
		String gensrc = src.replaceAll("\\$\\{className\\}", className);
		gensrc = gensrc.replaceAll("\\$\\{superClassName\\}", superClassName);
		gensrc = gensrc.replaceAll("\\$\\{setFormData\\}", setFormData);
		gensrc = gensrc.replaceAll("\\$\\{validateForm\\}", validateForm);
		gensrc = gensrc.replaceAll("\\$\\{buttonHandler\\}", buttonHandler);
		gensrc = gensrc.replaceAll("\\$\\{callWebMethod\\}", callWebMethod);
		gensrc = gensrc.replaceAll("\\$\\{onCalc\\}", onCalc);
		String srcpath = sourcePath + "/" + fullClassName.replaceAll("\\.", "/") + ".js";
		File f = new File(srcpath);
		if ((!f.exists()) || "1".equals(forceOverwrite)) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
			return srcpath;
		} else {
			return null;
		}
	}

}
