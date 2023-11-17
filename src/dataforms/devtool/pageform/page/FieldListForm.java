package dataforms.devtool.pageform.page;

import dataforms.controller.Form;
import dataforms.devtool.query.page.SelectFieldHtmlTable;

/**
 * フィールドリスト設定フォーム。
 */
public class FieldListForm extends Form {
	/**
	 * Logger.
	 */
//	private static Logger logger = LogManager.getLogger(FieldListForm.class);

	/**
	 * キーフィールドリスト。
	 */
	private static final String ID_FIELD_LIST = "fieldList";


	/**
	 * コンストラクタ。
	 */
	public FieldListForm() {
		this(null);
	}

	/**
	 * コンストラクタ。
	 * @param id フォームID。
	 */
	public FieldListForm(final String id) {
		super(id);
		SelectFieldHtmlTable table = new SelectFieldHtmlTable(ID_FIELD_LIST, true);
		table.getFieldList().get("fieldId").clearValidator();
		table.getFieldList().get("comment").setReadonly(true);
		table.setFixedColumns(3);
		this.addHtmlTable(table);
	}


	/**
	 * 指定されたクラスのフィールドリストを所得する。
	 * @param p パラメータ。
	 * @return フィールドリスト。
	 * @throws Exception 例外。
	 */
/*	@WebMethod
	public Response getFieldList(final Map<String, Object> p) throws Exception {
		logger.debug("p=" + JSON.encode(p));
		FieldList flist = SelectFieldHtmlTable.getFieldList((String) p.get("c"));
		List<Map<String, Object>> list = SelectFieldHtmlTable.getTableData(flist, "");
		Response resp = new JsonResponse(JsonResponse.SUCCESS, list);
		return resp;
	}
*/
}
