package dataforms.debug.alltype.page;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.controller.EditForm;
import dataforms.dao.Dao.BlobReadMode;
import dataforms.dao.file.FileObject;
import dataforms.debug.alltype.dao.FileFieldTestDao;
import dataforms.debug.alltype.dao.FileFieldTestTable;
import dataforms.debug.alltype.report.FielFieldTestReport;
import dataforms.field.base.FieldList;
import dataforms.field.common.ImageField;
import dataforms.field.common.WebResourceImageField;
import dataforms.response.BinaryResponse;
import dataforms.response.JsonResponse;
import dataforms.response.Response;
import dataforms.util.StringUtil;
import dataforms.validator.ValidationError;
import net.arnx.jsonic.JSON;

/**
 * 編集フォームクラス。
 */
public class FileTestEditForm extends EditForm {
	/**
	 * Logger.
	 */
	private static Logger logger = LogManager.getLogger(FileTestEditForm.class);


	/**
	 * コンストラクタ。
	 */
	public FileTestEditForm() {
		FileFieldTestTable tbl = new FileFieldTestTable();
		tbl.getFolderImageField().setThumbnailWidth(480);
		tbl.getFolderImageField().setThumbnailHeight(270);
		tbl.getStaticFolderImageFileField().setThumbnailWidth(480);
		tbl.getStaticFolderImageFileField().setThumbnailHeight(270);
		tbl.getStaticFolderImageFileField().setReducedThumbnail(false);
		tbl.getBlobFileField().setEnableFileReceiver(true);
		tbl.getBlobImageField().setEnableFileReceiver(true);
		tbl.getBlobVideoField().setEnableFileReceiver(true);
		tbl.getBlobAudioField().setEnableFileReceiver(true);
		this.addTableFields(tbl);
		this.addField(new WebResourceImageField("menuImage", "/frame/default/image/menu.png"));

		FieldList flist = this.getFieldList();
		ImageField ifld = (ImageField) flist.get(FileFieldTestTable.Entity.ID_BLOB_IMAGE);;
		ifld.setThumbnailWidth(480);
		ifld.setThumbnailHeight(270);
		ifld.setReducedThumbnail(false);
	}

	@Override
	public void init() throws Exception {
		super.init();
	}

	@Override
	protected Map<String, Object> queryData(final Map<String, Object> data) throws Exception {
		FileFieldTestDao dao = new FileFieldTestDao(this);
		Map<String, Object> ret = dao.queryEditData(data);
		ret.put("fileImage", "img001.jpg");
//		log.debug("formData=" + JSON.encode(ret, true));
		return ret;
	}

	@Override
	protected boolean isUpdate(final Map<String, Object> data) throws Exception {
		Long id = (Long) data.get("recordId");
		return (!StringUtil.isBlank(id));
	}

	@Override
	protected void insertData(final Map<String, Object> data) throws Exception {
		this.setUserInfo(data);
		logger.debug(() -> "insert data=" + JSON.encode(data, true));
		FileFieldTestDao dao = new FileFieldTestDao(this);
		dao.executeInsert(new FileFieldTestTable(), data);
	}

	@Override
	protected void updateData(final Map<String, Object> data) throws Exception {
		this.setUserInfo(data);
		logger.debug(() -> "updatet data=" + JSON.encode(data, true));
		FileFieldTestDao dao = new FileFieldTestDao(this);
		dao.executeUpdate(new FileFieldTestTable(), data);
	}

	@Override
	public void deleteData(final Map<String, Object> data) throws Exception {
		this.setUserInfo(data);
		FileFieldTestDao dao = new FileFieldTestDao(this);
		dao.executeDelete(new FileFieldTestTable(), data);
	}

	private byte[] printReport(final Map<String, Object> data) throws Exception {
		FileFieldTestDao dao = new FileFieldTestDao(this);
		dao.setBlobReadMode(BlobReadMode.FOR_DOWNLOAD); // BLOBの内容までFileObjectに展開する。
		Map<String, Object> printData = dao.queryEditData(data);
		FielFieldTestReport rep = new FielFieldTestReport();
		return rep.print(printData);
	}


	/**
	 * 印刷メソッド。
	 * @param p パラメータ。
	 * @return 応答情報。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public Response print(final Map<String, Object> p) throws Exception {
		Response ret = null;
		// Formから送信されたデータを確認します。
		List<ValidationError> list = this.validate(p);
		if (list.size() == 0) {
			// Formから送信されたデータをサーバーサイドで処理しやすいデータ型に変換します。
			Map<String, Object> data = this.convertToServerData(p);
			byte[] excel = this.printReport(data);
			FileObject fobj = new FileObject("download.xlsx", excel);
			ret = new BinaryResponse(fobj);
		} else {
			// 確認で問題があった場合その情報を返信します。
			ret = new JsonResponse(JsonResponse.INVALID, list);
		}
		return ret;
	}


}
