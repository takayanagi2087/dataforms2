package dataforms.validator;

/**
 * ひらがなバリデータクラス。
 *
 */
public final class HiraganaValidator extends RegexpValidator {
	/**
	 * コンストラクタ。
	 */
	public HiraganaValidator() {
		super("error.hiragana",  "^[\\u3040-\\u309F]+$");
	}
}
