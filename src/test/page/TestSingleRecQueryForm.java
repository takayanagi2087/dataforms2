package test.page;

import java.util.List;
import java.util.Map;

import dataforms.controller.QueryForm;
import dataforms.field.base.Field.MatchType;
import dataforms.field.base.FieldList;
import dataforms.field.common.SortOrderField;
import dataforms.report.ExportDataFile;
import test.dao.TestMultiRecTable;
import test.dao.TestSingleRecDao;
import test.field.Code1Field;
import test.field.Code2Field;
import test.field.ContentsField;


/**
 * 問い合わせフォームクラス。
 */
public class TestSingleRecQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public TestSingleRecQueryForm() {
		this.addField(new SortOrderField(TestMultiRecTable.Entity.ID_SORT_ORDER + "From")).setMatchType(MatchType.RANGE_FROM).setComment("ソート順(from)");
		this.addField(new SortOrderField(TestMultiRecTable.Entity.ID_SORT_ORDER + "To")).setMatchType(MatchType.RANGE_TO).setComment("ソート順(to)");
		this.addField(new Code1Field(TestMultiRecTable.Entity.ID_CODE1)).setMatchType(MatchType.FULL).setComment("コード1");
		this.addField(new Code2Field(TestMultiRecTable.Entity.ID_CODE2)).setMatchType(MatchType.FULL).setComment("コード2");
		this.addField(new ContentsField(TestMultiRecTable.Entity.ID_CONTENTS)).setMatchType(MatchType.FULL).setComment("内容");

	}

	@Override
	public void init() throws Exception {
		super.init();
		// フィールドに初期値を設定する場合は以下の様にしてください。
		// this.setFormData("fieldId", "初期値");
	}

	/**
	 * エクスポートデータのフィールドリストを作成します。
	 * @return フィールドリスト。
	 */
	@Override
	protected FieldList getExportDataFieldList(final Map<String, Object> data) throws Exception {
		TestSingleRecDao dao = new TestSingleRecDao(this);
		return dao.getListQuery().getFieldList();
	}

	/**
	 * エクスポートデータファイルのインスタンスを返します。
	 * @return エクスポートデータファイルのインスタンス。
	 */
	@Override
	protected ExportDataFile getExportDataFile() {
		ExportDataFile file = super.getExportDataFile();
		file.setFileName("export.xlsx");
		return file;
	}

	/**
	 * エクスポートするデータを返します。
	 * @param data 条件データ。
	 * @return エクスポートデータ。
	 */
	@Override
	protected List<Map<String, Object>> queryExportData(final Map<String, Object> data) throws Exception {
		TestSingleRecDao dao = new TestSingleRecDao(this);
		return dao.query(data, this.getFieldList());
	}


	// フォームの各フィールドの関連チェックを行う場合は、以下のvalidateFormメソッドを実装してください。
	/**
	 * フォームのデータをチェックします。
	 * @param p パラメータ。
	 * @return 判定結果リスト。
	 * @throws Exception 例外。
	 */
/*
	@Override
	protected List<ValidationError> validateForm(final Map<String, Object> data) throws Exception {
		List<ValidationError> list = super.validateForm(data);
		if (list.size() == 0) {
			if ( エラー判定 ) {
				list.add(new ValidationError(HogeTable.Entity.ID_FIELD_ID, MessagesUtil.getMessage(this.getPage(), "error.messagekey")));
			}
		}
		return list;
	}
*/


}
