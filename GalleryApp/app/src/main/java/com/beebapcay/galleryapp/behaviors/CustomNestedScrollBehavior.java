package com.beebapcay.galleryapp.behaviors;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;


public class CustomNestedScrollBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
	@Override
	public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
		return target.getId() == R.id.view_recycler_gallery_list;
	}
}
