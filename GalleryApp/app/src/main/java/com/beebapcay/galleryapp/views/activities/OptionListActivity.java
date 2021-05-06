package com.beebapcay.galleryapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.views.fragments.FavouritesFragment;
import com.beebapcay.galleryapp.views.fragments.LocationsFragment;
import com.beebapcay.galleryapp.views.fragments.PeopleFragment;
import com.beebapcay.galleryapp.views.fragments.RecentFragment;

public class OptionListActivity extends AppCompatActivity {
	private static final String TAG = OptionListActivity.class.getSimpleName();

	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option_list);

		doInitialization();
	}

	private void doInitialization() {
		mBundle = getIntent().getExtras();

		int destination = mBundle.getInt(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE);
		if (destination == R.string.title_favorites)
			getSupportFragmentManager().beginTransaction()
			.setReorderingAllowed(true)
			.add(R.id.view_fragment_container, FavouritesFragment.class, null)
			.commit();
		else if (destination == R.string.title_recent)
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.view_fragment_container, RecentFragment.class, null)
					.commit();
		else if (destination == R.string.title_people)
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.view_fragment_container, PeopleFragment.class, null)
					.commit();
		else if (destination == R.string.title_locations)
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.view_fragment_container, LocationsFragment.class, null)
					.commit();
		else throw new IllegalArgumentException("Option Fragment Unknown");
	}
}