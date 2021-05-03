package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.models.PictureModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {
	private final Context mContext;
	private final List<PictureModel> mDataPictures;

	public PicturesAdapter(Context context, List<PictureModel> dataPictures) {
		mContext = context;
		mDataPictures = new ArrayList<>(dataPictures);
	}

	@NonNull
	@Override
	public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new PictureViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
		holder.onBind(mDataPictures.get(position));
	}

	@Override
	public int getItemCount() {
		return mDataPictures.size();
	}

	static class PictureViewHolder extends RecyclerView.ViewHolder {
		private final ImageView mImageThumbnail;

		public PictureViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
		}

		public void onBind(PictureModel pictureModel) {
			Glide.with(mImageThumbnail.getContext())
					.load(pictureModel.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
		}
	}
}
