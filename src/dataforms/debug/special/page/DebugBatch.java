package dataforms.debug.special.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.BatchProcess;
import dataforms.debug.alltype.dao.SingleSelectDao;
import dataforms.field.base.FieldList;

/**
 * バッチ処理テスト。
 *
 */
public class DebugBatch extends BatchProcess {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(DebugBatch.class);

	@Override
	public int run(Map<String, Object> params) {
		logger.debug("params=" + params);
		try {
			SingleSelectDao dao = new SingleSelectDao(this);
			List<Map<String, Object>> list = dao.query(new HashMap<String, Object>(), new FieldList());
			return list.size();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}
}
