/**
 * @fileOverview {@link TableGeneratorEditForm}クラスを記述したファイルです。
 */

/**
 * @class TableGeneratorEditForm
 *
 * @extends EditForm
 */
class TableGeneratorEditForm extends EditForm {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		var tbl = this.getComponent("fieldList");
		this.find("#allErrorButton").click(function() {
			thisForm.find("[id$='\\.overwriteMode']").each(function() {
				$(this).val("error");
			});
		});
		this.find("#allSkipButton").click(function() {
			thisForm.find("[id$='\\.overwriteMode']").each(function() {
				$(this).val("skip");
			});
		});
		this.find("#allForceOverwriteButton").click(function() {
			thisForm.find("[id$='\\.overwriteMode']").each(function() {
				$(this).val("force");
			});
		});
		this.find("#errorSkipButton").click(function() {
			thisForm.find("[id$='\\.fieldClassName'].errorField").each(function() {
				tbl.getSameRowField($(this), "overwriteMode").val("skip");
			});
		});
		this.find("#errorForceButton").click(function() {
			thisForm.find("[id$='\\.fieldClassName'].errorField").each(function() {
				tbl.getSameRowField($(this), "overwriteMode").val("force");
			});
		});
		this.find("#printButton").click(function() {
			thisForm.print();
		});

		this.find("#showImportButton").click(function() {
			thisForm.showImportField();
		});

		this.find("#importButton").click(function() {
			thisForm.importTable();
		});

