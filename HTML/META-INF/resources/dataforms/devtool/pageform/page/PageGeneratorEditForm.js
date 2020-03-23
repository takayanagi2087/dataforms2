/**
 * @fileOverview {@link PageGeneratorEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class PageGeneratorEditForm
 * テーブル情報フォームクラス。
 * <pre>
 * テーブル情報を表示するためのフォームです。
 * </pre>
 * @extends EditForm
 */
class PageGeneratorEditForm extends EditForm {
	/**
	 * HTMLエレメントフォームとの対応付けを行います。
	 *
	 */
	attach() {
		var thisForm = this;
		super.attach();

		this.find("#pageClassName").change(function() {
			thisForm.updateFormName($(this));
		});
		this.find("#allErrorButton").click(function() {
			thisForm.find("[id$='OverwriteMode']").each(function() {
				$(this).val("error");
			});
		});
		this.find("#allSkipButton").click(function() {
			thisForm.find("[id$='OverwriteMode']").each(function() {
				$(this).val("skip");
			});
		});
		this.find("#allForceOverwriteButton").click(function() {
			thisForm.find("[id$='OverwriteMode']").each(function() {
				$(this).val("force");
			});
		});
		this.find("#errorSkipButton").click(function() {
			logger.log("errorSkipButton");
			thisForm.find(".errorField").each(function() {
				var id = $(this).attr(this.getIdAttribute());
				if (id.indexOf("ClassName") > 0) {
					var owmId = id.replace("ClassName", "ClassOverwriteMode");
					thisForm.find("#" + owmId).val("skip");
				}
			});
		});
		this.find("#errorForceButton").click(function() {
			logger.log("errorSkipButton");
			thisForm.find(".errorField").each(function() {
				var id = $(this).attr(this.getIdAttribute());
				if (id.indexOf("ClassName") > 0) {
					var owmId = id.replace("ClassName", "ClassOverwriteMode");
					thisForm.find("#" + owmId).val("force");
				}
			});
		});
		this.find("#queryFormClassFlag").click(function() {
			thisForm.setFormFlag();
		});
		this.find("#queryResultFormClassFlag").click(function() {
			thisForm.setFormFlag();
		});
		this.find("#editFormClassFlag").click(function() {
			thisForm.setFormFlag();
		});
		this.find("[name='updateTable']").click(function() {
			thisForm.setUpdateTable();
		});
	}

	/**
	 * Form名称を更新します。
	 * @param {jQuery} pc ページクラス名フィールド。
	 */
	updateFormName(pc) {
		var pcname = pc.val();
		var n = pcname.replace(/Page$/, "");
		this.find("#queryFormClassName").val(n + "QueryForm");
		this.find("#queryResultFormClassName").val(n + "QueryResultForm");
		this.find("#editFormClassName").val(n + "EditForm");
	}

