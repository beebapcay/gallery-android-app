package com.beebapcay.galleryapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.beebapcay.galleryapp.views.activities.OptionListActivity;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MainQuickOptionButtonFragment extends Fragment {
    private static final String TAG = MainQuickOptionButtonFragment.class.getSimpleName();

    ImageButton mFavouritesButton, mRecentButton, mPeopleButton, mLocationsButton;

    public MainQuickOptionButtonFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_quick_option_button, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFavouritesButton = view.findViewById(R.id.btn_favourite);
        mFavouritesButton.setOnClickListener(v -> onOptionButtonClicked(R.string.title_favorites));

        mRecentButton = view.findViewById(R.id.btn_recent);
        mRecentButton.setOnClickListener(v -> onOptionButtonClicked(R.string.title_recent));

        mPeopleButton = view.findViewById(R.id.btn_people);
        mPeopleButton.setOnClickListener(v -> onOptionButtonClicked(R.string.title_people));

        mLocationsButton = view.findViewById(R.id.btn_location);
        mLocationsButton.setOnClickListener(v -> onOptionButtonClicked(R.string.title_locations));
    }

    private void onOptionButtonClicked(int destination) {
        Intent intent = new Intent(requireActivity(), OptionListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE, destination);
        intent.putExtras(bundle);
        requireActivity().startActivity(intent);
    }
}