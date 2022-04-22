/**
 * @fileOverview {@link AllTypeEditForm}クラスを記述したファイルです。
 */

'use strict';

/**
 * @class AllTypeEditForm
 *
 * @extends EditForm
 */
class AllTypeEditForm extends EditForm {

	/**
	 * コンストラクタ。
	 */
	constructor() {
		super();
	}

	/**
	 * HTMLエレメントとの対応付けを行います。
	 */
	attach() {
		super.attach();
		var thisForm = this;
		this.get("printButton").click(function() {
			thisForm.print();
		});
		this.get("printPdfButton").click(function() {
			thisForm.printPdf();
		});
		this.get("printOutButton").click(function() {
			thisForm.printOut();
		});
		this.get("getValueTestButton").click(function() {
			thisForm.getValueTest();
		});
		this.get("userDialogButton").click(function() {
			var dlg = thisForm.parent.getComponent("userQueryDialog");
			dlg.data = null;
			dlg.showModal({
				close:function(event, ui) {
					if (dlg.data != null) {
						var json = JSON.stringify(dlg.data);
						logger.log("select data=" + json);
						alert(json);
					}
				}
			});
		});
//		$("#test1").html("<script>alert('aaa');</script>");
//		$("#test2").text("<script>alert('bbb');</script>");
//		$("#test3").val("<script>alert('ccc');</script>");
//		alert(MessagesUtil.getMessage("error.duplicate", "aaa"));
	}

	onCalc(jq) {
		logger.log("onCalc");
		logger.dir(jq);
		if (jq != null) {
			logger.log("id=" + jq.attr("id"));
			logger.dir(jq);
		}
	}


	/**
	 * 印刷処理を行います。
	 */
	print() {
		this.submit("print");
	}

	/**
	 * 印刷処理を行います。
	 */
	printPdf() {
		this.submit("printPdf");
	}

	/**
	 * 印刷処理を行います。
	 */
	printOut() {
		this.submit("printOut", function(r) {
			logger.log("r=");
			logger.dir(r);
		});
	}


	getFieldDump(id) {
		var v = this.getComponent(id).getValue();
		var text = id + "=" + v + ":" + v.constructor + "\n";
		return text;
	}

	/**
	 * 各種getValueのテストを行います。
	 */
	getValueTest() {
	/*	logger.log("getValueTest()");
		var text = "";
		text += this.getFieldDump("varcharField");
		text += this.getFieldDump("numericField");
		text += this.getFieldDump("dateField");
		text += this.getFieldDump("timeField");
		text += this.getFieldDump("timestampField");
		text += this.getFieldDump("dropdownField");
		text += this.getFieldDump("multiSelectListField");
		text += this.getFieldDump("radioField");
		text += this.getFieldDump("checkboxField");
		text += this.getFieldDump("uploadBlobData");
		text += this.getFieldDump("uploadFileData");

//		alert(text);
		logger.log(text);*/
		logger.log("StringUtil.fullToHalf=" + StringUtil.fullToHalf("アイウエオａｂｃｄｅｆ１２３４５６"));
		logger.log("StringUtil.halfToFull=" + StringUtil.halfToFull(StringUtil.fullToHalf("アイウエオａｂｃｄｅｆ１２３４５６")));
		let fmt = new SimpleDateFormat("yyyy/MM/dd");
		logger.log("format=" + fmt.format(new Date()));
		logger.log("parse=" + fmt.parse("2019/09/04"));
		logger.log("NumberUtil.addComma(123456789)=" + NumberUtil.addComma(123456789));
		logger.log("NumberUtil.delComma('123,456,789')=" + NumberUtil.delComma("123,456,789"));
		logger.log("NumberUtil.parse(123,456,789)=" + NumberUtil.parse("123,456,789"));

		logger.log("this.getQueryString()=" + JSON.stringify(this.getQueryString()));
		logger.log("QueryStringUtil.parse()=" + JSON.stringify(QueryStringUtil.parse(window.location.search)));

		var vshort = this.getComponent("smallintField").getValue();
		logger.log("vshort=");logger.dir(vshort);
		this.getComponent("smallintField").setValue(vshort * 10);

		var vint = this.getComponent("integerField").getValue();
		logger.log("vint=");logger.dir(vint);
		this.getComponent("integerField").setValue(vint * 10);

		var vlong = this.getComponent("bigintField").getValue();
		logger.log("vlong=");logger.dir(vlong);
		this.getComponent("bigintField").setValue(vlong * 10);

		var vdouble = this.getComponent("doubleField").getValue();
		logger.log("vdouble=");logger.dir(vdouble);
		this.getComponent("doubleField").setValue(vdouble * 10);

		var vnumeric = this.getComponent("numericField").getValue();
		logger.log("vnumeric=");logger.dir(vnumeric);
		this.getComponent("numericField").setValue(vnumeric * 10);


		var vnumeric = this.getComponent("numericField").getValue();
		logger.log("vnumeric=");logger.dir(vnumeric);
		this.getComponent("numericField").setValue(vnumeric * 10);

		var vdate = this.getComponent("dateField").getValue();
		logger.log("vdate=");logger.dir(vdate);
		this.getComponent("dateField").setValue(vdate);


		var vtime = this.getComponent("timeField").getValue();
		logger.log("vtime=");logger.dir(vtime);
		this.getComponent("timeField").setValue(vtime);

		var vtimestamp = this.getComponent("timestampField").getValue();
		logger.log("vtimestamp=");logger.dir(vtimestamp);
		this.getComponent("timestampField").setValue(vtimestamp);

		this.find("#attachFileTable thead tr").find("th").each(function() {
			logger.dir(this);
		});

		logger.dir("attachFileTable.getTableData = ");
		var attachFileTable = this.getComponent("attachFileTable");
		logger.dir(attachFileTable.getTableData());

		var param = {
			p1: "aaa"
			, p2: "bbb"
		};
		var m = this.getServerMethod("testJsonPost");
		m.execute(param, function(r) {
			if (r.status == ServerMethod.SUCCESS) {
				alert(r.result);
			}
		});
	}

}