	/**
	 * テーブル更新の有無を設定します。
	 */
	setUpdateTable() {
		var v = this.find("[name='updateTable']:checked").val();
		if (v == "0") {
			$(this.convertSelector("#tablePackageName")).prop("disabled", true);
			$(this.convertSelector("#tableClassName")).prop("disabled", true);
			$(this.convertSelector("#daoClassName")).prop("disabled", true);
			$(this.convertSelector("#daoClassOverwriteMode")).prop("disabled", true);
		} else {
			$(this.convertSelector("#tablePackageName")).prop("disabled", false);
			$(this.convertSelector("#tableClassName")).prop("disabled", false);
			$(this.convertSelector("#daoClassName")).prop("disabled", false);
			$(this.convertSelector("#daoClassOverwriteMode")).prop("disabled", false);
		}
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
		var cnfield = this.getComponent("pageClassName");
		if (this.saveMode == "new") {
			funcsel.lock(false);
			pkgname.lock(false);
			cnfield.lock(false);
		} else {
			funcsel.lock(true);
			pkgname.lock(true);
			cnfield.lock(true);
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
	 * 計算処理。
	 * @param element {jQuery} 計算イベントが発生した要素。
	 */
	onCalc(element) {
		this.setFormClassName();
		if (element != null) {
			if (element.attr(this.getIdAttribute()) == "functionSelect") {
				var funcname = element.val();
				var packageName = funcname.replace(/\//g, ".").substr(1) + ".dao";
				this.find("#tablePackageName").val(packageName);
			}
		}
		this.setFormFlag();
	}

	/**
	 * フォームの生成チェックボックスに応じて、各項目を制御します。
	 *
	 */
	setFormFlag() {
		var thisForm = this;
		if (!this.find("#queryFormClassFlag").prop("checked")) {
			thisForm.find("#queryFormClassName").val("");
			thisForm.find("#queryFormClassName").prop("disabled", true);
			thisForm.find("#queryFormClassOverwriteMode").prop("disabled", true);
		} else {
			thisForm.find("#queryFormClassName").prop("disabled", false);
			thisForm.setFormClassNameField("queryFormClassName", "QueryForm");
			thisForm.find("#queryFormClassOverwriteMode").prop("disabled", false);
		}
		if (!this.find("#queryResultFormClassFlag").prop("checked")) {
			thisForm.find("#queryResultFormClassName").val("");
			thisForm.find("#queryResultFormClassName").prop("disabled", true);
			thisForm.find("#queryResultFormClassOverwriteMode").prop("disabled", true);
		} else {
			thisForm.find("#queryResultFormClassName").prop("disabled", false);
			thisForm.setFormClassNameField("queryResultFormClassName", "QueryResultForm");
			thisForm.find("#queryResultFormClassOverwriteMode").prop("disabled", false);
		}
		if (!this.find("#editFormClassFlag").prop("checked")) {
			thisForm.find("#editFormClassName").val("");
			thisForm.find("#editFormClassName").prop("disabled", true);
			thisForm.find("#editFormClassOverwriteMode").prop("disabled", true);
		} else {
			thisForm.find("#editFormClassName").prop("disabled", false);
			thisForm.setFormClassNameField("editFormClassName", "EditForm");
			thisForm.find("#editFormClassOverwriteMode").prop("disabled", false);
		}
	}

	/**
	 * ページクラス名からフォームクラス名を設定します。
	 * @param name フォームクラス名ID。
	 * @param type フォームの種類。
	 */
	setFormClassNameField(name, type) {
		var pageclass = this.getFieldValue("pageClassName");
		var n = pageclass.replace(/Page$/, "");
		var f = this.find("#" + name);
		if (f.val() == "") {
			if (!f.prop("disabled")) {
				f.val(n + type);
			}
		}
	}

	/**
	 * Pageクラス名の変更時に呼び出され、各種フォームクラスの名称を設定します。
	 *
	 */
	setFormClassName() {
		var pageclass = this.getFieldValue("pageClassName");
		if (pageclass.length > 0) {
			this.setFormClassNameField("queryFormClassName", "QueryForm");
			this.setFormClassNameField("queryResultFormClassName", "QueryResultForm");
			this.setFormClassNameField("editFormClassName", "EditForm");
		} else {
			this.find("#queryFormClassName").val("");
			this.find("#queryResultFormClassName").val("");
			this.find("#editFormClassName").val("");
		}

	}

	/**
	 * 各フィールドのバリデーションを行います。
	 */
	validateFields() {
		var ret = super.validateFields();
		if (this.find("[name='updateTable']:checked").val() == "1") {
			var v = new RequiredValidator();
			v.messageKey = "error.required";
			var r = this.getComponent("tablePackageName").performValidator(v);
			if (r != null) {
				ret.push(r);
			}
			var r = this.getComponent("tableClassName").performValidator(v);
			if (r != null) {
				ret.push(r);
			}
			var r = this.getComponent("daoClassName").performValidator(v);
			if (r != null) {
				ret.push(r);
			}
		}
		return ret;
	}

	/**
	 * フォームデータを設定します。
	 * @param {Object} data フォームデータ。
	 */
	setFormData(data) {
		super.setFormData(data);
		this.setUpdateTable();
	}
}

