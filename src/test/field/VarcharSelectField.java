package test.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataforms.field.common.SelectField;
import dataforms.field.common.VarcharSingleSelectField;
import dataforms.validator.MaxLengthValidator;


/**
 * VarcharSelectFieldフィールドクラス。
 *
 */
public class VarcharSelectField extends VarcharSingleSelectField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 8;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "Varchar選択肢";
	/**
	 * コンストラクタ。
	 */
	public VarcharSelectField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public VarcharSelectField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
		this.setBlankOption(true);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}

	// 以下の選択肢取得処理を適切に変更して使用してください。
	/**
	 * 選択肢。
	 */
	private static final String[][] OPTIONS = {
		{"0", "選択肢01"}
		, {"1", "選択肢02"}
	};

	/**
	 * 選択肢リストを取得ます。
	 * @return 選択肢リスト。
	 */
	private List<Map<String, Object>> queryOptionList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String[] opt: OPTIONS) {
			SelectField.OptionEntity e = new SelectField.OptionEntity();
			e.setValue(opt[0]);
			e.setName(opt[1]);
			list.add(e.getMap());
		}
		return list;
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setOptionList(this.queryOptionList());
	}

	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p 入力パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Object obj = p; // TODO:必要な処理を行い、結果オブジェクトを作成してください。
		Response resp = new JsonResponse(JsonResponse.SUCCESS, obj);
		return resp;
	}
*/

}
