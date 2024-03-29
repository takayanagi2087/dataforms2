package dataforms.debug.alltype.page;

import java.awt.image.BufferedImage;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;

import dataforms.annotation.WebMethod;
import dataforms.app.enumtype.dao.EnumDao;
import dataforms.controller.EditForm;
import dataforms.dao.file.ImageData;
import dataforms.debug.alltype.dao.AllTypeAttachFileTable;
import dataforms.debug.alltype.dao.AllTypeDao;
import dataforms.debug.alltype.dao.AllTypeTable;
import dataforms.debug.alltype.report.AlltypeExcelReport;
import dataforms.debug.alltype.report.AlltypeXslFoReport;
import dataforms.htmltable.EditableHtmlTable;
import dataforms.report.PrintDevices;
import dataforms.response.BinaryResponse;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.util.StringUtil;
import dataforms.validator.FileSizeValidator;
import dataforms.validator.RequiredValidator;
import net.arnx.jsonic.JSON;

/**
 * 全データタイプの入力テスト用フォームクラス。
 *
 */
public class AllTypeEditForm extends EditForm {
    /**
     * Logger.
     */
	private static Logger logger = LogManager.getLogger(AllTypeEditForm.class.getName());

	/**
	 * コンストラクタ。
	 */
	public AllTypeEditForm() {
		this.addTableFields(new AllTypeTable());
		this.getFieldList().get("smallintField").setCalcEventField(true);
		this.getFieldList().get("dateField").setCalcEventField(true);
//		this.getFieldList().get("radioField").addValidator(new RequiredValidator());
//		this.getFieldList().get("checkboxField").addValidator(new RequiredValidator());

		this.getFieldList().get("uploadBlobData").addValidator(new FileSizeValidator(10 * 1024 * 1024));
		AllTypeAttachFileTable aft = new AllTypeAttachFileTable();
//		aft.getField("fileComment").setSortable(true, Field.SortOrder.DESC);
		EditableHtmlTable tbl = new EditableHtmlTable("attachFileTable", aft.getFieldList());
		tbl.setSortable(true);
//		tbl.setFixedColumns(5);
		tbl.setFixedWidth(50.0);
		tbl.setSortableSwitching(true);
		this.addHtmlTable(tbl);

		this.getFieldList().get("charField").addValidator(new RequiredValidator());


	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("attachFileTable", new ArrayList<HashMap<String, Object>>());

		logger.debug(() -> "userInfo=" + JSON.encode(this.getPage().getUserInfo()));

	}


	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		AllTypeDao dao = new AllTypeDao(this);
		AllTypeTable tbl = new AllTypeTable();
		Map<String, Object> ret = dao.getSelectedData(data, tbl.getPkFieldList());
		return ret;
	}

	@Override
	protected Map<String, Object> queryReferData(final Map<String, Object> data)
			throws Exception {
		AllTypeDao dao = new AllTypeDao(this);
		AllTypeTable tbl = new AllTypeTable();
		Map<String, Object> ret = dao.getReferData(data, tbl.getPkFieldList());
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 主キーとなるrecordIdFieldが入力されていた場合、更新モードと判断します。
	 */
	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		Long userIdField = (Long) data.get("recordIdField");
		return (!StringUtil.isBlank(userIdField));
	}

	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		AllTypeDao dao = new AllTypeDao(this);
		this.setUserInfo(data);
		dao.insertAllType(data);

	}

	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		AllTypeDao dao = new AllTypeDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.updateAllType(data);
	}


	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		AllTypeDao dao = new AllTypeDao(this);
		this.setUserInfo(data); // 更新を行うユーザIDを設定する.
		dao.deleteAllType(data);
	}


	/**
	 * バーコードイメージを取得します。
	 * @param text バーコードの文字列。
	 * @return バーコードイメージ。
	 * @throws Exception 例外。
	 */
	public ImageData getBarcodeImage(final String text) throws Exception {
		// zxing-core.jar zxing-javase.jarを使用したバーコード生成
		String contents = text;
		BarcodeFormat format = BarcodeFormat.CODE_39;
		int width = 200;
		int height = 50;
		Code39Writer writer = new Code39Writer();
		BitMatrix bitMatrix = writer.encode(contents, format, width, height);
		BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} finally {
			os.close();
		}
		ImageData ret = new ImageData();
		ret.setFileName("barcode.png");
		ret.setContents(os.toByteArray());
		logger.debug("Image conetnt-type=" + ret.getContentType());
		return ret;
