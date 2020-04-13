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
	 * @return インポートテキスト。
	 */
	public String getImportText() {
		StringBuilder sb = new StringBuilder();
		for (String cls: this.set) {
			sb.append("import " + cls + ";\n");
		}
		return sb.toString();
	}

}
