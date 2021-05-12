package com.beebapcay.galleryapp.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import com.beebapcay.galleryapp.configs.PrefName;

import java.io.IOException;

public class DetectFaceService extends Service {
	private SharedPreferences mHaveFacePref;
	public boolean isRunning = true;

	public DetectFaceService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mHaveFacePref = getSharedPreferences(PrefName.HAVE_FACE, MODE_PRIVATE);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(() -> {
			if (!isRunning) {
				Log.d("ThreadStop", "true");
				return;
			}
			Uri collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			String[] projection = new String[]{
					MediaStore.Images.Media._ID,
			};

			try (Cursor cursor = getContentResolver().query(
					collection,
					projection,
					null,
					null,
					MediaStore.Images.Media.DATE_MODIFIED + " DESC")) {
				int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

				while (cursor.moveToNext()) {
					long idImg = cursor.getLong(idColumn);
					Uri uriImg = ContentUris.withAppendedId(collection, idImg);

					if (mHaveFacePref.contains(String.valueOf(idImg))) continue;

					try {
						Bitmap srcImg = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImg);
						Bitmap srcFace = srcImg.copy(Bitmap.Config.RGB_565, true);

						int w = srcFace.getWidth();
						int h = srcFace.getHeight();

						if (w % 2 == 1) srcFace = Bitmap.createScaledBitmap(srcFace, ++w, h, false);
						if (h % 2 == 1) srcFace = Bitmap.createScaledBitmap(srcFace, w, ++h, false);

						FaceDetector faceDetector = new FaceDetector(w, h , 1);
						FaceDetector.Face[] faces = new FaceDetector.Face[1];
						faceDetector.findFaces(srcFace, faces);
						if (faces[0] != null)
						{
							mHaveFacePref.edit().putBoolean(String.valueOf(idImg), true).apply();
							Log.d("faceDetect", "true");
						}
						else
							mHaveFacePref.edit().putBoolean(String.valueOf(idImg), false).apply();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}
}