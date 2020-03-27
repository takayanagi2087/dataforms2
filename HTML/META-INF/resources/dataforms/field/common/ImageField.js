/**
 * @fileOverview {@link ImageField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class ImageField
 * 画像ファイルアップロードフィールドクラス。
 * @extends FileField
 */
class ImageField extends FileField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 削除チェックボックス、ダウンロードリンクなどの設定を行います。
	 * </pre>
	 */
	attach() {
		super.attach();
		var thisField = this;
		var linkid = this.id + "_link";
		var link = this.parent.get(linkid);
		var thumbid = this.id + "_thm"; // サムネイルID.
		var thumb = this.parent.get(thumbid);
		thumb.attr("width", this.thumbnailWidth);
		thumb.attr("height", this.thumbnailHeight);

		thumb.click(function(e) {
			logger.log("file=" + thisField.get().val().length);
			var fval = thisField.get().val();
			var val = {};
			if (fval.length == 0) {
				val.fileName = link.attr("data-value");
				val.size = link.attr("data-size");
				val.downloadParameter = link.attr("data-dlparam");
			} else {
				var fl = thisField.get().get()[0].files[0];
				var url = URL.createObjectURL(fl);
				val.fileName = fl.name;
				val.size = fl.size;
				val.url = url;
				logger.log("url=" + url);
			}
			if (val.fileName.length > 0) {
				thisField.showImage(val);
			}
		});
		this.get().change(function() {
			thisField.previewImage(this, thumb)
			thisField.showDelCheckbox();
		});
	}

	/**
	 * 削除チェックボックスの処理を行います。
	 */
	delFile(ck) {
		super.delFile(ck);
		// サムネイルの表示制御を追加。
		var thumbid = this.id + "_thm"; // サムネイルID.
		var thumb = this.parent.get(thumbid);
		if (ck.prop("checked")) {
			thumb.attr("src", null);
		} else {
			if (this.downloadUrl != null) {
				thumb.attr("src", this.downloadUrl);
			}
		}
	}

	/**
	 * 画像ファイル指定時のprevie表示。
	 */
	previewImage(inputFile, thumb) {
		var fileList = inputFile.files;
		if (fileList.length > 0) {
			var fileReader = new FileReader() ;
			// 読み込み後の処理を決めておく
			fileReader.onload = function() {
				// データURIを取得
				var dataUri = this.result ;
				// HTMLに書き出し (src属性にデータURIを指定)
				//document.getElementById( "output" ).innerHTML += '<img src="' + dataUri + '">' ;
				thumb.attr("src", dataUri);
			}
			fileReader.readAsDataURL(fileList[0]);
		}
	}

	/**
	 * 指定されたURLの画像を表示します。
	 * @param img イメージ。
	 */
	showImage(img) {
		logger.log("img=" + JSON.stringify(img));
		if (this.parent.id == "imageForm") {
			return;
		}
		var dlg = currentPage.getComponent("imageDialog");
		if (dlg == null) {
			if (img != null) {
				if (img.url != null) {
					window.open(img.url, "_image");
				} else {
					var url = location.pathname + "?dfMethod=" + encodeURIComponent(this.getUniqId()) + ".download"  + "&" + img.downloadParameter;
					if (currentPage.csrfToken != null) {
						url += "&csrfToken=" + currentPage.csrfToken;
					}
					window.open(url, "_image");
				}
			}
		} else {
			var imgfrm = dlg.getComponent("imageForm");
			var imgfld = imgfrm.getComponent("image");
			logger.dir(imgfld);
			imgfld.setValue(img);
			dlg.showModal();
		}
	}
	/**
	 * ファイルフィールドに付随する各種コンポーネントを配置します。
	 * @param comp ファイルフィールド。
	 */
	addElements(comp) {
		var htmlstr = this.additionalHtmlText;
		var html = htmlstr.replace(/\$\{fieldId\}/g, this.id);
		var html = html.replace(/\$\{width\}/g, this.thumbnailWidth);
		var html = html.replace(/\$\{height\}/g, this.thumbnailHeight);
		logger.log("htmlstr=" + html);
		var tag = comp.prop("tagName");
		var type = comp.prop("type");
		if ("INPUT" == tag && type == "file") {
			comp.after(html);
		} else if (tag == "DIV") {
			comp.html(html);
			this.hideDelCheckbox();
		}
	}

	/**
	 * 値を設定します。
	 *
	 * @param {String} value 値。
	 */
	setValue(value) {
		super.setValue(value);
		var thumbid = this.id + "_thm"; // サムネイルID.
		var thumb = this.parent.get(thumbid);
		this.downloadUrl = null;
		if (value != null) {
			var linkid = this.id + "_link";
			var fnlink = this.parent.get(linkid);
			if (value.url == null) {
				var url = location.pathname + "?dfMethod=" + encodeURIComponent(this.getUniqId()) + ".downloadThumbnail"  + "&" + value.downloadParameter;
				if (currentPage.csrfToken != null) {
					url += "&csrfToken=" + currentPage.csrfToken;
				}
				thumb.attr("src", url);
				this.downloadUrl = url;
			} else {
				thumb.attr("src", value.url);
				fnlink.attr("href", "javascript:void(0);");
				this.downloadUrl = value.url;
			}
		} else {
			thumb.removeAttr("src");
			thumb.attr("alt", "");
			this.downloadUrl = null;
		}
	}
}




