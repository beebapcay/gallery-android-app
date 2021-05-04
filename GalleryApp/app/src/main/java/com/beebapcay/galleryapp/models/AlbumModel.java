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

	private Uri mUri;
	private String mName;
	private Uri mImageThumbnailUri;
	private int mQuantity;

	public AlbumModel() {
	}

	public AlbumModel(Uri uri, String name, Uri imageThumbnailUri, int quantity) {
		mUri = uri;
		mName = name;
		mImageThumbnailUri = imageThumbnailUri;
		mQuantity = quantity;
	}

	protected AlbumModel(Parcel in) {
		mUri = in.readParcelable(Uri.class.getClassLoader());
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
		dest.writeParcelable(mUri, flags);
		dest.writeString(mName);
		dest.writeParcelable(mImageThumbnailUri, flags);
		dest.writeInt(mQuantity);
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri uri) {
		mUri = uri;
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
