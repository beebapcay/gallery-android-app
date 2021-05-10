package com.beebapcay.galleryapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.DisplayType;
import com.beebapcay.galleryapp.listeners.VideoListener;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.ItemModel;
import com.beebapcay.galleryapp.models.SessionModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.utils.DisplayItemUtil;
import com.bumptech.glide.Glide;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class VideosAdapter extends RecyclerView.Adapter {
	private static final int TYPE_LAYOUT_SESSION = 0;
	private static final int TYPE_LAYOUT_ITEM = 1;

	private final Context mContext;
	private final List<VideoModel> mDataVideos;
	private final List<ItemModel> mDataItems;
	private final VideoListener mVideoListener;

	public VideosAdapter(Context context, VideoListener videoListener) {
		mContext = context;
		mDataItems = new ArrayList<>();
		mDataVideos = new ArrayList<>();
		mVideoListener = videoListener;
	}

	@Override
	public int getItemViewType(int position) {
		if (mDataItems.get(position) instanceof SessionModel) return TYPE_LAYOUT_SESSION;
		else return TYPE_LAYOUT_ITEM;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (viewType == TYPE_LAYOUT_SESSION)
			return new GalleryAdapter.SessionViewHolder(
					LayoutInflater.from(parent.getContext()).inflate(
							R.layout.item_session,
							parent,
							false
					)
			);
		return new VideoViewHolder(
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
		if (holder instanceof GalleryAdapter.SessionViewHolder)
			((GalleryAdapter.SessionViewHolder) holder).onBind((SessionModel) mDataItems.get(position));
		else {
			((VideoViewHolder) holder).onBind((VideoModel) mDataItems.get(position));
			((VideoViewHolder) holder).mImageThumbnail.setOnClickListener(v -> mVideoListener.onVideoListener((VideoModel) mDataItems.get(position), position));
		}
	}

	@Override
	public int getItemCount() {
		return mDataItems.size();
	}

	public void loadData(List<VideoModel> dataVideos, DisplayType displayType) {
		if (dataVideos == null) return;
		mDataVideos.clear();
		mDataVideos.addAll(dataVideos);
		loadDataItems(displayType);
		notifyDataSetChanged();
	}

	public void loadDataItems(DisplayType displayType) {
		mDataItems.clear();
		List<GalleryModel> dataList = new ArrayList<>(mDataVideos);
		mDataItems.addAll(DisplayItemUtil.loadDataItems(dataList, displayType));
	}

	public boolean isSessionPos(int pos) {
		return mDataItems.get(pos) instanceof SessionModel;
	}

	static class VideoViewHolder extends RecyclerView.ViewHolder {
		TextView mTextDuration;
		ImageView mImageThumbnail, mFavouriteButton;

		public VideoViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
			mTextDuration = itemView.findViewById(R.id.text_duration);
			mFavouriteButton = itemView.findViewById(R.id.btn_favourite);
		}

		public void onBind(VideoModel video) {
			Glide.with(mImageThumbnail.getContext())
					.load(video.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
			String duration = DurationFormatUtils.formatDuration(video.getDuration(), "mm:ss");
			mTextDuration.setText(duration);
			if (video.isFavourite())
				mFavouriteButton.setVisibility(View.VISIBLE);
		}
	}
}
