package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.models.PictureModel;
import com.bumptech.glide.Glide;

public class HeroPictureFragment extends Fragment {
	private static final String TAG = HeroPictureFragment.class.getSimpleName();

	ImageView mHeroImage;

	private PictureModel mDataPicture;

	public HeroPictureFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mDataPicture = requireArguments().getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_picture, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mHeroImage = view.findViewById(R.id.image_hero_image);
		Glide.with(requireActivity())
				.load(mDataPicture.getUri())
				.into(mHeroImage);
	}
}