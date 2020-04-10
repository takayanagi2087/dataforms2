package dataforms.devtool.dao.page;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.controller.Page;
import dataforms.controller.QueryResultForm;
import dataforms.dao.QuerySetDao;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.DaoClassNameField;
import dataforms.devtool.field.PackageNameField;
import dataforms.field.base.FieldList;
import dataforms.field.base.TextField;
import dataforms.field.common.RowNoField;
import dataforms.htmltable.HtmlTable;
import dataforms.util.ClassFinder;
import dataforms.util.StringUtil;

/**
 * 検索結果フォーム。
 *
 */
public class DaoGeneratorQueryResultForm extends QueryResultForm {
	/**
	 * コンストラクタ。
	 */
	public DaoGeneratorQueryResultForm() {
		HtmlTable htmltbl = new HtmlTable(Page.ID_QUERY_RESULT
			, (new RowNoField()).setSpanField(true)
			, (new PackageNameField()).setHidden(true)
			, (new DaoClassNameField()).setHidden(true)
			, (new ClassNameField("fullClassName")).setSpanField(true).setSortable(true)
			, (new TextField("comment")).setSpanField(true).setSortable(true)
		);
		this.addHtmlTable(htmltbl);
		this.addPkField(htmltbl.getFieldList().get("packageName"));
		this.addPkField(htmltbl.getFieldList().get("daoClassName"));
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
		List<Class<?>> pageList = finder.findClasses(packageName, QuerySetDao.class);
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
			m.put("daoClassName", pagecls.getSimpleName());
			m.put("fullClassName", pagecls.getName());
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
			QuerySetDao dao = (QuerySetDao) cls.getDeclaredConstructor().newInstance();
			r.put("comment", dao.getComment());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("queryResult", queryResult);
		return result;
	}



}
