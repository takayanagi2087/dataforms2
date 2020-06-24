package dataforms.devtool.query.page;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.dao.Query;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryClassNameField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.RowNoField;
import dataforms.htmltable.HtmlTable;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.ClassFinder;
import dataforms.util.ClassNameUtil;
import dataforms.util.FileUtil;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;


/**
 * 問い合わせ結果フォームクラス。
 */
public class QueryGeneratorQueryResultForm extends QueryResultForm {
	/**
	 * Logger。
	 */
	private Logger logger = LogManager.getLogger(QueryGeneratorQueryResultForm.class);

	/**
	 * コンストラクタ。
	 */
	public QueryGeneratorQueryResultForm() {
		HtmlTable htmltbl = new HtmlTable(Page.ID_QUERY_RESULT
				, (new RowNoField()).setSpanField(true)
				, (new PackageNameField()).setHidden(true)
				, (new QueryClassNameField()).setHidden(true)
				, (new ClassNameField("fullClassName")).setSpanField(true).setComment("問合せクラス名")
				, (new TextField("queryComment"))
				, (new TextField("subQuery"))
			);
			this.addHtmlTable(htmltbl);
			this.addPkField(htmltbl.getFieldList().get("packageName"));
			this.addPkField(htmltbl.getFieldList().get("queryClassName"));
	}


	/**
	 * 副問合せの名前を取得します。
	 * @param queryClass 問合せクラス。
	 * @return 問合せの名前。
	 */
	private String getSubQueryClassName(final Class<? extends Query> queryClass) {
		String queryClassName = queryClass.getName();
		return queryClassName.replaceAll("Query$", "SubQuery");
	}

	/**
	 * 副問合せの名前を取得します。
	 * @param subQueryClassName 副問合せのクラス名。
	 * @return 副問合せのソースファイル。
	 */
	private String getSubQuerySourceFile(final String subQueryClassName) {
		String srcfile = DeveloperPage.getJavaSourcePath() + "/" + subQueryClassName.replaceAll("\\.", "/") + ".java";
		return srcfile;
	}

	/**
	 * 生成されている副問合せクラス名を取得します。
	 * @param queryClass 問合せクラス。
	 * @return 生成されている副問合せクラス名。
	 */
	private String getSubQuery(final Class<? extends Query> queryClass) {
		String subQueryClassName = this.getSubQueryClassName(queryClass);
		String srcfile = this.getSubQuerySourceFile(subQueryClassName);
		logger.debug(() -> "subquery src=" + srcfile);
		File f = new File(srcfile);
		if (f.exists()) {
			return subQueryClassName;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Object> queryPage(final Map<String, Object> data, final FieldList queryFormFieldList) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		String packageName = (String) data.get("packageName");
		String className = (String) data.get("queryClassName");
		ClassFinder finder = new ClassFinder();
		List<Class<?>> queryList = finder.findClasses(packageName, Query.class);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int no = 1;
		for (Class<?> querycls : queryList) {
			Map<String, Object> m = new HashMap<String, Object>();
			if (Page.class.getName().equals(querycls.getName())) {
				continue;
			}
			if (!StringUtil.isBlank(className)) {
				if (querycls.getName().indexOf(className) < 0) {
					continue;
				}
			}
			if ((querycls.getModifiers() & Modifier.ABSTRACT) != 0) {
				continue;
			}
			if (querycls.getName().indexOf("$") > 0) {
				// インナークラスは除外。
				continue;
			}
			try {
				querycls.getConstructor();
			} catch (NoSuchMethodException e) {
				// デフォルトコンストラクタが存在しない場合はヒットさせない。
				continue;
			}
			m.put("rowNo", Integer.valueOf(no));
			m.put("packageName", querycls.getPackage().getName());
			m.put("queryClassName", querycls.getSimpleName());
			m.put("fullClassName", querycls.getName());
			m.put("subQuery", this.getSubQuery((Class<? extends Query>)querycls));
			Query q = (Query) querycls.getDeclaredConstructor().newInstance();
			m.put("queryComment", q.getComment());
			result.add(m);
			no++;
		}
		ret.put("queryResult", result); // とりあえず空のリストを返信。
		return ret;
	}

	@Override
	protected void deleteData(final Map<String, Object> data) throws Exception {
		// 何もしない。
	}

	/**
	 * サブクエリのソースを生成します。
	 * @param queryClassName 問合せクラス名。
	 * @return 作成したソースファイル名。
	 * @throws Exception 例外。
	 */
	private String generateSubQuery(final String queryClassName) throws Exception {
		@SuppressWarnings("unchecked")
		Class<? extends Query> cls = (Class<? extends Query>) Class.forName(queryClassName);
		String subQueryClassName = this.getSubQueryClassName(cls);
		String srcfile = this.getSubQuerySourceFile(subQueryClassName);
		String javasrc = this.getStringResourse("template/SubQuery.java.template");
		String qname = cls.getSimpleName();
		String sname = qname.replaceAll("Query$", "SubQuery");
		String pname = ClassNameUtil.getPackageName(cls.getName());
		javasrc = javasrc.replaceAll("\\$\\{packageName\\}", pname);
		javasrc = javasrc.replaceAll("\\$\\{queryName\\}", qname);
		javasrc = javasrc.replaceAll("\\$\\{subQueryName\\}", sname);
		FileUtil.writeTextFile(srcfile, javasrc, DataFormsServlet.getEncoding());
		return srcfile;
	}


	/**
	 * 副問合せを生成します。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response generateSubQuery(final Map<String, Object> p) throws Exception {
		String queryClassName = (String) p.get("queryClass");
		String filename = this.generateSubQuery(queryClassName);
		Response resp = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.subquerygenerated", filename));
		return resp;
	}

}
