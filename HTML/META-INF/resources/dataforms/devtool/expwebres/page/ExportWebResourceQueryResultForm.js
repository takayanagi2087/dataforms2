/**
 * @fileOverview {@link ExportWebResourceQueryResultForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class ExportWebResourceQueryResultForm
 *
 * @extends QueryResultForm
 */
class ExportWebResourceQueryResultForm extends QueryResultForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		var thisForm = this;
		super.attach();
		this.get("selAll").click(function() {
			logger.log("selAll=" + $(this).prop("checked"));
			thisForm.selAll($(this));
		});
		this.get("exportButton").click(function() {
			thisForm.exportWebRes();
		});
		this.get("selectNotExportedButton").click(function() {
			thisForm.selectedNotExportedFile();
		});
	}

	/**
	 * 各フィールドのバリデーションを行います。
	 * @returns バリデーション結果。
	 */
	validateFields() {
		var ret = super.validateFields();
		if (ret.length == 0) {
			if (this.get("forceOverwrite").prop("checked") == false) {
				var result = this.formData.queryResult;
				for (var i = 0; i < result.length; i++) {
					var selid = "queryResult[" + i + "].sel";
					if (this.get(selid).prop("checked")) {
						var efid = "queryResult[" + i + "].existFlag";
						if (this.getFieldValue(efid) == "1") {
							ret.push(new ValidationError("queryResult[" + i + "].fileName",
								MessagesUtil.getMessage("error.alreadyexported", result[i].fileName)));
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * エクスポートされていないファイルを選択します。
	 */
	selectedNotExportedFile() {
		var result = this.formData.queryResult;
		for (var i = 0; i < result.length; i++) {
			var selid = "queryResult[" + i + "].sel";
			var efid = "queryResult[" + i + "].existFlag";
			if (this.getFieldValue(efid) != "1") {
				this.get(selid).prop("checked", true);
			}
		}
	}

	/**
	 * Webリソースをエクスポートします。
	 */
	exportWebRes() {
		this.parent.resetErrorStatus();
		if (this.validate()) {
			var p = this.get().serialize();
			logger.log("p=" + p);
			this.submit("exportWebResource", function(r) {
				if (r.status == ServerMethod.SUCCESS) {
					var systemName = MessagesUtil.getMessage("message.systemname");
					currentPage.alert(systemName, r.result);
				}
			});
		}
	}

	/**
	 * 全選択チェックボックスのイベント処理を行います。
	 * @param ck {jQuery} チェックボックスのjQueryオブジェクト。
	 *
	 */
	selAll(ck) {
		if (ck.prop("checked")) {
			this.find("[id$='.sel']").prop("checked", true);
		} else {
			this.find("[id$='.sel']").prop("checked", false);
		}
	}
}


