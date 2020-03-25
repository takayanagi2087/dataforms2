/**
 * @fileOverview {@link TableInfoForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class TableInfoForm
 * テーブル情報フォームクラス。
 * <pre>
 * テーブル情報を表示するためのフォームです。
 * </pre>
 * @extends Form
 */
class TableInfoForm extends Form {
	/**
	 * HTMLエレメントフォームとの対応付けを行います。
	 *
	 */
	attach() {
		super.attach();
		this.get("initTableButton").click(() => {
			this.initTable();
		});
		this.get("updateTableButton").click(() => {
			this.updateTable();
		});
		this.get("dropTableButton").click(() => {
			this.dropTable();
		});
		this.get("closeButton").click(() => {
			this.parent.close();
		});
	}

	/**
	 * フォームデータの設定を行います。
	 * @param {Object} formData フォームデータ。
	 */
	setFormData(formData) {
		super.setFormData(formData);
		if (formData.tableExists) {
			this.get("dropTableButton").prop("disabled", false);
		} else {
			this.get("dropTableButton").prop("disabled", true);
		}
	}

	/**
	 * クエリ結果リストのクラス情報を更新します。
	 * @param {Object} result クラス情報。
	 */
	updateTableInfo(result) {
		var page = this.parent.parent;
		var resultForm = page.getComponent("queryResultForm");
		resultForm.updateTableInfo(result);
	}

	/**
	 * DBテーブルの初期化を行います。
	 */
	initTable() {
		var thisForm = this;
		var systemName = MessagesUtil.getMessage("message.systemname");
		currentPage.confirm(systemName, MessagesUtil.getMessage("message.initTableConfirm"), function() {
			var clsname = thisForm.get("className").html();
			var p = "className=" + clsname;
			var method = thisForm.getServerMethod("initTable");
			method.execute(p, function(result) {
				if (result.status == ServerMethod.SUCCESS) {
					thisForm.setFormData(result.result);
					thisForm.updateTableInfo(result.result);
				}
			});
		});
	}


	/**
	 * DBテーブルの削除を行います。
	 */
	dropTable() {
		var thisForm = this;
		var systemName = MessagesUtil.getMessage("message.systemname");
		currentPage.confirm(systemName, MessagesUtil.getMessage("message.dropTableConfirm"), function() {
			var clsname = thisForm.get("className").html();
			var p = "className=" + clsname;
			var method = thisForm.getServerMethod("dropTable");
			method.execute(p, function(result) {
				if (result.status == ServerMethod.SUCCESS) {
					thisForm.setFormData(result.result);
					thisForm.updateTableInfo(result.result);
				}
			});
		});
	}

	/**
	 * DBテーブルの再構築を行います。
	 */
	updateTable() {
		var thisForm = this;
		var systemName = MessagesUtil.getMessage("message.systemname");
		currentPage.confirm(systemName, MessagesUtil.getMessage("message.updateTableConfirm"), function() {
			var clsname = thisForm.get("className").html();
			var p = "className=" + clsname;
			var method = thisForm.getServerMethod("updateTable");
			method.execute(p, function(result) {
				if (result.status == ServerMethod.SUCCESS) {
					thisForm.setFormData(result.result);
					thisForm.updateTableInfo(result.result);
				}
			});
		});
	}

}


