package dataforms.devtool.webres.page;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dataforms.annotation.WebMethod;
import dataforms.app.login.page.LoginInfoForm;
import dataforms.app.menu.page.SideMenuForm;
import dataforms.controller.DataForms;
import dataforms.controller.Form;
import dataforms.controller.WebComponent;
import dataforms.devtool.base.page.DeveloperPage;
import dataforms.devtool.field.ClassNameField;
import dataforms.devtool.field.JavascriptClassField;
import dataforms.devtool.field.PathNameField;
import dataforms.devtool.field.WebComponentTypeField;
import dataforms.devtool.field.WebSourcePathField;
import dataforms.devtool.webres.gen.FormHtmlGenerator;
import dataforms.field.common.FlagField;
import dataforms.field.common.PresenceField;
import dataforms.response.JsonResponse;
import dataforms.servlet.DataFormsServlet;
import dataforms.util.ClassNameUtil;
import dataforms.util.FileUtil;
import dataforms.util.MessagesUtil;
import dataforms.util.StringUtil;
import dataforms.validator.ValidationError;

// TODO:validatorの生成の際attachメソッドの生成は不要。
/**
 * Webリソース作成フォームクラス。
 *
 */
public class WebResourceForm extends Form {
	/**
	 * Log.
	 */
	private static Logger logger = LogManager.getLogger(WebResourceForm.class.getName());
	/**
	 * コンストラクタ。
	 */
	public WebResourceForm() {
		super(null);
		this.addField(new WebSourcePathField());
		this.addField(new ClassNameField()).setReadonly(true);
		this.addField(new WebComponentTypeField()).setReadonly(true);
		this.addField(new PathNameField("htmlPath")).setReadonly(true);
		this.addField(new PresenceField("htmlStatus")).setReadonly(true);
		this.addField(new FlagField("outputFormHtml"));
		this.addField(new PathNameField("javascriptPath")).setReadonly(true);
		this.addField(new PresenceField("javascriptStatus")).setReadonly(true);
		this.addField(new JavascriptClassField()).setReadonly(true);
		this.addField(new FlagField("forceOverwrite"));
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.setFormData("webSourcePath", DeveloperPage.getWebSourcePath());
	}

	/**
	 * HTMLを作成します。
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外。
	 */
	@WebMethod
	public JsonResponse generateHtml(final Map<String, Object> p) throws Exception {
		Map<String, Object> data = this.convertToServerData(p);
		String message = "";
		List<ValidationError> errlist = new ArrayList<ValidationError>();
		String srcpath = generateHtmlFile(data);
		if (srcpath != null) {
			message = MessagesUtil.getMessage(this.getPage(), "message.htmlgenerated", srcpath);
		} else {
			errlist.add(new ValidationError("htmlPath", MessagesUtil.getMessage(this.getPage(), "error.htmlalreadyexists")));
		}
		if (errlist.size() == 0) {
			JsonResponse r = new JsonResponse(JsonResponse.SUCCESS, message);
			return r;
		} else {
			JsonResponse r = new JsonResponse(JsonResponse.INVALID, errlist);
			return r;
		}
	}

	/**
	 * Pageクラスに対応したhtmlを作成する。
	 * @param className クラス名。
	 * @param sourcePath 出力先フォルダ。
	 * @param outputFormHtml Form別ファイル出力フラグ。
	 * @return HTMLテキスト。
	 * @throws Exception 例外。
	 */
	private String getDataformsHtml(final String className, final String sourcePath, final String outputFormHtml) throws Exception {
		String src = this.getStringResourse("template/Page.html.template");
		Class<?> pageClass = Class.forName(className);
		DataForms page = (DataForms) pageClass.getDeclaredConstructor().newInstance();
		StringBuilder sb = new StringBuilder();
		List<WebComponent> clist = page.getComponentList();
		for (WebComponent c: clist) {
			if (c instanceof Form) {
				Form f = (Form) c;
				if (isCommonForm(f)) {
					continue;
				}
				if ("0".equals(outputFormHtml)) {
					String srcpath = sourcePath + "/" + f.getClass().getName().replaceAll("\\.", "/") + ".html";
					File srcfile = new File(srcpath);
					srcfile.delete();
				}
				FormHtmlGenerator gen = FormHtmlGenerator.newFormHtmlGenerator(f, 3);
				sb.append(gen.generateFormHtml(outputFormHtml));
			}
		}
		src = src.replaceAll("\\$\\{forms\\}", sb.toString());
		return src;
	}

