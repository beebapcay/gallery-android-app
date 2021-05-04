package com.beebapcay.galleryapp.viewmodels;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.beebapcay.galleryapp.models.AlbumModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MediaViewModel extends ViewModel {
	private final MediaDataRepository mMediaDataRepository;
	private final MutableLiveData<List<PictureModel>> mLiveDataPictures = new MutableLiveData<>();
	private final MutableLiveData<List<VideoModel>> mLiveDataVideos = new MutableLiveData<>();
	private final MutableLiveData<List<AlbumModel>> mLiveDataAlbums = new MutableLiveData<>();

	public MediaViewModel(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	public Single<List<PictureModel>> loadPictures() {
		return mMediaDataRepository.loadPictures();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<VideoModel>> loadVideos() {
		return mMediaDataRepository.loadVideos();
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public Single<List<AlbumModel>> loadAlbums() {
		return mMediaDataRepository.loadAlbums();
	}

	public MutableLiveData<List<PictureModel>> getLiveDataPictures() {
		return mLiveDataPictures;
	}

	public MutableLiveData<List<VideoModel>> getLiveDataVideos() {
		return mLiveDataVideos;
	}

	public MutableLiveData<List<AlbumModel>> getLiveDataAlbums() {
		return mLiveDataAlbums;
	}
}
