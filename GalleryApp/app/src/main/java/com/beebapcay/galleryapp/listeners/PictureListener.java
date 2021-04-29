package com.beebapcay.galleryapp.listeners;

import com.beebapcay.galleryapp.models.PictureModel;

public interface PictureListener {
	void onPictureItemPressed(PictureModel pictureModel, int position);
	void onPictureItemLongPressed(PictureModel pictureModel, int position);
}
