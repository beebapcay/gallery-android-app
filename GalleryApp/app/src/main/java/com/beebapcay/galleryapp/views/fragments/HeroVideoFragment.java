package com.beebapcay.galleryapp.views.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class HeroVideoFragment extends Fragment {
	private static final String TAG = HeroVideoFragment.class.getSimpleName();

	VideoView mHeroVideo;

	private VideoModel mDataVideo;
	private MediaController mMediaController;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public HeroVideoFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_hero_video, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataVideo = (VideoModel) mHeroItemViewModel.getLiveDataItem().getValue();

		mHeroVideo = view.findViewById(R.id.video_hero_video);
		mMediaController = new MediaController(requireActivity());
		mMediaController.setAnchorView(mHeroVideo);
		mHeroVideo.setVideoURI(mDataVideo.getUri());
		mHeroVideo.setMediaController(mMediaController);
		mHeroVideo.setOnPreparedListener(mp -> {
			mHeroVideo.seekTo(1);
			mp.setOnVideoSizeChangedListener((mp1, width, height) -> mMediaController.setAnchorView(mHeroVideo));
		});
	}
}