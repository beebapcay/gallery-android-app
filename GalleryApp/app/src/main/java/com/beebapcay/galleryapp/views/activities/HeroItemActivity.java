package com.beebapcay.galleryapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.views.fragments.HeroPictureFragment;
import com.beebapcay.galleryapp.views.fragments.HeroVideoFragment;

public class HeroItemActivity extends AppCompatActivity {
	private static final String TAG = HeroItemActivity.class.getSimpleName();

	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hero_item);

		doInitialization();
	}

	public void doInitialization() {
		mBundle = getIntent().getExtras();

		String type = mBundle.getString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE);
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
	}

}