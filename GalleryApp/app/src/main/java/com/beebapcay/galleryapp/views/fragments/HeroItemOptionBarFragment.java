package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.beebapcay.galleryapp.R;

public class HeroItemOptionBarFragment extends Fragment {
	private static final String TAG = HeroItemOptionBarFragment.class.getSimpleName();

	public HeroItemOptionBarFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_item_option_bar, container, false);
	}
}
