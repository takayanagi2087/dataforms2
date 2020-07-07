package test.dao;

import java.util.Map;
import dataforms.dao.Table;
import test.field.FileIdField;
import test.field.FolderImageField;
import test.field.FolderVideoField;
import test.field.BlobImageField;
import test.field.BlobVideoField;
import dataforms.util.NumberUtil;
import test.field.BlobAudioField;
import dataforms.field.common.SortOrderField;
import test.field.FolderAudioField;
import test.field.BlobFileField;
import test.field.MemoField;
import test.field.FolderFileField;


/**
 * 各種ファイルテーブルクラス。
 *
 */
public class FileTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public FileTable() {
		this.setAutoIncrementId(true);
		this.setComment("各種ファイルテーブル");
		this.addPkField(new FileIdField()); //ファイルID
		this.addField(new SortOrderField()); //ソート順
		this.addField(new MemoField()); //メモ
		this.addField(new BlobFileField()); //BLOBファイル
		this.addField(new BlobImageField()); //BLOB画像
		this.addField(new BlobVideoField()); //BLOB動画
		this.addField(new BlobAudioField()); //BLOB音声
		this.addField(new FolderFileField()); //FOLDERファイル
		this.addField(new FolderImageField()); //FOLDER画像
		this.addField(new FolderVideoField()); //FOLDER動画
		this.addField(new FolderAudioField()); //FOLDER音声
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		FileTableRelation r = new FileTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** ファイルIDのフィールドID。 */
		public static final String ID_FILE_ID = "fileId";
		/** ソート順のフィールドID。 */
		public static final String ID_SORT_ORDER = "sortOrder";
		/** メモのフィールドID。 */
		public static final String ID_MEMO = "memo";
		/** BLOBファイルのフィールドID。 */
		public static final String ID_BLOB_FILE = "blobFile";
		/** BLOB画像のフィールドID。 */
		public static final String ID_BLOB_IMAGE = "blobImage";
		/** BLOB動画のフィールドID。 */
		public static final String ID_BLOB_VIDEO = "blobVideo";
		/** BLOB音声のフィールドID。 */
		public static final String ID_BLOB_AUDIO = "blobAudio";
		/** FOLDERファイルのフィールドID。 */
		public static final String ID_FOLDER_FILE = "folderFile";
		/** FOLDER画像のフィールドID。 */
		public static final String ID_FOLDER_IMAGE = "folderImage";
		/** FOLDER動画のフィールドID。 */
		public static final String ID_FOLDER_VIDEO = "folderVideo";
		/** FOLDER音声のフィールドID。 */
		public static final String ID_FOLDER_AUDIO = "folderAudio";

