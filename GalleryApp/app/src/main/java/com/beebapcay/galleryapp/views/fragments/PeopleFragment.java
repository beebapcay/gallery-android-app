package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.beebapcay.galleryapp.R;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class PeopleFragment extends Fragment {
	private static final String TAG = PeopleFragment.class.getSimpleName();

	Bundle mBundle;
	TextView mTitle, mSummary;
	RecyclerView mRecyclerView;

	public PeopleFragment() {}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_option_list, container,false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mTitle = view.findViewById(R.id.text_title);
		mTitle.setText(getString(R.string.title_people));

		mSummary = view.findViewById(R.id.text_summary);
		mSummary.setText("0 pictures 0 videos");
	}
}

