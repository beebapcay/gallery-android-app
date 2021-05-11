package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.adapters.SlidingImageAdapter;
import com.beebapcay.galleryapp.factories.MediaViewModelFactory;
import com.beebapcay.galleryapp.models.PictureModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.MediaViewModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SlideShowDialogFragment extends DialogFragment {
    public static final String TAG = SlideShowDialogFragment.class.getSimpleName();

    ImageButton mBackButton;

    private List<PictureModel> mDataPictures;
    private MediaViewModelFactory mMediaViewModelFactory;
    private MediaViewModel mMediaViewModel;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    public SlideShowDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaViewModelFactory = new MediaViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
        mMediaViewModel = new ViewModelProvider(requireActivity(), mMediaViewModelFactory).get(MediaViewModel.class);
        mDataPictures = (List<PictureModel>) mMediaViewModel.getLiveDataPictures().getValue();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slideshow_dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBackButton = view.findViewById(R.id.btn_back);
        mBackButton.setOnClickListener(v -> dismiss());

        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(getContext(), mDataPictures));

        CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5*density);

        NUM_PAGES = mDataPictures.size();

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