		this.find("#functionSelect").change(function() {
			thisForm.onChangeFunction();
		});
	}

	/**
	 * 機能変更時のパッケージ設定。
	 */
	onChangeFunction() {
		var func = this.find("#functionSelect").val();
		if (func != null && func.length > 0) {
			var fieldList = this.getComponent("fieldList");
			for (var i = 0; i < fieldList.getRowCount(); i++) {
				var pkgf = fieldList.getRowField(i, "packageName");
				var oldv = pkgf.getValue();
				if (oldv.length == 0) {
					pkgf.setValue(func.substr(1) + ".field");
				}
			}
		}
	}

	/**
	 * インポート関連フィールドの表示。
	 */
	showImportField() {
		this.find(".importFields").toggle();
	}

	/**
	 * インポート情報をフォームに設定します。
	 * @param {Object} data インポートデータ。
	 */
	setTableInfo(data) {
		this.find("#tableClassName").val(data.tableClassName);
		this.find("#tableComment").val(data.tableComment);
		if (data.updateInfoFlag == "0") {
			this.find("#updateInfoFlag").prop("checked", false);
		} else {
			this.find("#updateInfoFlag").prop("checked", true);
		}
		var fieldList = this.getComponent("fieldList");
		fieldList.setTableData(data.fieldList);
	}

	/**
	 * インポート関連フィールドの表示。
	 */
	importTable() {
		var thisForm = this;
		var m = this.getServerMethod("importTable");
		var importTable = this.find("#importTable").val();
		var func = this.find("#functionSelect").val();
		m.execute("importTable=" + importTable + "&functionSelect=" + func, function(r) {
			if (r.status == ServerMethod.SUCCESS) {
				thisForm.setTableInfo(r.result);
				thisForm.find(".importFields").toggle();
			}
		});
	}


	/**
	 * 編集モードにします。
	 * <pre>
	 * 各フィールドを編集可能状態にします。
	 * </pre>
	 */
	toEditMode() {
		super.toEditMode();
		// 更新時にはクラス名を編集できなくする。
		var funcsel = this.getComponent("functionSelect");
		var pkgname = this.getComponent("packageName");
		var cnfield = this.getComponent("tableClassName");
		if (this.saveMode == "new") {
			funcsel.lock(false);
			pkgname.lock(false);
			cnfield.lock(false);
		} else {
			funcsel.lock(true);
			pkgname.lock(true);
			cnfield.lock(true);
		}
		var tbl = this.getComponent("fieldList");
		var n = tbl.find("tbody>tr").length;
		for (var i = 0; i < n; i++) {
			var spkg = tbl.getComponent("fieldList[" + i + "].superPackageName");
			var scls = tbl.getComponent("fieldList[" + i + "].superSimpleClassName");
			var ovm = tbl.getComponent("fieldList[" + i + "].overwriteMode");
			logger.log("spkg.id=" + spkg.id);
			logger.log("scls.id=" + scls.id);
			var flg = this.find("#" + this.selectorEscape("fieldList[" + i + "].isDataformsField"));
			logger.log("flg=" + flg.val());
			if (flg.val() == "0") {
				logger.log("unlock");
				spkg.lock(false);
				scls.lock(false);
				ovm.get().show();
			} else {
				logger.log("lock");
				spkg.lock(true);
				scls.lock(true);
				ovm.get().hide();
				var len = tbl.getComponent("fieldList[" + i + "].fieldLength");
				if (len.get().val().length > 0) {
					len.lock(false);
				} else {
					len.lock(true);
				}
			}
		}
		this.find("#allErrorButton").prop("disabled", false);
		this.find("#allSkipButton").prop("disabled", false);
		this.find("#allForceOverwriteButton").prop("disabled", false);
		this.find("#errorSkipButton").prop("disabled", false);
		this.find("#errorForceButton").prop("disabled", false);
	}

	/**
	 * 確認モードにします。
	 * <pre>
	 * 各フィールドを編集可能状態にします。
	 * </pre>
	 */
	toConfirmMode() {
		super.toConfirmMode();
		this.find("#allErrorButton").prop("disabled", true);
		this.find("#allSkipButton").prop("disabled", true);
		this.find("#allForceOverwriteButton").prop("disabled", true);
		this.find("#errorSkipButton").prop("disabled", true);
		this.find("#errorForceButton").prop("disabled", true);
	}


	/**
	 * 計算イベント処理を行います。
	 * <pre>
	 * 計算イベントフィールドが更新された場合、このメソッドが呼び出されます。
	 * データ入力時の自動計算が必要な場合このメソッドをオーバーライドしてください。
	 * </pre>
	 * @param {jQuery} element イベントが発生した要素。
	 */
	onCalc(element) {
		if (element != null) {
			logger.log("element.id=" + element.attr(this.getIdAttribute()));
			var id = element.attr(this.getIdAttribute());
			if (id.indexOf("packageName") >= 0 || id.indexOf("fieldClassName") >= 0) {
				this.onCalcClass(element);
			} else if (id.indexOf("superPackageName") > 0 || id.indexOf("superSimpleClassName") >= 0) {
				this.onCalcSuperClass(element);
			}
		}
	}

	/**
	 * フィールドクラスの計算イベント処理を行います。
	 * @param {jQuery} element イベントが発生した要素。
	 */
	onCalcClass(element) {
		var thisForm = this;
		var tbl = this.getComponent("fieldList");
		var p = tbl.getSameRowField(element, "packageName").val();
		var c = tbl.getSameRowField(element, "fieldClassName").val();
		if (p.length > 0 && c.length > 0) {
			var classname = p + "." + c;
			logger.log("classname=" + classname);
			var method = this.getServerMethod("getFieldClassInfo");
			method.execute("classname=" + classname, function(ret) {
				if (ret.status == ServerMethod.SUCCESS) {
					var dfflg = tbl.getSameRowField(element, "isDataformsField");
					var len = tbl.getSameRowField(element, "fieldLength");
					var cmnt = tbl.getSameRowField(element, "comment");
					var bpkg = tbl.getSameRowField(element, "superPackageName");
					var bcls = tbl.getSameRowField(element, "superSimpleClassName");
					var owm = tbl.getSameRowField(element, "overwriteMode");
					dfflg.val(ret.result.isDataformsField);
					if (ret.result.isDataformsField == "1") {
						if (ret.result.fieldLength != null && ret.result.fieldLength.length > 0) {
							len.val(ret.result.fieldLength);
							tbl.getComponent(len.attr(this.getIdAttribute())).lock(false);
						} else {
							len.val("");
							tbl.getComponent(len.attr(this.getIdAttribute())).lock(true);
						}
						bpkg.val(ret.result.superClassPackage);
						bcls.val(ret.result.superClassSimpleName);
						cmnt.val(ret.result.fieldComment);
						tbl.getComponent(bpkg.attr(this.getIdAttribute())).lock(true);
						tbl.getComponent(bcls.attr(this.getIdAttribute())).lock(true);
						owm.hide();
					} else {
						if (ret.result.fieldLength != null && ret.result.fieldLength.length > 0) {
							if (len.val().length == 0) {
								len.val(ret.result.fieldLength);
							}
						}
						if (ret.result.superClassPackage != null) {
							bpkg.val(ret.result.superClassPackage);
						}
						if (ret.result.superClassSimpleName != null) {
							bcls.val(ret.result.superClassSimpleName);
						}
						if (ret.result.fieldComment != null && ret.result.fieldComment.length > 0) {
							if (cmnt.val().length == 0) {
								cmnt.val(ret.result.fieldComment);
							}
						}
						tbl.getComponent(bpkg.attr(thisForm.getIdAttribute())).lock(false);
						tbl.getComponent(bcls.attr(thisForm.getIdAttribute())).lock(false);
						owm.show();
					}
				}
			});
		}
	}

	/**
	 * 親クラスの計算イベント処理を行います。
	 * @param {jQuery} element イベントが発生した要素。
	 */
	onCalcSuperClass(element) {
		var tbl = this.getComponent("fieldList");
		var p = tbl.getSameRowField(element, "superPackageName").val();
		var c = tbl.getSameRowField(element, "superSimpleClassName").val();
		if (p.length > 0 && c.length > 0) {
			var classname = p + "." + c;
			logger.log("super classname=" + classname);
			var method = this.getServerMethod("getSuperFieldClassInfo");
			method.execute("superclassname=" + classname, function(ret) {
				if (ret.status == ServerMethod.SUCCESS) {
					var len = tbl.getSameRowField(element, "fieldLength");
					if (len.val().length == 0) {
						len.val(ret.result.fieldLength);
					}
				}
			});
		}
	}

	/**
	 * テーブル定義書を作成します。
	 */
	print() {
		var thisForm = this;
		thisForm.parent.resetErrorStatus();
		thisForm.submitForDownload("print");
	}

}





