package dataforms.validator;

/**
 * 全角文字バリデータクラス。
 *
 */
public final class DoublebyteValidator extends RegexpValidator {
	/**
	 * コンストラクタ。
	 */
	public DoublebyteValidator() {
		super("error.doublebyte", "^[^ -~｡-ﾟ]*$");
	}
}
