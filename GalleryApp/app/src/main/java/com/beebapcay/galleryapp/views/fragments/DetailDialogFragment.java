package com.beebapcay.galleryapp.views.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;

import org.apache.commons.io.FileUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class DetailDialogFragment extends DialogFragment {
	public static String TAG = DetailDialogFragment.class.getSimpleName();

	ImageButton mBackButton;
	TextView mDateModified, mName, mPath, mSize, mDimen;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;


	public DetailDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_detail_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mBackButton = view.findViewById(R.id.btn_back);
		mBackButton.setOnClickListener(v -> dismiss());

		mDateModified = view.findViewById(R.id.text_date_modified);
		@SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy \t\t HH:mm:ss");
		String dateModified = dateFormat.format(mDataItem.getDateModified());
		mDateModified.setText(dateModified);

		mName = view.findViewById(R.id.text_name);
		mName.setText(mDataItem.getName());

		mPath = view.findViewById(R.id.text_path);
		String path = mDataItem.getPath().replace("/storage/emulated/0", "").replace(mDataItem.getName(), "");
		mPath.setText(path);

		mSize = view.findViewById(R.id.text_size);
		String size = FileUtils.byteCountToDisplaySize(mDataItem.getSize());
		mSize.setText(size);

		mDimen = view.findViewById(R.id.text_dimen);
		String dimen = mDataItem.getHeight() + "x" + mDataItem.getWidth();
		mDimen.setText(dimen);
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}