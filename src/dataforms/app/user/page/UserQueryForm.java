package dataforms.app.user.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.user.dao.UserAttributeTable;
import dataforms.app.user.dao.UserDao;
import dataforms.app.user.dao.UserInfoTable;
import dataforms.app.user.field.LoginIdField;
import dataforms.app.user.field.UserAttributeTypeField;
import dataforms.app.user.field.UserAttributeValueField;
import dataforms.app.user.field.UserNameField;
import dataforms.controller.Page;
import dataforms.controller.QueryForm;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.db.dao.TableManagerDao;
import dataforms.field.base.Field;
import dataforms.field.base.FieldList;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.response.JsonResponse;
import dataforms.util.MessagesUtil;
import net.arnx.jsonic.JSON;

/**
 * ユーザ検索フォームクラス。
 */
public class UserQueryForm extends QueryForm {

    /**
     * Logger.
     */
    private static Logger logger = LogManager.getLogger(UserQueryForm.class.getName());

	/**
	 * コンストラクタ。
	 */
	public UserQueryForm() {
		this.addField(new LoginIdField()).setMatchType(Field.MatchType.BEGIN);
		this.addField(new UserNameField()).setMatchType(Field.MatchType.PART);
		EditableHtmlTable at = new EditableHtmlTable("attTable",
			new FieldList(
				new UserAttributeTypeField(),
				new UserAttributeValueField()
			)
		);
		this.addHtmlTable(at);
	}

	@Override
	public void init() throws Exception {
		super.init();
		// 初期データ設定.
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		this.setFormData("attTable", list);
	}

	/**
	 * ユーザ関連テーブルのエクスポートを行います。
	 * @param p パラメータ。
	 * @return エクスポート結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse export(final Map<String, Object> p) throws Exception {
		JsonResponse ret = null;
		if (this.getPage().checkUserAttribute("userLevel", "developer")) {
			TableManagerDao dao = new TableManagerDao(this);
			String initialDataPath =  DeveloperPage.getExportInitalDataPath(this.getPage()); // DeveloperPage.getWebSourcePath() + "/WEB-INF/initialdata";
			dao.exportData("dataforms.app.user.dao.UserInfoTable", initialDataPath);
			dao.exportData("dataforms.app.user.dao.UserAttributeTable", initialDataPath);
			ret = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.initializationdatacreated"));
		} else {
			ret = new JsonResponse(JsonResponse.INVALID, MessagesUtil.getMessage(this.getPage(), "error.permission"));
		}
		return ret;
	}

	/**
	 * ユーザ関連テーブルの初期化データのインポートを行います。
	 * @param p パラメータ。
	 * @return エクスポート結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse importData(final Map<String, Object> p) throws Exception {
		JsonResponse ret = null;
		if (this.getPage().checkUserAttribute("userLevel", "developer")) {
			TableManagerDao dao = new TableManagerDao(this);
			//String initialDataPath =  DeveloperPage.getExportInitalDataPath(this.getPage()); // DeveloperPage.getWebSourcePath() + "/WEB-INF/initialdata";
			String initialDataPath = Page.getServlet().getServletContext().getRealPath("/WEB-INF/initialdata");
			dao.executeUpdate("delete from " + new UserAttributeTable().getTableName(), new HashMap<String, Object>());
			dao.executeUpdate("delete from " + new UserInfoTable().getTableName(), new HashMap<String, Object>());
			dao.importData("dataforms.app.user.dao.UserInfoTable", initialDataPath);
			dao.importData("dataforms.app.user.dao.UserAttributeTable", initialDataPath);
			ret = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.initialDataImported"));
		} else {
			ret = new JsonResponse(JsonResponse.INVALID, MessagesUtil.getMessage(this.getPage(), "error.permission"));
		}
		return ret;
	}

	/**
	 * ユーザ関連テーブルのV1.x形式の初期化データのインポートを行います。
	 * @param p パラメータ。
	 * @return エクスポート結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse importV1Data(final Map<String, Object> p) throws Exception {
		JsonResponse ret = null;
		if (this.getPage().checkUserAttribute("userLevel", "developer")) {
			TableManagerDao dao = new TableManagerDao(this);
			String initialDataPath = Page.getServlet().getServletContext().getRealPath("/WEB-INF/initialdata_v1");
			dao.executeUpdate("delete from " + new UserAttributeTable().getTableName(), new HashMap<String, Object>());
			dao.executeUpdate("delete from " + new UserInfoTable().getTableName(), new HashMap<String, Object>());
			dao.importV1Data("dataforms.app.user.dao.UserInfoTable", "/dataforms/app/dao/user/UserInfoTable.data.json", initialDataPath);
			dao.importV1Data("dataforms.app.user.dao.UserAttributeTable", "/dataforms/app/dao/user/UserAttributeTable.data.json", initialDataPath);
			ret = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.initialDataImported"));
		} else {
			ret = new JsonResponse(JsonResponse.INVALID, MessagesUtil.getMessage(this.getPage(), "error.permission"));
		}
		return ret;
	}

	@Override
	protected FieldList getExportDataFieldList(Map<String, Object> data) throws Exception {
		FieldList flist = super.getExportDataFieldList(data);
		for (Field<?> f: flist) {
			f.setComment(f.getId());
		}
		return flist;
	}


	@Override
	protected List<Map<String, Object>> queryExportData(Map<String, Object> data) throws Exception {
		String lang = this.getPage().getRequest().getLocale().getLanguage();
		data.put("currentLangCode", lang);
		UserDao dao = new UserDao(this);
		List<Map<String, Object>> list = dao.queryUserList(this.getFieldList(), data);
		int rowNo = 1;
		for (Map<String, Object> m: list) {
			m.put("rowNo", Integer.valueOf(rowNo++));
		}
		logger.debug(() -> "userList=" + JSON.encode(list));
		return list;
	}
}
