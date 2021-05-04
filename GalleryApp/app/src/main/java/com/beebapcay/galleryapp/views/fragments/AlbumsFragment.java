package com.beebapcay.galleryapp.views.fragments;

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

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.AlbumsAdapter;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class AlbumsFragment extends Fragment {
    private static final String TAG = AlbumsFragment.class.getSimpleName();

    RecyclerView mRecyclerView;

    private MediaViewModelFactory mMediaViewModelFactory;
    private MediaViewModel mMediaViewModel;
    private AlbumsAdapter mAlbumsAdapter;

    public AlbumsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(requireContext()));
        mMediaViewModel = new ViewModelProvider(requireActivity(), mMediaViewModelFactory).get(MediaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.view_recycler_gallery_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    @Override
    public void onStart() {
        super.onStart();

        mAlbumsAdapter = new AlbumsAdapter(requireContext(), mMediaViewModel.getLiveDataAlbums().getValue());
        mRecyclerView.setAdapter(mAlbumsAdapter);
    }
}