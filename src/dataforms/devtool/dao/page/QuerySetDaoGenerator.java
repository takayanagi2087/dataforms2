package dataforms.devtool.dao.page;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.Form;
import dataforms.dao.Query;
import dataforms.dao.SingleTableQuery;
import dataforms.dao.Table;
import dataforms.devtool.pageform.page.DaoAndPageGeneratorEditForm;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.field.base.Field;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.FileUtil;
import dataforms.util.ImportUtil;
import dataforms.util.StringUtil;
import net.arnx.jsonic.JSON;

/**
 * QuerySetDao生成クラス。
 */
public class QuerySetDaoGenerator {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(QuerySetDaoGenerator.class);

	/**
	 * コンストラクタ。
	 */
	public QuerySetDaoGenerator() {
	}

	/**
	 * 指定された文字列リソースを取得します。
	 * @param path リソースパス。
	 * @return 文字列。
	 * @throws Exception 例外。
	 */
	protected String getStringResourse(final String path) throws Exception {
		Class<?> cls = this.getClass();
		InputStream is = cls.getResourceAsStream(path);
		String text = new String(FileUtil.readInputStream(is), DataFormsServlet.getEncoding());
		return text;
	}

	/**
	 * フィールドのプロパティを作成します。
	 * @param id フィールドID。
	 * @param fieldClassName フィールドクラス名。
	 * @return プロパティのソースコード。
	 * @throws Exception 例外。
	 */
	private String getFieldProperty(final String id, final String fieldClassName) throws Exception {
		String src = "	/**\n" +
				"	 * ${comment}。\n" +
				"	 */\n" +
				"	private ${className} ${variableName} = null;\n\n" +
				"	/**\n" +
				"	 * ${comment}を取得します。\n" +
				"	 * @return ${comment}。\n" +
				"	 */\n" +
				"	public ${className} ${getterName}() {\n" +
				"		return this.${variableName};\n" +
				"	}\n\n";
		@SuppressWarnings("unchecked")
		Class<? extends Field<?>> cls = (Class<? extends Field<?>>) Class.forName(fieldClassName);
		Field<?> field = Field.newFieldInstance(cls);
		String comment = field.getComment() + "フィールド";
		String ret = src.replaceAll("\\$\\{className\\}", cls.getSimpleName());
		ret = ret.replaceAll("\\$\\{variableName\\}", id + "Field");
		ret = ret.replaceAll("\\$\\{getterName\\}", "get" + StringUtil.firstLetterToUpperCase(id) + "Field");
		ret = ret.replaceAll("\\$\\{comment\\}", comment);
		return ret;

	}

	/**
	 * 問合せに使用するテーブルプロパティ設定ソースを作成します。
	 * @param packageName パッケージ名。
	 * @param tableClassName テーブルクラス名。
	 * @return テーブルプロパティ設定ソース。
	 * @throws Exception 例外。
	 */
	private String getProperty(final String packageName, final String tableClassName) throws Exception {
		String src = "	/**\n" +
				"	 * ${comment}。\n" +
				"	 */\n" +
				"	private ${className} ${variableName} = null;\n\n" +
				"	/**\n" +
				"	 * ${comment}を取得します。\n" +
				"	 * @return ${comment}。\n" +
				"	 */\n" +
				"	public ${className} get${className}() {\n" +
				"		return this.${variableName};\n" +
				"	}\n\n";
		Class<?> c = Class.forName(packageName + "." + tableClassName);
		Object obj = (Object) c.getConstructor().newInstance();
		String comment = "";
		if (obj instanceof Table) {
			Table table = (Table) obj;
			comment = table.getComment();
		} else if (obj instanceof Query){
			Query query = (Query) obj;
			comment = query.getComment();
		}
		String ret = src.replaceAll("\\$\\{className\\}", tableClassName);
		ret = ret.replaceAll("\\$\\{variableName\\}", StringUtil.firstLetterToLowerCase(tableClassName));
		ret = ret.replaceAll("\\$\\{comment\\}", comment);
		return ret;

	}

	/**
	 * 一覧取得問合せ設定情報を取得します。
	 * @param data POSTされたデータ。
	 * @return 一覧取得問合せ設定情報。
	 */
	private List<Map<String, Object>> getListQueryConfig(final Map<String, Object> data) {
		String json = (String) data.get(DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CONFIG);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = JSON.decode(json, ArrayList.class);
		return list;
	}

