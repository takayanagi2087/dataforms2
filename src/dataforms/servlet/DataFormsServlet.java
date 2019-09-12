package dataforms.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.errorpage.ConfigErrorPage;
import dataforms.app.errorpage.ErrorPage;
import dataforms.app.form.LoginInfoForm;
import dataforms.app.page.backuprestore.BackupForm;
import dataforms.app.page.login.LoginForm;
import dataforms.app.page.user.PasswordResetMailForm;
import dataforms.app.page.user.UserRegistForm;
import dataforms.controller.ApplicationException;
import dataforms.controller.JsonResponse;
import dataforms.controller.Page;
import dataforms.controller.Response;
import dataforms.controller.WebComponent;
import dataforms.dao.JDBCConnectableObject;
import dataforms.dao.file.BlobFileStore;
import dataforms.dao.file.FileObject;
import dataforms.devtool.dao.db.TableManagerDao;
import dataforms.devtool.page.base.DeveloperPage;
import dataforms.mail.MailSender;
import dataforms.menu.SideMenu;
import dataforms.util.AutoLoginCookie;
import dataforms.util.CryptUtil;
import dataforms.util.HttpRangeInfo;
import dataforms.util.MessagesUtil;
import dataforms.util.MessagesUtil.ClientMessageTransfer;
import dataforms.util.StringUtil;
import net.arnx.jsonic.JSON;

/**
 * DataForms用サーブレットクラスです。
 * <pre>
 * *.dfにマッピングします。
 * /hoge/hogehoge/XxxPage.dfをアクセスすると、
 * ページクラスhoge.hogehoge.XxxPageクラスのインスタンスを作成し、getHtmlメソッドを呼び出します。
 * getHtmlは/hoge/hogehoge/XxxPage.htmlや/hoge/hogehoge/XxxPage.jsからページを構成し表示します。
 * 次のようにメソッドを指定してアクセスすると、
 * /hoge/hogehoge/XxxPage.df?dfMethod=compid.method
 * hoge.hogehoge.XxxPageのインスタンスを作成した後、compidを持つコンポーネントを検索し、
 * そのmethodを呼び出します。
 * </pre>
 *
 */
@WebServlet(name = "DataFormsServlet", displayName = "DataFormsServlet", urlPatterns = {"*.df" })
public class DataFormsServlet extends HttpServlet {

	/**
	 * HTML取得のメソッド名。
	 */
	private static final String GET_HTML_METHOD_NAME = "getHtml";

	/**
	 * UID.
	 */
	private static final long serialVersionUID = -8576472991434646040L;

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(DataFormsServlet.class.getName());

	/**
	 * jndi-prefix.
	 */
	private static String jndiPrefix = null;

	/**
	 * データソース名称.
	 */
	private static String dataSourceName = null;

	/**
	 * WEBリソースアクセス用URL。
	 */
	private static String webResourceUrl = null;

	/**
	 * 文字コード.
	 */
	private static String encoding = null;

	/**
	 * JSONの出力モード指定.
	 */
	private static boolean jsonDebug = false;

	/**
	 * 一時ファイル領域.
	 */
	private static String tempDir = null;

	/**
	 * データエクスポート、インポートディレクトリ.
	 */
	private static String exportImportDir = null;

	/**
	 * CSSとSCRIPT.
	 */
	private static String cssAndScript = null;

	/**
	 * エラーページ.
	 */
	private static String errorPage = null;

	/**
	 * javascriptでのバリデーションを有効にする.
	 */
	private static boolean clientValidation = true;


	/**
	 * javascriptのログレベルを設定.
	 */
	private static String clientLogLevel = "info";

	/**
	 * アップロードデータフォルダ.
	 */
	private static String uploadDataFolder = null;


	/**
	 * データソースのオブジェクト.
	 */
	private DataSource dataSource = null;


	/**
	 * サポート言語.
	 */
	private static String supportLanguage = null;

	/**
	 * 固定言語.
	 */
	private static String fixedLanguage = null;

	/**
	 * 開発ツールの無効化フラグ。
	 */
	private static boolean disableDeveloperTools = true;


	/**
	 * QueryStringを暗号化する際に使用するパスワード。
	 */
	private static String queryStringCryptPassword = "yyy_password_yyy";

	/**
	 * 設定の状態.
	 */
	private static String configStatus = null;

	/**
	 * ページオーバーライドマップ。
	 */
	private static Map<String, String> pageOverrideMap = new HashMap<String, String>();

	/**
	 * サーブレットインスタンス設定Bean.
	 */
	private static ServletInstanceBean servletInstanceBean = null;


	/**
	 * CSRF対策用暗号化キー。
	 *
	 * <pre>
	 * CSRF対策のため送信する照合情報は、セッションIDを以下のパスワードで暗号化して送信します。
	 * </pre>
	 */
	private static String csrfSessionidCrypPassword = null;


