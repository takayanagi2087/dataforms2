package dataforms.app.login.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.user.dao.UserInfoTable;
import dataforms.controller.Form;
import dataforms.controller.WebEntryPoint;
import dataforms.field.base.TextField;
import dataforms.mail.MailSender;
import dataforms.mail.MailTemplate;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.util.AutoLoginCookie;
import dataforms.util.MessagesUtil;
import dataforms.util.OnetimePasswordUtil;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

/**
 * ワンタイムパスワード確認フォーム。
 *
 */
public class OnetimePasswordForm extends Form {

	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(OnetimePasswordForm.class);

	/**
	 * コンストラクタ。
	 */
	public OnetimePasswordForm() {
		super(null);
		this.addField(new TextField("onetimePassword")).addValidator(new RequiredValidator());
	}

	/**
	 * ワンタイムパスワードメールを送信する。
	 * @param userInfo ユーザ情報。
	 * @return 生成したワンタイムパスワード。
	 */
	private String sendOnetimeMail(Map<String, Object> userInfo) throws Exception {
		String path = this.getAppropriatePath("/mailTemplate/onetimePassMail.txt", this.getPage().getRequest());
		String text = this.getWebResource(path);
		logger.debug(() -> "template=" + text);
		String onetime = OnetimePasswordUtil.generateOnetimePassword();
		logger.debug("onetime=" + onetime);
		UserInfoTable.Entity e = new UserInfoTable.Entity(userInfo);
		logger.debug("mailAddress=" + e.getMailAddress());
		MailTemplate template = new MailTemplate(text, null);
		template.setArg("onetimePassword", onetime);
		template.addToAddress(e.getMailAddress());
		template.setFrom(MailSender.getMailFrom());
		template.setReplyTo(MailSender.getMailFrom());
		Session session = MailSender.getMailSession();
		MailSender sender = new MailSender();
		sender.send(template, session);
		return onetime;
	}

	@Override
	public void init() throws Exception {
		super.init();
		HttpSession session = this.getPage().getRequest().getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(OnetimePasswordUtil.USERINFO);
		String onetime = this.sendOnetimeMail(userInfo);
		session.setAttribute(OnetimePasswordUtil.ONETIME, onetime);
	}

	/**
	 * ワンタイムパスワードの再尊信。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response sendOnetimePassword(final Map<String, Object> p) throws Exception {
		HttpSession session = this.getPage().getRequest().getSession();
		@SuppressWarnings("unchecked")
		Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(OnetimePasswordUtil.USERINFO);
		String onetime = this.sendOnetimeMail(userInfo);
		session.setAttribute(OnetimePasswordUtil.ONETIME, onetime);
		Response r = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(getWebEntryPoint(), "message.sent"));
		return r;
	}

	/**
	 * ワンタイムパスワードの確認を行います。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response login(final Map<String, Object> p) throws Exception {
		String onetime = (String) p.get("onetimePassword");
		HttpSession session = this.getPage().getRequest().getSession();
		String pass = (String) session.getAttribute(OnetimePasswordUtil.ONETIME);
		if (onetime.equals(pass)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute(OnetimePasswordUtil.USERINFO);
			session.setAttribute(WebEntryPoint.USER_INFO, userInfo);
			session.removeAttribute(OnetimePasswordUtil.USERINFO);
			OnetimePasswordUtil.setSkipOnetimeCookie(getPage(), userInfo);
			AutoLoginCookie.setAutoLoginCookie(this.getPage(), userInfo);
			Response r = new JsonResponse(JsonResponse.SUCCESS, "");
			return r;
		} else {
			List<ValidationError> list = new ArrayList<ValidationError>();
			list.add(new ValidationError(
				"onetimePassword"
				,MessagesUtil.getMessage(getWebEntryPoint(), "error.ontimepasswordnotmatch")
			));
			Response r = new JsonResponse(JsonResponse.INVALID, list);
			return r;
		}
	}

}
