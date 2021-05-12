package com.beebapcay.galleryapp.repositories;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.beebapcay.galleryapp.configs.PrefKey;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
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
	private final SharedPreferences mFavouritePref;
	private final SharedPreferences mPrivacyPref;
	private final SharedPreferences mLocationPref;
	private final SharedPreferences mHaveFacePref;

	private MediaDataRepository(Context context) {
		mContext = context;
		mFavouritePref = mContext.getSharedPreferences(PrefName.FAVOURITES, Context.MODE_PRIVATE);
		mPrivacyPref = mContext.getSharedPreferences(PrefName.PRIVACY, Context.MODE_PRIVATE);
		mLocationPref = mContext.getSharedPreferences(PrefName.LOCATION, Context.MODE_PRIVATE);
		mHaveFacePref = mContext.getSharedPreferences(PrefName.HAVE_FACE, Context.MODE_PRIVATE);
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
				MediaStore.Images.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				PictureModel pictureModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						isFavourite, location);
				dataPictures.add(pictureModel);
			}
		}

		Log.d(TAG, "Found: " + dataPictures.size() + " pictures");
		return Single.just(dataPictures);
	}

	public Single<List<GalleryModel>> loadPicturesHaveFace() {
		List<GalleryModel> dataPicturesHaveFace = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				boolean haveFace = mHaveFacePref.getBoolean(String.valueOf(id), false);
				if (!haveFace) continue;

				PictureModel pictureModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						isFavourite, location);
				dataPicturesHaveFace.add(pictureModel);
			}
		}