		/**
		 * コンストラクタ。
		 */
		public Entity() {

		}
		/**
		 * コンストラクタ。
		 * @param map 操作対象マップ。
		 */
		public Entity(final Map<String, Object> map) {
			super(map);
		}
		/**
		 * ファイルIDを取得します。
		 * @return ファイルID。
		 */
		public java.lang.Long getFileId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_FILE_ID));
		}

		/**
		 * ファイルIDを設定します。
		 * @param fileId ファイルID。
		 */
		public void setFileId(final java.lang.Long fileId) {
			this.getMap().put(Entity.ID_FILE_ID, fileId);
		}

		/**
		 * ソート順を取得します。
		 * @return ソート順。
		 */
		public java.lang.Short getSortOrder() {
			return NumberUtil.shortValueObject(this.getMap().get(Entity.ID_SORT_ORDER));
		}

		/**
		 * ソート順を設定します。
		 * @param sortOrder ソート順。
		 */
		public void setSortOrder(final java.lang.Short sortOrder) {
			this.getMap().put(Entity.ID_SORT_ORDER, sortOrder);
		}

		/**
		 * メモを取得します。
		 * @return メモ。
		 */
		public java.lang.String getMemo() {
			return (java.lang.String) this.getMap().get(Entity.ID_MEMO);
		}

		/**
		 * メモを設定します。
		 * @param memo メモ。
		 */
		public void setMemo(final java.lang.String memo) {
			this.getMap().put(Entity.ID_MEMO, memo);
		}

		/**
		 * BLOBファイルを取得します。
		 * @return BLOBファイル。
		 */
		public dataforms.dao.file.FileObject getBlobFile() {
			return (dataforms.dao.file.FileObject) this.getMap().get(Entity.ID_BLOB_FILE);
		}

		/**
		 * BLOBファイルを設定します。
		 * @param blobFile BLOBファイル。
		 */
		public void setBlobFile(final dataforms.dao.file.FileObject blobFile) {
			this.getMap().put(Entity.ID_BLOB_FILE, blobFile);
		}

		/**
		 * BLOB画像を取得します。
		 * @return BLOB画像。
		 */
		public dataforms.dao.file.ImageData getBlobImage() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_BLOB_IMAGE);
		}

		/**
		 * BLOB画像を設定します。
		 * @param blobImage BLOB画像。
		 */
		public void setBlobImage(final dataforms.dao.file.ImageData blobImage) {
			this.getMap().put(Entity.ID_BLOB_IMAGE, blobImage);
		}

		/**
		 * BLOB動画を取得します。
		 * @return BLOB動画。
		 */
		public dataforms.dao.file.ImageData getBlobVideo() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_BLOB_VIDEO);
		}

		/**
		 * BLOB動画を設定します。
		 * @param blobVideo BLOB動画。
		 */
		public void setBlobVideo(final dataforms.dao.file.ImageData blobVideo) {
			this.getMap().put(Entity.ID_BLOB_VIDEO, blobVideo);
		}

		/**
		 * BLOB音声を取得します。
		 * @return BLOB音声。
		 */
		public java.lang.Object getBlobAudio() {
			return (java.lang.Object) this.getMap().get(Entity.ID_BLOB_AUDIO);
		}

		/**
		 * BLOB音声を設定します。
		 * @param blobAudio BLOB音声。
		 */
		public void setBlobAudio(final java.lang.Object blobAudio) {
			this.getMap().put(Entity.ID_BLOB_AUDIO, blobAudio);
		}

		/**
		 * FOLDERファイルを取得します。
		 * @return FOLDERファイル。
		 */
		public dataforms.dao.file.FileObject getFolderFile() {
			return (dataforms.dao.file.FileObject) this.getMap().get(Entity.ID_FOLDER_FILE);
		}

		/**
		 * FOLDERファイルを設定します。
		 * @param folderFile FOLDERファイル。
		 */
		public void setFolderFile(final dataforms.dao.file.FileObject folderFile) {
			this.getMap().put(Entity.ID_FOLDER_FILE, folderFile);
		}

		/**
		 * FOLDER画像を取得します。
		 * @return FOLDER画像。
		 */
		public dataforms.dao.file.ImageData getFolderImage() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_FOLDER_IMAGE);
		}

		/**
		 * FOLDER画像を設定します。
		 * @param folderImage FOLDER画像。
		 */
		public void setFolderImage(final dataforms.dao.file.ImageData folderImage) {
			this.getMap().put(Entity.ID_FOLDER_IMAGE, folderImage);
		}

		/**
		 * FOLDER動画を取得します。
		 * @return FOLDER動画。
		 */
		public dataforms.dao.file.VideoData getFolderVideo() {
			return (dataforms.dao.file.VideoData) this.getMap().get(Entity.ID_FOLDER_VIDEO);
		}

		/**
		 * FOLDER動画を設定します。
		 * @param folderVideo FOLDER動画。
		 */
		public void setFolderVideo(final dataforms.dao.file.VideoData folderVideo) {
			this.getMap().put(Entity.ID_FOLDER_VIDEO, folderVideo);
		}

		/**
		 * FOLDER音声を取得します。
		 * @return FOLDER音声。
		 */
		public java.lang.String getFolderAudio() {
			return (java.lang.String) this.getMap().get(Entity.ID_FOLDER_AUDIO);
		}

		/**
		 * FOLDER音声を設定します。
		 * @param folderAudio FOLDER音声。
		 */
		public void setFolderAudio(final java.lang.String folderAudio) {
			this.getMap().put(Entity.ID_FOLDER_AUDIO, folderAudio);
		}


	}
	/**
	 * ファイルIDフィールドを取得します。
	 * @return ファイルIDフィールド。
	 */
	public FileIdField getFileIdField() {
		return (FileIdField) this.getField(Entity.ID_FILE_ID);
	}

	/**
	 * ソート順フィールドを取得します。
	 * @return ソート順フィールド。
	 */
	public SortOrderField getSortOrderField() {
		return (SortOrderField) this.getField(Entity.ID_SORT_ORDER);
	}

	/**
	 * メモフィールドを取得します。
	 * @return メモフィールド。
	 */
	public MemoField getMemoField() {
		return (MemoField) this.getField(Entity.ID_MEMO);
	}

	/**
	 * BLOBファイルフィールドを取得します。
	 * @return BLOBファイルフィールド。
	 */
	public BlobFileField getBlobFileField() {
		return (BlobFileField) this.getField(Entity.ID_BLOB_FILE);
	}

	/**
	 * BLOB画像フィールドを取得します。
	 * @return BLOB画像フィールド。
	 */
	public BlobImageField getBlobImageField() {
		return (BlobImageField) this.getField(Entity.ID_BLOB_IMAGE);
	}

	/**
	 * BLOB動画フィールドを取得します。
	 * @return BLOB動画フィールド。
	 */
	public BlobVideoField getBlobVideoField() {
		return (BlobVideoField) this.getField(Entity.ID_BLOB_VIDEO);
	}

	/**
	 * BLOB音声フィールドを取得します。
	 * @return BLOB音声フィールド。
	 */
	public BlobAudioField getBlobAudioField() {
		return (BlobAudioField) this.getField(Entity.ID_BLOB_AUDIO);
	}

	/**
	 * FOLDERファイルフィールドを取得します。
	 * @return FOLDERファイルフィールド。
	 */
	public FolderFileField getFolderFileField() {
		return (FolderFileField) this.getField(Entity.ID_FOLDER_FILE);
	}

	/**
	 * FOLDER画像フィールドを取得します。
	 * @return FOLDER画像フィールド。
	 */
	public FolderImageField getFolderImageField() {
		return (FolderImageField) this.getField(Entity.ID_FOLDER_IMAGE);
	}

	/**
	 * FOLDER動画フィールドを取得します。
	 * @return FOLDER動画フィールド。
	 */
	public FolderVideoField getFolderVideoField() {
		return (FolderVideoField) this.getField(Entity.ID_FOLDER_VIDEO);
	}

	/**
	 * FOLDER音声フィールドを取得します。
	 * @return FOLDER音声フィールド。
	 */
	public FolderAudioField getFolderAudioField() {
		return (FolderAudioField) this.getField(Entity.ID_FOLDER_AUDIO);
	}


}
