package com.beebapcay.galleryapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.mukesh.image_processing.ImageProcessor;

import java.io.IOException;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = FilterActivity.class.getSimpleName();

    private ImageButton mBackButton;
    private ImageView mFilterView, mOriginalView, mGrayView;
    private ImageProcessor mProcessor;
    private Bundle mBundle;
    private GalleryModel mDataItem;
    private Bitmap mOriginalBitmap, mGrayBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        doInitialization();
    }

    private void doInitialization() {
        mBundle = getIntent().getExtras();

        mDataItem = mBundle.getParcelable(ExtraIntentKey.FILTER_HERO_ITEM_DATA);
        try {
            mOriginalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mDataItem.getUri());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBackButton = findViewById(R.id.btn_back);
        mBackButton.setOnClickListener(v -> finish());

        mFilterView = findViewById(R.id.view_filter_image);
        mFilterView.setImageBitmap(mOriginalBitmap);

        mOriginalView = findViewById(R.id.original_image);
        mOriginalView.setImageBitmap(mOriginalBitmap);

        mProcessor = new ImageProcessor();

        mGrayView = findViewById(R.id.grayscale_image);
        mGrayBitmap = mProcessor.doGreyScale(mOriginalBitmap);
        mGrayView.setImageBitmap(mGrayBitmap);

        onClickFilterListener();

    }

    private void onClickFilterListener() {

        mOriginalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterView.setImageBitmap(mOriginalBitmap);
            }
        });

        mGrayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilterView.setImageBitmap(mGrayBitmap);
            }
        });
    }
}