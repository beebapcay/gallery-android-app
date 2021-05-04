package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.utils.GalleryDiffCallback;
import com.beebapcay.galleryapp.utils.PictureDiffCallback;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {
	private final Context mContext;
	private final List<PictureModel> mDataPictures;

	public PicturesAdapter(Context context) {
		mContext = context;
		mDataPictures = new ArrayList<>();
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

	public void sortFilter(FilterType filterType) {
		if (filterType == FilterType.DATE)
			Collections.sort(mDataPictures, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));
		else if (filterType == FilterType.NAME)
			Collections.sort(mDataPictures, (o1, o2) -> o1.getName().compareTo(o2.getName()));
		else
			Collections.sort(mDataPictures, (o1, o2) -> (int) (o1.getSize() - o2.getSize()));
	}

	public void loadData(List<PictureModel> dataPictures) {
		final PictureDiffCallback pictureDiffCallback = new PictureDiffCallback(mDataPictures, dataPictures);
		final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(pictureDiffCallback);

		mDataPictures.clear();
		mDataPictures.addAll(dataPictures);

		diffResult.dispatchUpdatesTo(this);
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
