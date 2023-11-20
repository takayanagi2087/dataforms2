package fieldtest.page;

import dataforms.controller.QueryForm;
import fieldtest.field.SqlSmallintField;
import fieldtest.field.SqlVarcharField;
import fieldtest.field.SqlDateField;
import fieldtest.field.SqlClobField;
import fieldtest.field.SqlIntegerField;
import dataforms.field.base.Field.MatchType;
import java.util.List;
import dataforms.field.base.FieldList;
import fieldtest.field.SqlTimestampField;
import fieldtest.field.SqlDoubleField;
import fieldtest.field.SqlCharField;
import java.util.Map;
import fieldtest.field.SqlTimeField;
import fieldtest.dao.SqlTypeTable;
import fieldtest.field.SqlNumericField;
import dataforms.report.ExportDataFile;

import fieldtest.dao.SqlTypeDao;


/**
 * 問い合わせフォームクラス。
 */
public class SqlTypeQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SqlTypeQueryForm() {
		this.addField(new SqlCharField(SqlTypeTable.Entity.ID_SQL_CHAR)).setMatchType(MatchType.FULL).setComment("CHARフィールド");
		this.addField(new SqlVarcharField(SqlTypeTable.Entity.ID_SQL_VARCHAR)).setMatchType(MatchType.PART).setComment("VarcharField");
		this.addField(new SqlSmallintField(SqlTypeTable.Entity.ID_SQL_SMALLINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("SMALLINTフィールド(from)");
		this.addField(new SqlSmallintField(SqlTypeTable.Entity.ID_SQL_SMALLINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("SMALLINTフィールド(to)");
		this.addField(new SqlIntegerField(SqlTypeTable.Entity.ID_SQL_INTEGER + "From")).setMatchType(MatchType.RANGE_FROM).setComment("INTEGERフィールド(from)");
		this.addField(new SqlIntegerField(SqlTypeTable.Entity.ID_SQL_INTEGER + "To")).setMatchType(MatchType.RANGE_TO).setComment("INTEGERフィールド(to)");
		this.addField(new SqlDoubleField(SqlTypeTable.Entity.ID_SQL_DOUBLE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("DOUBLEフィールド(from)");
		this.addField(new SqlDoubleField(SqlTypeTable.Entity.ID_SQL_DOUBLE + "To")).setMatchType(MatchType.RANGE_TO).setComment("DOUBLEフィールド(to)");
		this.addField(new SqlNumericField(SqlTypeTable.Entity.ID_SQL_NUMERIC + "From")).setMatchType(MatchType.RANGE_FROM).setComment("NUMERICフィールド(from)");
		this.addField(new SqlNumericField(SqlTypeTable.Entity.ID_SQL_NUMERIC + "To")).setMatchType(MatchType.RANGE_TO).setComment("NUMERICフィールド(to)");
		this.addField(new SqlDateField(SqlTypeTable.Entity.ID_SQL_DATE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("DATEフィールド(from)");
		this.addField(new SqlDateField(SqlTypeTable.Entity.ID_SQL_DATE + "To")).setMatchType(MatchType.RANGE_TO).setComment("DATEフィールド(to)");
		this.addField(new SqlTimeField(SqlTypeTable.Entity.ID_SQL_TIME + "From")).setMatchType(MatchType.RANGE_FROM).setComment("TIMEフィールド(from)");
		this.addField(new SqlTimeField(SqlTypeTable.Entity.ID_SQL_TIME + "To")).setMatchType(MatchType.RANGE_TO).setComment("TIMEフィールド(to)");
		this.addField(new SqlTimestampField(SqlTypeTable.Entity.ID_SQL_TIMESTAMP + "From")).setMatchType(MatchType.RANGE_FROM).setComment("TIMESTAMPフィールド(from)");
		this.addField(new SqlTimestampField(SqlTypeTable.Entity.ID_SQL_TIMESTAMP + "To")).setMatchType(MatchType.RANGE_TO).setComment("TIMESTAMPフィールド(to)");
		this.addField(new SqlClobField(SqlTypeTable.Entity.ID_SQL_CLOB)).setMatchType(MatchType.FULL).setComment("CLOBフィールド");

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
		SqlTypeDao dao = new SqlTypeDao(this);
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
		SqlTypeDao dao = new SqlTypeDao(this);
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


	// 独自のWebメソッドを作成する場合は、以下のコードを参考にしてください。
	/**
	 * Webメソッドのサンプル。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
/*
	@WebMethod
	public Response webMethod(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			Map<String, Object> data = this.convertToServerData(p);
			ret = new JsonResponse(JsonResponse.SUCCESS, data);	// TODO:何らかの処理を行いResponseのインスタンスを作成してください。
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}
*/

}