	/**
	 * フォームを出力します。
	 * @param f フォーム。
	 * @param sourcePath ファイルの出力先。
	 * @param forceOverwrite 強制上書きフラグ。
	 * @return 出力ファイル。
	 * @throws Exception 例外。
	 */
	private String outputFormHtml(final Form f, final String sourcePath, final String forceOverwrite) throws Exception {
		String src = this.getStringResourse("template/Form.html.template");
		FormHtmlGenerator gen = FormHtmlGenerator.newFormHtmlGenerator(f, 2);
		StringBuilder sb = new StringBuilder();
		sb.append(gen.generateFormHtml("0"));
		String gensrc = src.replaceAll("\\$\\{form\\}", sb.toString());
		String srcpath = sourcePath + "/" + f.getClass().getName().replaceAll("\\.", "/") + ".html";
		File file = new File(srcpath);
		if ((!file.exists()) || "1".equals(forceOverwrite)) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
		} else {
			srcpath = null;
		}
		return srcpath;
	}

	/**
	 * ページまたはダイアログクラス中のフォームを別HTMLに出力します。
	 * @param className ページクラス名。
	 * @param sourcePath ファイルの出力パス。
	 * @param forceOverwrite 強制上書きフラグ。
	 * @throws Exception 例外。
	 */
	private void outputForms(final String className, final String sourcePath, final String forceOverwrite) throws Exception {
		Class<?> pageClass = Class.forName(className);
		DataForms page = (DataForms) pageClass.getDeclaredConstructor().newInstance();
		List<WebComponent> clist = page.getComponentList();
		for (WebComponent c: clist) {
			if (c instanceof Form) {
				Form f = (Form) c;
				if (isCommonForm(f)) {
					continue;
				}
				this.outputFormHtml(f, sourcePath, forceOverwrite);
			}
		}
	}

	/**
	 * 生成対象外の共通フォームかどうかを判定します。
	 * @param f フォーム。
	 * @return 生成対象外の共通フォームの場合true。
	 */
	private boolean isCommonForm(final Form f) {
		return f instanceof LoginInfoForm || f instanceof SideMenuForm;
	}


	/**
	 * HTMLファイルを作成します。
	 * @param data データ。
	 * @return 出力されたファイル。
	 * @throws Exception 例外。
	 */
	private String generateHtmlFile(final Map<String, Object> data) throws Exception {
		String webComponentType = (String) data.get("webComponentType");
		String outputFormHtml = (String) data.get("outputFormHtml");
		String forceOverwrite = (String) data.get("forceOverwrite");
		String sourcePath = (String) data.get("webSourcePath");
		String fullClassName = (String) data.get("className");
		String gensrc = "";
		if ("page".equals(webComponentType) || "dialog".equals(webComponentType)) {
			gensrc = this.getDataformsHtml(fullClassName, sourcePath, outputFormHtml);
			if ("1".equals(outputFormHtml)) {
				this.outputForms(fullClassName, sourcePath, forceOverwrite);
			}
			String srcpath = sourcePath + "/" + fullClassName.replaceAll("\\.", "/") + ".html";
			File f = new File(srcpath);
			if ((!f.exists()) || "1".equals(forceOverwrite)) {
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
				return srcpath;
			} else {
				return null;
			}
		} else if ("form".equals(webComponentType)) {
			Class<?> fcls = Class.forName(fullClassName);
			Form f = (Form) fcls.getConstructor().newInstance();
			return 	this.outputFormHtml(f, sourcePath, forceOverwrite);
		}
		return null;
	}


	/**
	 * Javascriptを作成します。
	 * @param p パラメータ。
	 * @return 処理結果。
	 * @throws Exception 例外
	 */
	@WebMethod
	public JsonResponse generateJavascript(final Map<String, Object> p) throws Exception {
		Map<String, Object> data = this.convertToServerData(p);
		String message = "";
		List<ValidationError> errlist = new ArrayList<ValidationError>();
		String srcpath = generateJavascriptFile(data);
		if (srcpath != null) {
			message = MessagesUtil.getMessage(this.getPage(), "message.javascriptgenerated", srcpath);
		} else {
			errlist.add(new ValidationError("javascriptPath", MessagesUtil.getMessage(this.getPage(), "error.javascriptalreadyexists")));
		}
		if (errlist.size() == 0) {
			JsonResponse r = new JsonResponse(JsonResponse.SUCCESS, message);
			return r;
		} else {
			JsonResponse r = new JsonResponse(JsonResponse.INVALID, errlist);
			return r;
		}
	}

	/**
	 * テンプレートファイルを取得します。
	 * @param name リソース名。
	 * @return テンプレートの内容。
	 * @throws Exception 例外。
	 */
	private String getTemplate(final String name) throws Exception {
		String ret = "";
		Class<?> cls = this.getClass();
		InputStream is = cls.getResourceAsStream(name);
		if (is != null) {
			try {
				ret = new String(FileUtil.readInputStream(is), DataFormsServlet.getEncoding());
			} finally {
				is.close();
			}
		}
		return ret;
	}

	/**
	 * Javascriptのソーステンプレートを取得します。
	 * @param cls Javaクラス。
	 * @return テンプレートの文字列。
	 * @throws Exception 例外。
	 */
	private String getTemplate(final Class<?> cls) throws Exception {
		String template = null;
		Class<?> superClass = cls.getSuperclass();
		while (true) {
			String classname = superClass.getSimpleName();
			logger.debug(() -> "template class=" + classname);
			String src = this.getTemplate("template/" + classname + ".js.template");
			logger.debug(() -> "src=" + src);
			if (!StringUtil.isBlank(src)) {
				template = src;
				break;
			}
			superClass = superClass.getSuperclass();
		}
		return template;
	}

	/**
	 * javascriptファイルを作成します。
	 * @param data データ。
	 * @return 出力されたファイル。
	 * @throws Exception 例外。
	 *
	 */
	private String generateJavascriptFile(final Map<String, Object> data) throws Exception {

		String setFormData = this.getTemplate("template/setFormData.js.template");
		String validateForm = this.getTemplate("template/validateForm.js.template");
		String buttonHandler = this.getTemplate("template/buttonHandler.js.template");
		String callWebMethod = this.getTemplate("template/callWebMethod.js.template");
		String onCalc = this.getTemplate("template/onCalc.js.template");

		String forceOverwrite = (String) data.get("forceOverwrite");
		String sourcePath = (String) data.get("webSourcePath");
		String fullClassName = (String) data.get("className");
		Class<?> cls = Class.forName(fullClassName);
		cls.getSuperclass().getSimpleName();
		String superClassName = cls.getSuperclass().getSimpleName();
		String className = ClassNameUtil.getSimpleClassName(fullClassName);
		String src = this.getTemplate(cls);
		String gensrc = src.replaceAll("\\$\\{className\\}", className);
		gensrc = gensrc.replaceAll("\\$\\{superClassName\\}", superClassName);
		gensrc = gensrc.replaceAll("\\$\\{setFormData\\}", setFormData);
		gensrc = gensrc.replaceAll("\\$\\{validateForm\\}", validateForm);
		gensrc = gensrc.replaceAll("\\$\\{buttonHandler\\}", buttonHandler);
		gensrc = gensrc.replaceAll("\\$\\{callWebMethod\\}", callWebMethod);
		gensrc = gensrc.replaceAll("\\$\\{onCalc\\}", onCalc);
		String srcpath = sourcePath + "/" + fullClassName.replaceAll("\\.", "/") + ".js";
		File f = new File(srcpath);
		if ((!f.exists()) || "1".equals(forceOverwrite)) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			FileUtil.writeTextFileWithBackup(srcpath, gensrc, DataFormsServlet.getEncoding());
			return srcpath;
		} else {
			return null;
		}
	}

}
