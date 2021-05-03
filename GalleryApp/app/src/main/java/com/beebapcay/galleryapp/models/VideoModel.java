package com.beebapcay.galleryapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class VideoModel extends GalleryModel implements Parcelable {
	public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
		@Override
		public VideoModel createFromParcel(Parcel in) {
			return new VideoModel(in);
		}

		@Override
		public VideoModel[] newArray(int size) {
			return new VideoModel[size];
		}
	};

	private long mDuration;

	public VideoModel() {
		super();
	}

	public VideoModel(long id, Uri uri, String name, long size, Date dateAdded, Date dateModified, int height, int width,long duration) {
		super(id, uri, name, size, dateAdded, dateModified, height, width);
		mDuration = duration;
	}

	protected VideoModel(Parcel in) {
		mId = in.readLong();
		mUri = in.readParcelable(Uri.class.getClassLoader());
		mName = in.readString();
		mSize = in.readLong();
		mDuration = in.readLong();
		mDateAdded = new Date(in.readLong());
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
		dest.writeLong(mDuration);
		dest.writeLong(mDateAdded.getTime());
		dest.writeLong(mDateModified.getTime());
		dest.writeInt(mHeight);
		dest.writeInt(mWidth);
	}

	public long getDuration() {
		return mDuration;
	}

	public void setDuration(long duration) {
		mDuration = duration;
	}

}
