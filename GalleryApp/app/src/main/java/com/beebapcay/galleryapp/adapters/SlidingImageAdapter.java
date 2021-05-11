package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.models.PictureModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private List<String> mUriPitures;
    private LayoutInflater inflater;
    private Context mContext;

    public SlidingImageAdapter(Context context, List<PictureModel> pictures) {
        mContext = context;
        mUriPitures = new ArrayList<>();
        for (PictureModel picture : pictures)
            mUriPitures.add(picture.getUri().toString());
        inflater = LayoutInflater.from(context);
        Log.d("length", String.valueOf(mUriPitures.size()));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mUriPitures.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_slideshow, view, false);

        assert imageLayout != null;

        ImageView imageView = imageLayout.findViewById(R.id.image_slideshow);

        Glide.with(mContext)
                .load(mUriPitures.get(position))
                .into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
