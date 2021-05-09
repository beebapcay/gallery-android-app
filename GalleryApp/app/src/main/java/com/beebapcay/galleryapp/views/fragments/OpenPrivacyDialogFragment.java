package com.beebapcay.galleryapp.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beebapcay.galleryapp.R;
import com.beebapcay.galleryapp.configs.ExtraIntentKey;
import com.beebapcay.galleryapp.configs.PrefKey;
import com.beebapcay.galleryapp.configs.PrefName;
import com.beebapcay.galleryapp.views.activities.OptionListActivity;


public class OpenPrivacyDialogFragment extends DialogFragment {
	public static String TAG = OpenPrivacyDialogFragment.class.getSimpleName();

	SharedPreferences mSharedPreferences;
	EditText mPinInput;
	Button mCancelButton, mConfirmButton;
	private String mPin;

	public OpenPrivacyDialogFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSharedPreferences = requireActivity().getSharedPreferences(PrefName.PRIVACY, Context.MODE_PRIVATE);
		mPin = mSharedPreferences.getString(PrefKey.PIN, null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_open_privacy_dialog, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mCancelButton = view.findViewById(R.id.action_cancel);
		mCancelButton.setOnClickListener(v -> dismiss());

		mPinInput = view.findViewById(R.id.input_pin);

		mConfirmButton = view.findViewById(R.id.action_confirm);
		mConfirmButton.setOnClickListener(v -> {
			String pin = mPinInput.getText().toString().trim();
			if (!pin.equals("")) {
				if (mPin == null) {
					mSharedPreferences.edit().putString(PrefKey.PIN, pin).apply();
					Toast.makeText(requireActivity(), "Setup PIN successfully", Toast.LENGTH_SHORT).show();
				}
				else {
					if (pin.equals(mPin)) {
						Intent intent = new Intent(requireActivity(), OptionListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt(ExtraIntentKey.EXTRA_OPTION_GALLERY_LIST_TYPE, R.string.title_privacy);
						intent.putExtras(bundle);
						requireActivity().startActivity(intent);
					}
					else Toast.makeText(requireActivity(), "Wrong PIN", Toast.LENGTH_SHORT).show();
				}
			}
			dismiss();
		});

	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			dialog.getWindow().setGravity(Gravity.CENTER);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		}
	}
}