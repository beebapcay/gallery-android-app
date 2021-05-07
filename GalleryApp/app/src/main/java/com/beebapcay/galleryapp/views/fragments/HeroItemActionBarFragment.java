package com.beebapcay.galleryapp.views.fragments;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.observers.LambdaObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HeroItemActionBarFragment extends Fragment {
	private static final String TAG = HeroItemActionBarFragment.class.getSimpleName();

	ImageButton mBackButton, mMoreButton;
	PopupMenu mMorePopupMenu;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public HeroItemActionBarFragment() {
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
		return inflater.inflate(R.layout.fragment_hero_item_action_bar, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mBackButton = view.findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(v -> requireActivity().onBackPressed());

		mMoreButton = view.findViewById(R.id.btn_more);
		mMoreButton.setOnClickListener(v -> onMoreButtonClicked());
	}

	@SuppressLint("NonConstantResourceId")
	private void onMoreButtonClicked() {
		if (mMorePopupMenu == null) {
			mMorePopupMenu = new PopupMenu(requireContext(), mMoreButton);
			mMorePopupMenu.inflate(R.menu.menu_hero_item_action_bar_more);
			mMorePopupMenu.setOnMenuItemClickListener(item -> {
				mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();
				switch (item.getItemId()) {
					case R.id.action_details:
						onActionDetails();
						return true;
					case R.id.action_set_as_wallpaper:
						setAsWallpaper(mDataItem);
						return true;
				}
				return false;
			});
		}
		mMorePopupMenu.show();
	}

	private void setAsWallpaper(GalleryModel dataPicture) {
		new Thread() {
			@Override
			public void run() {
				WallpaperManager wallpaperManager = WallpaperManager.getInstance(requireContext());
				try {
					wallpaperManager.setBitmap(MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), dataPicture.getUri()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void onActionDetails() {
		new DetailDialogFragment().show(getChildFragmentManager(), DetailDialogFragment.TAG);
	}
}
