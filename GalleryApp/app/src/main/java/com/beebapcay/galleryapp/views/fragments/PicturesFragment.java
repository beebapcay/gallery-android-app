package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.PictureAdapter;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PicturesFragment extends Fragment {
    private static final String TAG = PicturesFragment.class.getSimpleName();

    RecyclerView mRecyclerView;

    private MediaViewModelFactory mMediaViewModelFactory;
    private MediaViewModel mMediaViewModel;
    private PictureAdapter mPictureAdapter;

    public PicturesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(requireContext()));
        mMediaViewModel = new ViewModelProvider(requireActivity(), mMediaViewModelFactory).get(MediaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pictures, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.view_recycler_gallery_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }


    @Override
    public void onStart() {
        super.onStart();

        mPictureAdapter = new PictureAdapter(requireContext(), mMediaViewModel.getLiveDataPictures().getValue());
        mRecyclerView.setAdapter(mPictureAdapter);
    }
}