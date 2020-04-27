package dataforms.controller;

import java.util.Map;

import dataforms.response.Response;

/**
 * WEB APIの基本クラス。
 *
 */
public abstract class WebApi extends WebProcess {
	/**
	 * {@inheritDoc}
	 *
	 * <pre>
	 * </pre>
	 */
	@Override
	public abstract boolean isAuthenticated(final Map<String, Object> params) throws Exception;

	/**
	 * {@inheritDoc}
	 *
	 * <pre>
	 * </pre>
	 *
	 */
	@Override
	public abstract Response exec(final Map<String, Object> p) throws Exception;
}
