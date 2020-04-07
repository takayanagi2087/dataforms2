package sample.field;

import java.util.List;
import java.util.Map;

import dataforms.dao.SingleTableQuery;
import dataforms.field.sqltype.CharField;
import dataforms.validator.MaxLengthValidator;
import sample.dao.MaterialMasterTable;
import sample.dao.MaterialOrderItemTable;
import sample.dao.SupplierMasterTable;


/**
 * SupplierCodeFieldフィールドクラス。
 *
 */
public class SupplierCodeField extends CharField {
	/**
	 * フィールド長。
	 */
	private static final int LENGTH = 6;

	/**
	 * フィールドコメント。
	 */
	private static final String COMMENT = "仕入先コード";
	/**
	 * コンストラクタ。
	 */
	public SupplierCodeField() {
		this(null);
	}
	/**
	 * コンストラクタ。
	 * @param id フィールドID。
	 */
	public SupplierCodeField(final String id) {
		super(id, LENGTH);
		this.setComment(COMMENT);
	}

	@Override
	protected void onBind() {
		super.onBind();
		this.addValidator(new MaxLengthValidator(this.getLength()));

	}

	/**
	 * 関連フィールドリスト。
	 */
	private static final String[] IDLIST = {
		SupplierMasterTable.Entity.ID_SUPPLIER_CODE
		, SupplierMasterTable.Entity.ID_SUPPLIER_NAME
		, SupplierMasterTable.Entity.ID_SUPPLIER_ID
	};


	@Override
	protected List<Map<String, Object>> queryAutocompleteSourceList(Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new SupplierMasterTable());
		List<Map<String, Object>> list = this.queryAutocompleteSourceList(data, query
			, (Map<String, Object> map, String ... ids) -> {
				return (String) map.get(ids[0]) + ":" + (String) map.get(ids[1]);
			}
			, IDLIST
		);
		return list;
	}

	@Override
	protected Map<String, Object> queryRelationData(Map<String, Object> data) throws Exception {
		SingleTableQuery query = new SingleTableQuery(new SupplierMasterTable());
		Map<String, Object> ret = this.queryRelationData(data, query, null, (Map<String, Object> m) -> {
				m.put(MaterialOrderItemTable.Entity.ID_ORDER_PRICE, m.get(MaterialMasterTable.Entity.ID_UNIT_PRICE));
			}
			, IDLIST
		);
		return ret;
	}

}
