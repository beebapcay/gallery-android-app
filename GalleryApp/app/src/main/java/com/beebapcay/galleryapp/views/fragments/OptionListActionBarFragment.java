package com.beebapcay.galleryapp.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;

public class OptionListActionBarFragment extends Fragment {
	private static final String TAG = OptionListActionBarFragment.class.getSimpleName();

	ImageButton mBackButton;
	TextView mTitle, mSummary;

	public OptionListActionBarFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_option_list_action_bar, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mBackButton = view.findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(v -> requireActivity().onBackPressed());

		mTitle = view.findViewById(R.id.text_title);
		mTitle.setText(requireArguments().getString(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE));

		mSummary = view.findViewById(R.id.text_summary);
		mSummary.setText("0 pictures 0 videos");
	}
}
