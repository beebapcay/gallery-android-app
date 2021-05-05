package com.beebapcay.galleryapp.repositories;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
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
		List<PictureModel> dataPictures = new ArrayList<>();

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
				dataPictures.add(pictureModel);
			}
		}

		Log.d(TAG, "Found: " + dataPictures.size() + " pictures");
		return Single.just(dataPictures);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<VideoModel>> loadVideos() {
		List<VideoModel> dataVideos = new ArrayList<>();

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
			int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				Date dateAdded = new Date(cursor.getLong(dateAddedColumn) * 1000);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				VideoModel videoModel = new VideoModel(id, uri, name, size, dateAdded, dateModified, height, width, duration);
				dataVideos.add(videoModel);
			}
		}

		Log.d(TAG, "Found: " + dataVideos.size() + " videos");
		return Single.just(dataVideos);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<AlbumModel>> loadAlbums() {
		List<AlbumModel> dataAlbums = new ArrayList<>();
		List<Long> listAlbumId = new ArrayList<>();

		//Pictures -> Albums
		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[] {
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.BUCKET_ID,
				MediaStore.Images.Media.BUCKET_DISPLAY_NAME
		};
		ContentResolver contentResolver = mContext.getContentResolver();

		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Images.Media.DATE_MODIFIED + " DESC")) {
			int imgIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID);
			int bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

			while (cursor.moveToNext()) {
				long imgId = cursor.getLong(imgIdColumn);
				long bucketId = cursor.getLong(bucketIdColumn);
				String bucketName = cursor.getString(bucketNameColumn);
				if (bucketName == null) bucketName = "No name";

				if (!listAlbumId.contains(bucketId)) {
					Uri bucketUri = ContentUris.withAppendedId(collection, bucketId);
					Uri imgUri = ContentUris.withAppendedId(collection, imgId);

					AlbumModel albumModel = new AlbumModel(bucketUri, bucketName, imgUri, 1);
					dataAlbums.add(albumModel);
					listAlbumId.add(bucketId);
				}
				else
					dataAlbums.get(listAlbumId.indexOf(bucketId)).increaseQuantity(1);
			}
		}

		//Videos -> Albums
		collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		projection = new String[] {
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.BUCKET_ID,
				MediaStore.Video.Media.BUCKET_DISPLAY_NAME
		};

		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int videoIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID);
			int bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);

			while (cursor.moveToNext()) {
				long videoId = cursor.getLong(videoIdColumn);
				long bucketId = cursor.getLong(bucketIdColumn);
				String bucketName = cursor.getString(bucketNameColumn);
				if (bucketName == null) bucketName = "0";

				if (!listAlbumId.contains(bucketId)) {
					Uri bucketUri = ContentUris.withAppendedId(collection, bucketId);
					Uri imgUri = ContentUris.withAppendedId(collection, videoId);

					AlbumModel albumModel = new AlbumModel(bucketUri, bucketName, imgUri, 1);
					dataAlbums.add(albumModel);
					listAlbumId.add(bucketId);
				}
				else
					dataAlbums.get(listAlbumId.indexOf(bucketId)).increaseQuantity(1);
			}
		}

		Log.d(TAG, "Found: " + dataAlbums.size() + " albums");
		return Single.just(dataAlbums);
	}
}
