package com.beebapcay.galleryapp.viewmodels;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;

public class HeroItemViewModel extends ViewModel {
	private final MediaDataRepository mMediaDataRepository;
	private final MutableLiveData<GalleryModel> mLiveDataItem = new MutableLiveData<>();
	private final MutableLiveData<Boolean> mLiveIsFavourite = new MutableLiveData<>();
	private final MutableLiveData<Uri> mUriCrop = new MutableLiveData<>();
	private final MutableLiveData<Bitmap> mFilterBitmap = new MutableLiveData<>();

	public HeroItemViewModel(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	public MutableLiveData<GalleryModel> getLiveDataItem() {
		return mLiveDataItem;
	}

	public void delete(GalleryModel dataItem) {
		mMediaDataRepository.deleteItem(dataItem);
	}

	public MutableLiveData<Boolean> getLiveIsFavourite() {
		return mLiveIsFavourite;
	}

	public MutableLiveData<Uri> getUriCrop() {
		return mUriCrop;
	}

	public MutableLiveData<Bitmap> getFilterBitmap() {
		return mFilterBitmap;
	}

	public void saveCropPicture(Uri uriCrop, String name, String des) {
		mMediaDataRepository.createPictureFromUri(uriCrop, name, des);
	}

	public void saveCopyPicture(Uri uri, String name, String des) {
		mMediaDataRepository.createPictureFromUri(uri, name, des);
	}

	public void saveFilterPicture(Bitmap filterBitmap, String name, String des) {
		mMediaDataRepository.createPictureFromBitmap(filterBitmap, name, des);
	}
}
