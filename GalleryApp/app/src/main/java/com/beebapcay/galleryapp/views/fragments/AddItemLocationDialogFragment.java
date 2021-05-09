package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;

import java.util.Objects;


public class AddItemLocationDialogFragment extends DialogFragment {
	public static String TAG = AddItemLocationDialogFragment.class.getSimpleName();

	EditText mLocationInput;
	Button mCancelButton, mConfirmButton;
	SharedPreferences mSharedPreferences;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public AddItemLocationDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
		mSharedPreferences = requireContext().getSharedPreferences(PrefName.LOCATION, Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_add_item_location_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();

		mCancelButton = view.findViewById(R.id.action_cancel);
		mCancelButton.setOnClickListener(v -> dismiss());

		mLocationInput = view.findViewById(R.id.input_location_name);
		mLocationInput.setText(mDataItem.getLocation());

		mConfirmButton = view.findViewById(R.id.action_confirm);
		mConfirmButton.setOnClickListener(v -> {
			String location = mLocationInput.getText().toString().trim();
			if (location.equals(mDataItem.getLocation())) dismiss();
			else {
				mDataItem.setLocation(location);
				mHeroItemViewModel.getLiveDataItem().setValue(mDataItem);
				if (location.equals(""))
					mSharedPreferences.edit().remove(String.valueOf(mDataItem.getId())).apply();
				else mSharedPreferences.edit().putString(String.valueOf(mDataItem.getId()), location).apply();
			}
			dismiss();
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setGravity(Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}