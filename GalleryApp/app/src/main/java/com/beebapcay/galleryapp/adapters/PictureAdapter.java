package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.util.Log;
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
public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureHolder> {
	private final List<PictureModel> mDataPictures;
	private final Context mContext;

	public PictureAdapter(Context context, List<PictureModel> dataPictures) {
		mContext = context;
		mDataPictures = new ArrayList<>(dataPictures);
	}

	@NonNull
	@Override
	public PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new PictureHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				));
	}

	@Override
	public void onBindViewHolder(@NonNull PictureHolder holder, int position) {
		holder.bind(mDataPictures.get(position));
	}

	@Override
	public int getItemCount() {
		return mDataPictures.size();
	}

	class PictureHolder extends RecyclerView.ViewHolder {
		private final ImageView mImageThumbnail;

		public PictureHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
		}

		public void bind(PictureModel pictureModel) {
			Glide.with(mImageThumbnail.getContext())
					.load(pictureModel.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
		}
	}
}
