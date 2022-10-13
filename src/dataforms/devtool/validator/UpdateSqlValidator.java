package dataforms.devtool.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dataforms.validator.FieldValidator;

/**
 * 更新系SQLのバリデーター。
 */
public class UpdateSqlValidator extends FieldValidator {
	/**
	 * コンストラクタ。
	 */
	public UpdateSqlValidator() {
		super("error.parametersremain");
	}

	@Override
	public boolean validate(Object value) throws Exception {
		String sql = (String) value;
		Pattern p = Pattern.compile(":[a-zA-Z][0-9a-zA-Z_]*");
		Matcher m = p.matcher(sql);
		if (m.find()) {
			return false;
		}
		return true;
	}
}
