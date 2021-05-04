package com.beebapcay.galleryapp.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.beebapcay.galleryapp.models.GalleryModel;

import java.util.List;

public class GalleryDiffCallback extends DiffUtil.Callback {
	private List<GalleryModel> oldList;
	private List<GalleryModel> newList;

	public GalleryDiffCallback(List<GalleryModel> oldList, List<GalleryModel> newList) {
		this.oldList = oldList;
		this.newList = newList;
	}

	@Override
	public int getOldListSize() {
		return oldList.size();
	}

	@Override
	public int getNewListSize() {
		return newList.size();
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		return oldList.get(oldItemPosition).getUri() == newList.get(newItemPosition).getUri();
	}
}
