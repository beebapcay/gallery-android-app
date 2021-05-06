package com.beebapcay.galleryapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.PicturesAdapter;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.listeners.PictureListener;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.beebapcay.galleryapp.views.activities.HeroItemActivity;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PicturesFragment extends Fragment implements PictureListener {
    private static final String TAG = PicturesFragment.class.getSimpleName();

    TextView mDestTitle;
    RecyclerView mRecyclerView;

    private MediaViewModelFactory mMediaViewModelFactory;
    private MediaViewModel mMediaViewModel;
    private PicturesAdapter mPicturesAdapter;

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

        mDestTitle = view.findViewById(R.id.text_title_dest);
        mDestTitle.setText(R.string.title_dest_pictures);

        mRecyclerView = view.findViewById(R.id.view_recycler_gallery_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPicturesAdapter = new PicturesAdapter(requireContext(), this);
        mRecyclerView.setAdapter(mPicturesAdapter);

        mMediaViewModel.getLiveDataPictures().observe(requireActivity(), dataPictures -> {
            mPicturesAdapter.loadData(dataPictures);
        });
    }

    @Override
    public void onPictureListener(PictureModel picture, int position) {
        Intent intent = new Intent(requireActivity(), HeroItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ExtraIntentKey.EXTRA_HERO_ITEM_TYPE, "picture");
        bundle.putParcelable(ExtraIntentKey.EXTRA_HERO_ITEM_DATA, picture);
        intent.putExtras(bundle);
        requireActivity().startActivity(intent);
    }
}