//		return null;
	}


	/**
	 * 印刷処理を行います。
	 * @param param パラメータ。
	 * @return 応答。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response print(final Map<String, Object> param) throws Exception {
		logger.debug(() -> "queryString=" + JSON.encode(this.getPage().getQueryString(), true));
		Map<String, Object> data = this.convertToServerData(param);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("attachFileTable");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			m.put("no", Integer.valueOf(i + 1));
			m.put("barcode", this.getBarcodeImage(Integer.toString(123450000 + i)));
		}
		logger.debug(() -> "data=" + JSON.encode(data, true));
		String template = AllTypeEditForm.getServlet().getServletContext().getRealPath("/exceltemplate/alltypeExcelTempl.xlsx");
		logger.debug(() -> "template=" + template);
		AlltypeExcelReport rep = new AlltypeExcelReport(template);
		byte[] excel = rep.print(data);
		BinaryResponse ret = new BinaryResponse(excel);
		ret.setFileName("test001.xlsx");
		return ret;
	}

	/**
	 * 印刷処理を行います。
	 * @param param パラメータ。
	 * @return 応答。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response printPdf(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.convertToServerData(param);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("attachFileTable");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			m.put("no", Integer.valueOf(i + 1));
			m.put("barcode", this.getBarcodeImage(Integer.toString(123450000 + i)));
		}

		logger.debug(() -> "list.size()=" + list.size());
		logger.debug(() -> "data=" + JSON.encode(data, true));
		String template = AllTypeEditForm.getServlet().getServletContext().getRealPath("/exceltemplate/alltypeExcelTempl.fo");
		logger.debug(() -> "template=" + template);
		AlltypeXslFoReport rep = new AlltypeXslFoReport(template);
		byte[] pdf = rep.print(data);
		BinaryResponse ret = new BinaryResponse(pdf);
		ret.setFileName("test001.pdf");
		ret.setContentType("application/pdf");
		return ret;
	}

	/**
	 * 印刷処理を行います。
	 * @param param パラメータ。
	 * @return 応答。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response printOut(final Map<String, Object> param) throws Exception {
		Map<String, Object> data = this.convertToServerData(param);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("attachFileTable");
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> m = list.get(i);
			m.put("no", Integer.valueOf(i + 1));
			m.put("barcode", this.getBarcodeImage(Integer.toString(123450000 + i)));
		}

		logger.debug(() -> "list.size()=" + list.size());
		logger.debug(() -> "data=" + JSON.encode(data, true));
		String template = AllTypeEditForm.getServlet().getServletContext().getRealPath("/exceltemplate/alltypeExcelTempl.fo");
		logger.debug(() -> "template=" + template);
		AlltypeXslFoReport rep = new AlltypeXslFoReport(template);


		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintService(PrintDevices.getPrintService("Microsoft Print to PDF"));
//		pj.setPrintService(PrintDevices.getPrintService("PX-M5041F"));
//		pj.setPrintService(PrintDevices.getPrintService("Canon MG6300 series Printer"));
//		pj.setPrintService(PrintDevices.getPrintService("EPSON PX-M5041F"));
		rep.print(data, pj);
		JsonResponse ret = new JsonResponse(JsonResponse.SUCCESS, "");
		return ret;
	}

	/**
	 * 各種メソッドのテストメソッド。
	 * @param p パラメータ。
	 * @return "ok"というメッセージ。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response testJsonPost(final Map<String, Object> p) throws Exception {
		logger.debug(() -> "param=" + JSON.encode(p, true));

		EnumDao dao = new EnumDao(this);
		logger.debug("optionName={}", dao.getOptionName("userLevel", "admin", Locale.JAPAN.getLanguage()));

		return new JsonResponse(JsonResponse.SUCCESS, "ok");
	}
}
