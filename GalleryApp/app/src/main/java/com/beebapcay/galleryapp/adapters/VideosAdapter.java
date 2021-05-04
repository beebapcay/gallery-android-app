package com.beebapcay.galleryapp.adapters;

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
import com.beebapcay.galleryapp.models.VideoModel;
import com.beebapcay.galleryapp.utils.DurationFormat;
import com.bumptech.glide.Glide;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
	private final Context mContext;
	private final List<VideoModel> mDataVideos;

	public VideosAdapter(Context context, List<VideoModel> dataVideos) {
		mContext = context;
		mDataVideos = new ArrayList<>(dataVideos);
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
	}

	@Override
	public int getItemCount() {
		return mDataVideos.size();
	}


	static class VideoViewHolder extends RecyclerView.ViewHolder {
		private final TextView mTextDuration;
		private final ImageView mImageThumbnail;

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
