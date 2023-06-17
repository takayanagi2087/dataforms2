package dataforms.debug.alltype.dao;

import java.util.Map;
import dataforms.dao.Table;
import dataforms.debug.alltype.field.StaticFolderImageFileField;
import dataforms.field.common.BlobStoreVideoField;
import dataforms.field.common.RecordIdField;
import dataforms.field.common.BlobStoreAudioField;
import dataforms.debug.alltype.field.FileCommentField;
import dataforms.field.common.FolderStoreFileField;
import dataforms.field.common.BlobStoreFileField;
import dataforms.field.common.FolderStoreVideoField;
import dataforms.debug.alltype.field.StaticFolderFileField;
import dataforms.util.NumberUtil;
import dataforms.field.common.BlobStoreImageField;
import dataforms.field.common.FolderStoreImageField;
import dataforms.field.common.FolderStoreAudioField;


/**
 * クラス。
 *
 */
public class FileFieldTestTable extends Table {
	/**
	 * コンストラクタ。
	 */
	public FileFieldTestTable() {
		this.setAutoIncrementId(true);
		this.setComment("");
		this.addPkField(new RecordIdField()).setNotNull(true); //レコードID
		this.addField(new FileCommentField()); //ファイルコメント
		this.addField(new BlobStoreFileField("blobFile")); //
		this.addField(new FolderStoreFileField("folderFile")); //
		this.addField(new StaticFolderFileField()); //
		this.addField(new BlobStoreImageField("blobImage")); //
		this.addField(new FolderStoreImageField("folderImage")); //
		this.addField(new StaticFolderImageFileField()); //
		this.addField(new BlobStoreVideoField("blobVideo")); //
		this.addField(new FolderStoreVideoField("folderVideo")); //
		this.addField(new BlobStoreAudioField("blobAudio")); //
		this.addField(new FolderStoreAudioField("folderAudio")); //
		this.addUpdateInfoFields();
	}

	@Override
	public String getJoinCondition(final Table joinTable, final String alias) {
		FileFieldTestTableRelation r = new FileFieldTestTableRelation(this);
		return r.getJoinCondition(joinTable, alias);
	}

	/**
	 * Entity操作クラスです。
	 */
	public static class Entity extends dataforms.dao.Entity {
		/** レコードIDのフィールドID。 */
		public static final String ID_RECORD_ID = "recordId";
		/** ファイルコメントのフィールドID。 */
		public static final String ID_FILE_COMMENT = "fileComment";
		/** のフィールドID。 */
		public static final String ID_BLOB_FILE = "blobFile";
		/** のフィールドID。 */
		public static final String ID_FOLDER_FILE = "folderFile";
		/** のフィールドID。 */
		public static final String ID_STATIC_FOLDER_FILE = "staticFolderFile";
		/** のフィールドID。 */
		public static final String ID_BLOB_IMAGE = "blobImage";
		/** のフィールドID。 */
		public static final String ID_FOLDER_IMAGE = "folderImage";
		/** のフィールドID。 */
		public static final String ID_STATIC_FOLDER_IMAGE_FILE = "staticFolderImageFile";
		/** のフィールドID。 */
		public static final String ID_BLOB_VIDEO = "blobVideo";
		/** のフィールドID。 */
		public static final String ID_FOLDER_VIDEO = "folderVideo";
		/** のフィールドID。 */
		public static final String ID_BLOB_AUDIO = "blobAudio";
		/** のフィールドID。 */
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
		 * レコードIDを取得します。
		 * @return レコードID。
		 */
		public java.lang.Long getRecordId() {
			return NumberUtil.longValueObject(this.getMap().get(Entity.ID_RECORD_ID));
		}

		/**
		 * レコードIDを設定します。
		 * @param recordId レコードID。
		 */
		public void setRecordId(final java.lang.Long recordId) {
			this.getMap().put(Entity.ID_RECORD_ID, recordId);
		}

		/**
		 * ファイルコメントを取得します。
		 * @return ファイルコメント。
		 */
		public java.lang.String getFileComment() {
			return (java.lang.String) this.getMap().get(Entity.ID_FILE_COMMENT);
		}

