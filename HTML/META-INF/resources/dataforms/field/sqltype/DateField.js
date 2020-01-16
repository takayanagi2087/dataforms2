/**
 * @fileOverview {@link DateField}クラスを記述したファイルです。
 */

/**
 * @class DateField
 * Date型フィールドクラス。
 * <pre>
 * </pre>
 * @extends DateTimeField
 */
class DateField extends DateTimeField {
	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
		this.displayFormat = MessagesUtil.getMessage("format.datefield");
		this.editFormat = MessagesUtil.getMessage("editformat.datefield");
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 各種フォーマットの設定と、Datapickerの設定を行います。
	 * </pre>
	 */
	attach() {
		super.attach();
		var thisField = this;
		var comp = this.get();
		if (!comp.prop("readonly")) {
			if (this.datepickerEnabled) {
				this.setDatepicker();
			}
			this.setFormat(this.displayFormat, this.editFormat);
		}
		this.backupStyle();
	}

	/**
	 * Datepickerで日付を選択したときに呼び出されます。
	 * @param {String} datetext 日付テキスト。
	 * @param {Object} Datepickerのインスタンス。
	 */
	onSelect(datetext, inst) {
		if (this.calcEventField) {
			var form = this.getParentForm();
			form.onCalc($(this));
		}
	}

	/**
	 * Datepickerの設定を行います。
	 */
	setDatepicker() {
		var datepickerFormat = MessagesUtil.getMessage("format.datepicker");
		var comp = this.get();
		var tag = comp.prop("tagName");
		var type = comp.prop("type");
		var thisField = this;
		if (tag == "INPUT" && type == "text") {
			$(this.convertSelector("#" + this.selectorEscape(this.id))).datepicker({
				dateFormat: datepickerFormat
				,autoSize: true
				,showOn: "button"
				,buttonText: "..."
				,beforeShow:function(input, inst) {
				}
				,onSelect:function(datetext, inst) {
					thisField.onSelect(datetext, inst);
				}
			});
		}
	}

	/**
	 * 日付を表示フォーマットに変換します。
	 * @param {String} value 日付のlong値.
	 * @returns {String} 変換結果.
	 */
	getFormatedText(value) {
		if (value != null) {
			var d = new Date(value);
			var displayFormat = MessagesUtil.getMessage("format.datefield");
			var fmt = new SimpleDateFormat(displayFormat);
			return fmt.format(d);
		} else {
			return value;
		}
	}

	/**
	 * INPUTまたはSELECTタグの場合の値設定を行います。
	 * @param {jQuery} comp 値を設定するコンポーネント。
	 * @param {String} value 値。
	 */
	setInputValue(comp, value) {
		comp.val(this.getFormatedText(value));
	}

	/**
	 * SPAN等の表示用タグへの値設定を行います。
	 * @param {jQuery} comp 値を設定するコンポーネント。
	 * @param {String} value 値。
	 */
	setTextValue(comp, value) {
		if (value == null) {
			comp.text("");
		} else {
			comp.text(this.getFormatedText(value));
		}
	}

	/**
	 * フィールドのIDが変更された場合呼び出されます。
	 * <pre>
	 * EditableHtmlTableの場合、行追加/削除やソートによって、各フィールドのIDが変化します。
	 * DateFieldはIDが変わると、datepickerの動作がおかしくなるため、このメソッドで、datepickerを再作成を行います。
	 * </pre>
	 */
	onIdChange() {
		this.get().datepicker("destroy");
		this.setDatepicker();
	}

	/**
	 * フィールドが削除された場合呼び出されます。
	 * <pre>
	 * HtmlTableのデータ更新時やEdiatableHtmlTableの行削除時等フィールドが、削除されるタイミングで
	 * 呼び出されます。通常イベントハンドラ等の削除を行います。
	 * DateFieldの場合datepickerを削除します。
	 * </pre>
	 */
	onDestroy() {
		this.get().datepicker("destroy");
		Field.prototype.onDestroy.call(this);
	}

	/**
	 * フィールドのロックを行う。
	 * <pre>
	 * ボタンの表示/非表示制御を追加します。
	 * </pre>
	 */
	lock(lk) {
		DateTimeField.prototype.lock.call(this, lk);
		var tbtn = this.get().next(".ui-datepicker-trigger:first");
		if (lk) {
			tbtn.hide();
		} else {
			tbtn.show();
		}
	}
}



