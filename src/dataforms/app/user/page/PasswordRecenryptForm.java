package dataforms.app.user.page;

import java.util.List;
import java.util.Map;

import dataforms.annotation.WebMethod;
import dataforms.app.user.dao.UserDao;
import dataforms.app.user.field.CryptAlgolithmField;
import dataforms.app.user.field.PasswordField;
import dataforms.controller.Form;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.util.MessagesUtil;
import dataforms.validator.RequiredValidator;
import dataforms.validator.ValidationError;

/**
 * 問い合わせフォームクラス。
 */
public class PasswordRecenryptForm extends Form {
	/**
	 * 暗号化パスワードフィールドID。
	 */
	private static final String ID_CRYPT_PASSWORD = "cryptPassword";
	/**
	 * 暗号化パスワード(確認)フィールドID。
	 */
	private static final String ID_CRYPT_PASSWORD_CHECK = "cryptPasswordCheck";

	/**
	 * コンストラクタ。
	 */
	public PasswordRecenryptForm() {
		super(null);
		this.addField(new CryptAlgolithmField()).setComment("暗号化アルゴリズム").addValidator(new RequiredValidator());
		this.addField(new PasswordField(ID_CRYPT_PASSWORD)).setComment("パスワード/キー").addValidator(new RequiredValidator());
		this.addField(new PasswordField(ID_CRYPT_PASSWORD_CHECK)).setComment("パスワード/キー(確認)").addValidator(new RequiredValidator());
	}

	@Override
	protected List<ValidationError> validateForm(Map<String, Object> data) throws Exception {
		List<ValidationError> list = super.validateForm(data);
		if (list.size() == 0) {
			String password = (String) data.get(ID_CRYPT_PASSWORD);
			String passwordCheck = (String) data.get(ID_CRYPT_PASSWORD_CHECK);
			if (!password.equals(passwordCheck)) {
				String msg = MessagesUtil.getMessage(this.getPage(), "error.passwordnotmatch");
				list.add(new ValidationError(ID_CRYPT_PASSWORD_CHECK, msg));
			}
		}
		return list;
	}

	/**
	 * パスワードを更新します。
	 * @param data データ。
	 * @throws Exception 例外。
	 */
	private void updatePassword(final Map<String, Object> data) throws Exception {
		UserDao dao = new UserDao(this);
		String algolithm = (String) data.get("cryptAlgolithm");
		String password = (String) data.get(ID_CRYPT_PASSWORD);
		dao.reencryptPassword(algolithm, password);
	}

	/**
	 * 再暗号化を行います。
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response reencrypt(final Map<String, Object> p) throws Exception {
		Response resp = null;
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			Map<String, Object> data = this.convertToServerData(p);
			this.updatePassword(data);
			resp = new JsonResponse(JsonResponse.SUCCESS, MessagesUtil.getMessage(this.getPage(), "message.passwordreencrypted"));
		} else {
			resp = new JsonResponse(JsonResponse.INVALID, list);
		}
		return resp;
	}
}
