package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter {
	private final Context mContext;
	private final List<GalleryModel> mDataGallery;

	public GalleryAdapter(Context context, List<PictureModel> dataPictures, List<VideoModel> dataVideos) {
		mContext = context;
		mDataGallery = new ArrayList<>();
		mDataGallery.addAll(dataPictures);
		mDataGallery.addAll(dataVideos);
	}

	@Override
	public int getItemViewType(int position) {
		boolean s = (mDataGallery.get(position) instanceof PictureModel);
		if (s == true) return 0;
		else return 1;
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

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof PicturesAdapter.PictureViewHolder) ((PicturesAdapter.PictureViewHolder) holder).onBind((PictureModel) mDataGallery.get(position));
		else ((VideosAdapter.VideoViewHolder) holder).onBind((VideoModel) mDataGallery.get(position));
	}

	@Override
	public int getItemCount() {
		return mDataGallery.size();
	}

	public void sortedByDate(boolean increase) {
		if (increase)
			Collections.sort(mDataGallery, (o1, o2) -> o1.getDateModified().compareTo(o2.getDateModified()));
		else
			Collections.sort(mDataGallery, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));
	}
}
