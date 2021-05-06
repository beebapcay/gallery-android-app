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
import com.beebapcay.galleryapp.configs.ExtraIntentKey;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
public class RecentFragment extends Fragment {
	private static final String TAG = RecentFragment.class.getSimpleName();

	Bundle mBundle;
	RecyclerView mRecyclerView;

	public RecentFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_option_list, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		Bundle bundle = new Bundle();
		bundle.putString(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE, getString(R.string.title_recent));
		getChildFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.add(R.id.view_action_bar, OptionListActionBarFragment.class, bundle)
				.commit();
	}
}

