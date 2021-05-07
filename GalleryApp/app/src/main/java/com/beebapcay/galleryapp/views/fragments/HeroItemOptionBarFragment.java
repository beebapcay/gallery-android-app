package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.bumptech.glide.Glide;

public class HeroItemOptionBarFragment extends Fragment {
	private static final String TAG = HeroItemOptionBarFragment.class.getSimpleName();

	ImageButton mFavouriteButton, mDeleteButton, mShareButton;
	DeleteItemDialogFragment mDeleteItemDialogFragment;
	SharedPreferences mSharedPreferences;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;
	private boolean isFavourite;

	public HeroItemOptionBarFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
		mSharedPreferences = requireContext().getSharedPreferences(PrefName.FAVOURITES, Context.MODE_PRIVATE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_item_option_bar, container, false);
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();
		isFavourite = mSharedPreferences.getBoolean(String.valueOf(mDataItem.getId()), false);

		mHeroItemViewModel.getLiveIsFavourite().setValue(isFavourite);

		mDeleteButton = view.findViewById(R.id.btn_delete);
		mDeleteButton.setOnClickListener(v -> {
			mDeleteItemDialogFragment = new DeleteItemDialogFragment();
			mDeleteItemDialogFragment.show(getChildFragmentManager(), DeleteItemDialogFragment.TAG);
		});

		mFavouriteButton = view.findViewById(R.id.btn_favourite);
		if (isFavourite) {
			mFavouriteButton.setImageResource(R.drawable.ic_heart_filled);
			mFavouriteButton.setImageTintList(requireContext().getColorStateList(R.color.colorFavourite));
		}

		else mFavouriteButton.setImageResource(R.drawable.ic_heart_outlined);
		mFavouriteButton.setOnClickListener(v -> {
			isFavourite = !isFavourite;
			mHeroItemViewModel.getLiveIsFavourite().setValue(isFavourite);
		});

		mShareButton = view.findViewById(R.id.btn_share);
		mShareButton.setOnClickListener(v -> {
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			ContentResolver contentResolver = getContext().getContentResolver();
			String type = contentResolver.getType(mDataItem.getUri());
			if (type.equals("image/jpg") || type.equals("image/jpeg") || type.equals("image/png"))
				sendIntent.setType("image/*");
			else
				sendIntent.setType("video/*");
			sendIntent.putExtra(Intent.EXTRA_STREAM, mDataItem.getUri());

			Intent shareIntent = Intent.createChooser(sendIntent, "Share Via");
			startActivity(shareIntent);
		});

		mHeroItemViewModel.getLiveIsFavourite().observe(requireActivity(), isFavourite -> {
			if (isFavourite) {
				mFavouriteButton.setImageResource(R.drawable.ic_heart_filled);
				mFavouriteButton.setImageTintList(requireContext().getColorStateList(R.color.colorFavourite));
				mSharedPreferences.edit().putBoolean(String.valueOf(mDataItem.getId()), true).apply();

			}
			else {
				mFavouriteButton.setImageResource(R.drawable.ic_heart_outlined);
				mFavouriteButton.setImageTintList(requireContext().getColorStateList(R.color.colorHeroItemOptionBarIcon));
				mSharedPreferences.edit().remove(String.valueOf(mDataItem.getId())).apply();

			}
		});

	}

	@Override
	public void onStart() {
		super.onStart();
	}


}
