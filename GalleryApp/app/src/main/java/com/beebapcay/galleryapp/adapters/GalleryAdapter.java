package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.DisplayType;
import com.beebapcay.galleryapp.listeners.GalleryListener;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.ItemModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.SessionModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.utils.DisplayItemUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter {
	private static final int TYPE_LAYOUT_SESSION = 0;
	private static final int TYPE_LAYOUT_PICTURE = 1;
	private static final int TYPE_LAYOUT_VIDEO = 2;

	private final Context mContext;
	private final List<GalleryModel> mDataGallery;
	private final List<ItemModel> mDataItems;
	private final GalleryListener mGalleryListener;

	public GalleryAdapter(Context context, GalleryListener galleryListener) {
		mContext = context;
		mDataItems = new ArrayList<>();
		mDataGallery = new ArrayList<>();
		mGalleryListener = galleryListener;
	}

	@Override
	public int getItemViewType(int position) {
		if (mDataItems.get(position) instanceof SessionModel) return TYPE_LAYOUT_SESSION;
 		if (mDataItems.get(position) instanceof PictureModel) return TYPE_LAYOUT_PICTURE;
		return TYPE_LAYOUT_VIDEO;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == TYPE_LAYOUT_SESSION) return new SessionViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_session,
						parent,
						false
				)
		);
		if (viewType == TYPE_LAYOUT_PICTURE) return new PicturesAdapter.PictureViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				)
		);
		return new VideosAdapter.VideoViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_video,
						parent,
						false
				)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof SessionViewHolder)
			((SessionViewHolder) holder).onBind((SessionModel) mDataItems.get(position));
		else if (holder instanceof PicturesAdapter.PictureViewHolder) {
			((PicturesAdapter.PictureViewHolder) holder).onBind((PictureModel) mDataItems.get(position));
			((PicturesAdapter.PictureViewHolder) holder).mImageThumbnail
					.setOnClickListener(v -> mGalleryListener.onGalleryClicked((PictureModel) mDataItems.get(position), position));
		} else {
			((VideosAdapter.VideoViewHolder) holder).onBind((VideoModel) mDataItems.get(position));
			((VideosAdapter.VideoViewHolder) holder).mImageThumbnail
					.setOnClickListener(v -> mGalleryListener.onGalleryClicked((VideoModel) mDataItems.get(position), position));
		}
	}

	@Override
	public int getItemCount() {
		return mDataItems.size();
	}

	public void loadData(List<GalleryModel> dataGallery, DisplayType displayType) {
		if (dataGallery == null) return;
		mDataGallery.clear();
		mDataGallery.addAll(dataGallery);
		loadDataItems(displayType);
		notifyDataSetChanged();
	}

	public void loadDataItems(DisplayType displayType) {
		mDataItems.clear();
		mDataItems.addAll(DisplayItemUtil.loadDataItems(mDataGallery, displayType));
	}

	public boolean isSessionPos(int pos) {
		return mDataItems.get(pos) instanceof SessionModel;
	}

	static class SessionViewHolder extends RecyclerView.ViewHolder {
		TextView mSession;

		public SessionViewHolder(@NonNull View itemView) {
			super(itemView);

			mSession = itemView.findViewById(R.id.text_session);
		}

		public void onBind(SessionModel session) {
			mSession.setText(session.getAlpha());
		}
	}
}
