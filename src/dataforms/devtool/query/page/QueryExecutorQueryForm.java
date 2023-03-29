package dataforms.devtool.query.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.QueryForm;
import dataforms.dao.Dao;
import dataforms.dao.Query;
import dataforms.dao.sqlgen.SqlGenerator;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.QueryClassNameField;
import dataforms.devtool.field.SqlField;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.validator.RequiredValidator;

/**
 * 問い合わせフォームクラス。
 */
public class QueryExecutorQueryForm extends QueryForm {

	/**
	 * Log.
	 */
	private static Logger logger = LogManager.getLogger(QueryExecutorQueryForm.class);

	/**
	 * コンストラクタ。
	 */
	public QueryExecutorQueryForm() {
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField());
		this.addField(new QueryClassNameField("queryClassName")).setAutocomplete(true).setRelationDataAcquisition(true);
		this.addField(new SqlField("sql")).addValidator(new RequiredValidator());
	}

	@Override
	public void init() throws Exception {
		super.init();
		String tableName = (String) this.getPage().getRequest().getSession().getAttribute(QueryExecutorPage.ID_TABLE_NAME);
		if (tableName != null) {
			this.setFormData("sql", "select * from " + tableName);
		}
	}

	/**
	 * 指定された問合せクラスのインスタンスを作成します。
	 * @param param POSTされたパラメータ。
	 * @return 問合せクラスのインスタンス。
	 * @throws Exception 例外。
	 */
	private Query newQuery(final Map<String, Object> param) throws Exception {
		String packageName = (String) param.get("packageName");
		String queryClassName = (String) param.get("queryClassName");
		logger.debug("queryClass=" + packageName + "." + queryClassName);
		@SuppressWarnings("unchecked")
		Class<? extends Query> q = (Class<? extends Query>) Class.forName(packageName + "." + queryClassName);
		return q.getDeclaredConstructor().newInstance();
	}

	/**
	 * 指定された問合せクラスに対応したsqlを取得します。
	 * @param param パラメータ。
	 * @return SQLの応答。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response getSql(final Map<String, Object> param) throws Exception {
		Query query = this.newQuery(param);
		Dao dao = new Dao(this);
		SqlGenerator gen = dao.getSqlGenerator();
		String sql = gen.generateQuerySql(query);
		Response resp = new JsonResponse(JsonResponse.SUCCESS, sql);
		return resp;
	}

	/**
	 * エクスポートデータのフィールドリスト。
	 */
	private FieldList exportFieldList = null;


	@Override
	protected List<Map<String, Object>> queryExportData(final Map<String, Object> data) throws Exception {
		Dao dao = new Dao(this);
		String sql = (String) data.get("sql");
		List<Map<String, Object>> list = dao.executeQuery(sql, new HashMap<String, Object>());
		//
		FieldList flist = dao.getResultSetFieldList();
		for (Field<?> f: flist) {
			f.init();
		}
		this.exportFieldList = flist;
		logger.info("*this.exportFieldList=" + this.exportFieldList);
		return list;
	}

	@Override
	protected FieldList getExportDataFieldList(final Map<String, Object> data) throws Exception {
		logger.info("this.exportFieldList=" + this.exportFieldList);
		return this.exportFieldList;
	}

}
