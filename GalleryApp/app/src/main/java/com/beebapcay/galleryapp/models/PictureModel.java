package com.beebapcay.galleryapp.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PictureModel implements Parcelable {
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

	private long mId;
	private Uri mUri;
	private String mName;
	private long mSize;

	private Date mDateAdded;
	private Date mDateModified;
	private int mHeight;
	private int mWidth;


	public PictureModel() {
	}

	public PictureModel(long id, Uri uri, String name, long size, Date dateAdded, Date dateModified, int height, int width) {
		mId = id;
		mUri = uri;
		mName = name;
		mSize = size;

		mDateAdded = dateAdded;
		mDateModified = dateModified;
		mHeight = height;
		mWidth = width;
	}

	protected PictureModel(Parcel in) {
		mId = in.readLong();
		mUri = in.readParcelable(Uri.class.getClassLoader());
		mName = in.readString();
		mSize = in.readLong();

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

		dest.writeLong(mDateAdded.getTime());
		dest.writeLong(mDateModified.getTime());
		dest.writeInt(mHeight);
		dest.writeInt(mWidth);
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

	public long getSize() {
		return mSize;
	}

	public void setSize(long size) {
		mSize = size;
	}

	public Date getDateAdded() {
		return mDateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		mDateAdded = dateAdded;
	}

	public Date getDateModified() {
		return mDateModified;
	}

	public void setDateModified(Date dateModified) {
		mDateModified = dateModified;
	}

	public Uri getUri() {
		return mUri;
	}

	public void setUri(Uri uri) {
		mUri = uri;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int height) {
		mHeight = height;
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int width) {
		mWidth = width;
	}
}
