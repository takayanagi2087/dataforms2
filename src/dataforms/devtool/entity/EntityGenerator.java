package dataforms.devtool.entity;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.WebComponent;
import dataforms.devtool.query.page.SelectFieldHtmlTable;
import dataforms.devtool.util.FieldListUtil;
import dataforms.devtool.util.FieldListUtil.GetClassNameFunctionalInterface;
import dataforms.devtool.util.FieldListUtil.GetFieldIdFunctionalInterface;
import dataforms.util.ImportUtil;
import dataforms.util.StringUtil;

/**
 * エンティティ生成クラス。
 */
public class EntityGenerator extends WebComponent {
	/**
	 * Logger.
	 */
	private Logger logger = LogManager.getLogger(EntityGenerator.class);

	/**
	 * フィールドリスト。
	 */
	private List<Map<String, Object>> fieldList = null;

	/**
	 * コンストラクタ。
	 * @param fieldList フィールドリスト。
	 */
	public EntityGenerator(final List<Map<String, Object>> fieldList) {
		this.fieldList = fieldList;
	}

	/**
	 * フィールドIDを取得します。
	 * @param m フィールドリスト。
	 * @return フィールドID。
	 */
	private String getFieldId(final Map<String, Object> m) {
		String ret = (String) m.get(SelectFieldHtmlTable.ID_FIELD_ID);
		String alias = (String) m.get(SelectFieldHtmlTable.ID_ALIAS);
		if (!StringUtil.isBlank(alias)) {
			ret = alias;
		}
		return ret;
	}


	/**
	 * Entityを生成します。
	 * @param implist インポートリスト。
	 * @return 生成したソース文字列。
	 * @throws Exception 例外。
	 */
	public String generate(final ImportUtil implist) throws Exception {
		String javasrc = this.getStringResourse("template/Entity.java.template");
		javasrc = javasrc.replaceAll("\\$\\{idConstants\\}", FieldListUtil.generateFieldIdConstant(this.fieldList, (Map<String, Object> m) -> {
			return this.getFieldId(m);
		}));
		javasrc = javasrc.replaceAll("\\$\\{valueGetterSetter\\}", FieldListUtil.generateFieldValueGetterSetter(fieldList,
			(Map<String, Object> m) -> {
				return this.getFieldId(m);
			}
			, (Map<String, Object> m) -> {
				String fcls =  (String) m.get(SelectFieldHtmlTable.ID_FIELD_CLASS_NAME);
				String sel = (String) m.get(SelectFieldHtmlTable.ID_SEL);
				if ("dataforms.field.sqlfunc.CountField".equals(sel)) {
					fcls = "dataforms.field.sqltype.BigintField";
				}
				logger.debug("fcls=" + fcls);
				return fcls;
			}
			, implist
		));
		javasrc = javasrc.replaceAll("\\$\\{fieldGetter\\}", FieldListUtil.generateFieldGetter(fieldList,
			(Map<String, Object> m) -> {
				return this.getFieldId(m);
			},
			implist
		));
		return javasrc;
	}

	/**
	 * Entityを生成します。
	 * @param implist インポートリスト。
	 * @param cfunc クラス名取得関数インターフェース。
	 * @param func フィールドID取得関数インターフェース。
	 * @return 生成したソース文字列。
	 * @throws Exception 例外。
	 */
	public String generate(final GetFieldIdFunctionalInterface func, final GetClassNameFunctionalInterface cfunc, final ImportUtil implist) throws Exception {
		String javasrc = this.getStringResourse("template/Entity.java.template");
		javasrc = javasrc.replaceAll("\\$\\{idConstants\\}", FieldListUtil.generateFieldIdConstant(this.fieldList
			, func
		));
		javasrc = javasrc.replaceAll("\\$\\{valueGetterSetter\\}", FieldListUtil.generateFieldValueGetterSetter(fieldList,
			(Map<String, Object> m) -> {
				return this.getFieldId(m);
			}
			, cfunc
			, implist
		));
		javasrc = javasrc.replaceAll("\\$\\{fieldGetter\\}", FieldListUtil.generateFieldGetter(fieldList
			, func
			, implist
		));
		return javasrc;
	}

}
