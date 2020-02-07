package dataforms.field.common;

import java.util.List;
import java.util.Map;

import dataforms.util.MessagesUtil;

/**
 * *.propertiesの内容を複数選択するフィールド。
 *
 */
public class PropertiesMultiSelectField extends MultiSelectField<String> {

	/**
	 * propertiesのキー。
	 */
	private String key = null;

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 * @param key リソースのkey。
	 * @param length 項目長。
	 */
	public PropertiesMultiSelectField(final String id, final String key, final int length) {
		super(id, length);
		this.key = key;
	}

	@Override
	public void init() throws Exception {
		super.init();
		List<Map<String, Object>> options = MessagesUtil.getSelectFieldOption(this.getPage(), this.key);
		this.setOptionList(options);
	}
}
