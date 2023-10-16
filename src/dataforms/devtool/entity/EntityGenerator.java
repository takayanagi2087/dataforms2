package dataforms.devtool.entity;

import java.util.List;
import java.util.Map;

import dataforms.controller.WebComponent;
import dataforms.devtool.util.FieldListUtil;
import dataforms.devtool.util.FieldListUtil.GetClassNameFunctionalInterface;
import dataforms.devtool.util.FieldListUtil.GetFieldIdFunctionalInterface;
import dataforms.util.ImportUtil;

/**
 * エンティティ生成クラス。
 */
public class EntityGenerator extends WebComponent {
	/**
	 * Logger.
	 */
//	private Logger logger = LogManager.getLogger(EntityGenerator.class);

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
	 * Entityを生成します。
	 * @param implist インポートリスト。
	 * @param implistfg インポートリスト(Query生成時に指定する)。
	 * @param func a
	 * @param cfunc a
	 * @return 生成したソース文字列。
	 * @throws Exception 例外。
	 */
	public String generate(final ImportUtil implist, final ImportUtil implistfg, final GetFieldIdFunctionalInterface func, final GetClassNameFunctionalInterface cfunc) throws Exception {
		String javasrc = this.getStringResourse("template/Entity.java.template");
		javasrc = javasrc.replaceAll("\\$\\{idConstants\\}", FieldListUtil.generateFieldIdConstant(this.fieldList, func));
		javasrc = javasrc.replaceAll("\\$\\{valueGetterSetter\\}", FieldListUtil.generateFieldValueGetterSetter(fieldList, func, cfunc, implist));
		javasrc = javasrc.replaceAll("\\$\\{fieldGetter\\}", FieldListUtil.generateFieldGetter(fieldList, func, implistfg));
		return javasrc;
	}
}
