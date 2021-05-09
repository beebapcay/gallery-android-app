package com.beebapcay.galleryapp.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.views.fragments.SlideshowFragment;

public class SlideshowActivity extends AppCompatActivity {
    private static final String TAG = SlideshowActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        doInitialization();
    }

    private void doInitialization() {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.view_fragment_slideshow, SlideshowFragment.class, null)
                .commit();
    }
}
