package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.listeners.VideoListener;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.utils.PictureDiffCallback;
import com.beebapcay.galleryapp.utils.VideoDiffCallback;
import com.bumptech.glide.Glide;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
	private final Context mContext;
	private final List<VideoModel> mDataVideos;
	private final VideoListener mVideoListener;

	public VideosAdapter(Context context, VideoListener videoListener) {
		mContext = context;
		mDataVideos = new ArrayList<>();
		mVideoListener = videoListener;
	}

	@NonNull
	@Override
	public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
	public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
		holder.onBind(mDataVideos.get(position));
		holder.mImageThumbnail.setOnClickListener(v -> mVideoListener.onVideoListener(mDataVideos.get(position), position));
	}

	@Override
	public int getItemCount() {
		return mDataVideos.size();
	}

	public void sortFilter(FilterType filterType) {
		if (filterType == FilterType.DATE)
			Collections.sort(mDataVideos, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));
		else if (filterType == FilterType.NAME)
			Collections.sort(mDataVideos, (o1, o2) -> o1.getName().compareTo(o2.getName()));
		else
			Collections.sort(mDataVideos, (o1, o2) -> (int) (o1.getSize() - o2.getSize()));
	}

	public void loadData(List<VideoModel> dataVideos) {
		final VideoDiffCallback videoDiffCallback = new VideoDiffCallback(mDataVideos, dataVideos);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(videoDiffCallback);

		mDataVideos.clear();
		mDataVideos.addAll(dataVideos);

		diffResult.dispatchUpdatesTo(this);
	}

	static class VideoViewHolder extends RecyclerView.ViewHolder {
		TextView mTextDuration;
		ImageView mImageThumbnail;

		public VideoViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
			mTextDuration = itemView.findViewById(R.id.text_duration);
		}

		public void onBind(VideoModel videoModel) {
			Glide.with(mImageThumbnail.getContext())
					.load(videoModel.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
			String duration = DurationFormatUtils.formatDuration(videoModel.getDuration(), "mm:ss");
			mTextDuration.setText(duration);
		}
	}
}
