package com.beebapcay.galleryapp.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AlbumListViewModel extends ViewModel {
	private final MediaDataRepository mMediaDataRepository;
	private final MutableLiveData<List<GalleryModel>> mLiveDataGallery = new MutableLiveData<>();


	public AlbumListViewModel(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	public void clearData() {
		mLiveDataGallery.setValue(new ArrayList<>());
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<GalleryModel>> loadGallery(AlbumModel dataAlbum) {
		return mMediaDataRepository.loadGalleryFromAlbum(dataAlbum);
	}

	public MutableLiveData<List<GalleryModel>> getLiveDataGallery() {
		return mLiveDataGallery;
	}
}
