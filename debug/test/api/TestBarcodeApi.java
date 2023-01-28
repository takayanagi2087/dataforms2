package test.api;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.google.zxing.BarcodeFormat;

import dataforms.barcode.BarcodeApi;

/**
 * Barcode APIのテスト。
 *
 */
public class TestBarcodeApi extends BarcodeApi {

	@Override
	protected byte[] getBarcodeImage(Map<String, Object> p) throws Exception {
		String msg = (String) p.get("msg");
		return this.createBarcodeImage(msg, BarcodeFormat.QR_CODE, StandardCharsets.UTF_8, 160, 160);
	}

}
