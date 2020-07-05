package test.page;

import dataforms.controller.QueryForm;
import test.field.SqlBigintField;
import test.field.SqlVarcharField;
import test.field.SqlNumericField;
import test.field.SqlClobField;
import test.field.SqlTimestampField;
import test.field.SqlDoubleField;
import test.dao.SqlTypeTable;
import dataforms.field.base.Field.MatchType;
import test.field.SqlIntegerField;
import test.field.SqlTimeField;
import test.field.SqlCharField;
import java.util.List;
import dataforms.field.base.FieldList;
import test.field.SqlDateField;
import test.field.AddressField;
import java.util.Map;
import dataforms.field.common.ZipCodeField;
import test.field.SqlSmallintField;
import dataforms.report.ExportDataFile;

import test.dao.SqlTypeDao;


/**
 * 問い合わせフォームクラス。
 */
public class SqlTypeQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public SqlTypeQueryForm() {
		this.addField(new SqlCharField(SqlTypeTable.Entity.ID_SQL_CHAR)).setMatchType(MatchType.FULL).setComment("Charフィールド");
		this.addField(new SqlVarcharField(SqlTypeTable.Entity.ID_SQL_VARCHAR)).setMatchType(MatchType.FULL).setComment("Varcharフィールド");
		this.addField(new SqlSmallintField(SqlTypeTable.Entity.ID_SQL_SMALLINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Smallintフィールド(from)");
		this.addField(new SqlSmallintField(SqlTypeTable.Entity.ID_SQL_SMALLINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("Smallintフィールド(to)");
		this.addField(new SqlIntegerField(SqlTypeTable.Entity.ID_SQL_INTEGER + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Integerフィールド(from)");
		this.addField(new SqlIntegerField(SqlTypeTable.Entity.ID_SQL_INTEGER + "To")).setMatchType(MatchType.RANGE_TO).setComment("Integerフィールド(to)");
		this.addField(new SqlBigintField(SqlTypeTable.Entity.ID_SQL_BIGINT + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Bigintフィールド(from)");
		this.addField(new SqlBigintField(SqlTypeTable.Entity.ID_SQL_BIGINT + "To")).setMatchType(MatchType.RANGE_TO).setComment("Bigintフィールド(to)");
		this.addField(new SqlDoubleField(SqlTypeTable.Entity.ID_SQL_DOUBLE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Doubleフィールド(from)");
		this.addField(new SqlDoubleField(SqlTypeTable.Entity.ID_SQL_DOUBLE + "To")).setMatchType(MatchType.RANGE_TO).setComment("Doubleフィールド(to)");
		this.addField(new SqlNumericField(SqlTypeTable.Entity.ID_SQL_NUMERIC + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Numericフィールド(from)");
		this.addField(new SqlNumericField(SqlTypeTable.Entity.ID_SQL_NUMERIC + "To")).setMatchType(MatchType.RANGE_TO).setComment("Numericフィールド(to)");
		this.addField(new SqlDateField(SqlTypeTable.Entity.ID_SQL_DATE + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Dateフィールド(from)");
		this.addField(new SqlDateField(SqlTypeTable.Entity.ID_SQL_DATE + "To")).setMatchType(MatchType.RANGE_TO).setComment("Dateフィールド(to)");
		this.addField(new SqlTimeField(SqlTypeTable.Entity.ID_SQL_TIME + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Timeフィールド(from)");
		this.addField(new SqlTimeField(SqlTypeTable.Entity.ID_SQL_TIME + "To")).setMatchType(MatchType.RANGE_TO).setComment("Timeフィールド(to)");
		this.addField(new SqlTimestampField(SqlTypeTable.Entity.ID_SQL_TIMESTAMP + "From")).setMatchType(MatchType.RANGE_FROM).setComment("Timestampフィールド(from)");
		this.addField(new SqlTimestampField(SqlTypeTable.Entity.ID_SQL_TIMESTAMP + "To")).setMatchType(MatchType.RANGE_TO).setComment("Timestampフィールド(to)");
		this.addField(new SqlClobField(SqlTypeTable.Entity.ID_SQL_CLOB)).setMatchType(MatchType.FULL).setComment("Clobフィールド");
		this.addField(new ZipCodeField(SqlTypeTable.Entity.ID_ZIP_CODE)).setMatchType(MatchType.FULL).setComment("郵便番号");
		this.addField(new AddressField(SqlTypeTable.Entity.ID_ADDRESS)).setMatchType(MatchType.FULL).setComment("住所");

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
