package dataforms.devtool.db.page;

import dataforms.controller.Form;
import dataforms.devtool.field.PathNameField;
import dataforms.servlet.DataFormsServlet;

/**
 * インポートフォームクラス。
 * <pre>
 * インポートデータが存在するフォルダを指定するフォームです。
 * </pre>
 */
public class ImportDataForm extends Form {
	/**
	 * コンストラクタ。
	 */
	public ImportDataForm() {
		super("importDataForm");
		this.addField(new PathNameField());
	}


	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("pathName", DataFormsServlet.getExportImportDir());
	}
}
