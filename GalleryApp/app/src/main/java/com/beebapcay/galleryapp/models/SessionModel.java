package com.beebapcay.galleryapp.models;

public class SessionModel extends ItemModel{
	private String mAlpha;

	public SessionModel(String alpha, int pos) {
		mAlpha = alpha;
		mPosition = pos;
	}

	public String getAlpha() {
		return mAlpha;
	}

	public void setAlpha(String alpha) {
		mAlpha = alpha;
	}
}
