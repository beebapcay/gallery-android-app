package com.beebapcay.galleryapp.listeners;

import com.beebapcay.galleryapp.models.AlbumModel;

public interface AlbumListener {
	void onAlbumClicked(AlbumModel album, int position);
}
