package dataforms.devtool.webres.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dataforms.app.enumtype.dao.EnumDao;
import dataforms.controller.QueryForm;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.FunctionSelectField;
import dataforms.devtool.field.PackageNameField;
import dataforms.devtool.field.PageClassNameField;
import dataforms.devtool.field.WebComponentTypeListField;
import dataforms.devtool.field.WebSourcePathField;
import dataforms.field.common.FlagField;
import dataforms.validator.RequiredValidator;

/**
 * 問い合わせフォームクラス。
 */
public class WebResourceQueryForm extends QueryForm {
	/**
	 * コンストラクタ。
	 */
	public WebResourceQueryForm() {
		this.addField(new WebSourcePathField()).setReadonly(true);
		FunctionSelectField funcsel = new FunctionSelectField();
//		funcsel.setPackageOption("page");
		this.addField(funcsel);
		this.addField(new PackageNameField()).addValidator(new RequiredValidator());
		this.addField(new PageClassNameField()).setAutocomplete(true);
		this.addField(new WebComponentTypeListField());
		this.addField(new FlagField("generatableOnly"));
		this.addField(new ClassNameField());
	}

	@Override
	public void init() throws Exception {
		super.init();
		EnumDao dao = new EnumDao(this);
		List<Map<String, Object>> list = dao.getOptionList("webCompType", this.getPage().getCurrentLanguage());
		List<String> tlist = new ArrayList<String>();
		for (Map<String, Object> m: list) {
			tlist.add((String) m.get("value"));
		}
		this.setFormData("webSourcePath", DeveloperPage.getWebSourcePath());
		this.setFormData("webComponentTypeList", tlist);
		this.setFormData("generatableOnly", "0");
	}
}
