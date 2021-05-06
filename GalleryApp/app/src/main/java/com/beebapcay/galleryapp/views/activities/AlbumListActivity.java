package com.beebapcay.galleryapp.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.GalleryAdapter;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.factories.AlbumListViewModelFactory;
import com.beebapcay.galleryapp.listeners.GalleryListener;
import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.AlbumListViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumListActivity extends AppCompatActivity implements GalleryListener {
	private static final String TAG = AlbumListActivity.class.getSimpleName();

	TextView mAlbumName, mAlbumSummary;
	RecyclerView mRecyclerView;

	private Bundle mBundle;
	private AlbumModel mDataAlbum;
	private AlbumListViewModelFactory mAlbumListViewModelFactory;
	private AlbumListViewModel mAlbumListViewModel;
	private int mNumPictures, mNumVideos;
	private GalleryAdapter mGalleryAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_list);

		doInitialization();
	}

	@SuppressLint("SetTextI18n")
	public void doInitialization() {
		mBundle = getIntent().getExtras();
		mDataAlbum = mBundle.getParcelable(ExtraIntentKey.EXTRA_ALBUM_DATA);

		//MediaViewModel
		mAlbumListViewModelFactory = new AlbumListViewModelFactory(MediaDataRepository.getInstance(this));
		mAlbumListViewModel = new ViewModelProvider(this, mAlbumListViewModelFactory).get(AlbumListViewModel.class);

		mAlbumName = findViewById(R.id.text_album_name);
		mAlbumName.setText(mDataAlbum.getName());

		mAlbumSummary = findViewById(R.id.text_album_summary);

		mRecyclerView = findViewById(R.id.view_recycler_gallery_list);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

		mGalleryAdapter = new GalleryAdapter(this, this);
		mRecyclerView.setAdapter(mGalleryAdapter);

		mAlbumListViewModel.getLiveDataGallery().observe(this, dataGallery -> {
			mGalleryAdapter.loadData(dataGallery);
			mGalleryAdapter.sortFilter(FilterType.DATE);
		});
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	protected void onStart() {
		super.onStart();

		loadMediaData();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public void loadMediaData() {
		mAlbumListViewModel.clearData();
		mNumPictures = 0;
		mNumVideos = 0;

		mAlbumListViewModel.loadGallery(mDataAlbum)
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataGallery -> {
					for (GalleryModel item : dataGallery) {
						if (item instanceof PictureModel) mNumPictures++;
						else mNumVideos++;
					}
					String summary = ((mNumPictures != 0) ? (mNumPictures + " pictures") : "") +
							((mNumPictures != 0 && mNumVideos != 0) ? "\t\t" : "") +
							((mNumVideos != 0) ? (mNumVideos + " videos") : "");
					mAlbumSummary.setText(summary);
					mAlbumListViewModel.getLiveDataGallery().setValue(dataGallery);
				});
	}

	@Override
	public void onGalleryClicked(GalleryModel gallery, int position) {
		Intent intent = new Intent(this, HeroItemActivity.class);
		Bundle bundle = new Bundle();
		if (gallery instanceof PictureModel) {
			bundle.putString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE, "picture");
			bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, (PictureModel) gallery);
		}
		else {
			bundle.putString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE, "video");
			bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, (VideoModel) gallery);
		}
		intent.putExtras(bundle);
		startActivity(intent);
	}

}