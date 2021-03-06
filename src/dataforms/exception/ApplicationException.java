package dataforms.exception;

import dataforms.controller.WebEntryPoint;
import dataforms.util.MessagesUtil;

/**
 * アプリケーションレベルで発生する例外クラス。
 *
 */
public class ApplicationException extends Exception {
	/**
	 * UID。
	 */
	private static final long serialVersionUID = 7025730004513064767L;

	/**
	 * メッセージキー。
	 */
	private String messageKey = null;

	/**
	 * コンストラクタ。
	 * @param epoint エラーが発生したページ。
	 * @param msgkey メッセージのキー。
	 * @param args メッセージ引数。
	 */
	public ApplicationException(final WebEntryPoint epoint, final String msgkey, final String... args) {
		super(MessagesUtil.getMessage(epoint, msgkey, args));
		this.messageKey = msgkey;
	}


	/**
	 * コンストラクタ。
	 *
	 * @param msgkey メッセージのキー。
	 * @param message メッセージのテキスト。
	 * @deprecated
	 */
	public ApplicationException(final String msgkey, final String message) {
		super(message);
		this.messageKey = msgkey;
	}

	/**
	 * メッセージキーを取得します。
	 * @return メッセージキー。
	 */
	public String getMessageKey() {
		return messageKey;
	}


	/**
	 * メッセージキーを設定します。
	 * @param messageKey メッセージキー。
	 */
	public void setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
	}

}
