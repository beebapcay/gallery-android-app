package com.beebapcay.galleryapp.repositories;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MediaDataRepository {
	private static final String TAG = MediaDataRepository.class.getSimpleName();

	@SuppressLint("StaticFieldLeak")
	private static MediaDataRepository mSingletonInstance = null;

	private final Context mContext;

	private final List<PictureModel> mPictureModels = new ArrayList<>();
	private final List<VideoModel> mVideoModels = new ArrayList<>();
	private final List<AlbumModel> mAlbumModels = new ArrayList<>();

	private MediaDataRepository(Context context) {
		mContext = context;
	}

	public static MediaDataRepository getInstance(Context context) {
		if (mSingletonInstance == null) mSingletonInstance = new MediaDataRepository(context);
		return mSingletonInstance;
	}


	public void fetchMediaData() {
		fetchPictures();
		fetchVideos();
	}

	public void fetchPictures() {
		mPictureModels.clear();

		Uri collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATE_ADDED,
				MediaStore.Images.Media.DATE_MODIFIED,
				MediaStore.Images.Media.HEIGHT,
				MediaStore.Images.Media.WIDTH
		};

		ContentResolver contentResolver = mContext.getContentResolver();
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Images.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
			int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);

				Date dateAdded = new Date(cursor.getLong(dateAddedColumn) * 1000);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				PictureModel pictureModel = new PictureModel(id, uri, name, size, dateAdded, dateModified, height, width);
				mPictureModels.add(pictureModel);
			}
		}

		Log.d(TAG, "Found: " + mPictureModels.size() + " pictures");
	}


	public void fetchVideos() {
		mVideoModels.clear();

		Uri collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

		@SuppressLint("InlinedApi") String[] projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATE_ADDED,
				MediaStore.Video.Media.DATE_MODIFIED,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.WIDTH
		};

		ContentResolver contentResolver = mContext.getContentResolver();
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				long duration = cursor.getLong(durationColumn);

				Date dateAdded = new Date(cursor.getLong(dateAddedColumn) * 1000);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				VideoModel videoModel = new VideoModel(id, uri, name, size, duration, dateAdded, dateModified, height, width);
				mVideoModels.add(videoModel);
			}
		}

		Log.d(TAG, "Found: " + mVideoModels.size() + " videos");
	}

	public void fetchAlbums() {
		mAlbumModels.clear();

		Log.d(TAG, "Found: " + mAlbumModels.size() + " albums");
	}

	public List<PictureModel> getPictureModels() {
		return mPictureModels;
	}

	public List<VideoModel> getVideoModels() {
		return mVideoModels;
	}

	public List<AlbumModel> getAlbumModels() {
		return mAlbumModels;
	}
}