	/**
	 * Apache-FOPの設定ファイルのバス。
	 */
	private static String apacheFopConfig = "/WEB-INF/apachefop/fop.xconf";

	/**
	 * Pageの拡張子を取得します。
	 * <pre>
	 * Servletアノテーションの先頭のURLパターンから、拡張子を取得します。
	 * </pre>
	 * @return Pageの拡張子。
	 */
	public String getPageExt() {
		WebServlet an = this.getClass().getAnnotation(WebServlet.class);
		String[] uplist = an.urlPatterns();
		return uplist[0].substring(2);
	}


	/**
	 * CSRF対策用暗号化キーを取得します。
	 * @return CSRF対策用暗号化キー。
	 */
	public static String getCsrfSessionidCrypPassword() {
		return csrfSessionidCrypPassword;
	}

	/**
	 * ページオーバーライドマップを考慮したクラス名を取得します。
	 * @param name クラス名。
	 * @return 変換後のクラス名。
	 */
	public static String convertPageClassName(final String name) {
		String classname = name;
		if (pageOverrideMap.containsKey(name)) {
			classname = pageOverrideMap.get(name);
			log.info("page-override=" + name + "->" + classname);
		}
		return classname;
	}

	/**
	 * ページオーバーライドマップを初期化します。
	 */
	private void initPageOverrideMap() {
		Enumeration<String> e = this.getServletContext().getInitParameterNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			if (key.trim().indexOf("page-override:") == 0) {
				String keyclass = key.trim().replaceAll("page-override:", "");
				String classname = this.getServletContext().getInitParameter(key);
				DataFormsServlet.pageOverrideMap.put(keyclass, classname);
			}
		}
	}

	/**
	 * 各メティア対応のスタイルシート設定を取得します。
	 */
	private void initMediaCss() {
		Enumeration<String> e = this.getServletContext().getInitParameterNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			if (key.trim().indexOf("css-media:") == 0) {
				String cssfile = key.trim().replaceAll("css-media:", "");
				String media = this.getServletContext().getInitParameter(key);
				log.debug("css=" + cssfile + ",media=" + media);
				Page.addPreloadCss(cssfile, media);
			}
		}
	}



	/**
	 * WEBリソースアクセス用のURLを取得します。
	 *
	 * @return WEBリソースアクセス用のURL。
	 */
	public static String getWebResourceUrl() {
		return webResourceUrl;
	}

	/**
	 * 初期化パラメータを取得します。
	 * @throws ServletException 例外。
	 */
	@Override
	public void init() throws ServletException {
		this.initPageOverrideMap();
		this.initMediaCss();
		DataFormsServlet.jndiPrefix = this.getServletContext().getInitParameter("jndi-prefix");
		if (DataFormsServlet.jndiPrefix == null) {
			DataFormsServlet.jndiPrefix = "java:/comp/env/";
		}
		DataFormsServlet.dataSourceName = this.getServletContext().getInitParameter("data-source");
		log.info("init:dataSourceName=" + DataFormsServlet.dataSourceName);
		String webresurl= this.getServletContext().getInitParameter("web-resource-url");
		if (!StringUtil.isBlank(webresurl)) {
			DataFormsServlet.webResourceUrl = webresurl;
		}
		log.info("init:webResourceUrl=" + DataFormsServlet.webResourceUrl);
		DataFormsServlet.encoding = this.getServletContext().getInitParameter("encoding");
		if (DataFormsServlet.encoding == null) {
			DataFormsServlet.encoding = "utf-8";
		}
		log.info("init:encoding=" + DataFormsServlet.encoding);
		DataFormsServlet.jsonDebug = Boolean
				.parseBoolean(this.getServletContext().getInitParameter("json-debug") == null ? "false"
						: this.getServletContext().getInitParameter("json-debug"));
		log.info("init:jsonDebug=" + DataFormsServlet.jsonDebug);
		DataFormsServlet.tempDir = this.getServletContext().getInitParameter("temp-dir");
		if (DataFormsServlet.tempDir == null) {
			DataFormsServlet.tempDir = "/tmp";
		}
		// 一時フォルダがない場合作成する。
		File tmp = new File(DataFormsServlet.tempDir);
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		log.info("init:tempDir=" + DataFormsServlet.tempDir);
		DataFormsServlet.exportImportDir = this.getServletContext().getInitParameter("export-import-dir");
		if (DataFormsServlet.exportImportDir == null) {
			DataFormsServlet.exportImportDir = "/tmp/data";
		}
		log.info("init:exportImportDir=" + DataFormsServlet.exportImportDir);
		DataFormsServlet.cssAndScript = this.getServletContext().getInitParameter("css-and-scripts");
		if (DataFormsServlet.cssAndScript == null) {
			DataFormsServlet.cssAndScript = "/frame/jslib.html";
		}
		log.info("init:cssAndScript=" + DataFormsServlet.cssAndScript);
		DataFormsServlet.errorPage = this.getServletContext().getInitParameter("error-page");
		if (DataFormsServlet.errorPage == null) {
			DataFormsServlet.errorPage = "/dataforms/app/errorpage/ErrorPage." + this.getPageExt();
		} else {
			DataFormsServlet.errorPage += ("." + this.getPageExt());
		}
		log.info("init:errorPage=" + DataFormsServlet.errorPage);
		DataFormsServlet.clientValidation = Boolean
				.parseBoolean(this.getServletContext().getInitParameter("client-validation") == null ? "true"
						: this.getServletContext().getInitParameter("client-validation"));
		log.info("init:clientValidation=" + DataFormsServlet.clientValidation);
		DataFormsServlet.clientLogLevel = this.getServletContext().getInitParameter("client-log-level");
		if (DataFormsServlet.clientLogLevel == null) {
			DataFormsServlet.clientLogLevel = "info";
		}
		log.info("init:clientLogLevel=" + DataFormsServlet.clientLogLevel);
		DataFormsServlet.uploadDataFolder = this.getServletContext().getInitParameter("upload-data-folder");
		if (DataFormsServlet.uploadDataFolder == null) {
			DataFormsServlet.uploadDataFolder = "/uploadData";
		}
		log.info("init:uploadDataFolder=" + DataFormsServlet.uploadDataFolder);
		DataFormsServlet.supportLanguage = this.getServletContext().getInitParameter("support-language");
		if (DataFormsServlet.supportLanguage == null) {
			DataFormsServlet.supportLanguage = "ja";
		}
		log.info("init:supportLanguage=" + DataFormsServlet.supportLanguage);
		DataFormsServlet.fixedLanguage = this.getServletContext().getInitParameter("fixed-language");
		log.info("init:fixedLanguage=" + DataFormsServlet.fixedLanguage);
		DataFormsServlet.disableDeveloperTools = Boolean
				.parseBoolean(this.getServletContext().getInitParameter("disable-developer-tools") == null ? "true"
						: this.getServletContext().getInitParameter("disable-developer-tools"));
		log.info("init:disableDeveloperTools=" + DataFormsServlet.disableDeveloperTools);
		CryptUtil.setCryptPassword(this.getServletContext().getInitParameter("crypt-password") == null ? "d@d@f0ms"
				: this.getServletContext().getInitParameter("crypt-password"));
		DataFormsServlet.setQueryStringCryptPassword(this.getServletContext().getInitParameter("query-string-crypt-password") == null ? "d@d@f0ms"
				: this.getServletContext().getInitParameter("query-string-crypt-password"));
		DataFormsServlet.csrfSessionidCrypPassword = this.getServletContext().getInitParameter("csrf-sessionid-crypt-password");

		DataFormsServlet.cookieCheck = Boolean.parseBoolean(
				this.getServletContext().getInitParameter("cookie-check") == null ? "false"
				: this.getServletContext().getInitParameter("cookie-check")
		);
		Page.setFramePath(this.getServletContext().getInitParameter("frame-path") == null ? "/frame/default"
				: this.getServletContext().getInitParameter("frame-path"));
		log.info("init:framePath=" + Page.getFramePath());
		Page.setAppcacheFile(this.getServletContext().getInitParameter("appcache-file"));
		this.getMessageProperties();
		String topPage = this.getServletContext().getInitParameter("top-page");
		if (!StringUtil.isBlank(topPage)) {
			Page.setTopPage(topPage);
		}
		String backButton = this.getServletContext().getInitParameter("browser-back-button");
		if (!StringUtil.isBlank(backButton)) {
			Page.setBrowserBackButton(backButton);
		}
		String autoLogin = this.getServletContext().getInitParameter("auto-login");
		if (!StringUtil.isBlank(autoLogin)) {
			AutoLoginCookie.setAutoLogin("enabled".equals(autoLogin));
		}
		String secureAutoLoginCookie = this.getServletContext().getInitParameter("secure-auto-login-cookie");
		if (!StringUtil.isBlank(secureAutoLoginCookie)) {
			AutoLoginCookie.setSecure("true".equals(secureAutoLoginCookie));
		}
		DeveloperPage.setJavaSourcePath(this.getServletContext().getInitParameter("java-source-path"));
		DeveloperPage.setWebSourcePath(this.getServletContext().getInitParameter("web-source-path"));
		String streamingBlockSize = this.getServletContext().getInitParameter("streaming-block-size");
		log.debug("streamingBlockSize=" + streamingBlockSize);
		if (!StringUtil.isBlank(streamingBlockSize)) {
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> bslist = (List<LinkedHashMap<String, Object>>) JSON.decode(streamingBlockSize, ArrayList.class);
			HttpRangeInfo.setBlockSizeList(bslist);
		}

		String contentTypeList = this.getServletContext().getInitParameter("content-type-list");
		log.debug("contentTypeList=" + contentTypeList);
		if (!StringUtil.isBlank(contentTypeList)) {
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, String>> ctlist = (List<LinkedHashMap<String, String>>) JSON.decode(contentTypeList, ArrayList.class);
			FileObject.setContentTypeList(ctlist);
		}
		String backupFileName = this.getServletContext().getInitParameter("backup-file-name");
		if (backupFileName == null) {
			backupFileName = "backup";
		}
		BackupForm.setBackupFileName(backupFileName);
		String apacheFopConfig = this.getServletContext().getInitParameter("apache-fop-config");
		log.debug("apacheFopConfig=" + contentTypeList);
		if (apacheFopConfig != null) {
			DataFormsServlet.setApacheFopConfig(apacheFopConfig);
		}

		Boolean multiOpenMenu = Boolean.parseBoolean(
				this.getServletContext().getInitParameter("multi-open-menu") == null
				? "true"
				: this.getServletContext().getInitParameter("multi-open-menu")
		);
		SideMenu.setMultiOpenMenu(multiOpenMenu);

		this.getUserRegistConf();
		this.setupServletInstanceBean();
		super.init();
		WebComponent.setServlet(this);
		// DB存在チェック。
		this.checkDbConnection();
		// DBの存在チェック。
		this.checkDBStructure();
	}


	/**
	 * ユーザ登録関連設定を取得します。
	 */
	public void getUserRegistConf() {
		// ユーザ登録ページ関連設定
		LoginInfoForm.setUserRegistPage(this.getServletContext().getInitParameter("user-regist-page"));
		UserRegistForm.setUserEnablePage(this.getServletContext().getInitParameter("user-enable-page"));
		LoginForm.setPasswordResetMailPage(this.getServletContext().getInitParameter("password-reset-mail-page"));
		PasswordResetMailForm.setPasswordResetPage(this.getServletContext().getInitParameter("password-reset-page"));
		String conf = this.getServletContext().getInitParameter("user-regist-page-config");
		if (conf == null) {
			conf = "{\"loginIdIsMail\": true, \"mailCheck\": true, \"sendUserEnableMail\": true}";
			log.debug("user-regist-page-config is missing. use default. " + conf);
		}
		Map<String, Object> m = JSON.decode(conf);
		log.debug("m.class=" + m.getClass().getName());
		log.debug("conf=" + m.toString());
		UserRegistForm.setConfig(m);
		// メール関連設定。
		String mailSession = this.getServletContext().getInitParameter("mail-session");
		if (mailSession != null) {
			MailSender.setJndiPrefix(this.getServletContext().getInitParameter("jndi-prefix"));
			MailSender.setMailSessionName(mailSession);
			MailSender.setMailFrom(this.getServletContext().getInitParameter("mail-from"));
		}
	}

	/**
	 * メッセージプロパティの設定情報を取得します。
	 */
	private void getMessageProperties() {
		String clientMessages = this.getServletContext().getInitParameter("client-messages");
		if (clientMessages == null) {
			clientMessages = "/frame/messages/ClientMessages";
		}
		String appClientMessages = this.getServletContext().getInitParameter("app-client-messages");
		if (appClientMessages == null) {
			appClientMessages = "/frame/messages/AppClientMessages";
		}
		String messages = this.getServletContext().getInitParameter("messages");
		if (messages == null) {
			messages = "/frame/messages/Messages";
		}
		String appMessages = this.getServletContext().getInitParameter("app-messages");
		if (appMessages == null) {
			appMessages = "/frame/messages/AppMessages";
		}
		log.info("init:clientMessages=" + clientMessages);
		log.info("init:appClientMessages=" + appClientMessages);
		log.info("init:messages=" + messages);
		log.info("init:appMessages=" + appMessages);
		MessagesUtil.setClientMessagesName(clientMessages);
		MessagesUtil.setAppClientMessagesName(appClientMessages);
		MessagesUtil.setMessagesName(messages);
		MessagesUtil.setAppMessagesName(appMessages);

		String clientMessagesTransfer = this.getServletContext().getInitParameter("client-message-transfer");
		if (clientMessagesTransfer == null) {
			clientMessagesTransfer = "CLIENT_ONLY";
		}
		MessagesUtil.setClientMessageTransfer(ClientMessageTransfer.valueOf(clientMessagesTransfer));
	}

	/**
	 * ServletInstanceBeanの設定を行います。
	 */
	private void setupServletInstanceBean() {
		String beanClass = this.getServletContext().getInitParameter("servlet-instance-bean");
		log.info("beanClass = " + beanClass);
		if (beanClass != null) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends ServletInstanceBean> clazz = (Class<? extends ServletInstanceBean>) Class.forName(beanClass);
				DataFormsServlet.servletInstanceBean = clazz.newInstance();
				DataFormsServlet.servletInstanceBean.init();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}




	/**
	 * DBの接続チェックを行ないます。
	 */
	private void checkDbConnection() {
		if (DataFormsServlet.dataSourceName == null) {
			// web.xmlにデータソースの指定が無い場合。
			DataFormsServlet.configStatus = "error.notfounddatasourcesetting";
		} else {
			try {
				if (DataFormsServlet.dataSourceName != null) {
					Context initContext = new InitialContext();
					String dspath = DataFormsServlet.jndiPrefix + DataFormsServlet.dataSourceName;
					log.info("lookup data source=" + dspath);
					this.dataSource = (DataSource) initContext.lookup(dspath);
				}
			} catch (NameNotFoundException e) {
				// アプリケーションサーバにデータソースの設定が無い場合。
				log.error(e.getMessage(), e);
				DataFormsServlet.configStatus = "error.notfounddatasource";
			} catch (Exception e) {
				// DBサーバが動作していない場合のエラーであるため、運用時トラブルで発生する可能性がある。
				// そのため設定エラーとしない。
				//DataFormsServlet.dbStatus = "error.cannotconnectdatasource";
				log.error(e.getMessage(), e);
			}
		}
	}




	/**
	 * データベースの構造チェック。
	 *
	 */
	private void checkDBStructure() {
		try {
			Connection conn = this.getConnection();
			try {
				JDBCConnectableObject cobj = new JDBCConnectableObject() {
					@Override
					public final Connection getConnection() {
						return conn;
					}
				};
				TableManagerDao dao = new TableManagerDao(cobj);
				List<Map<String, Object>> list = dao.queryTableClass("dataforms.app", "");
				for (Map<String, Object> m: list) {
					String differenceVal = (String) m.get("differenceVal");
					if ("1".equals(differenceVal)) {
						String className = (String) m.get("className");
						log.info("update table structure=" + className);
						dao.dropAllForeignKeys();
						dao.updateTable(className);
						dao.createAllForeignKeys();
					}
				}
				conn.commit();
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * JDBC接続を取得します。
	 * @return JDBC接続。
	 * @throws Exception 例外。
	 */
	public final Connection getConnection() throws Exception {
		if (DataFormsServlet.dataSourceName != null) {
			Connection conn = this.dataSource.getConnection();
			conn.setAutoCommit(false);
			return conn;
		} else {
			return null;
		}

	}

	/**
	 * エラーページを取得します。
	 * @return エラーページ。
	 */
	public static String getErrorPage() {
		return errorPage;
	}

	/**
	 * 一時ファイル領域を取得します。
	 * @return 一時ファイル領域。
	 */
	public static String getTempDir() {
		return tempDir;
	}

	/**
	 * Export/Inputで使用するデフォルトフォルダを取得します。
	 * @return Export/Inputで使用するデフォルトフォルダ。
	 */
	public static String getExportImportDir() {
		return exportImportDir;
	}

	/**
	 * クライアントバリデーションの有無を取得します。
	 * @return クライアントバリデーション有りの場合true。
	 */
	public static boolean isClientValidation() {
		return clientValidation;
	}


	/**
	 * 文字コードを取得します。
	 * @return 文字コード。
	 */
	public static String getEncoding() {
		return encoding;
	}


	/**
	 * アップロードデータフォルダを取得します。
	 * @return アップロードデータフォルダ。
	 */
	public static String getUploadDataFolder() {
		return uploadDataFolder;
	}

	/**
	 * アップロードデータフォルダを指定します.
	 * @param uploadDataFolder アップロードデータフォルダ.
	 */
	public static void setUploadDataFolder(final String uploadDataFolder) {
		DataFormsServlet.uploadDataFolder = uploadDataFolder;
	}


	/**
	 * Jsonのデバックを行うかどうかを取得します。
	 * <pre>
	 * trueの場合jsonを読みやすい形式に整形します。
	 * </pre>
	 * @return Jsonデバックを行う場合true。
	 */
	public static boolean isJsonDebug() {
		return jsonDebug;
	}

	/**
	 * Jsonのデバックを行うかどうかを設定します。
	 * <pre>
	 * trueの場合jsonを読みやすい形式に整形します。
	 * </pre>
	 * @param jsonDebug Jsonデバックを行う場合true。
	 */
	public static void setJsonDebug(final boolean jsonDebug) {
		DataFormsServlet.jsonDebug = jsonDebug;
	}

	/**
	 * CSSとSCRIPTの読み込み設定ファイルを取得します。
	 * @return CSSとSCRIPTの読み込み設定ファイル。
	 */
	public static String getCssAndScript() {
		return cssAndScript;
	}

	/**
	 * サポート言語を取得します。
	 * @return サポート言語。
	 */
	public static String getSupportLanguage() {
		return supportLanguage;
	}

	/**
	 * 言語固定設定を行った場合、その言語を返します。
	 * @return 固定された言語。
	 */
	public static String getFixedLanguage() {
		return fixedLanguage;
	}

	/**
	 * 開発ツール無効フラグを取得します。
	 * @return 開発ツール無効フラグ。
	 */
	public static boolean isDisableDeveloperTools() {
		return disableDeveloperTools;
	}

	/**
	 * 開発ツール無効フラグを設定します。
	 * @param disableDeveloperTools 開発ツール無効フラグ。
	 */
	public static void setDisableDeveloperTools(final boolean disableDeveloperTools) {
		DataFormsServlet.disableDeveloperTools = disableDeveloperTools;
	}

	/**
	 * QueryStringを暗号化する際のパスワードを取得します。
	 * @return QueryStringを暗号化する際のパスワード。
	 */
	public static String getQueryStringCryptPassword() {
		return queryStringCryptPassword;
	}

	/**
	 * QueryStringを暗号化する際のパスワードを設定します。
	 * @param queryStringCryptPassword QueryStringを暗号化する際のパスワード。
	 */
	public static void setQueryStringCryptPassword(final String queryStringCryptPassword) {
		DataFormsServlet.queryStringCryptPassword = queryStringCryptPassword;
	}

	/**
	 * リクエストに対応したFormクラス名を取得します。
	 * @param context コンテキスト。
	 * @param uri URI。
	 * @return クラス名。
	 */
	private String getTargetClassName(final String context, final String uri) {
		String path = uri.substring(context.length() + 1);
		int idx = path.lastIndexOf(".");
		if (idx >= 0) {
			path = path.substring(0, idx);
		}
		path = path.replaceAll("/", ".");
		return path;
	}

	/**
	 * 指定したクラスのインスタンスを作成します。
	 * @param classname クラス名。
	 * @return クラスのインスタンス。
	 * @throws Exception 例外。
	 */
	private Page newDataFormsInstance(final String classname) throws Exception {
		@SuppressWarnings("unchecked")
		Class<? extends Page> clazz = (Class<? extends Page>) Class.forName(classname);
		Page dataforms = clazz.newInstance();
		return dataforms;
	}


	/**
	 * リクエストに対応したPageのインスタンスを取得します。
	 * @param req HTTP要求情報。
	 * @return DataFormsのインスタンス。
	 * @throws Exception 例外。
	 */
	protected final Page getPage(final HttpServletRequest req) throws Exception {
		String pageext = this.getPageExt();
		if (DataFormsServlet.configStatus == null) {
			String uri = req.getRequestURI();
			String context = req.getContextPath();
			log.info("context=" + context + ", uri=" + uri);
			String classname = DataFormsServlet.convertPageClassName(this.getTargetClassName(context, uri));
			log.info("classname=" + classname);
			Page page = this.newDataFormsInstance(classname);
			page.setPage(page);
			page.setRequest(req);
			page.setPageExt(pageext);
			return page;
		} else {
			ConfigErrorPage page = new ConfigErrorPage(DataFormsServlet.configStatus);
			page.setPage(page);
			page.setRequest(req);
			page.setPageExt(pageext);
			return page;
		}
	}

	/**
	 * メソッドを呼び出します。
	 * <pre>
	 * InvocationTargetExceptionが発生した場合、その原因をスローするようにします。
	 * </pre>
	 * @param comp コンポーネント。
	 * @param param パラメータ。
	 * @param m メソッド。
	 * @return 実行結果。
	 * @throws Exception 例外。
	 */
	private Object callMethod(final WebComponent comp, final Map<String, Object> param, final Method m) throws Exception {
		Object ret = null;
		try {
			ret = m.invoke(comp, param);
		} catch (InvocationTargetException e) {
			Throwable th = e.getCause();
			if (th != null) {
				if (th instanceof ApplicationException) {
					throw (ApplicationException) th;
				}
			}
			throw e;
		}
		return ret;
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		this.doProcess(req, resp);
	}


	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		this.doProcess(req, resp);
	}

	/**
	 * QueryStringを解析しMapに展開します。
	 * @param queryString 問合せ文字列。
	 * @return 解析結果のMap。
	 * @throws Exception 例外。
	 */
	private Map<String, Object> parseQueryString(final String queryString) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!StringUtil.isBlank(queryString)) {
			String[] pairs = queryString.split("&");
			for (String pair: pairs) {
				String[] sp = pair.split("=");
				if (sp.length == 2) {
					this.addParamMap(ret, URLDecoder.decode(sp[0], DataFormsServlet.getEncoding()), URLDecoder.decode(sp[1], DataFormsServlet.getEncoding()));
				}
			}
		}
		log.debug("queryString=" + JSON.encode(ret, true));
		return ret;

	}

	/**
	 * QyeryStringをマップに展開します。
	 * @param req 要求情報。
	 * @param param パラメータ。
	 * @return QueryStringを展開したマップ。
	 * @throws Exception 例外。
	 */
	private Map<String, Object> getQueryString(final HttpServletRequest req, final Map<String, Object> param) throws Exception {
		// ajax呼び出しの場合はヘッダにqueryStringが設定されてくる。
		String queryString = req.getHeader("queryString");
		log.debug("header queryString=" + queryString);
		if (queryString == null) {
			// formのsubmitの場合はPOSTされた情報にqueryStringを入れておく。
			queryString = (String) param.get("dfQueryString");
		}
		log.debug("other queryString=" + queryString);
		return this.parseQueryString(queryString);
	}

	/**
	 * servletの処理を実装します。
	 * @param req 要求情報。
	 * @param resp 応答情報。
	 * @throws ServletException サーブレット例外。
	 * @throws IOException IO例外。
	 */
	protected void doProcess(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		boolean isJsonResponse = false;
		req.setCharacterEncoding(encoding);
		Page page = null;
		try {
			try {
				page = this.getPage(req);
				try {
					Map<String, Object> param = this.getParameterMap(req);

					String method = (String) param.get("dfMethod");
					if (method == null) {
						method = GET_HTML_METHOD_NAME;
						String queryString = req.getQueryString();
						log.debug("getHtml queryString=" + queryString);
						page.setQueryString(this.parseQueryString(queryString));
					} else {
						page.setQueryString(this.getQueryString(req, param));
					}
					log.info("method=" + method);
					Map<String, Object> userinfo = page.getUserInfo();
					if (userinfo != null) {
						log.info("access user=" + userinfo.get("loginId") + "(" + userinfo.get("userId") + ")");
					}
//					String[] split = method.split("\\.");
					int lidx = method.lastIndexOf(".");
					Method m = null;
					WebComponent obj = page;
					if (lidx < 0) {
						// 該当ページのメソッドを呼び出す。
						m = obj.getClass().getMethod(method, Map.class);
					} else {
						// '.'を含むメソッドの場合ページから対応オブジェクトを取得する.
						method = method.replaceAll("\\[[0-9]+\\]", "");
						// log.info("method=" + method);
						String[] sp = method.split("\\.");
						for (int i = 0; i < sp.length - 1; i++) {
							obj = obj.getComponent(sp[i]);
						}
						m = obj.getClass().getMethod(sp[sp.length - 1], Map.class);
					}
					Class<?> mt = m.getReturnType();
					if (JsonResponse.class.getName().equals(mt.getName())) {
						isJsonResponse = true;
					}

					if (!GET_HTML_METHOD_NAME.equals(method)) {
						if (!page.isValidRequest(param)) {
							throw new ApplicationException(page, "error.csrftoken");
						}
					}

					WebMethod wma = m.getAnnotation(WebMethod.class);
					if (wma == null) {
						log.error(MessagesUtil.getMessage(page, "error.notwebmethod", method));
						throw new Exception();
					}
					Connection conn = null;
					if (wma.useDB()) {
						conn = this.getConnection();
					}
					try {
						page.setConnection(conn);
						page.autoLogin();
						if (!wma.everyone()) {
							if (!page.isAuthenticated(param)) {
								throw new ApplicationException(page, "error.auth");
							}
						}
						page.setRequest(req);
						page.setResponse(resp);
						param.remove("dfMethod");
						Response r = (Response) callMethod(obj, param, m);
						r.send(resp);
						if (conn != null) {
							conn.commit();
						}
					} catch (Exception e) {
						if (conn != null) {
							conn.rollback();
						}
						log.error(e.getMessage(), e);
						throw e;
					} finally {
						if (conn != null) {
							conn.close();
						}
					}
				} finally {
					page.releasePage();
				}
			} catch (ApplicationException e) {
				// TODO:JsonResponseを戻り値にしないと、csrfエラーメッセージが表示されな問題を対策する必要がある。
				log.error(e.getMessageKey() + ":" + e.getMessage(), e);
				if (isJsonResponse) {
					Map<String, Object> einfo = new HashMap<String, Object>();
					einfo.put("key", e.getMessageKey());
					einfo.put("message", e.getMessage());
					JsonResponse r = new JsonResponse(JsonResponse.APPLICATION_EXCEPTION, einfo);
					r.send(resp);
				} else {
					this.redirectErrorPage(page, req, resp, e.getMessage());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resp.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR);
		}

	}


	/**
	 * パラメータを取得します。
	 * @param req HTTP要求情報。
	 * @return パラメータマップ。
	 * @throws Exception 例外。
	 */
	protected Map<String, Object> getParameterMap(final HttpServletRequest req) throws Exception {
		if (ServletFileUpload.isMultipartContent(req)) {
			return this.getParameterMapForMultipart(req);
		} else {
			return this.getParameterMapForUrlencoded(req);
		}
	}



	/**
	 * File Uploadが無い場合のパラメータ解析を行います。
	 * @param req HTTPリクエスト。
	 * @return パラメータ解析結果。
	 * @throws Exception 例外。
	 */
	private Map<String, Object> getParameterMapForUrlencoded(final HttpServletRequest req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String[]> m = req.getParameterMap();
		for (String key : m.keySet()) {
			String[] value = m.get(key);
			if (value == null) {
				map.put(key, null);
			} else {
				if (value.length > 1) {
					List<String> list = new ArrayList<String>();
					for (String s : value) {
						list.add(s);
					}
					map.put(key, list);
				} else {
					map.put(key, value[0]);
				}
			}
		}
		return map;
	}


	/**
	 * パラメータマップに値を追加します。
	 * @param map パラメータマップ。
	 * @param key キー。
	 * @param value 値。
	 */
	private void addParamMap(final Map<String, Object> map, final String key, final String value) {
//		log.info("paramater:" + key + "=" + value);
		if (map.containsKey(key)) {
			Object o = map.get(key);
			if (o instanceof List<?>) {
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>) o;
				list.add(value);
				map.put(key, list);
			} else {
				List<String> list = new ArrayList<String>();
				list.add((String) o);
				list.add(value);
				map.put(key, list);
			}
		} else {
			map.put(key, value);
		}
	}


	/**
	 * File Uploadがある場合のパラメータ解析を行います。
	 * @param req HTTPリクエスト。
	 * @return パラメータ解析結果。
	 * @throws Exception 例外。
	 */
	private Map<String, Object> getParameterMapForMultipart(final HttpServletRequest req) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(tempDir)); //一時的に保存する際のディレクトリ
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding(encoding);
		@SuppressWarnings("unchecked")
		List<FileItem> list = upload.parseRequest(req);
		for (FileItem item : list) {
			String key = item.getFieldName();
			if (key != null) {
				if (item.isFormField()) {
					String value = item.getString(encoding);
					this.addParamMap(map, key, value);
				} else {
					String filename = item.getName();
					if (StringUtil.isBlank(filename)) {
						map.put(key, null);
					} else {
						map.put(key, item);
					}
				}
			}
		}
		return map;
	}


	/**
	 * エラーページにリダイレクトします。
	 * @param page ページ。
	 * @param req 要求情報。
	 * @param resp 応答情報。
	 * @param message メッセージ。
	 * @throws Exception 例外。
	 */
	private void redirectErrorPage(final Page page, final HttpServletRequest req, final HttpServletResponse resp, final String message) throws Exception {
		String context = req.getContextPath();
		String errorPage = DataFormsServlet.errorPage;
		if (page != null) {
			errorPage = page.getErrorPage();
		}
//		String url = context + errorPage + "?msg=" + java.net.URLEncoder.encode(message, DataFormsServlet.encoding);
		String url = context + errorPage;
		log.info("errorPage=" + url);
		try {
			req.getSession().setAttribute(ErrorPage.ID_ERROR_MESSAGE, message);
			resp.sendRedirect(url);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * クライアントログレベルを取得します。
	 * @return クライアントログレベル。
	 */
	public static String getClientLogLevel() {
		return clientLogLevel;
	}

	@Override
	public void destroy() {
		log.debug("DataFormsServlet destroy");
		if (DataFormsServlet.servletInstanceBean != null) {
			try {
				DataFormsServlet.servletInstanceBean.destroy();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		super.destroy();
		BlobFileStore.cleanup();
	}

	/**
	 * ブラウザのクッキー受け入れチェックフラグ。
	 *
	 */
	private static boolean cookieCheck = false;

	/**
	 * ブラウザのクッキー受け入れチェックフラグを取得します。
	 * @return ブラウザのクッキー受け入れチェックフラグ。
	 */
	public static boolean isCookieCheck() {
		return cookieCheck;
	}


	/**
	 * ブラウザのクッキー受け入れチェックフラグを指定します。
	 * @param cookieCheck ブラウザのクッキー受け入れチェックフラグ。
	 */
	public static void setCookieCheck(final boolean cookieCheck) {
		DataFormsServlet.cookieCheck = cookieCheck;
	}

	/**
	 * Apache-FOPの設定ファイルバスを取得します。
	 * @return Apache-FOPの設定ファイルバス。
	 */
	public static String getApacheFopConfig() {
		return apacheFopConfig;
	}


	/**
	 * Apache-FOPの設定ファイルのパスを取得します。
	 * @param apacheFopConfig Apache-FOPの設定ファイルバス。
	 */
	public static void setApacheFopConfig(final String apacheFopConfig) {
		DataFormsServlet.apacheFopConfig = apacheFopConfig;
	}

}
