package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.beebapcay.galleryapp.R;

public class HeroItemOptionBarFragment extends Fragment {
	private static final String TAG = HeroItemOptionBarFragment.class.getSimpleName();

	ImageButton mDeleteButton;

	private DeleteItemDialogFragment mDeleteItemDialogFragment;

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

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDeleteButton = view.findViewById(R.id.btn_delete);
		mDeleteButton.setOnClickListener(v -> {
			mDeleteItemDialogFragment = new DeleteItemDialogFragment();
			mDeleteItemDialogFragment.show(getChildFragmentManager(), DeleteItemDialogFragment.TAG);
		});
	}

	@Override
	public void onStart() {
		super.onStart();
	}
}
