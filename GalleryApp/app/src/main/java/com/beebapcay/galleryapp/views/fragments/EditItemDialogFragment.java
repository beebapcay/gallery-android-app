package com.beebapcay.galleryapp.views.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditItemDialogFragment extends DialogFragment{
    public static String TAG = EditItemDialogFragment.class.getSimpleName();

    ImageButton mBackButton, mRotateButton, mCropButton;
    Button mSaveButton;
    CropImageView mCropImageView;

    private GalleryModel mDataItem;
    private HeroItemViewModelFactory mHeroItemViewModelFactory;
    private HeroItemViewModel mHeroItemViewModel;

    public EditItemDialogFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
        mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();

        mBackButton = view.findViewById(R.id.btn_back);
        mBackButton.setOnClickListener(v -> dismiss());

        mCropImageView = view.findViewById(R.id.image_edit);
        mCropImageView.setImageUriAsync(mDataItem.getUri());
        Bitmap bitmap = mCropImageView.getCroppedImage();

        mCropButton = view.findViewById(R.id.btn_crop);
        mCropButton.setOnClickListener(v -> {
            CropImage.activity(mDataItem.getUri())
                    .start(getContext(),this);
        });
    }
}
