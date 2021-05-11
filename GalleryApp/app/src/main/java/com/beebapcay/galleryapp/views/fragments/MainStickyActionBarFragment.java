package com.beebapcay.galleryapp.views.fragments;

import android.annotation.SuppressLint;
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
import android.widget.Toast;


import com.beebapcay.galleryapp.R;

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

    @SuppressLint("NonConstantResourceId")
    private void onMoreButtonClicked() {
        mMorePopupMenu = new PopupMenu(getContext(), mMoreButton);
        mMorePopupMenu.getMenuInflater().inflate(R.menu.menu_sticky_action_bar_more, mMorePopupMenu.getMenu());
        mMorePopupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_slideshow:
                    Toast.makeText(getContext(), "Start slideshow", Toast.LENGTH_SHORT).show();
                    new SlideShowDialogFragment().show(getChildFragmentManager(), SlideShowDialogFragment.TAG);
                    return true;
                case R.id.action_privacy:
                    new OpenPrivacyDialogFragment().show(getParentFragmentManager(), OpenPrivacyDialogFragment.TAG);
                    return true;
            }
            return false;
        });
        mMorePopupMenu.show();

    }



}