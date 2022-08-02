package dataforms.field.common;

import java.util.Map;

import dataforms.field.base.Field;
import dataforms.util.MessagesUtil;

/**
 * ファイルDrag&Drop受付フィールド。
 *
 */
public class FileReceiverField extends Field<String> {

	/**
	 * ファイルフィールドID。
	 * <pre>
	 * Drag&amp;Dropされた際にファイルをセットするFileFieldのID。
	 * </pre>
	 */
	private String fileFieldId = null;

	/**
	 * メッセージ。
	 */
	private String message = null;

	/**
	 * ファイルフィールドIDを取得します。
	 * @return ファイルフィールドID。
	 */
	public String getFileFieldId() {
		return fileFieldId;
	}

	/**
	 * ファイルフィールドIDを設定します。
	 * @param fileFieldId ファイルフィールドID。
	 */
	public void setFileFieldId(final String fileFieldId) {
		this.fileFieldId = fileFieldId;
	}

	/**
	 * メッセージを取得します。
	 * @return メッセージ。
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * メッセージを設定します。
	 * @param message メッセージ。
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * コンストラクタ。
	 * @param fileFieldId ファイルフィールドID。
	 */
	public FileReceiverField(final String fileFieldId) {
		this(null, fileFieldId);
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 * @param fileFieldId ファイルフィールドID。
	 */
	public FileReceiverField(final String id, final String fileFieldId) {
		super(id);
		this.fileFieldId = fileFieldId;
	}

	@Override
	public void init() throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		super.init();
		this.message = MessagesUtil.getMessage(getWebEntryPoint(), "message.filereceiver");
	}

	@Override
	public void setClientValue(Object v) {
		this.setValue((String) v);
	}

	@Override
	public Map<String, Object> getProperties() throws Exception {
		Map<String, Object> prop = super.getProperties();
		prop.put("fileFieldId", this.fileFieldId);
		prop.put("message", this.message);
		return prop;
	}
}
