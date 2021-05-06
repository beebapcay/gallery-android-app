package com.beebapcay.galleryapp.views.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.models.AlbumModel;

public class AlbumListActivity extends AppCompatActivity {
	private static final String TAG = AlbumListActivity.class.getSimpleName();

	TextView mAlbumName, mAlbumSummary;

	private Bundle mBundle;
	private AlbumModel mDataAlbum;


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

		mAlbumName = findViewById(R.id.text_album_name);
		mAlbumName.setText(mDataAlbum.getName());

		mAlbumSummary = findViewById(R.id.text_album_summary);
		mAlbumSummary.setText(mDataAlbum.getQuantity() + " items");
	}
}