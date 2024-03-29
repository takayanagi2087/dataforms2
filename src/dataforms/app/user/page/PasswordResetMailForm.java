package dataforms.app.user.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.app.user.dao.UserDao;
import dataforms.app.user.dao.UserInfoTable;
import dataforms.app.user.field.MailAddressField;
import dataforms.controller.EditForm;
import dataforms.controller.WebComponent;
import dataforms.mail.MailSender;
import dataforms.mail.MailTemplate;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.CryptUtil;
import dataforms.util.MessagesUtil;
import dataforms.validator.RequiredValidator;
import net.arnx.jsonic.JSON;

/**
 * パスワードリセットメール送信フォームクラス。
 *
 */
public class PasswordResetMailForm extends EditForm {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(PasswordResetMailForm.class);

	/**
	 * パスワードリセットページ。
	 */
	private static String passwordResetPage = null;

	/**
	 * コンストラクタ。
	 */
	public PasswordResetMailForm() {
		this.addField(new MailAddressField()).addValidator(new RequiredValidator()).setComment("メールアドレス");
	}

	/**
	 * パスワードリセットページを取得します。
	 * @return パスワードリセットページ。
	 */
	public static String getPasswordResetPage() {
		return passwordResetPage;
	}

	/**
	 * パスワードリセットページを設定します。
	 * @param passwordResetPage パスワードリセットページ。
	 */
	public static void setPasswordResetPage(final String passwordResetPage) {
		PasswordResetMailForm.passwordResetPage = passwordResetPage;
	}

	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		return new HashMap<String, Object>();
	}

	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		return false;
	}

	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		UserInfoTable.Entity e = new UserInfoTable.Entity(data);
		UserDao dao = new UserDao(this);
		List<Map<String, Object>> list = dao.queryUserListByMail(e.getMailAddress());
		logger.debug(() -> "userInfo=" + JSON.encode(list, true));
		String path = this.getAppropriatePath("/mailTemplate/passwordResetMail.txt", this.getPage().getRequest());
		String text = this.getWebResource(path);
		logger.debug(() -> "template=" + text);

		HttpServletRequest req = this.getPage().getRequest();
		String url = req.getRequestURL().toString();
		String uri = req.getRequestURI();
		url = url.replaceAll(uri, req.getContextPath()) + PasswordResetMailForm.getPasswordResetPage() + "." + WebComponent.getServlet().getPageExt();
		MailTemplate template = new MailTemplate(text, null);
		String urllist = "";
		for (Map<String, Object> u: list) {
			UserInfoTable.Entity ue = new UserInfoTable.Entity(u);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put(UserInfoTable.Entity.ID_USER_ID, ue.getUserId());
			m.put(UserInfoTable.Entity.ID_LOGIN_ID, ue.getLoginId());
			m.put(UserInfoTable.Entity.ID_USER_NAME, ue.getUserName());
			m.put(UserInfoTable.Entity.ID_MAIL_ADDRESS, ue.getMailAddress());
			String json = JSON.encode(m);
			String key = CryptUtil.encrypt(json, DataFormsServlet.getQueryStringCryptPassword());
			String enckey = java.net.URLEncoder.encode(key, DataFormsServlet.getEncoding());
			logger.debug("url={}", url);
			urllist += (url + "?key=" + enckey + "\n") ;
		}
		template.setLink("passwordResetPage", urllist, urllist);

		template.addToAddress(e.getMailAddress());
		template.setFrom(MailSender.getMailFrom());
		template.setReplyTo(MailSender.getMailFrom());
//		template.setArg(UserInfoTable.Entity.ID_USER_NAME, e.getUserName());

		Session session = MailSender.getMailSession();
		MailSender sender = new MailSender();
		sender.send(template, session);

	}

	@Override
	protected String getSavedMessage(final Map<String, Object> data) {
		return MessagesUtil.getMessage(this.getPage(), "message.passwordmailsent");
	}

	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
	}

	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
	}

}