//		List<GalleryModel> dataPicturesHaveFace = new ArrayList<>();
//		for (GalleryModel item : dataPictures) {
//			try {
//				Bitmap srcImg = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), item.getUri());
//				Bitmap srcFace = srcImg.copy(Bitmap.Config.RGB_565, true);
//				srcImg = null;
//				int w = srcFace.getWidth();
//				int h = srcFace.getHeight();
//				if (w % 2 == 1)
//					srcFace = Bitmap.createScaledBitmap(srcFace, ++w, h, false);
//				if (h % 2 == 1)
//					srcFace = Bitmap.createScaledBitmap(srcFace, w, ++h, false);
//				FaceDetector faceDetector = new FaceDetector(w, h , 1);
//				FaceDetector.Face[] faces = new FaceDetector.Face[1];
//				faceDetector.findFaces(srcFace, faces);
//				if (faces[0] != null) {
//					Log.d("FindFace", "true");
//					dataPicturesHaveFace.add(item);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		Log.d(TAG, "Found: " + dataPicturesHaveFace.size() + " pictures have face");
		return Single.just(dataPicturesHaveFace);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<VideoModel>> loadVideos() {
		List<VideoModel> dataVideos = new ArrayList<>();

		Uri collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

		String[] projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				VideoModel videoModel = new VideoModel(id, uri, name, size, path, dateModified, height, width, duration,
						isFavourite, location);
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

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(imgId), false);
				if (isPrivacy) continue;

				if (!listAlbumId.contains(bucketId)) {
					Uri imgUri = ContentUris.withAppendedId(collection, imgId);

					AlbumModel albumModel = new AlbumModel(bucketId, bucketName, imgUri, 1);
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
				if (bucketName == null) bucketName = "No Name";

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(videoId), false);
				if (isPrivacy) continue;

				if (!listAlbumId.contains(bucketId)) {
					Uri imgUri = ContentUris.withAppendedId(collection, videoId);

					AlbumModel albumModel = new AlbumModel(bucketId, bucketName, imgUri, 1);
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

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadGalleryFromAlbum(AlbumModel dataAlbum) {
		List<GalleryModel> dataGallery = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DATE_MODIFIED,
				MediaStore.Images.Media.HEIGHT,
				MediaStore.Images.Media.WIDTH
		};
		String selection = MediaStore.Images.Media.BUCKET_ID + " == ?";
		String[] selectionArgs = new String[] {String.valueOf(dataAlbum.getId())};

		ContentResolver contentResolver = mContext.getContentResolver();
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				selection,
				selectionArgs,
				MediaStore.Images.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				GalleryModel galleryModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						 isFavourite, location);
				dataGallery.add(galleryModel);
			}
		}

		collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DATE_MODIFIED,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.WIDTH
		};
		selection = MediaStore.Video.Media.BUCKET_ID + " == ?";
		selectionArgs = new String[] {String.valueOf(dataAlbum.getId())};

		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				selection,
				selectionArgs,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				GalleryModel galleryModel = new VideoModel(id, uri, name, size, path, dateModified, height, width, duration,
						isFavourite, location);
				dataGallery.add(galleryModel);
			}
		}

		Log.d(TAG + " LoadMediaAlbum", "Found: " + dataGallery.size() + " items");
		return Single.just(dataGallery);
	}

	public void deleteItem(GalleryModel dataItem) {
		Uri itemUri = dataItem.getUri();
		ContentResolver contentResolver = mContext.getContentResolver();
		contentResolver.delete(itemUri, null, null);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadFavourites() {
		List<GalleryModel> dataFavourites = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				if (!isFavourite) continue;

				String location = mLocationPref.getString(String.valueOf(id), null);

				PictureModel pictureModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						true, location);
				dataFavourites.add(pictureModel);
			}
		}

		collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DATE_MODIFIED,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.WIDTH
		};
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				if (!isFavourite) continue;

				String location = mLocationPref.getString(String.valueOf(id), null);

				VideoModel videoModel = new VideoModel(id, uri, name, size, path, dateModified, height, width, duration,
						true, location);
				dataFavourites.add(videoModel);
			}
		}

		Log.d(TAG + " loadFavourites", "Found: " + dataFavourites.size() + " items");
		return Single.just(dataFavourites);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadLocations() {
		List<GalleryModel> dataFavourites = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);

				String location = mLocationPref.getString(String.valueOf(id), null);
				if (location == null) continue;

				PictureModel pictureModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						isFavourite, location);
				dataFavourites.add(pictureModel);
			}
		}

		collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DATE_MODIFIED,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.WIDTH
		};
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);

				String location = mLocationPref.getString(String.valueOf(id), null);
				if (location == null) continue;

				VideoModel videoModel = new VideoModel(id, uri, name, size, path, dateModified, height, width, duration,
						isFavourite, location);
				dataFavourites.add(videoModel);
			}
		}

		Log.d(TAG + " loadFavourites", "Found: " + dataFavourites.size() + " items");
		return Single.just(dataFavourites);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadPrivacy() {
		List<GalleryModel> dataFavourites = new ArrayList<>();

		Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		String[] projection = new String[]{
				MediaStore.Images.Media._ID,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.DATA,
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
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (!isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				PictureModel pictureModel = new PictureModel(id, uri, name, size, path, dateModified, height, width,
						isFavourite, location);
				dataFavourites.add(pictureModel);
			}
		}

		collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		projection = new String[]{
				MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DISPLAY_NAME,
				MediaStore.Video.Media.SIZE,
				MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.DATA,
				MediaStore.Video.Media.DATE_MODIFIED,
				MediaStore.Video.Media.HEIGHT,
				MediaStore.Video.Media.WIDTH
		};
		try (Cursor cursor = contentResolver.query(
				collection,
				projection,
				null,
				null,
				MediaStore.Video.Media.DATE_MODIFIED + " DESC")) {
			int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
			int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
			int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
			int pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
			int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
			int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
			int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);

			while (cursor.moveToNext()) {
				long id = cursor.getLong(idColumn);
				Uri uri = ContentUris.withAppendedId(collection, id);
				String name = cursor.getString(nameColumn);
				long size = cursor.getLong(sizeColumn);
				String path = cursor.getString(pathColumn);
				Date dateModified = new Date(cursor.getLong(dateModifiedColumn) * 1000);
				int height = cursor.getInt(heightColumn);
				int width = cursor.getInt(widthColumn);
				long duration = cursor.getLong(durationColumn);

				boolean isPrivacy = mPrivacyPref.getBoolean(String.valueOf(id), false);
				if (!isPrivacy) continue;

				boolean isFavourite = mFavouritePref.getBoolean(String.valueOf(id), false);
				String location = mLocationPref.getString(String.valueOf(id), null);

				VideoModel videoModel = new VideoModel(id, uri, name, size, path, dateModified, height, width, duration,
						isFavourite, location);
				dataFavourites.add(videoModel);
			}
		}

		Log.d(TAG + " loadFavourites", "Found: " + dataFavourites.size() + " items");
		return Single.just(dataFavourites);
	}

}
