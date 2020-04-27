package dataforms.controller;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataforms.response.Response;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.CryptUtil;
import dataforms.util.StringUtil;

/**
 * WEBエントリーポイントクラス。
 * <pre>
 * Page、バッチ処理、API等サーブレットから直接呼び出されるWebComponentは、
 * このインターフェースを実装する必要があります。
 * </pre>
 *
 */
public interface WebEntryPoint {


//	Response getHtml(final Map<String, Object> params) throws Exception;
	/**
	 * Webアプリケーションの処理を実行します。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	Response exec(final Map<String, Object> p) throws Exception;

	/**
	 * 要求情報を取得します。
	 * @return 要求情報。
	 */
	HttpServletRequest getRequest();

	/**
	 * 要求情報を設定します。
	 * @param request 要求情報。
	 */
	void setRequest(final HttpServletRequest request);

	/**
	 * 応答情報を取得します。
	 * @return 応答情報。
	 */
	HttpServletResponse getResponse();

	/**
	 * 応答情報を設定します。
	 * @param response 応答情報。
	 */
	void setResponse(final HttpServletResponse response);

	/**
	 * QueryStringマップを設定します。
	 * @param map マップ。
	 */
	void setQueryString(final Map<String, Object> map);

	/**
	 * QueryStringマップを取得します。
	 * @return QueryStringマップ。
	 */
	Map<String, Object> getQueryString();


	/**
	 * JDBC接続を設定します。
	 * @param connection JDBC接続。
	 */
	void setConnection(final Connection connection);


	/**
	 * 認証済みかどうかを返します。
	 * @param params POSTされたパラメータ。
	 * @return 認証済みの場合true。
	 * @throws Exception 例外。
	 */
	default boolean isAuthenticated(final Map<String, Object> params) throws Exception {
		return false;
	}

	/**
	 * セッションからユーザ情報を取得します。
	 * @return ユーザ情報。
	 */
	default Map<String, Object> getUserInfo() {
		@SuppressWarnings("unchecked")
		Map<String, Object> userInfo = (Map<String, Object>) this.getRequest().getSession().getAttribute("userInfo");
		return userInfo;
	}


    /**
     * CSRF対策のTOKENを取得します。
     * @return CSRF対策のTOKEN。
     * @throws Exception 例外。
     */
    default String getCsrfToken() throws Exception{
		String token = null;
		String pass = DataFormsServlet.getCsrfSessionidCrypPassword();
		if (pass != null) {
			String sid = this.getRequest().getSession().getId();
			String key = CryptUtil.encrypt(sid, pass);
			token = key;
		}
		return token;
    }

    /**
     * CSRF対策のリクエストチェックを行います。
     * @param param パラメータ。
     * @return 問題ない場合true。
     * @throws Exception 例外。
     */
    default boolean isValidRequest(final Map<String, Object> param) throws Exception {
    	boolean ret = false;
    	if (DataFormsServlet.getCsrfSessionidCrypPassword() == null) {
    		ret = true;
    	} else {
    		String csrfToken = (String) param.get("csrfToken");
    		String token = this.getCsrfToken();
    		if (!StringUtil.isBlank(csrfToken)) {
    			String etoken = java.net.URLEncoder.encode(token, DataFormsServlet.getEncoding());
    			if (csrfToken.equals(token) || csrfToken.equals(etoken)) {
    				ret = true;
    			}
    		}
    	}
    	return ret;
    }
}
