/**
 * @fileOverview {@link TableInfoForm}クラスを記述したファイルです。
 */

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
		var thisForm = this;
		this.find("#initTableButton").click(function() {
			thisForm.initTable();
		});
		this.find("#updateTableButton").click(function() {
			thisForm.updateTable();
		});
		this.find("#dropTableButton").click(function() {
			thisForm.dropTable();
		});
		this.find("#closeButton").click(function() {
			thisForm.parent.close();
		});
	}

	/**
	 * フォームデータの設定を行います。
	 * @param {Object} formData フォームデータ。
	 */
	setFormData(formData) {
		super.setFormData(formData);
		if (formData.tableExists) {
			this.find("#dropTableButton").prop("disabled", false);
			this.find("#exportDataButton").prop("disabled", false);
		} else {
			this.find("#dropTableButton").prop("disabled", true);
			this.find("#exportDataButton").prop("disabled", true);
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
			var clsname = thisForm.find("#className").html();
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
			var clsname = thisForm.find("#className").html();
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
			var clsname = thisForm.find("#className").html();
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


