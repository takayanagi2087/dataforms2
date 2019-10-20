package dataforms.app.enumeration.page;

import java.util.Map;

import dataforms.annotation.WebMethod;
import dataforms.app.enumtype.dao.EnumGroupTable;
import dataforms.controller.QueryForm;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.db.dao.TableManagerDao;
import dataforms.field.base.Field.MatchType;
import dataforms.response.JsonResponse;
import dataforms.util.MessagesUtil;



/**
 * 問い合わせフォームクラス。
 */
public class EnumGroupQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public EnumGroupQueryForm() {
		EnumGroupTable table = new EnumGroupTable();
		this.addField(table.getEnumGroupCodeField()).setMatchType(MatchType.PART);
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
			String initialDataPath = DeveloperPage.getExportInitalDataPath(this.getPage()); //DeveloperPage.getWebSourcePath() + "/WEB-INF/initialdata";
			dao.exportData("dataforms.app.dao.enumeration.EnumGroupTable", initialDataPath);
			ret = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.initializationdatacreated"));
		} else {
			ret = new JsonResponse(JsonResponse.INVALID, MessagesUtil.getMessage(this.getPage(), "error.permission"));
		}
		return ret;
	}

}
