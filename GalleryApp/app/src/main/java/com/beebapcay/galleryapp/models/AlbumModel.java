package com.beebapcay.galleryapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class AlbumModel implements Parcelable {
	public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
		@Override
		public AlbumModel createFromParcel(Parcel in) {
			return new AlbumModel(in);
		}

		@Override
		public AlbumModel[] newArray(int size) {
			return new AlbumModel[size];
		}
	};

	private long mId;
	private String mName;
	private Uri mImageThumbnailUri;
	private int mQuantity;

	public AlbumModel() {
	}

	public AlbumModel(long id, String name, Uri imageThumbnailUri, int quantity) {
		mId = id;
		mName = name;
		mImageThumbnailUri = imageThumbnailUri;
		mQuantity = quantity;
	}

	protected AlbumModel(Parcel in) {
		mId = in.readLong();
		mName = in.readString();
		mImageThumbnailUri = in.readParcelable(Uri.class.getClassLoader());
		mQuantity = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeString(mName);
		dest.writeParcelable(mImageThumbnailUri, flags);
		dest.writeInt(mQuantity);
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public Uri getImageThumbnailUri() {
		return mImageThumbnailUri;
	}

	public void setImageThumbnailUri(Uri imageThumbnailUri) {
		mImageThumbnailUri = imageThumbnailUri;
	}

	public int getQuantity() {
		return mQuantity;
	}

	public void setQuantity(int quantity) {
		mQuantity = quantity;
	}

	public void increaseQuantity(int amount) {
		mQuantity += amount;
	}
}
