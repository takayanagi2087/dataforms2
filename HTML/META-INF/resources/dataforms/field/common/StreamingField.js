/**
 * @fileOverview {@link StreamingField}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class StreamingField
 * ストリーミングファイルアップロードフィールドクラス。
 * @extends FileField
 */
class StreamingField extends FileField {
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
		var player = this.getPlayer();
		player.on("abort", function() {
			logger.log("abort");
			setTimeout(function() {
				thisField.deleteTempFile();
			}, 3000);
		});
		player.on("ended", function() {
			logger.log("ended");
			setTimeout(function() {
				thisField.deleteTempFile();
			}, 3000);
		});
	}

	/**
	 * ファイルの選択処理。
	 * @param {jQuery} fld ファイルフィールド。
	 */
	selectFile(fld) {
		super.selectFile(fld);
		var player = this.getPlayer();
		var f = fld.get()[0];
		for(var j=0; j < f.files.length; j++){
			var url = URL.createObjectURL(f.files[j])
			logger.log("url=" + url);
			player.attr("src", url);
		}
	}


	/**
	 * 削除チェックボックスの処理を行います。
	 */
	delFile(ck) {
		super.delFile(ck);
		var player = this.getPlayer();
		if (ck.prop("checked")) {
			player.attr("src", null);
		} else {
			if (this.downloadUrl != null) {
				player.attr("src", this.downloadUrl);
			}
		}
	}
	/**
	 * ファイルフィールドに付随する各種コンポーネントを配置します。
	 * @param comp ファイルフィールド。
	 */
	addElements(comp) {
		var htmlstr = this.additionalHtmlText;
		var html = htmlstr.replace(/\$\{fieldId\}/g, this.id);
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
	 * ストリーミングデータのプレーヤーを取得します。
	 * @returns {jQuery} ストリーミングデータのプレーヤー。
	 */
	getPlayer() {
		var playerid = this.id + "_player"; // プレーヤー.
		var player = this.parent.get(playerid);
		return player;
	}

	/**
	 * サーバ中のストリーミングデータの一時ファイルを削除します。
	 */
	deleteTempFile() {
		var playerid = this.id + "_player"; // プレーヤーID.
		var player = this.parent.get(playerid);
		var key = player.attr("data-key");
		logger.log("key=" + key);
		var m = this.getServerMethod("deleteTempFile");
		m.execute(key, function(ret) {});
	}

	/**
	 * 値を設定します。
	 *
	 * @param {Object} value 値。
	 */
	setValue(value) {
		super.setValue(value);
		var videoid = this.id + "_player"; // プレーヤーID.
		var video = this.parent.get(videoid);
		this.downloadUrl = null;
		if (value != null) {
			this.downloadParameter = value.downloadParameter;
			var url = location.pathname + "?dfMethod=" + encodeURIComponent(this.getUniqId()) + ".download"  + "&" + value.downloadParameter;
			if (currentPage.csrfToken != null) {
				url += "&csrfToken=" + currentPage.csrfToken;
			}
			video.attr("src", url);
			video.attr("data-key", value.downloadParameter);
			this.downloadUrl = url;
		} else {
			video.attr("src", "");
			video.removeAttr("data-key");
			this.downloadUrl = null;
		}
	}

}






