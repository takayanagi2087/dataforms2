package dataforms.devtool.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import dataforms.dao.file.FileObject;
import dataforms.dao.file.ImageData;
import dataforms.dao.file.VideoData;
import dataforms.dao.sqldatatype.SqlBigint;
import dataforms.dao.sqldatatype.SqlChar;
import dataforms.dao.sqldatatype.SqlDate;
import dataforms.dao.sqldatatype.SqlDouble;
import dataforms.dao.sqldatatype.SqlInteger;
import dataforms.dao.sqldatatype.SqlNumeric;
import dataforms.dao.sqldatatype.SqlSmallint;
import dataforms.dao.sqldatatype.SqlTime;
import dataforms.dao.sqldatatype.SqlTimestamp;
import dataforms.dao.sqldatatype.SqlVarchar;
import dataforms.field.common.CharSingleSelectField;
import dataforms.field.common.FileObjectField;
import dataforms.field.common.ImageField;
import dataforms.field.common.MultiSelectField;
import dataforms.field.common.VideoField;
import dataforms.field.sqltype.BigintField;
import dataforms.field.sqltype.CharField;
import dataforms.field.sqltype.ClobField;
import dataforms.field.sqltype.DateField;
import dataforms.field.sqltype.IntegerField;
import dataforms.field.sqltype.NumericField;
import dataforms.field.sqltype.SmallintField;
import dataforms.field.sqltype.TimeField;
import dataforms.field.sqltype.TimestampField;
import dataforms.field.sqltype.VarcharField;
import dataforms.util.StringUtil;

/**
 * フィールドリスト、ユーティリティ。
 *
 */
public final class FieldListUtil {
	/**
	 * コンストラクタ。
	 */
	private FieldListUtil() {

	}


	/**
	 * フィールドIDを取得します。
	 * @param m フィールドリスト。
	 * @return フィールドID。
	 */
	public static String getFieldId(final Map<String, Object> m) {
		String fieldId = (String) m.get("fieldId");
		if (StringUtil.isBlank(fieldId)) {
			String fclass = (String) m.get("fieldClassName");
			StringBuilder fsb = new StringBuilder(fclass.replaceAll("Field$", ""));
			char c = fsb.charAt(0);
			fsb.setCharAt(0, Character.toLowerCase(c));
			fieldId = fsb.toString();
		}
		return fieldId;
	}


