package com.beebapcay.galleryapp.listeners;

import com.beebapcay.galleryapp.models.GalleryModel;

public interface GalleryListener {
	void onGalleryClicked(GalleryModel gallery, int position);
}
