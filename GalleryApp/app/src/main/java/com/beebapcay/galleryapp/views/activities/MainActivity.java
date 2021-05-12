package com.beebapcay.galleryapp.views.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.services.DetectFaceService;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0;

	MediaViewModel mMediaViewModel;
	MediaViewModelFactory mMediaViewModelFactory;
	BottomNavigationView mBottomNavigationView;
	NavController mNavController;

	ComponentName mDetectFaceService;
	Intent mDetectFaceServiceIntent;



	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		doInitialization();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	protected void onStart() {
		super.onStart();

		loadMediaData();

		//Start Service Detect Face
		if (mDetectFaceServiceIntent == null || mDetectFaceService == null) {
			mDetectFaceServiceIntent = new Intent(MainActivity.this, DetectFaceService.class);
			mDetectFaceService = startService(mDetectFaceServiceIntent);
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	private void doInitialization() {
		//MediaViewModel
		mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(this));
		mMediaViewModel = new ViewModelProvider(this, mMediaViewModelFactory).get(MediaViewModel.class);

		checkReadExternalStoragePermission();

		//Set Navigate of NavigationBottomView
		mBottomNavigationView = findViewById(R.id.view_bottom_nav);
		mNavController = Navigation.findNavController(this, R.id.view_dest_container);
		NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

	}

	@SuppressLint("NewApi")
	private void checkReadExternalStoragePermission() {
		boolean needPermission =
				ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
						!= PackageManager.PERMISSION_GRANTED ||
						ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
								!= PackageManager.PERMISSION_GRANTED ||
						ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
								!= PackageManager.PERMISSION_GRANTED ||
						ContextCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER)
								!= PackageManager.PERMISSION_GRANTED;
		if (needPermission) {
			requestPermissions(new String[]{
					Manifest.permission.READ_EXTERNAL_STORAGE,
					Manifest.permission.WRITE_EXTERNAL_STORAGE,
					Manifest.permission.MANAGE_EXTERNAL_STORAGE,
					Manifest.permission.SET_WALLPAPER,
			}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
		}
		else loadMediaData();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		switch (requestCode) {
			case REQUEST_CODE_READ_EXTERNAL_STORAGE: {
				if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
					Toast.makeText(this, "Cant not load media data", Toast.LENGTH_SHORT).show();
				else loadMediaData();
				break;
			}
			default:
				throw new IllegalStateException("Unexpected value: " + requestCode);
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public void loadMediaData() {
		mMediaViewModel.clearData();
		mMediaViewModel.loadPictures()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataPictures -> {
					mMediaViewModel.updateGalleryFromPictures(dataPictures);
					mMediaViewModel.getLiveDataPictures().setValue(dataPictures);
				});

		mMediaViewModel.loadVideos()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataVideos -> {
					mMediaViewModel.updateGalleryFromVideos(dataVideos);
					mMediaViewModel.getLiveDataVideos().setValue(dataVideos);
				});

		mMediaViewModel.loadAlbums()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataAlbums -> mMediaViewModel.getLiveDataAlbums().setValue(dataAlbums));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy: ");
		stopService(mDetectFaceServiceIntent);
	}
}