	/**
	 * テーブルまたは問合せのプロパティコードを作成します。
	 * @param data データ。
	 * @param implist インポートリスト。
	 * @return プロパティのソース。
	 * @throws Exception 例外。
	 */
	private String getProperties(final Map<String, Object> data, final ImportUtil implist) throws Exception {
		Set<String> set = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		{
			String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_LIST_QUERY_PACKAGE_NAME);
			String className = (String) data.get(DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CLASS_NAME);
			String fullClassName = packageName + "." + className;
			if (!StringUtil.isBlank(className)) {
				if (!set.contains(fullClassName)) {
					sb.append(this.getProperty(packageName, className));
					set.add(fullClassName);
				}
			}
		}
		{
			String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME);
			String className = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME);
			String fullClassName = packageName + "." + className;
			if (!StringUtil.isBlank(className)) {
				if (!set.contains(fullClassName)) {
					sb.append(this.getProperty(packageName, className));
					set.add(fullClassName);
				}
				@SuppressWarnings("unchecked")
				Class<? extends Query> qcls = (Class<? extends Query>) Class.forName(fullClassName);
				Object obj = qcls.getConstructor().newInstance();
				if (obj instanceof Query) {
					Query query = (Query) obj;
					Table table = query.getMainTable();
					String tableClassName = table.getClass().getName();
					logger.debug("tableClassName=" + tableClassName);
					if (!set.contains(tableClassName)) {
						implist.add(tableClassName);
						set.add(tableClassName);
					}
				}
			}
		}
		{
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) data.get(DaoAndPageGeneratorEditForm.ID_MULTI_RECORD_QUERY_LIST);
			for (Map<String, Object> m: list) {
				String packageName = (String) m.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
				String className = (String) m.get(DaoAndPageGeneratorEditForm.ID_QUERY_CLASS_NAME);
				String fullClassName = packageName + "." + className;
				if (!StringUtil.isBlank(className)) {
					if (!set.contains(fullClassName)) {
						sb.append(this.getProperty(packageName, className));
						set.add(fullClassName);
					}
				}
			}
		}
		{
			String editFormType = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_TYPE_SELECT);
			if ("2".equals(editFormType)) {
				List<Map<String, Object>> list = this.getListQueryConfig(data);
				for (Map<String, Object> m: list) {
					String sel = (String) m.get(SelectFieldHtmlTable.ID_EDIT_KEY);
					if ("1".equals(sel)) {
						String fieldId = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
						String fullClassName = (String) m.get(SelectFieldHtmlTable.ID_FIELD_CLASS_NAME);
						int idx = fullClassName.lastIndexOf(".");
						if (idx >= 0) {
							if (!set.contains(fullClassName)) {
								sb.append(this.getFieldProperty(fieldId, fullClassName));
								set.add(fullClassName);
								implist.add(fullClassName);
								// implist.add(fullClassName);
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}


	/**
	 * 編集フォーム無DAOソース生成を行います。
	 * @param javasrc javaソース文字列。
	 * @param implist インポートリスト。
	 * @return javaソース文字列。
	 */
/*	private String noEditForm(String javasrc, final ImportUtil implist) {
		implist.add(Table.class.getName());
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", "");
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
		implist.add("dataforms.dao.Query");
		return javasrc;
	}*/

	/**
	 * Mainテーブルを取得します。
	 * @param className QueryまたはTableのクラス名。
	 * @return 主テーブルのインスタンス。
	 * @throws Exception 例外。
	 */
	private Table getMainTable(final String className) throws Exception {
		Table mainTable = null;
		if (!StringUtil.isBlank(className)) {
			Class<?> qclass = Class.forName(className);
			Object obj = qclass.getConstructor().newInstance();
			if (obj instanceof Query) {
				Query q = (Query) obj;
				mainTable = q.getMainTable();
			} else if (obj instanceof Table){
				mainTable = (Table) obj;
			}
		}
		return mainTable;
	}


	/**
	 * 1レコード編集フォーム用DAOソース生成を行います。
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @param javasrc javaソーステキスト。
	 * @return javaソーステキスト。
	 * @throws Exception 例外。
	 */
	private String singleRecordEditForm(final Map<String, Object> data, final ImportUtil implist, String javasrc) throws Exception {
		String p = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_PACKAGE_NAME);
		{
			String listQueryPackage = (String) data.get(DaoAndPageGeneratorEditForm.ID_LIST_QUERY_PACKAGE_NAME);
			String listQueryClass = (String) data.get(DaoAndPageGeneratorEditForm.ID_LIST_QUERY_CLASS_NAME);
			String queryPackage = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME);
			String queryClass = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME);
			if (!StringUtil.isBlank(queryClass)) {
				if (!StringUtil.isBlank(queryClass)) {
					if (!p.equals(queryPackage)) {
						implist.add(queryPackage + "." + queryClass);
					}
					logger.debug("listQuery=" + listQueryPackage + "," + listQueryClass);
					logger.debug("editQuery=" + queryPackage + "," + queryClass);
					if (listQueryPackage.equals(queryPackage) && listQueryClass.equals(queryClass)) {
						javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "this." + StringUtil.firstLetterToLowerCase(queryClass));
					} else {
						javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "this." + StringUtil.firstLetterToLowerCase(queryClass) + " = new " + queryClass + "()");
					}
				} else {
					implist.add(Query.class.getName());
					javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
				}
			}
			if (!StringUtil.isBlank(queryClass)) {
				String className = queryPackage + "." + queryClass;
				Table mainTable = this.getMainTable(className);
				javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
			} else {
				implist.add(Table.class.getName());
				javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
			}
		}
		StringBuilder sb = new StringBuilder();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("multiRecordQueryList");
		for (Map<String, Object> m: list) {
			String pkgname = (String) m.get("packageName");
			String clsname = (String) m.get("queryClassName");
			if (!p.equals(pkgname)) {
				implist.add(pkgname + "." + clsname);
			}
			sb.append("\t\tthis.addMultiRecordQueryList(this." + StringUtil.firstLetterToLowerCase(clsname) + " = new " + clsname + "());\n");
		}
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", sb.toString());
		return javasrc;
	}

	/**
	 * 選択フィールドリストのソースを作成します。
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @param packagename パッケージ名。
	 * @param classname クラス名。
	 * @return 選択フィールドリストのソース。
	 * @throws Exception 例外。
	 */
	private String getKeyListSource(final Map<String, Object> data, final ImportUtil implist, final String packagename, final String classname) throws Exception {
		int cnt = 0;
		StringBuilder sb = new StringBuilder();
		List<Map<String, Object>> list = this.getListQueryConfig(data);

		Class<?> cls = Class.forName(packagename + "." + classname);
		if (Table.class.isAssignableFrom(cls)) {
			sb.append("\t\tQuery query = new SingleTableQuery(new " + classname + "());\n");
		} else {
			sb.append("\t\tQuery query = new " + classname + "();\n");
		}
		sb.append("\t\tthis.setMultiRecordQueryKeyList(new FieldList(\n");
		StringBuilder fsb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String sel = (String) m.get(SelectFieldHtmlTable.ID_EDIT_KEY);
			if ("1".equals(sel)) {
				if (fsb.length() > 0) {
					fsb.append("\t\t\t, ");
				} else {
					fsb.append("\t\t\t");
				}
				String fieldId = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
				String tbl = (String) m.get(SelectFieldHtmlTable.ID_TABLE_CLASS_NAME);
				String fieldClassName = (String) m.get(SelectFieldHtmlTable.ID_FIELD_CLASS_NAME);
				int idx = fieldClassName.lastIndexOf(".");
				if (idx >= 0) {
					fieldClassName = fieldClassName.substring(idx + 1);
				}
				String fld = "this." + fieldId + "Field = (" + fieldClassName + ") query.getFieldList().get(" + tbl + ".Entity.ID_" + StringUtil.camelToSnake(fieldId).toUpperCase() + ")\n";
				fsb.append(fld);
				cnt++;
			}
		}
		sb.append(fsb.toString());
		sb.append("\n\t\t));\n");
		if (cnt > 0) {
			if (Table.class.isAssignableFrom(cls)) {
				implist.add(SingleTableQuery.class);
			}
			implist.add("dataforms.field.base.FieldList");
			return sb.toString();
		} else {
			return "";
		}
	}


	/**
	 * 複数レコード編集フォーム用DAOソース生成を行います。
	 *
	 * @param data フォームデータ。
	 * @param implist インポートリスト。
	 * @param javasrc javaソーステキスト。
	 * @return javaソーステキスト。
	 * @throws Exception 例外。
	 */
	private String multiRecordEditForm(final Map<String, Object> data, final ImportUtil implist, String javasrc) throws Exception {
		String p = (String) data.get(DaoAndPageGeneratorEditForm.ID_PACKAGE_NAME);
		String packagename = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_PACKAGE_NAME);
		String classname = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_QUERY_CLASS_NAME);
		String src = "\t\tthis.addMultiRecordQueryList(this." + StringUtil.firstLetterToLowerCase(classname) + " = new " + classname + "());\n"
					+ this.getKeyListSource(data, implist, packagename, classname);
		javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", src);
		implist.add(Query.class.getName());
		javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
		if (!p.equals(packagename)) {
			implist.add(packagename + "." + classname);
		}

		Table mainTable = this.getMainTable(packagename + "." + classname);
		javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", mainTable.getClass().getSimpleName());
		return javasrc;
	}


	/**
	 * ソースを生成する。
	 * @param form フォーム。
	 * @param data POSTされたデータ。
	 * @throws Exception 例型。
	 */
	public void generage(final Form form, final Map<String, Object> data) throws Exception {
		List<Map<String, Object>> list = this.getListQueryConfig(data);
		logger.debug("fieldList=" + list.getClass().getName());
		for (int i = 0; i < list.size(); i++) {
			logger.debug("fieldInfo=" + JSON.encode(list.get(i)));
		}
		String javasrc = this.getStringResourse("template/QuerySetDao.java.template");
		//logger.debug("template=" + javasrc);
		String packageName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_PACKAGE_NAME);
		String daoClassName = (String) data.get(DaoAndPageGeneratorEditForm.ID_DAO_CLASS_NAME);
		ImportUtil implist = new ImportUtil(packageName);
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", packageName);
		javasrc = javasrc.replaceAll("\\$\\{daoClassName\\}", daoClassName);
		javasrc = javasrc.replaceAll("\\$\\{properties\\}", this.getProperties(data, implist));

		String daoclass = packageName + "." + daoClassName;
		String comment = (String) data.get(DaoAndPageGeneratorEditForm.ID_PAGE_NAME) + "用DAOクラス";
		logger.debug("comment=" + comment);
		javasrc = javasrc.replaceAll("\\$\\{comment\\}", comment);
		{
			String queryPackage = (String) data.get("listQueryPackageName");
			String queryClass = (String) data.get("listQueryClassName");
			if (!StringUtil.isBlank(queryClass)) {
				if (!packageName.equals(queryPackage)) {
					String qc = queryPackage + "." + queryClass;
					implist.add(qc);
				}
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "this." + StringUtil.firstLetterToLowerCase(queryClass) + " = new " + queryClass + "()");
			} else {
				implist.add(Query.class.getName());
				javasrc = javasrc.replaceAll("\\$\\{listQuery\\}", "(Query) null");
			}
		}
		String editFormSelect = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_FORM_SELECT);
		String editFormType = (String) data.get(DaoAndPageGeneratorEditForm.ID_EDIT_TYPE_SELECT);
		if ("1".equals(editFormSelect)) {
			if ("0".equals(editFormType)) {
				javasrc = this.singleRecordEditForm(data, implist, javasrc);
//				javasrc = this.noEditForm(javasrc, implist);
			} else if ("1".equals(editFormType)) {
				javasrc = this.singleRecordEditForm(data, implist, javasrc);
			} else {
				javasrc = this.multiRecordEditForm(data, implist, javasrc);
			}
		} else {
			javasrc = javasrc.replaceAll("\\$\\{singleRecordQuery\\}", "(Query) null");
			javasrc = javasrc.replaceAll("\\$\\{addMultiRecordQueryList\\}", "");
			javasrc = javasrc.replaceAll("\\$\\{mainTable\\}", "Table");
			implist.add(dataforms.dao.Query.class);
			implist.add(dataforms.dao.Table.class);
		}
		javasrc = javasrc.replaceAll("\\$\\{importTables\\}", implist.getImportText());
		logger.debug("javasrc={}", javasrc);

		String path = (String) data.get(DaoAndPageGeneratorEditForm.ID_JAVA_SOURCE_PATH);
		String srcPath = path + "/" + daoclass.replaceAll("\\.", "/") + ".java";
		logger.debug("srcPath=" + srcPath);
		FileUtil.writeTextFileWithBackup(srcPath, javasrc, DataFormsServlet.getEncoding());

	}
}
