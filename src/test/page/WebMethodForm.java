package test.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.Form;
import dataforms.controller.Page;
import dataforms.exception.ApplicationException;
import dataforms.field.base.TextField;
import dataforms.field.common.FolderStoreFileField;
import dataforms.field.sqltype.IntegerField;
import dataforms.response.BinaryResponse;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.validator.ValidationError;

/**
 * WebMethodのテスト。
 */
public class WebMethodForm extends Form {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(WebMethodForm.class);

	/**
	 * コンストラクタ。
	 */
	public WebMethodForm() {
		super("webMethodForm");
		this.addField(new TextField("text"));
		this.addField(new IntegerField("intValue"));
		this.addField(new FolderStoreFileField("file"));
	}

	/**
	 * jsonMethodの呼び出し。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response jsonMethod(final Map<String, Object> p) throws Exception {
		//logger.debug("p=" + JSON.encode(p, true));
		List<ValidationError> elist = this.validate(p);
		if (elist.size() == 0) {
			Map<String, Object> data = this.convertToServerData(p);
			data.remove("file");
			Map<String, Object> r = new HashMap<String, Object>();
			r.put("text", data.get("text"));
			r.put("intValue", data.get("intValue"));
			return new JsonResponse(JsonResponse.SUCCESS, r);
		} else {
			return new JsonResponse(JsonResponse.INVALID, elist);
		}

	}

	/**
	 * jsonMethodの呼び出し(例外ケース)。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response applicationExceptionMethod(final Map<String, Object> p) throws Exception {
		throw new ApplicationException(this.getPage(), "error.required", "text");
	}

	/**
	 * ファイルダウンロード。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response downloadFile(final Map<String, Object> p) throws Exception {
//		logger.debug("p=" + JSON.encode(p, true));
		List<ValidationError> elist = this.validate(p);
		if (elist.size() == 0) {
			String file = Page.getServlet().getServletContext().getRealPath("/WEB-INF/lib/fop.jar");
			logger.debug("file=" + file);
			Response r = new BinaryResponse(file, "application/zip", "fop.zip");
			return r;
		} else {
			return new JsonResponse(JsonResponse.INVALID, elist);
		}
	}
}
