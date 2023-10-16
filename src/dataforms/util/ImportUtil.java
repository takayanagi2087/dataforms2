package dataforms.util;

import java.util.HashSet;
import java.util.Set;

/**
 * インポートクラスユーティリティ。
 *
 */
public class ImportUtil {
	/**
	 * クラス名の集合。
	 */
	private Set<String> set = new HashSet<String>();

	/**
	 * コンストラクタ。
	 */
	public ImportUtil() {

	}

	/**
	 * クラスの追加。
	 * @param cls クラス名。
	 */
	public void add(final String cls) {
		if (!this.set.contains(cls)) {
			this.set.add(cls);
		}
	}

	/**
	 * クラスの追加。
	 * @param cls クラス。
	 */
	public void add(final Class<?> cls) {
		this.add(cls.getName());
	}

	/**
	 * インポートテキストを取得します。
	 * @patam src ソースコード。
	 * @return インポートテキスト。
	 */
	public String getImportText() {
		StringBuilder sb = new StringBuilder();
		for (String cls: this.set) {
			sb.append("import " + cls + ";\n");
		}
		return sb.toString();
	}

	/**
	 * 単純なクラス名を取得する。
	 * @param cname フルクラス名。
	 * @return 単純なクラス名。
	 */
	private String getClassName(final String cname) {
		String ret = cname;
		int idx = cname.lastIndexOf('.');
		if (idx >= 0) {
			ret = cname.substring(idx + 1);
		}
		return ret;
	}

	/**
	 * インポートテキストを取得します。
	 * @param src ソースコード。
	 * @return インポートテキスト。
	 */
	public String getImportText(final String src) {
		StringBuilder sb = new StringBuilder();
		for (String cls: this.set) {
			String c = this.getClassName(cls);
			int idx = src.indexOf(" " + c);
			if (idx >= 0) {
				sb.append("import " + cls + ";\n");
			}
		}
		return sb.toString();
	}

}
