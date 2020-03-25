package dataforms.app.user.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.user.dao.UserDao;
import dataforms.app.user.field.LoginIdField;
import dataforms.app.user.field.UserAttributeTypeField;
import dataforms.app.user.field.UserAttributeValueField;
import dataforms.app.user.field.UserNameField;
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
    private static Logger logger = Logger.getLogger(UserQueryForm.class.getName());

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
	 * 列挙型関連テーブルのエクスポートを行います。
	 * @param p パラメータ。
	 * @return Json形式のエクスポート。
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
		logger.debug("userList=" + JSON.encode(list));
		return list;
	}
}