		/**
		 * ファイルコメントを設定します。
		 * @param fileComment ファイルコメント。
		 */
		public void setFileComment(final java.lang.String fileComment) {
			this.getMap().put(Entity.ID_FILE_COMMENT, fileComment);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.FileObject getBlobFile() {
			return (dataforms.dao.file.FileObject) this.getMap().get(Entity.ID_BLOB_FILE);
		}

		/**
		 * nullを設定します。
		 * @param blobFile null。
		 */
		public void setBlobFile(final dataforms.dao.file.FileObject blobFile) {
			this.getMap().put(Entity.ID_BLOB_FILE, blobFile);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.FileObject getFolderFile() {
			return (dataforms.dao.file.FileObject) this.getMap().get(Entity.ID_FOLDER_FILE);
		}

		/**
		 * nullを設定します。
		 * @param folderFile null。
		 */
		public void setFolderFile(final dataforms.dao.file.FileObject folderFile) {
			this.getMap().put(Entity.ID_FOLDER_FILE, folderFile);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.FileObject getStaticFolderFile() {
			return (dataforms.dao.file.FileObject) this.getMap().get(Entity.ID_STATIC_FOLDER_FILE);
		}

		/**
		 * nullを設定します。
		 * @param staticFolderFile null。
		 */
		public void setStaticFolderFile(final dataforms.dao.file.FileObject staticFolderFile) {
			this.getMap().put(Entity.ID_STATIC_FOLDER_FILE, staticFolderFile);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.ImageData getBlobImage() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_BLOB_IMAGE);
		}

		/**
		 * nullを設定します。
		 * @param blobImage null。
		 */
		public void setBlobImage(final dataforms.dao.file.ImageData blobImage) {
			this.getMap().put(Entity.ID_BLOB_IMAGE, blobImage);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.ImageData getFolderImage() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_FOLDER_IMAGE);
		}

		/**
		 * nullを設定します。
		 * @param folderImage null。
		 */
		public void setFolderImage(final dataforms.dao.file.ImageData folderImage) {
			this.getMap().put(Entity.ID_FOLDER_IMAGE, folderImage);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.ImageData getStaticFolderImageFile() {
			return (dataforms.dao.file.ImageData) this.getMap().get(Entity.ID_STATIC_FOLDER_IMAGE_FILE);
		}

		/**
		 * nullを設定します。
		 * @param staticFolderImageFile null。
		 */
		public void setStaticFolderImageFile(final dataforms.dao.file.ImageData staticFolderImageFile) {
			this.getMap().put(Entity.ID_STATIC_FOLDER_IMAGE_FILE, staticFolderImageFile);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.VideoData getBlobVideo() {
			return (dataforms.dao.file.VideoData) this.getMap().get(Entity.ID_BLOB_VIDEO);
		}

		/**
		 * nullを設定します。
		 * @param blobVideo null。
		 */
		public void setBlobVideo(final dataforms.dao.file.VideoData blobVideo) {
			this.getMap().put(Entity.ID_BLOB_VIDEO, blobVideo);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public dataforms.dao.file.VideoData getFolderVideo() {
			return (dataforms.dao.file.VideoData) this.getMap().get(Entity.ID_FOLDER_VIDEO);
		}

		/**
		 * nullを設定します。
		 * @param folderVideo null。
		 */
		public void setFolderVideo(final dataforms.dao.file.VideoData folderVideo) {
			this.getMap().put(Entity.ID_FOLDER_VIDEO, folderVideo);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.Object getBlobAudio() {
			return (java.lang.Object) this.getMap().get(Entity.ID_BLOB_AUDIO);
		}

		/**
		 * nullを設定します。
		 * @param blobAudio null。
		 */
		public void setBlobAudio(final java.lang.Object blobAudio) {
			this.getMap().put(Entity.ID_BLOB_AUDIO, blobAudio);
		}

		/**
		 * nullを取得します。
		 * @return null。
		 */
		public java.lang.Object getFolderAudio() {
			return (java.lang.Object) this.getMap().get(Entity.ID_FOLDER_AUDIO);
		}

		/**
		 * nullを設定します。
		 * @param folderAudio null。
		 */
		public void setFolderAudio(final java.lang.Object folderAudio) {
			this.getMap().put(Entity.ID_FOLDER_AUDIO, folderAudio);
		}


	}
	/**
	 * レコードIDフィールドを取得します。
	 * @return レコードIDフィールド。
	 */
	public RecordIdField getRecordIdField() {
		return (RecordIdField) this.getField(Entity.ID_RECORD_ID);
	}

	/**
	 * ファイルコメントフィールドを取得します。
	 * @return ファイルコメントフィールド。
	 */
	public FileCommentField getFileCommentField() {
		return (FileCommentField) this.getField(Entity.ID_FILE_COMMENT);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public BlobStoreFileField getBlobFileField() {
		return (BlobStoreFileField) this.getField(Entity.ID_BLOB_FILE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public FolderStoreFileField getFolderFileField() {
		return (FolderStoreFileField) this.getField(Entity.ID_FOLDER_FILE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public StaticFolderFileField getStaticFolderFileField() {
		return (StaticFolderFileField) this.getField(Entity.ID_STATIC_FOLDER_FILE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public BlobStoreImageField getBlobImageField() {
		return (BlobStoreImageField) this.getField(Entity.ID_BLOB_IMAGE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public FolderStoreImageField getFolderImageField() {
		return (FolderStoreImageField) this.getField(Entity.ID_FOLDER_IMAGE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public StaticFolderImageFileField getStaticFolderImageFileField() {
		return (StaticFolderImageFileField) this.getField(Entity.ID_STATIC_FOLDER_IMAGE_FILE);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public BlobStoreVideoField getBlobVideoField() {
		return (BlobStoreVideoField) this.getField(Entity.ID_BLOB_VIDEO);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public FolderStoreVideoField getFolderVideoField() {
		return (FolderStoreVideoField) this.getField(Entity.ID_FOLDER_VIDEO);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public BlobStoreAudioField getBlobAudioField() {
		return (BlobStoreAudioField) this.getField(Entity.ID_BLOB_AUDIO);
	}

	/**
	 * フィールドを取得します。
	 * @return フィールド。
	 */
	public FolderStoreAudioField getFolderAudioField() {
		return (FolderStoreAudioField) this.getField(Entity.ID_FOLDER_AUDIO);
	}


}
