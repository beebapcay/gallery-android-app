package com.beebapcay.galleryapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PictureModel extends GalleryModel implements Parcelable {
	public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
		@Override
		public PictureModel createFromParcel(Parcel in) {
			return new PictureModel(in);
		}

		@Override
		public PictureModel[] newArray(int size) {
			return new PictureModel[size];
		}
	};

	public PictureModel() {
		super();
	}

	public PictureModel(long id, Uri uri, String name, long size, String path, Date dateModified, int height, int width) {
		super(id, uri, name, size, path, dateModified, height, width);
	}

	protected PictureModel(Parcel in) {
		mId = in.readLong();
		mUri = in.readParcelable(Uri.class.getClassLoader());
		mName = in.readString();
		mSize = in.readLong();
		mPath = in.readString();
		mDateModified = new Date(in.readLong());
		mHeight = in.readInt();
		mWidth = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mId);
		dest.writeParcelable(mUri, flags);
		dest.writeString(mName);
		dest.writeLong(mSize);
		dest.writeString(mPath);
		dest.writeLong(mDateModified.getTime());
		dest.writeInt(mHeight);
		dest.writeInt(mWidth);
	}
}
