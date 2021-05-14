package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.bumptech.glide.Glide;
import com.mukesh.image_processing.ImageProcessor;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterItemDialogFragment extends DialogFragment {
	public static String TAG = FilterItemDialogFragment.class.getSimpleName();

	ImageButton mBackButton;
	Button mSaveButton;
	ImageView mFilterView, mOriginalView, mGrayView, mDarkView, mLightView,
			mContrastView, mSepiaView, mSnowView, mSaturationView, mEngraveView,
			mBlurView, mShadowView, mFleaView, mHueView, mBlackView;
	ImageProcessor mProcessor;
	ProgressBar mProgressBar;
	HorizontalScrollView mFilterStyleView;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;
	private Bitmap mOriginalBitmap, mGrayBitmap, mDarkBitmap, mLightBitmap, mFilterBitmap,
			mContrastBitmap, mSnowBitmap, mSaturationBitmap, mEngraveBitmap;

	public FilterItemDialogFragment() {
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_filter_item_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		try {
			mOriginalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), mDataItem.getUri());
			mFilterBitmap = mOriginalBitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}

		mProgressBar = view.findViewById(R.id.progress_process_filter);
		mFilterStyleView = view.findViewById(R.id.view_filter_style);

		mProcessor = new ImageProcessor();

		mBackButton = view.findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(v -> dismiss());

		mFilterView = view.findViewById(R.id.image_filter_preview);
		Glide.with(this).load(mOriginalBitmap).into(mFilterView);

		mOriginalView = view.findViewById(R.id.original_image);
		mGrayView = view.findViewById(R.id.grayscale_image);
		mDarkView = view.findViewById(R.id.dark_image);
		mLightView = view.findViewById(R.id.light_image);
		mContrastView = view.findViewById(R.id.contrast_image);
		mSnowView = view.findViewById(R.id.snow_image);
		mSaturationView = view.findViewById(R.id.saturation_image);
		mEngraveView = view.findViewById(R.id.engrave_image);

		mProgressBar.setVisibility(View.VISIBLE);
		mFilterStyleView.setVisibility(View.GONE);

		Completable.fromCallable(() -> {
			mGrayBitmap = mProcessor.doGreyScale(mOriginalBitmap);
			mDarkBitmap = mProcessor.doBrightness(mOriginalBitmap, -40);
			mLightBitmap = mProcessor.doBrightness(mOriginalBitmap, 40);
			mContrastBitmap = mProcessor.createContrast(mOriginalBitmap, 1.5);
			mSepiaBitmap = mProcessor.createSepiaToningEffect(mOriginalBitmap, 1, 2, 1, 5);
			mSnowBitmap = mProcessor.applySnowEffect(mOriginalBitmap);
			mSaturationBitmap = mProcessor.applySaturationFilter(mOriginalBitmap, 3);
			mEngraveBitmap = mProcessor.engrave(mOriginalBitmap);
			return null;
		}).subscribeOn(Schedulers.newThread())
				.doOnTerminate(() -> {
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
					mProgressBar.setVisibility(View.GONE);
					mFilterStyleView.setVisibility(View.VISIBLE);
					Glide.with(this).load(mOriginalBitmap).into(mOriginalView);
					Glide.with(this).load(mGrayBitmap).into(mGrayView);
					Glide.with(this).load(mDarkBitmap).into(mDarkView);
					Glide.with(this).load(mLightBitmap).into(mLightView);
					Glide.with(this).load(mContrastBitmap).into(mContrastView);
					Glide.with(this).load(mSnowBitmap).into(mSnowView);
					Glide.with(this).load(mSaturationBitmap).into(mSaturationView);
					Glide.with(this).load(mEngraveBitmap).into(mEngraveView);
				});

		mOriginalView.setOnClickListener(v -> {
			mFilterBitmap = mOriginalBitmap;
			Glide.with(this).load(mOriginalBitmap).into(mFilterView);
		});
		mGrayView.setOnClickListener(v -> {
			mFilterBitmap = mGrayBitmap;
			Glide.with(this).load(mGrayBitmap).into(mFilterView);
		});
		mDarkView.setOnClickListener(v -> {
			mFilterBitmap = mDarkBitmap;
			Glide.with(this).load(mDarkBitmap).into(mFilterView);
		});
		mLightView.setOnClickListener(v -> {
			mFilterBitmap = mLightBitmap;
			Glide.with(this).load(mLightBitmap).into(mFilterView);
		});
		mContrastView.setOnClickListener(v -> {
			mFilterBitmap = mContrastBitmap;
			Glide.with(this).load(mContrastBitmap).into(mFilterView);
		});
		mSnowView.setOnClickListener(v -> {
			mFilterBitmap = mSnowBitmap;
			Glide.with(this).load(mSnowBitmap).into(mFilterView);
		});
		mSaturationView.setOnClickListener(v -> {
			mFilterBitmap = mSaturationBitmap;
			Glide.with(this).load(mSaturationBitmap).into(mFilterView);
		});
		mEngraveView.setOnClickListener(v -> {
			mFilterBitmap = mEngraveBitmap;
			Glide.with(this).load(mEngraveBitmap).into(mFilterView);
		});

		mSaveButton = view.findViewById(R.id.btn_save);
		mSaveButton.setOnClickListener(v -> {
			if (mFilterBitmap != mOriginalBitmap)
				mHeroItemViewModel.getFilterBitmap().setValue(mFilterBitmap);
			dismiss();
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}