	/**
	 * フィールドIdの定数を展開します。
	 * @param list フィールドリスト。
	 * @return フィールドIDの定数値。
	 */
	public static String generateFieldIdConstant(final List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String fieldId = getFieldId(m);
			String comment = (String) m.get("comment");
			sb.append("\t\t/** " + comment + "のフィールドID。 */\n");
			sb.append("\t\tpublic static final String ID_");
			sb.append(StringUtil.camelToUpperCaseSnake(fieldId));
			sb.append(" = \"");
			sb.append(fieldId);
			sb.append("\";\n");
		}
		return sb.toString();
	}

	/**
	 * フィールドに対応する値の型を返します。
	 * @param field フィールド。
	 * @return 値の型。
	 */
	private static Class<?> getFieldValueType(final Class<?> field) {
		Class<?> ret = Object.class;
		if (FileObjectField.class.isAssignableFrom(field)) {
			ret = FileObject.class;
		} else if (ImageField.class.isAssignableFrom(field)) {
			ret = ImageData.class;
		} else if (VideoField.class.isAssignableFrom(field)) {
			ret = VideoData.class;
		} else 	if (SqlVarchar.class.isAssignableFrom(field)
			|| SqlChar.class.isAssignableFrom(field)
			|| CharField.class.isAssignableFrom(field)
			|| VarcharField.class.isAssignableFrom(field)
			|| ClobField.class.isAssignableFrom(field)
			|| CharSingleSelectField.class.isAssignableFrom(field)) {
			ret = String.class;
		} else if (DateField.class.isAssignableFrom(field)) {
			ret = java.sql.Date.class;
		} else if (TimeField.class.isAssignableFrom(field)) {
			ret = java.sql.Time.class;
		} else if (TimestampField.class.isAssignableFrom(field)) {
			ret = java.sql.Timestamp.class;
		} else if (SmallintField.class.isAssignableFrom(field)) {
			ret = Short.class;
		} else if (IntegerField.class.isAssignableFrom(field)) {
			ret = Integer.class;
		} else if (BigintField.class.isAssignableFrom(field)) {
			ret = Long.class;
		} else if (NumericField.class.isAssignableFrom(field)) {
			ret = BigDecimal.class;
		} else if (MultiSelectField.class.isAssignableFrom(field)) {
			ret = List.class;
		} else if (SqlBigint.class.isAssignableFrom(field)) { //
			ret = Long.class;
		} else if (SqlInteger.class.isAssignableFrom(field)) {
			ret = Integer.class;
		} else if (SqlSmallint.class.isAssignableFrom(field)) {
			ret = Short.class;
		} else if (SqlDouble.class.isAssignableFrom(field)) {
			ret = Double.class;
		} else if (SqlNumeric.class.isAssignableFrom(field)) {
			ret = BigDecimal.class;
		} else if (SqlDate.class.isAssignableFrom(field)) {
			ret = java.sql.Date.class;
		} else if (SqlTime.class.isAssignableFrom(field)) {
			ret = java.sql.Time.class;
		} else if (SqlTimestamp.class.isAssignableFrom(field)) {
			ret = java.sql.Timestamp.class;
		}
		return ret;
	}

	/**
	 * フィールドのgetter/setterを作成します。。
	 * @param list フィールドリスト。
	 * @return フィールドのgetter/setter。
	 * @throws Exception 例外。
	 */
	public static String generateFieldValueGetterSetter(final List<Map<String, Object>> list) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String superPackageName = (String) m.get("superPackageName");
			String superSimpleClassName = (String) m.get("superSimpleClassName");
			Class<?> cls = Class.forName(superPackageName + "." + superSimpleClassName);
			Class<?> valueType = FieldListUtil.getFieldValueType(cls);
			String fieldId = FieldListUtil.getFieldId(m);
			String uFieldId = StringUtil.firstLetterToUpperCase(fieldId);
			String comment = (String) m.get("comment");
			sb.append("\t\t/**\n");
			sb.append("\t\t * " + comment + "を取得します。\n");
			sb.append("\t\t * @return " + comment + "。\n");
			sb.append("\t\t */\n");
			sb.append("\t\tpublic " + valueType.getName() + " get" + uFieldId+ "() {\n");
			sb.append("\t\t\treturn (" + valueType.getName() + ") this.getMap().get(Entity.ID_" + StringUtil.camelToUpperCaseSnake(fieldId) + ");\n");
			sb.append("\t\t}\n\n");

			sb.append("\t\t/**\n");
			sb.append("\t\t * " + comment + "を設定します。\n");
			sb.append("\t\t * @param " + fieldId + " " + comment + "。\n");
			sb.append("\t\t */\n");
			sb.append("\t\tpublic void set" + uFieldId+ "(final " + valueType.getName() + " " + fieldId + ") {\n");
			sb.append("\t\t\tthis.getMap().put(Entity.ID_" + StringUtil.camelToUpperCaseSnake(fieldId) + ", " + fieldId + ");\n");
			sb.append("\t\t}\n\n");
		}
		return sb.toString();
	}

	/**
	 * フィールドIdの定数を展開します。
	 * @param list フィールドリスト。
	 * @return フィールドIDの定数値。
	 */
	public static String generateFieldGetter(final List<Map<String, Object>> list) {
		StringBuilder sb = new StringBuilder();
		for (Map<String, Object> m: list) {
			String fieldId = FieldListUtil.getFieldId(m);
			String uFieldId = StringUtil.firstLetterToUpperCase(fieldId);
			String fieldClassSimpleName = (String) m.get("fieldClassName");
			String comment = (String) m.get("comment");
			sb.append("\t/**\n");
			sb.append("\t * " + comment + "フィールドを取得します。\n");
			sb.append("\t * @return " + comment + "フィールド。\n");
			sb.append("\t */\n");
			sb.append("\tpublic " + fieldClassSimpleName + " get" + uFieldId + "Field() {\n");
			sb.append("\t\treturn (" + fieldClassSimpleName + ") this.getField(Entity.ID_" + StringUtil.camelToUpperCaseSnake(fieldId) + ");\n");
			sb.append("\t}\n\n");
		}
		return sb.toString();
	}


}
