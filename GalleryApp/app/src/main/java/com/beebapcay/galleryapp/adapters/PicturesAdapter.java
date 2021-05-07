package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.FilterType;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.listeners.PictureListener;
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
	private final PictureListener mPictureListener;

	public PicturesAdapter(Context context, PictureListener pictureListener) {
		mContext = context;
		mDataPictures = new ArrayList<>();
		mPictureListener = pictureListener;
	}

	@NonNull
	@Override
	public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new PictureViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				), mContext
		);
	}

	@Override
	public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
		holder.onBind(mDataPictures.get(position));
		holder.mImageThumbnail.setOnClickListener(v -> mPictureListener.onPictureListener(mDataPictures.get(position), position));
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
		mDataPictures.clear();
		mDataPictures.addAll(dataPictures);
		notifyDataSetChanged();
	}

	static class PictureViewHolder extends RecyclerView.ViewHolder {
		Context mContext;
		SharedPreferences mSharedPreferences;
		ImageView mImageThumbnail, mFavouriteButton;

		public PictureViewHolder(@NonNull View itemView, Context context) {
			super(itemView);
			mContext = context;
			mSharedPreferences = mContext.getSharedPreferences(PrefName.FAVOURITES, Context.MODE_PRIVATE);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
			mFavouriteButton = itemView.findViewById(R.id.btn_favourite);
		}

		public void onBind(PictureModel pictureModel) {
			Glide.with(mImageThumbnail.getContext())
					.load(pictureModel.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
			boolean isFavourite = mSharedPreferences.getBoolean(String.valueOf(pictureModel.getId()), false);
			if (isFavourite)
				mFavouriteButton.setVisibility(View.VISIBLE);
		}
	}
}
