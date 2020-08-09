package dataforms.debug.alltype.report;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.controller.WebComponent;
import dataforms.debug.alltype.dao.FileFieldTestTable;
import dataforms.report.ExcelReport;

/**
 * ファイルフィールドテストレポート。
 *
 */
public class FielFieldTestReport extends ExcelReport {

	/**
	 * Logger。
	 */
	private Logger logger = LogManager.getLogger(FielFieldTestReport.class);

	/**
	 * コンストラクタ。
	 */
	public FielFieldTestReport() {
		String template = WebComponent.getServlet().getServletContext().getRealPath("/exceltemplate/FileFieldTest.xlsx");
		this.setTemplatePath(template);
		FileFieldTestTable table = new FileFieldTestTable();
		this.addTableFields(table);

	}

	@Override
	public byte[] print(final Map<String, Object> data) throws Exception {
		FileFieldTestTable.Entity e = new FileFieldTestTable.Entity(data);
		logger.debug("class=" + e.getBlobImage().getClass().getName());
		return super.print(data);
	}
}
