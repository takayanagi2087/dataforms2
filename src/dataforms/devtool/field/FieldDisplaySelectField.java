package dataforms.devtool.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataforms.devtool.field.FieldDisplaySelectField.FieldDisplay;
import dataforms.field.common.SelectField;
import dataforms.field.common.SingleSelectField;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;

/**
 * マッチタイプ選択フィールドクラス。
 */
public class FieldDisplaySelectField extends SingleSelectField<FieldDisplay> {
	/**
	 * フィールド表示。
	 */
	public static enum FieldDisplay {
		/**
		 * 表示しない。
		 */
		NONE
		/**
		 * INPUTタグ。
		 */
		, INPUT
		/**
		 * INPUTタグ読取専用。
		 */
		, INPUT_READONLY
		/**
		 * INPUTタグ非表示。
		 */
		, INPUT_HIDDEN
		/**
		 * 表示のみ。
		 */
		, SPAN
	}

	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public FieldDisplaySelectField(final String id) {
		super(id);
	}

	/**
	 * MatchTypeのリストを取得する。
	 * @return MatchTypeのリスト。
	 */
	private List<Map<String, Object>> getMatchTypeList() {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (FieldDisplay mt: FieldDisplay.values()) {
			String key = "fielddisplay." + mt.toString();
			String name = MessagesUtil.getMessage(getWebEntryPoint(), key);
			if (!StringUtil.isBlank(name)) {
				Map<String, Object> opt = new HashMap<String, Object>();
				opt.put(SelectField.OptionEntity.ID_VALUE, mt.toString());
				opt.put(SelectField.OptionEntity.ID_NAME, name);
				ret.add(opt);
			}
		}
		return ret;
	}

	@Override
	public void init() throws Exception {
		super.init();
		List<Map<String, Object>> options = this.getMatchTypeList();
		this.setOptionList(options);
	}
}
