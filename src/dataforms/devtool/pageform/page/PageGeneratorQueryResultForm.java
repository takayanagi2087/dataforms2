package dataforms.devtool.pageform.page;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.PageClassNameField;
import dataforms.devtool.field.PagePatternSelectField;
import dataforms.field.base.FieldList;
import dataforms.field.common.RowNoField;
import dataforms.htmltable.HtmlTable;
import dataforms.util.ClassFinder;
import dataforms.util.StringUtil;

/**
 * Pageクラス問い合わせ結果フォーム。
 *
 */
public class PageGeneratorQueryResultForm extends QueryResultForm {

	/**
	 * Log.
	 */
//	private Logger log = Logger.getLogger(PageGeneratorQueryResultForm.class);

	/**
	 * コンストラクタ。
	 */
	public PageGeneratorQueryResultForm() {
		HtmlTable htmltbl = new HtmlTable(Page.ID_QUERY_RESULT
			, (new RowNoField()).setSpanField(true)
			, (new PackageNameField()).setHidden(true)
			, (new PageClassNameField()).setHidden(true)
			, (new ClassNameField("fullClassName")).setSpanField(true)
			, (new ClassNameField("daoClassName")).setSpanField(true)
			, (new PagePatternSelectField("pagePattern")).setSpanField(true)
		);
		this.addHtmlTable(htmltbl);
		this.addPkField(htmltbl.getFieldList().get("packageName"));
		this.addPkField(htmltbl.getFieldList().get("pageClassName"));
	}


	/**
	 * ページクラスの一覧を取得します。
	 * @param data パラメータ。
	 * @return クエリ結果。
	 * @throws Exception 例外。
	 */
	private List<Map<String, Object>> queryPageClass(final Map<String, Object> data) throws Exception {
		String packageName = (String) data.get("packageName");
		String classname = (String) data.get("className");
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ClassFinder finder = new ClassFinder();
		List<Class<?>> pageList = finder.findClasses(packageName, Page.class);
		int no = 1;
		for (Class<?> pagecls : pageList) {
			Map<String, Object> m = new HashMap<String, Object>();
			if (Page.class.getName().equals(pagecls.getName())) {
				continue;
			}
			if (!StringUtil.isBlank(classname)) {
				if (pagecls.getName().indexOf(classname) < 0) {
					continue;
				}
			}
			if ((pagecls.getModifiers() & Modifier.ABSTRACT) != 0) {
				continue;
			}
			m.put("rowNo", Integer.valueOf(no));
			m.put("packageName", pagecls.getPackage().getName());
			m.put("pageClassName", pagecls.getSimpleName());
			m.put("fullClassName", pagecls.getName());
			Page p = (Page) pagecls.getConstructor().newInstance();
			m.put("pagePattern", p.getPagePattern());

			result.add(m);
			no++;
		}
		return result;
	}


	@Override
	protected Map<String, Object> queryPage(final Map<String, Object> data, final FieldList flist) throws Exception {
		List<Map<String, Object>> queryResult = this.queryPageClass(data);
		for (Map<String, Object> r: queryResult) {
			String className = (String) r.get("fullClassName");
			Class<?> cls = Class.forName(className);
			Page p = (Page) cls.getDeclaredConstructor().newInstance();
			PageClassInfo pi = new PageClassInfo(p);
			r.put("daoClassName", pi.getDaoClass());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("queryResult", queryResult);
		return result;
	}

}
