package com.beebapcay.galleryapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.PicturesAdapter;
import com.beebapcay.galleryapp.adapters.VideosAdapter;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.listeners.VideoListener;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.beebapcay.galleryapp.views.activities.HeroItemActivity;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class VideosFragment extends Fragment implements VideoListener {
    private static final String TAG = VideosFragment.class.getSimpleName();

    TextView mDestTitle;
    RecyclerView mRecyclerView;

    private MediaViewModelFactory mMediaViewModelFactory;
    private MediaViewModel mMediaViewModel;
    private VideosAdapter mVideosAdapter;

    public VideosFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(requireContext()));
        mMediaViewModel = new ViewModelProvider(requireActivity(), mMediaViewModelFactory).get(MediaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDestTitle = view.findViewById(R.id.text_title_dest);
        mDestTitle.setText(R.string.title_dest_videos);

        mRecyclerView = view.findViewById(R.id.view_recycler_gallery_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mVideosAdapter = new VideosAdapter(requireContext(), this);
        mRecyclerView.setAdapter(mVideosAdapter);

        mMediaViewModel.getLiveDataVideos().observe(requireActivity(), dataVideos -> {
            mVideosAdapter.loadData(dataVideos);
            mVideosAdapter.sortFilter(FilterType.DATE);
            mRecyclerView.smoothScrollToPosition(0);
        });
    }

    @Override
    public void onVideoListener(VideoModel video, int position) {
        Intent intent = new Intent(requireActivity(), HeroItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE, "video");
        bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, video);
        intent.putExtras(bundle);
        requireActivity().startActivity(intent);
    }
}