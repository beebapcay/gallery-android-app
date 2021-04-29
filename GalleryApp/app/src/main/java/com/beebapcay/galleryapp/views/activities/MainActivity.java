package com.beebapcay.galleryapp.views.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 0;

	MediaViewModel mMediaViewModel;
	MediaViewModelFactory mMediaViewModelFactory;
	BottomNavigationView mBottomNavigationView;
	NavController mNavController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		doInitialization();
	}

	@Override
	protected void onStart() {
		super.onStart();

		loadMediaData();
	}

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
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		switch (requestCode) {
			case REQUEST_CODE_READ_EXTERNAL_STORAGE: {
				if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
					Toast.makeText(this, "Cant not load media data", Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				throw new IllegalStateException("Unexpected value: " + requestCode);
		}
	}

	public void loadMediaData() {
		mMediaViewModel.loadPictures()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataPictures -> mMediaViewModel.getLiveDataPictures().setValue(dataPictures));

		mMediaViewModel.loadVideos()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataVideos -> mMediaViewModel.getLiveDataVideos().setValue(dataVideos));

		mMediaViewModel.loadAlbums()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataAlbums -> mMediaViewModel.getLiveDataAlbums().setValue(dataAlbums));

	}
}