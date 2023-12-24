package pagepat.page;

import dataforms.controller.Form;
import dataforms.field.base.TextField;
import dataforms.field.sqltype.DateField;
import dataforms.field.sqltype.NumericField;

/**
 * 単純なページ 用フォームクラス。
 */
public class SimpleForm extends Form {
	/**
	 * コンストラクタ。
	 */
	public SimpleForm() {
		super(null);
		// TODO:1.フィールドを追加します。
		// TODO:2.Webリソース作成で、ページのHTMLを作成します。
		// TODO:3.必要に応じて作成したHTMLにボタンを追加します。
		// TODO:4.Webリソース作成でFormのjsを作成し、各種イベント処理を追加します。
		this.addField(new TextField("text"));
		this.addField(new NumericField("numeric", 16, 3));
		this.addField(new DateField("date"));
	}

	@Override
	public void init() throws Exception {
		super.init();
	}
}
