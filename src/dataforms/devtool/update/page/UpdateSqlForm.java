package dataforms.devtool.update.page;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.Form;
import dataforms.dao.Dao;
import dataforms.dao.Table;
import dataforms.dao.sqlgen.SqlGenerator;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.TableClassNameField;
import dataforms.devtool.field.UpdateSqlTypeField;
import dataforms.devtool.field.UpdateSqlTypeField.UpdateSqlType;
import dataforms.field.sqltype.ClobField;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.validator.RequiredValidator;

/**
 * 更新系SQL実行フォームクラス。
 */
public class UpdateSqlForm extends Form {
	/**
	 * Log.
	 */
	private static Logger logger = LogManager.getLogger(UpdateSqlForm.class);

	/**
	 * コンストラクタ。
	 */
	public UpdateSqlForm() {
		super(null);
		this.addField(new FunctionSelectField());
		this.addField(new PackageNameField());
		this.addField(new TableClassNameField()).setAutocomplete(true).setRelationDataAcquisition(true);
		this.addField(new UpdateSqlTypeField());
		this.addField(new ClobField("sql")).addValidator(new RequiredValidator());
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("updateSqlType", UpdateSqlType.INSERT.getString());
	}

	/**
	 * 指定されたテーブルクラスのインスタンスを作成します。
	 * @param param POSTされたパラメータ。
	 * @return 問合せクラスのインスタンス。
	 * @throws Exception 例外。
	 */
	private Table newTable(final Map<String, Object> param) throws Exception {
		String packageName = (String) param.get("packageName");
		String tableClassName = (String) param.get("tableClassName");
		logger.debug("tableClass=" + packageName + "." + tableClassName);
		@SuppressWarnings("unchecked")
		Class<? extends Table> q = (Class<? extends Table>) Class.forName(packageName + "." + tableClassName);
		return q.getDeclaredConstructor().newInstance();
	}

	private String generateSql(final String type, final Table table) throws Exception {
		Dao dao = new Dao(this);
		SqlGenerator gen = dao.getSqlGenerator();
		String sql = null;
		if (UpdateSqlType.INSERT.getString().equals(type)) {
			sql = gen.generateInsertSql(table);
		} else if (UpdateSqlType.UPDATE.getString().equals(type)) {
			sql = gen.generateUpdateSql(table);
		} else if (UpdateSqlType.DELETE.getString().equals(type)) {
			sql = gen.generateDeleteSql(table);
		}
		return sql;
	}

	/**
	 * 指定された問合せクラスに対応したsqlを取得します。
	 * @param param パラメータ。
	 * @return SQLの応答。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response generateSql(final Map<String, Object> param) throws Exception {
		Table table = this.newTable(param);
		String type = (String) param.get("updateSqlType");
		String sql = this.generateSql(type, table);
		Response resp = new JsonResponse(JsonResponse.SUCCESS, sql);
		return resp;
	}
}
