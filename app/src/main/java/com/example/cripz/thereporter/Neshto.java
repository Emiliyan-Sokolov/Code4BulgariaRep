package com.example.cripz.thereporter;

import android.os.AsyncTask;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Created by gigo on 7.6.2015 ã..
 */
public class Neshto extends AsyncTask<Void, Void, Bundle>{
	GPSLocation gps;
	WeakReference<ReportActivity> activityRef;

	public Neshto(GPSLocation gps, ReportActivity activity) {
		this.activityRef = new WeakReference<ReportActivity>(activity);
		this.gps = gps;
	}

	@Override
	protected Bundle doInBackground(Void... voids) {

		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();

		Bundle result = new Bundle();
		if(latitude != 0 && longitude != 0) {
			result.putDouble("lat", latitude);
			result.putDouble("lon", longitude);
		}

		return result;
	}

	@Override
	public void onPostExecute(Bundle taskResult){
		ReportActivity activity = activityRef.get();
		if (activity != null && taskResult.size() != 0){
			activity.onLocationReceived(taskResult.getDouble("lat"), taskResult.getDouble("lon"));
		}
	}
}
