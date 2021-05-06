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
import android.widget.TextView;
import android.widget.Toast;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.AlbumsAdapter;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.listeners.AlbumListener;
import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.beebapcay.galleryapp.views.activities.AlbumListActivity;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class AlbumsFragment extends Fragment implements AlbumListener {
    private static final String TAG = AlbumsFragment.class.getSimpleName();

    TextView mDestTitle;
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

        mDestTitle = view.findViewById(R.id.text_title_dest);
        mDestTitle.setText(R.string.title_dest_albums);

        mRecyclerView = view.findViewById(R.id.view_recycler_gallery_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAlbumsAdapter = new AlbumsAdapter(requireContext(), this);
        mRecyclerView.setAdapter(mAlbumsAdapter);

        mMediaViewModel.getLiveDataAlbums().observe(requireActivity(), dataAlbums -> {
            mAlbumsAdapter.loadData(dataAlbums);
        });
    }

    @Override
    public void onAlbumClicked(AlbumModel album, int position) {
        Intent intent = new Intent(requireActivity(), AlbumListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(ExtraIntentKey.EXTRA_ALBUM_DATA, album);
        intent.putExtras(bundle);
        requireActivity().startActivity(intent);
    }
}