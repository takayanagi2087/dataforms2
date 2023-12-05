package dataforms.devtool.pageform.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.dao.Query;
import dataforms.dao.Table;
import dataforms.devtool.javasrc.JavaSrcGenerator;
import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.field.base.Field;
import dataforms.field.base.Field.Display;
import dataforms.field.base.Field.MatchType;
import dataforms.field.base.FieldList;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.ImportUtil;
import dataforms.util.StringUtil;
import net.arnx.jsonic.JSON;

/**
 * ページジェネレータ。
 */
public class QueryFormGenerator extends JavaSrcGenerator {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(QueryFormGenerator.class);

	/**
	 * コンストラクタ。
	 */
	public QueryFormGenerator() {

	}

	/**
	 * 指定されたクラスのフィールドリストを取得します。
	 * @param pkg パッケージ。
	 * @param cls クラス。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getFieldList(final String pkg, final String cls) throws Exception {
		FieldList flist = null;
		if (!StringUtil.isBlank(pkg) && !StringUtil.isBlank(cls)) {
			Class<?> clazz = Class.forName(pkg + "." + cls);
			Object obj = clazz.getConstructor().newInstance();
			if (obj instanceof Table) {
				Table table = (Table) obj;
				flist = table.getFieldList();
			} else if (obj instanceof Query) {
				Query query = (Query) obj;
				flist = query.getFieldList();
			}
		}
		return flist;
	}

	/**
	 * 問合せのフィールドリストを取得します。
	 * @param data POSTされたデータ。
	 * @param pkgid 問合せパッケージ名のフィールドID。
	 * @param clsid 問合せクラス名のフィールドID。
	 * @param confid フィールド設定情報のフィールドID。
	 * @param editKeyOnly 編集キーのみ。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getQueryFieldList(final Map<String, Object> data, final String pkgid, final String clsid, final String confid, final boolean editKeyOnly) throws Exception {
		FieldList ret = null;
		String pkg = (String) data.get(pkgid);
		String cls = (String) data.get(clsid);
		FieldList flist = this.getFieldList(pkg, cls);
		if (flist != null) {
			ret = new FieldList();
			String json = (String) data.get(confid);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list =  JSON.decode(json, ArrayList.class);
			for (Map<String, Object> m: list) {
				String fieldId = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
				String matchType = (String) m.get(SelectFieldHtmlTable.ID_MATCH_TYPE);
				String listFieldDisplay = (String) m.get(SelectFieldHtmlTable.ID_LIST_FIELD_DISPLAY);
				String editKey = (String) m.get(SelectFieldHtmlTable.ID_EDIT_KEY);
				logger.debug("fieldId=" + fieldId);
				Field<?> field = flist.get(fieldId);
				field.setMatchType(Field.MatchType.valueOf(matchType));
				field.setEditFormDisplay(Display.valueOf(listFieldDisplay));
				if (!editKeyOnly) {
					// editKeyOnlyのみでない場合、全てのフィールドを転記
					ret.add(field);
				} else {
					if ("1".equals(editKey)) {
						ret.add(field);
					}
				}
			}
		}
		return  ret;
	}


	/**
	 * 問合せフォームのフィールドリストを取得します。
	 * @param data POSTされたデータ。
	 * @return 一覧取得問合せのフィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getQueryFormFieldList(final Map<String, Object> data) throws Exception {
		FieldList flist = this.getQueryFieldList(
			data
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_PACKAGE_NAME
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CLASS_NAME
			, DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CONFIG
			, false
		);
		if (flist == null) {
			flist = this.getQueryFieldList(
				data
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME
				, DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CONFIG
				, true
			);
		}
		return flist;
	}

	/**
	 * 編集対象取得問合せのフィールドリストを取得します。
	 * @param data POSTされたデータ。
	 * @return 編集対象取得問合せのフィールドリスト。
	 * @throws Exception 例外。
	 */
	protected FieldList getEditQueryFieldList(final Map<String, Object> data) throws Exception {
		String pkg = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME);
		String cls = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME);
		return getFieldList(pkg, cls);
	}

	/**
	 * 条件フィールドの追加処理コードを作成します。
	 * @param data POSTデータ。
	 * @param implist インポートリスト。
	 * @return 条件フィールドの追加処理コード。
	 * @throws Exception 例外。
	 */
	private String getQueryFormFieldList(final Map<String, Object> data, final ImportUtil implist) throws Exception {
		implist.add("java.util.List");
		implist.add("java.util.Map");
		implist.add("dataforms.report.ExportDataFile");
		implist.add("dataforms.field.base.FieldList");
		implist.add("dataforms.field.base.Field.MatchType");

		FieldList flist = this.getQueryFormFieldList(data);
		StringBuilder sb = new StringBuilder();
		String qscn = null;
		for (Field<?> f: flist) {
			Table tbl = f.getTable();
			String scn = tbl.getClass().getSimpleName();
			if (qscn != null) {
				scn = qscn;
			} else {
				implist.add(tbl.getClass());
			}
			MatchType type = f.getMatchType();
			if (type == MatchType.NONE) {
				;
			} else if (type == MatchType.RANGE_FROM) {
				String id = f.getId();
				String cname = f.getClass().getSimpleName();
				String cmt = f.getComment();
				sb.append("\t\tthis.addField(new " + cname + "(" + scn + ".Entity.ID_" + StringUtil.camelToUpperCaseSnake(id) + " + \"From\")).setMatchType(MatchType.RANGE_FROM).setComment(\"" + cmt + "(from)\");\n");
				sb.append("\t\tthis.addField(new " + cname + "(" + scn + ".Entity.ID_" + StringUtil.camelToUpperCaseSnake(id) + " + \"To\")).setMatchType(MatchType.RANGE_TO).setComment(\"" + cmt + "(to)\");\n");
				implist.add(f.getClass());
			} else {
				String id = f.getId();
				String mt = type.name();
				String cname = f.getClass().getSimpleName();
				String cmt = f.getComment();
				sb.append("\t\tthis.addField(new " + cname + "(" + scn + ".Entity.ID_" + StringUtil.camelToUpperCaseSnake(id) + ")).setMatchType(MatchType." + mt + ").setComment(\"" + cmt + "\");\n");
				implist.add(f.getClass());
			}
		}
		return sb.toString();
	}


	@Override
	public void generage(Form form, Map<String, Object> data) throws Exception {
		logger.debug("Generate QueryForm class");
		Template tmp = new Template(this.getClass(), "template/QueryForm.java.template");

		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String pageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME);
		String daoPackageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_PACKAGE_NAME);
		String daoClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME);
		String formClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_QUERY_FORM_CLASS_NAME);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME, packageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_PAGE_NAME, pageName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_QUERY_FORM_CLASS_NAME, formClassName);
		tmp.replace(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME, daoClassName);

		String daocls = daoPackageName + "." + daoClassName;
		tmp.replace("daoClassFullName", daocls);

		ImportUtil implist = new ImportUtil();
		String queryFormFieldList = this.getQueryFormFieldList(data, implist);
		tmp.replace("queryFormFieldList", queryFormFieldList);
		tmp.replace("queryFormImportList", implist.getImportText());

		Template vfTmp = new Template(this.getClass(), "template/validateForm.java.template");
		Template wmTmp = new Template(this.getClass(), "template/webMethod.java.template");
		tmp.replace("validateForm", vfTmp.getSource());
		tmp.replace("webMethod", wmTmp.getSource());

		logger.debug("QueryForm:\n" + tmp.getSource());

		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String formclass = packageName + "." + formClassName;
		String srcPath = path + "/" + formclass.replaceAll("\\.", "/") + ".java";
		logger.debug("srcPath=" + srcPath);
		FileUtil.writeTextFileWithBackup(srcPath, tmp.getSource(), DataFormsServlet.getEncoding());

	}
}
