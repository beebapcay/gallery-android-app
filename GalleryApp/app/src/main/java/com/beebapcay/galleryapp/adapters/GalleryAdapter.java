package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.listeners.GalleryListener;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.utils.GalleryDiffCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter {
	private static final int TYPE_LAYOUT_PICTURE = 0;
	private static final int TYPE_LAYOUT_VIDEO = 1;

	private final Context mContext;
	private final List<GalleryModel> mDataGallery;
	private final GalleryListener mGalleryListener;

	public GalleryAdapter(Context context, GalleryListener galleryListener) {
		mContext = context;
		mDataGallery = new ArrayList<>();
		mGalleryListener = galleryListener;
	}

	@Override
	public int getItemViewType(int position) {
		if (mDataGallery.get(position) instanceof PictureModel) return TYPE_LAYOUT_PICTURE;
		return TYPE_LAYOUT_VIDEO;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == 0) return new PicturesAdapter.PictureViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				)
		);
		else return new VideosAdapter.VideoViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_video,
						parent,
						false
				)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof PicturesAdapter.PictureViewHolder) {
			((PicturesAdapter.PictureViewHolder) holder).onBind((PictureModel) mDataGallery.get(position));
			((PicturesAdapter.PictureViewHolder) holder).mImageThumbnail
					.setOnClickListener(v -> mGalleryListener.onGalleryClicked(mDataGallery.get(position), position));
		}
		else {
			((VideosAdapter.VideoViewHolder) holder).onBind((VideoModel) mDataGallery.get(position));
			((VideosAdapter.VideoViewHolder) holder).mImageThumbnail
					.setOnClickListener(v -> mGalleryListener.onGalleryClicked(mDataGallery.get(position), position));
		}
	}

	@Override
	public int getItemCount() {
		return mDataGallery.size();
	}

	public void sortFilter(FilterType filterType) {
		if (filterType == FilterType.DATE)
			Collections.sort(mDataGallery, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));
		else if (filterType == FilterType.NAME)
			Collections.sort(mDataGallery, (o1, o2) -> o1.getName().compareTo(o2.getName()));
		else
			Collections.sort(mDataGallery, (o1, o2) -> (int) (o1.getSize() - o2.getSize()));
	}

	public void loadData(List<GalleryModel> dataGallery) {
		final GalleryDiffCallback galleryDiffCallback = new GalleryDiffCallback(mDataGallery, dataGallery);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(galleryDiffCallback);

		mDataGallery.clear();
		mDataGallery.addAll(dataGallery);

		diffResult.dispatchUpdatesTo(this);
	}
}
