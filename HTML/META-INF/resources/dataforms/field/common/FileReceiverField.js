/**
 * @fileOverview {@link FileReceiverField}クラスを記述したファイルです。
 */

 'use strict';

 /**
 * @class FileReceiverField
 * ファイルDrag&Drop受付フィールド。
 * @extends Field
 */
 class FileReceiverField extends Field {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * ファイルのドロップイベントの設定を行います。
	 * </pre>
	 */
	attach() {
		super.attach();

		this.get().on("dragenter", (ev) => {
			ev.stopPropagation();
			ev.preventDefault();
			$(ev.target).addClass("fileReceiverActive");
		});

		this.get().on("dragover", (ev) => {
			ev.stopPropagation();
			ev.preventDefault();
		});

		this.get().on("drop", (ev) => {
			ev.stopPropagation();
			ev.preventDefault();
			$(ev.target).removeClass("fileReceiverActive");
			this.setFile(ev.originalEvent.dataTransfer.files);
		});

		this.get().on("dragleave", (ev) => {
			ev.stopPropagation();
			ev.preventDefault();
			$(ev.target).removeClass("fileReceiverActive");
		});

	}

	/**
	 * ファイルを設定します。
	 * @param {Array} ファイルリスト。
	 */
	setFile(files) {
		let fileField = this.parent.getComponent(this.fileFieldId);
		let el = fileField.get().get()[0];
		el.files = files;
		fileField.selectFile(fileField.get());
	}

	/**
	 * 値の設定を行います。
	 * <pre>
	 * 固定のメッセージを表示します。
	 * </pre>
	 */
	setValue(_) {
		super.setValue(this.message);
	}
}