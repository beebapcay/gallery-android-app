package com.beebapcay.galleryapp.models;

import android.net.Uri;

import java.util.Date;

public class GalleryModel {
	protected long mId;
	protected Uri mUri;
	protected String mName;
	protected long mSize;
	protected String mPath;
	protected Date mDateModified;
	protected int mHeight;
	protected int mWidth;

	protected boolean mIsFavourite;
	protected String mLocation;

	public GalleryModel() {
	}

	public GalleryModel(long id, Uri uri, String name, long size, String path, Date dateModified, int height, int width,
						boolean isFavourite, String location) {
		mId = id;
		mUri = uri;
		mName = name;
		mSize = size;
		mPath = path;
		mDateModified = dateModified;
		mHeight = height;
		mWidth = width;

		mIsFavourite = isFavourite;
		mLocation = location;
	}

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
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

	public long getSize() {
		return mSize;
	}

	public void setSize(long size) {
		mSize = size;
	}

	public String getPath() {
		return mPath;
	}

	public void setPath(String path) {
		mPath = path;
	}

	public Date getDateModified() {
		return mDateModified;
	}

	public void setDateModified(Date dateModified) {
		mDateModified = dateModified;
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

	public boolean isFavourite() {
		return mIsFavourite;
	}

	public void setFavourite(boolean favourite) {
		mIsFavourite = favourite;
	}

	public String getLocation() {
		return mLocation;
	}

	public void setLocation(String location) {
		mLocation = location;
	}
}
