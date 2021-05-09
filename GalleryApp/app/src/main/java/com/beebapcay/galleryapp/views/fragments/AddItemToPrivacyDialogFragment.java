package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;


public class AddItemToPrivacyDialogFragment extends DialogFragment {
	public static String TAG = AddItemToPrivacyDialogFragment.class.getSimpleName();

	Button mCancelButton, mMoveToPrivacyButton;
	SharedPreferences mSharedPreferences;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public AddItemToPrivacyDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);

		mSharedPreferences = requireContext().getSharedPreferences(PrefName.PRIVACY, Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_add_item_to_privacy_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();

		mCancelButton = view.findViewById(R.id.action_cancel);
		mCancelButton.setOnClickListener(v -> dismiss());

		mMoveToPrivacyButton = view.findViewById(R.id.action_move_to_privacy);
		mMoveToPrivacyButton.setOnClickListener(v -> {
			mSharedPreferences.edit().putBoolean(String.valueOf(mDataItem.getId()), true).apply();
			Toast.makeText(requireActivity(), "Move to Privacy successfully", Toast.LENGTH_SHORT).show();
			dismiss();
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}