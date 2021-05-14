package com.beebapcay.galleryapp.views.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.bumptech.glide.Glide;

import java.util.Random;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class HeroPictureFragment extends Fragment {
	private static final String TAG = HeroPictureFragment.class.getSimpleName();

	ImageView mHeroImage;

	private PictureModel mDataPicture;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public HeroPictureFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_picture, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataPicture = (PictureModel) mHeroItemViewModel.getLiveDataItem().getValue();

		mHeroImage = view.findViewById(R.id.image_hero_image);
		Glide.with(requireActivity())
				.load(mDataPicture.getUri())
				.into(mHeroImage);

		mHeroItemViewModel.getUriCrop().observe(requireActivity(), uriCrop -> {
			if (uriCrop != null) {
				Completable.fromCallable(() -> {
					mHeroItemViewModel.saveCropPicture(uriCrop, mDataPicture.getName(), null);
					return null;
				}).subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(() -> Toast.makeText(requireActivity(), "Save crop image", Toast.LENGTH_SHORT).show());
			}
		});

		mHeroItemViewModel.getFilterBitmap().observe(requireActivity(), filterBitmap -> {
			if (filterBitmap != null) {
				Completable.fromCallable(() -> {
					mHeroItemViewModel.saveFilterPicture(filterBitmap, mDataPicture.getName(), null);
					return null;
				}).subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(() -> Toast.makeText(requireActivity(), "Save filter image", Toast.LENGTH_SHORT).show());
			}
		});
	}
}