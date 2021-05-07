package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.factories.HeroItemViewModelFactory;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.repositories.MediaDataRepository;
import com.beebapcay.galleryapp.viewmodels.HeroItemViewModel;

import io.reactivex.rxjava3.core.Completable;


public class DeleteItemDialogFragment extends DialogFragment {
	public static String TAG = DeleteItemDialogFragment.class.getSimpleName();

	Button mCancel, mMoveToRecycleBin;

	private GalleryModel mDataItem;
	private HeroItemViewModelFactory mHeroItemViewModelFactory;
	private HeroItemViewModel mHeroItemViewModel;

	public DeleteItemDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHeroItemViewModelFactory = new HeroItemViewModelFactory(MediaDataRepository.getInstance(requireActivity()));
		mHeroItemViewModel = new ViewModelProvider(requireActivity(), mHeroItemViewModelFactory).get(HeroItemViewModel.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_delete_item_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mDataItem = mHeroItemViewModel.getLiveDataItem().getValue();

		mCancel = view.findViewById(R.id.action_cancel);
		mCancel.setOnClickListener(v -> dismiss());

		mMoveToRecycleBin = view.findViewById(R.id.action_move_to_recycle_bin);
		mMoveToRecycleBin.setOnClickListener(v -> {
			mHeroItemViewModel.delete(mDataItem);
			requireActivity().finish();
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}