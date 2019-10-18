/**
 * @fileOverview {@link FuncEditForm}クラスを記述したファイルです。
 */

/**
 * @class FuncEditForm
 *
 * @extends EditForm
 */
class FuncEditForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.find("#exportInitDataButton").click(function() {
			thisForm.exportInitData()
		});
	}

	/**
	 * データのエクスポートを行います。
	 */
	exportInitData() {
		var thisForm = this;
		currentPage.confirm(null, MessagesUtil.getMessage("message.dexportAsInitialDataConfirm"), function() {
			thisForm.submit("export", function(data) {
				currentPage.alert(null, data.result);
			});
		});
	}


	/**
	 * フォームに対してデータを設定します。
	 * @param data データ。
	 */
/*	FuncEditForm.prototype.setFormData = function(data) {
		EditForm.prototype.setFormData.call(this, data);
	};*/

	/**
	 * 編集モードにします。
	 */
	toEditMode() {
		super.toEditMode();
		var thisForm = this;
		var table = this.getComponent("funcTable");
		this.find("[id$='\\.funcPath']").each(function() {
			var f = thisForm.getComponent($(this).attr("id"));
			logger.dir(f);
			logger.log("id=" + f.id + ":" + $(this).val());
			if ($(this).val().indexOf("/dataforms") == 0) {
				var field = table.getSameRowField($(this), "funcName");
				var namef = thisForm.getComponent(field.attr("id"));
				f.lock(true);
				namef.lock(true);
			}
		});
	}
}


