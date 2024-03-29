package dataforms.dialog.image;

import dataforms.controller.Form;
import dataforms.field.common.ImageField;
/**
 * 画像表示フォーム。
 * @author takayanagi
 *
 */
public class ImageForm extends Form {
	/**
	 * コンストラクタ。
	 */
	public ImageForm() {
		super(null);
		ImageField f = new ImageField("image");
		f.setThumbnailWidth(960);
		f.setThumbnailHeight(540);
		f.setReducedThumbnail(false);
		this.addField(f);
	}
}
