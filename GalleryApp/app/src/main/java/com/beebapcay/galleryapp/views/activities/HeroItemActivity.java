package com.beebapcay.galleryapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.beebapcay.galleryapp.views.fragments.HeroPictureFragment;
import com.beebapcay.galleryapp.views.fragments.HeroVideoFragment;

public class HeroItemActivity extends AppCompatActivity {
	private static final String TAG = HeroItemActivity.class.getSimpleName();

	private Bundle mBundle;
	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hero_item);

		doInitialization();
	}

	public void doInitialization() {
		mBundle = getIntent().getExtras();

		String type = mBundle.getString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE);
		mDataItem = mBundle.getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA);

		if (type.equals("picture")) {
			Bundle bundle = new Bundle();
			bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, mBundle.getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA));
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.view_fragment_container, HeroPictureFragment.class, bundle)
					.commit();
		}
		else if (type.equals("video")) {
			Bundle bundle = new Bundle();
			bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, mBundle.getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA));
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.view_fragment_container, HeroVideoFragment.class, bundle)
					.commit();
		}
		else throw new IllegalArgumentException("Hero Item Unknown");

		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(this));
		mHeroItemViewModel = new ViewModelProvider(this, mHeroItemViewModelFactory).get(HeroItemViewModel.class);
	}

}