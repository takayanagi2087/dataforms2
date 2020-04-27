package dataforms.debug.special.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.annotation.WebMethod;
import dataforms.controller.WebApi;
import dataforms.debug.alltype.dao.SingleSelectDao;
import dataforms.field.base.FieldList;
import dataforms.response.JsonResponse;
import dataforms.response.Response;

/**
 * API実装テスト。
 *
 */
public class DebugApi extends WebApi {
	/**
	 * 誰でもアクセス可。
	 */
	@Override
	public boolean isAuthenticated(final Map<String, Object> params) throws Exception {
		return true;
	}

	/**
	 * APIの処理を実装。
	 *
	 */
	@WebMethod
	@Override
	public Response exec(final Map<String, Object> p) throws Exception {
		try {
			SingleSelectDao dao = new SingleSelectDao(this);
			List<Map<String, Object>> list = dao.query(new HashMap<String, Object>(), new FieldList());
			return new JsonResponse(JsonResponse.SUCCESS, list);
		} catch (Exception e) {
			return new JsonResponse(JsonResponse.APPLICATION_EXCEPTION, e.getMessage());
		}
	}

}
