package com.beebapcay.galleryapp.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.repositories.MediaDataRepository;

import java.lang.reflect.InvocationTargetException;

public class HeroItemViewModelFactory  implements ViewModelProvider.Factory {
	private final MediaDataRepository mMediaDataRepository;

	public HeroItemViewModelFactory(MediaDataRepository mediaDataRepository) {
		mMediaDataRepository = mediaDataRepository;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		try {
			return modelClass.getConstructor(MediaDataRepository.class).newInstance(mMediaDataRepository);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Unknown ViewModel class");
	}
}
