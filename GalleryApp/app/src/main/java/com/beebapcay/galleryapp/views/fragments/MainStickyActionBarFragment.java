package com.beebapcay.galleryapp.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.configs.PrefKey;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.beebapcay.galleryapp.views.activities.HeroItemActivity;
import com.beebapcay.galleryapp.views.activities.SlideshowActivity;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class MainStickyActionBarFragment extends Fragment {
    private static final String TAG = MainStickyActionBarFragment.class.getSimpleName();

    ImageButton mCameraButton,mMoreButton;
    PopupMenu mMorePopupMenu;
    public MainStickyActionBarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_sticky_action_bar, container, false);
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCameraButton = view.findViewById(R.id.btn_camera);
        mCameraButton.setOnClickListener(v -> onCameraButtonClicked());

        mMoreButton = view.findViewById(R.id.btn_more);
        mMoreButton.setOnClickListener(v -> onMoreButtonClicked());
    }

    private void onCameraButtonClicked() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(intent);
    }

    private void onMoreButtonClicked() {
        mMorePopupMenu = new PopupMenu(getContext(), mMoreButton);
        mMorePopupMenu.getMenuInflater().inflate(R.menu.menu_sticky_action_bar_more, mMorePopupMenu.getMenu());
        mMorePopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_slideshow) {
                    Toast.makeText(getContext(), "Slideshow", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requireActivity(), SlideshowActivity.class);
                    requireActivity().startActivity(intent);
                }
                return true;
            }
        });
        mMorePopupMenu.show();

    }



}