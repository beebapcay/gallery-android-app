package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.SlidingImageAdapter;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class SlideShowDialogFragment extends DialogFragment {
	public static final String TAG = SlideShowDialogFragment.class.getSimpleName();



	ImageButton mBackButton;

	private List<PictureModel> mDataPictures;
	private MediaViewModelFactory mMediaViewModelFactory;
	private MediaViewModel mMediaViewModel;
	private ViewPager mPager;
	private int mCurrentPage = 0;
	private int mNumPages = 0;
	private PagerAdapter mPagerAdapter;

	public SlideShowDialogFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mMediaViewModel = new ViewModelProvider(requireActivity(), mMediaViewModelFactory).get(MediaViewModel.class);
		mDataPictures = mMediaViewModel.getLiveDataPictures().getValue();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_slideshow_dialog, container, false);
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBackButton = view.findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(v -> dismiss());

		mPager = (ViewPager) view.findViewById(R.id.pager);
		mPagerAdapter = new SlidingImageAdapter(requireContext(), mDataPictures);
		mPager.setAdapter(mPagerAdapter);

		mNumPages = mDataPictures.size();

		final Handler handler = new Handler();
		final Runnable update = () -> {
			if (mCurrentPage == mNumPages) {
				mCurrentPage = 0;
			}
			mPager.setCurrentItem(mCurrentPage++, true);
		};
		Timer swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(update);
			}
		}, 3000, 3000);
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}
