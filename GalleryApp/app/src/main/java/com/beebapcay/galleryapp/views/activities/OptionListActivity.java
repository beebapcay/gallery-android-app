package com.beebapcay.galleryapp.views.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.GalleryAdapter;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.OptionListViewModelFactory;
import com.beebapcay.galleryapp.listeners.GalleryListener;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.OptionListViewModel;
import com.beebapcay.galleryapp.views.fragments.OptionListActionBarFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OptionListActivity extends AppCompatActivity implements GalleryListener {
	private static final String TAG = OptionListActivity.class.getSimpleName();

	RecyclerView mRecyclerView;

	private Bundle mBundle;
	private OptionListViewModelFactory mOptionListViewModelFactory;
	private OptionListViewModel mOptionListViewModel;
	private int mNumPictures = 0, mNumVideos = 0;
	private GalleryAdapter mGalleryAdapter;

	@RequiresApi(api = Build.VERSION_CODES.Q)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_list);

		doInitialization();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	private void doInitialization() {
		mOptionListViewModelFactory = new OptionListViewModelFactory(MediaDataRepository.getInstance(this));
		mOptionListViewModel = new ViewModelProvider(this, mOptionListViewModelFactory).get(OptionListViewModel.class);

		mBundle = getIntent().getExtras();
		int destination = mBundle.getInt(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE);

		mRecyclerView = findViewById(R.id.view_recycler_gallery_list);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

		mGalleryAdapter = new GalleryAdapter(this, this);
		mRecyclerView.setAdapter(mGalleryAdapter);

		if (destination == R.string.title_favorites) onDestIsFavourites();
		else if (destination == R.string.title_recent) onDestIsRecent();
		else if (destination == R.string.title_people) onDestIsPeople();
		else if (destination == R.string.title_locations) onDestIsLocations();
		else throw new IllegalArgumentException("Option Fragment Unknown");

		mOptionListViewModel.getLiveDataItems().observe(this, dataItems -> {
			mGalleryAdapter.loadData(dataItems);
		});
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	private void onDestIsFavourites() {
		mOptionListViewModel.clearData();
		mOptionListViewModel.loadFavourites()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataFavourites -> {
					for (GalleryModel item : dataFavourites) {
						if (item instanceof PictureModel) mNumPictures++;
						else mNumVideos++;
					}

					String summary = ((mNumPictures != 0) ? (mNumPictures + " pictures") : "") +
							((mNumPictures != 0 && mNumVideos != 0) ? "\t\t" : "") +
							((mNumVideos != 0) ? (mNumVideos + " videos") : "");

					Bundle bundle = new Bundle();
					bundle.putString("title", getString(R.string.title_favorites));
					bundle.putString("summary", summary);
					getSupportFragmentManager().setFragmentResult("content", bundle);

					mOptionListViewModel.getLiveDataItems().setValue(dataFavourites);
				});
	}

	private void onDestIsRecent() {

	}

	private void onDestIsPeople() {
		mOptionListViewModel.clearData();
		mOptionListViewModel.loadPicturesHaveFace()
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(dataItems -> {
					for (GalleryModel item : dataItems) {
						if (item instanceof PictureModel) mNumPictures++;
						else mNumVideos++;
					}

					String summary = ((mNumPictures != 0) ? (mNumPictures + " pictures") : "") +
							((mNumPictures != 0 && mNumVideos != 0) ? "\t\t" : "") +
							((mNumVideos != 0) ? (mNumVideos + " videos") : "");

					Bundle bundle = new Bundle();
					bundle.putString("title", getString(R.string.title_favorites));
					bundle.putString("summary", summary);
					getSupportFragmentManager().setFragmentResult("content", bundle);

					mOptionListViewModel.getLiveDataItems().setValue(dataItems);
				});
	}

	private void onDestIsLocations() {

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