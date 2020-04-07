package sample.field;

import java.util.List;
import java.util.Map;

import dataforms.dao.SingleTableQuery;
import dataforms.field.sqltype.VarcharField;
import dataforms.validator.MaxLengthValidator;
import sample.dao.MaterialMasterTable;
import sample.dao.MaterialOrderItemTable;


/**
 * MaterialNameFieldフィールドクラス。
 *
 */
public class MaterialNameField extends VarcharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 128;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "資材名称";
	/**
	 * コンストラクタ。
	 */
	public MaterialNameField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public MaterialNameField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));
	}

	/**
	 * 関連フィールドIDリスト。
	 */
	private static final String[] IDLIST = {
			 MaterialMasterTable.Entity.ID_MATERIAL_NAME
			, MaterialMasterTable.Entity.ID_MATERIAL_CODE
			, MaterialMasterTable.Entity.ID_MATERIAL_ID
			, MaterialMasterTable.Entity.ID_UNIT_PRICE
			, MaterialMasterTable.Entity.ID_MATERIAL_UNIT
	};

	@Override
	protected List<Map<String, Object>> queryAutocompleteSourceList(Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new MaterialMasterTable());
		List<Map<String, Object>> list = this.queryAutocompleteSourceList(data, query
			, (Map<String, Object> map, String ... ids) -> {
				return (String) map.get(ids[1]) + ":" + (String) map.get(ids[0]);
			}
			, IDLIST
		);
		return list;
	}

	@Override
	protected Map<String, Object> queryRelationData(Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new MaterialMasterTable());
		Map<String, Object> ret = this.queryRelationData(data, query, null, (Map<String, Object> m) -> {
				m.put(MaterialOrderItemTable.Entity.ID_ORDER_PRICE, m.get(MaterialMasterTable.Entity.ID_UNIT_PRICE));
			}
			, IDLIST
		);
		return ret;
	}


}
