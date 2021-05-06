package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.models.PictureModel;

public class HeroPictureFragment extends Fragment {
	private static final String TAG = HeroPictureFragment.class.getSimpleName();

	private PictureModel dataPicture;

	public HeroPictureFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			dataPicture = requireArguments().getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_picture, container, false);
	}
}