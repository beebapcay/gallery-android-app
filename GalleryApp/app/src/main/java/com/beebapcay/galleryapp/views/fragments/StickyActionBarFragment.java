package com.beebapcay.galleryapp.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.beebapcay.galleryapp.R;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class StickyActionBarFragment extends Fragment {
    private static final String TAG = StickyActionBarFragment.class.getSimpleName();

    ImageButton mCameraButton, mFilterButton, mMoreButton;
    PopupMenu mFilterPopupMenu, mMorePopupMenu;

    public StickyActionBarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sticky_action_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCameraButton = view.findViewById(R.id.btn_camera);
        mCameraButton.setOnClickListener(v -> onCameraButtonClicked());

        mFilterButton = view.findViewById(R.id.btn_filter);
        mFilterButton.setOnClickListener(v -> onFilterButtonClicked());

        mMoreButton = view.findViewById(R.id.btn_more);
        mMoreButton.setOnClickListener(v -> onMoreButtonClicked());
    }

    private void onCameraButtonClicked() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivity(intent);
    }

    private void onFilterButtonClicked() {
        if (mFilterPopupMenu == null) {
            mFilterPopupMenu = new PopupMenu(requireContext(), mFilterButton);
            mFilterPopupMenu.inflate(R.menu.menu_sticky_action_bar_filter);
        }
        mFilterPopupMenu.show();
    }

    private void onMoreButtonClicked() {
        if (mMorePopupMenu == null) {
            mMorePopupMenu = new PopupMenu(requireContext(), mMoreButton);
            mMorePopupMenu.inflate(R.menu.menu_sticky_action_bar_more);
        }
        mMorePopupMenu.show();
    }

}