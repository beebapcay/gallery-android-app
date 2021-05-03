package com.beebapcay.galleryapp.repositories;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal", "UnusedReturnValue"})
public class MediaDataRepository {
	private static final String TAG = MediaDataRepository.class.getSimpleName();

	@SuppressLint("StaticFieldLeak")
	private static MediaDataRepository sSingletonInstance = null;

	private final Context mContext;

	private MediaDataRepository(Context context) {
		mContext = context;
	}

	public static MediaDataRepository getInstance(Context context) {
		if (sSingletonInstance == null) sSingletonInstance = new MediaDataRepository(context);
		return sSingletonInstance;
	}

	public Single<List<PictureModel>> loadPictures() {
		Log.d("TrackLoadData", "Reposity start");
		List<PictureModel> mDataPictures = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
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
				mDataPictures.add(pictureModel);
			}
		}

		Log.d(TAG, "Found: " + mDataPictures.size() + " pictures");
		Log.d("TrackLoadData", "Reposity end");
		return Single.just(mDataPictures);
	}


	public Single<List<VideoModel>> loadVideos() {
		List<VideoModel> mDataVideos = new ArrayList<>();

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

				VideoModel videoModel = new VideoModel(id, uri, name, size, dateAdded, dateModified, height, width, duration);
				mDataVideos.add(videoModel);
			}
		}

		Log.d(TAG, "Found: " + mDataVideos.size() + " videos");
		return Single.just(mDataVideos);
	}

	public Single<List<AlbumModel>> loadAlbums() {
		List<AlbumModel> mDataAlbums = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		Log.d(TAG, "Found: " + mDataAlbums.size() + " albums");
		return Single.just(mDataAlbums);
	}
}
