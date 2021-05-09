package com.beebapcay.galleryapp.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class OptionListViewModel extends ViewModel {
	private final MediaDataRepository mMediaDataRepository;
	private final MutableLiveData<List<GalleryModel>> mLiveDataItems = new MutableLiveData<>();

	public OptionListViewModel(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	public void clearData() {
		mLiveDataItems.setValue(new ArrayList<>());
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadFavourites() {
		return mMediaDataRepository.loadFavourites();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadLocations() {
		return mMediaDataRepository.loadLocations();
	}

	public Single<List<GalleryModel>> loadPicturesHaveFace() {
		return mMediaDataRepository.loadPicturesHaveFace();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadPrivacy() {return mMediaDataRepository.loadPrivacy();}


	public MutableLiveData<List<GalleryModel>> getLiveDataItems() {
		return mLiveDataItems;
	}
}
