package com.beebapcay.galleryapp;

import android.app.Application;
import android.content.SharedPreferences;

import com.beebapcay.galleryapp.configs.PrefKey;

public class GalleryApplication extends Application {

	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	@Override
	public void onCreate() {
		super.onCreate();

	}
}
