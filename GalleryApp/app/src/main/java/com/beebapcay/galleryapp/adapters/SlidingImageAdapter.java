package com.beebapcay.galleryapp.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.models.PictureModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class SlidingImageAdapter extends PagerAdapter {

    private List<PictureModel> mDataPictures;
    private LayoutInflater inflater;
    private Context mContext;

    public SlidingImageAdapter(Context context, List<PictureModel> pictures) {
        this.mContext = context;
        this.mDataPictures = pictures;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mDataPictures.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_slideshow, view, false);

        assert imageLayout != null;
        final ImageView imageView = view.findViewById(R.id.image_slideshow);

        Glide.with(mContext)
                .load(mDataPictures.get(position).getUri())
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
