package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.models.VideoModel;


public class HeroVideoFragment extends Fragment {
	private static final String TAG = HeroVideoFragment.class.getSimpleName();

	private VideoModel dataVideo;

	public HeroVideoFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			dataVideo = getArguments().getParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_video, container, false);
	}
}