package com.beebapcay.galleryapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;

public class HeroItemViewModel extends ViewModel {
	private final MediaDataRepository mMediaDataRepository;
	private final MutableLiveData<GalleryModel> mLiveDataItem = new MutableLiveData<>();

	public HeroItemViewModel(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	public MutableLiveData<GalleryModel> getLiveDataItem() {
		return mLiveDataItem;
	}

	public void delete(GalleryModel dataItem) {
		mMediaDataRepository.deleteItem(dataItem);
	}
}
