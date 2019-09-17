/**
 * @fileOverview {@link AudioField}クラスを記述したファイルです。
 */

/**
 * @class AudioField
 * 音声ファイルアップロードフィールドクラス。
 * @extends StreamingField
 */
//AudioField = createSubclass("AudioField", {}, "StreamingField");
class AudioField extends StreamingField {
	/**
	 * HTMLエレメントとの対応付けを行います。
	 * <pre>
	 * 削除チェックボックス、ダウンロードリンクなどの設定を行います。
	 * </pre>
	 */
	attach() {
		super.attach();
	}
}

