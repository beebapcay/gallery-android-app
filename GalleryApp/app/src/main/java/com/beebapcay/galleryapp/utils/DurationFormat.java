package com.beebapcay.galleryapp.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;

public class DurationFormat {
	@RequiresApi(api = Build.VERSION_CODES.O)
	public static String getDuration(long millis) {
		Duration duration = Duration.ofMillis(millis);
		long hours = duration.toHours();
		long minutes = duration.minusHours(hours).toMinutes();
		long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
		return (hours == 0 ? "" : hours + ":") + minutes + ":" + seconds;
	}
}
