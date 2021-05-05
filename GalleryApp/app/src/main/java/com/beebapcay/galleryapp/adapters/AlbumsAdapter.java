package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.listeners.AlbumListener;
import com.beebapcay.galleryapp.models.AlbumModel;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder> implements AlbumListener {
	private Context mContext;
	private List<AlbumModel> mDataAlbums;

	public AlbumsAdapter(Context context, List<AlbumModel> dataAlbums) {
		mContext = context;
		mDataAlbums = new ArrayList<>(dataAlbums);
	}

	@NonNull
	@Override
	public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new AlbumViewHolder(
				LayoutInflater.from(parent.getContext()).inflate(
						R.layout.item_album,
						parent,
						false
				)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
		holder.onBind(mDataAlbums.get(position));
		holder.mImageThumbnail.setOnClickListener(v -> onAlbumClicked(mDataAlbums.get(position), position));
	}

	@Override
	public int getItemCount() {
		return mDataAlbums.size();
	}

	@Override
	public void onAlbumClicked(AlbumModel album, int position) {
		Toast.makeText(mContext, album.getUri().toString(), Toast.LENGTH_SHORT).show();
	}


	static class AlbumViewHolder extends RecyclerView.ViewHolder {
		RoundedImageView mImageThumbnail;
		TextView mAlbumName, mAlbumQuantity;

		public AlbumViewHolder(@NonNull View itemView) {
			super(itemView);
			mImageThumbnail = itemView.findViewById(R.id.image_thumbnail);
			mAlbumName = itemView.findViewById(R.id.text_album_name);
			mAlbumQuantity = itemView.findViewById(R.id.text_album_quantity);
		}

		public void onBind(AlbumModel albumModel) {
			Glide.with(mImageThumbnail.getContext())
					.load(albumModel.getImageThumbnailUri())
					.placeholder(R.drawable.ic_placeholder)
					.into(mImageThumbnail);
			mImageThumbnail.setBackgroundResource(android.R.color.transparent);
			mAlbumName.setText(albumModel.getName());
			mAlbumQuantity.setText(String.valueOf(albumModel.getQuantity()));
		}
	}
}
