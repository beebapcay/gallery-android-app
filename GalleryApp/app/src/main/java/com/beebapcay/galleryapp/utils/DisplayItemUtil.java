package com.beebapcay.galleryapp.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.beebapcay.galleryapp.configs.DisplayType;
import com.beebapcay.galleryapp.models.GalleryModel;
import com.beebapcay.galleryapp.models.ItemModel;
import com.beebapcay.galleryapp.models.SessionModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DisplayItemUtil {
	public static List<ItemModel> loadDataItems(List<GalleryModel> dataList, DisplayType displayType) {
		List<ItemModel> dataItems = new ArrayList<>();

		if (displayType == DisplayType.NULL) {
			Collections.sort(dataList, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));
			dataItems.addAll(dataList);
			return dataItems;
		}
		if (displayType == DisplayType.DATE) {
			Collections.sort(dataList, (o1, o2) -> o2.getDateModified().compareTo(o1.getDateModified()));

			if (dataList.size() == 0) return dataItems;
			String date = getDateFormat(dataList.get(0).getDateModified());
			SessionModel session = new SessionModel(date, 0);
			dataItems.add(session);

			int i;
			for (i = 0; i < dataList.size() - 1; i++) {
				dataList.get(i).setPosition(dataItems.size());
				dataItems.add(dataList.get(i));

				String currentDate = getDateFormat(dataList.get(i).getDateModified());
				String nextDate = getDateFormat(dataList.get(i + 1).getDateModified());

				if (!nextDate.equals(currentDate)) {
					session = new SessionModel(nextDate, dataItems.size());
					dataItems.add(session);
				}
			}
			String lastDate = getDateFormat(dataList.get(i).getDateModified());
			if (!lastDate.equals(session.getAlpha())) {
				session = new SessionModel(lastDate, dataItems.size());
				dataItems.add(session);
			}
			dataList.get(i).setPosition(dataItems.size());
			dataItems.add(dataList.get(i));
		}
		if (displayType == DisplayType.LOCATION) {
			Collections.sort(dataList, (o1, o2) -> o1.getLocation().compareTo(o2.getLocation()));
			if (dataList.size() == 0) return dataItems;
			SessionModel session = new SessionModel(dataList.get(0).getLocation(), 0);
			dataItems.add(session);

			int i;
			for (i = 0; i < dataList.size() - 1; i++) {
				dataList.get(i).setPosition(dataItems.size());
				dataItems.add(dataList.get(i));

				String currentLocation = dataList.get(i).getLocation();
				String  nextLocation = dataList.get(i + 1).getLocation();

				if (!nextLocation.equals(currentLocation)) {
					session = new SessionModel(nextLocation, dataItems.size());
					dataItems.add(session);
				}
			}
			String lastLocation = dataList.get(i).getLocation();
			if (!lastLocation.equals(session.getAlpha())) {
				session = new SessionModel(lastLocation, dataItems.size());
				dataItems.add(session);
			}
			dataList.get(i).setPosition(dataItems.size());
			dataItems.add(dataList.get(i));
		}
		Log.d("loadDataItems: ", dataItems.toString());

		return dataItems;
	};

	public static String getDateFormat(Date date) {
		@SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		String today = dateFormat.format(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.roll(Calendar.DATE, false);
		String yesterday = dateFormat.format(calendar.getTime());

		String format = dateFormat.format(date);
		if (format.equals(today)) return "Today";
		if (format.equals(yesterday)) return "Yesterday";
		return format;
	}
}
