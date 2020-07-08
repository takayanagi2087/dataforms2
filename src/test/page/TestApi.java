package test.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Override
	public boolean isAuthenticated(final Map<String, Object> params) throws Exception {
		return true;
	}

	@WebMethod
	@Override
	public Response exec(final Map<String, Object> p) throws Exception {
		TestMultiRecDao dao = new TestMultiRecDao(this);
		List<Map<String, Object>> list = dao.query(new HashMap<String, Object>(), new FieldList());
		TextResponse r = new TextResponse(JSON.encode(list, true), "application/json");
		return r;
	}

}
