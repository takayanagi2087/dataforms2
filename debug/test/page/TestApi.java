package test.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.WebApi;
import dataforms.field.base.FieldList;
import dataforms.response.Response;
import dataforms.response.TextResponse;
import net.arnx.jsonic.JSON;
import test.dao.TestMultiRecDao;

/**
 * APIのテスト。
 *
 */
public class TestApi extends WebApi {

	/**
	 * Logger。
	 */
	private Logger logger = LogManager.getLogger(TestApi.class);

	@Override
	public boolean isAuthenticated(final Map<String, Object> params) throws Exception {
		return true;
	}

	@WebMethod
	@Override
	public Response exec(final Map<String, Object> p) throws Exception {
		String err = (String) p.get("err");
		logger.debug(() -> "err=" + err);
		if (err != null) {
			throw new Exception("error");
		}
		TestMultiRecDao dao = new TestMultiRecDao(this);
		List<Map<String, Object>> list = dao.query(new HashMap<String, Object>(), new FieldList());
		TextResponse r = new TextResponse(JSON.encode(list, true), "application/json");
		return r;
	}

}
