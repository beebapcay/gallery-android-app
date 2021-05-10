package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.DisplayType;
import com.beebapcay.galleryapp.listeners.PictureListener;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.ItemModel;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.models.SessionModel;
import com.beebapcay.galleryapp.utils.DisplayItemUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PicturesAdapter extends RecyclerView.Adapter {
	private static final int TYPE_LAYOUT_SESSION = 0;
	private static final int TYPE_LAYOUT_ITEM = 1;

	private final Context mContext;
	private final List<PictureModel> mDataPictures;
	private final List<ItemModel> mDataItems;
	private final PictureListener mPictureListener;

	public PicturesAdapter(Context context, PictureListener pictureListener) {
		mContext = context;
		mDataItems = new ArrayList<>();
		mDataPictures = new ArrayList<>();
		mPictureListener = pictureListener;
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
		return new PictureViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_picture,
						parent,
						false
				)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof GalleryAdapter.SessionViewHolder)
			((GalleryAdapter.SessionViewHolder) holder).onBind((SessionModel) mDataItems.get(position));
		else {
			((PictureViewHolder) holder).onBind((PictureModel) mDataItems.get(position));
			((PictureViewHolder) holder).mImageThumbnail.setOnClickListener(v -> mPictureListener.onPictureListener((PictureModel) mDataItems.get(position), position));
		}
	}

	@Override
	public int getItemCount() {
		return mDataItems.size();
	}

	public void loadData(List<PictureModel> dataPictures, DisplayType displayType) {
		if (dataPictures == null) return;
		mDataPictures.clear();
		mDataPictures.addAll(dataPictures);
		loadDataItems(displayType);
		notifyDataSetChanged();
	}

	public void loadDataItems(DisplayType displayType) {
		mDataItems.clear();
		List<GalleryModel> dataList = new ArrayList<>(mDataPictures);
		mDataItems.addAll(DisplayItemUtil.loadDataItems(dataList, displayType));
	}

	public boolean isSessionPos(int pos) {
		return mDataItems.get(pos) instanceof SessionModel;
	}

	static class PictureViewHolder extends RecyclerView.ViewHolder {
		ImageView mImageThumbnail, mFavouriteButton;

		public PictureViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
			mFavouriteButton = itemView.findViewById(R.id.btn_favourite);
		}

		public void onBind(PictureModel picture) {
			Glide.with(mImageThumbnail.getContext())
					.load(picture.getUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
			if (picture.isFavourite())
				mFavouriteButton.setVisibility(View.VISIBLE);
		}
	}
}
