package com.example.cripz.thereporter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ReportActivity extends ActionBarActivity {
	private GPSLocation gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_report, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public void getLocation(View view) {
		gps = new GPSLocation(ReportActivity.this);

//		if(gps.canGetLocation()) {
//			Bundle executionRes = new Bundle();
//
//			final Runnable beeper = new Runnable() {
//				public void run() {
//					Neshto locationTask = new Neshto(gps, ReportActivity.this);
//					locationTask.execute();
//				};
//			};
//			execHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
//		} else {
//			gps.showSettingsAlert();
//		}
	}

	public String getAddress(String lat, String lon)
	{
		String address = "";
		String source = "";
		BufferedReader in = null;
		String line;

		String MAPS_API = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

		try {
			Document doc = Jsoup.connect(MAPS_API + lat + ',' + lon).get();
			String src = doc.html();

			address = src.split("\"formatted_address\" : \"")[1].split("\n")[0];
		} catch (IOException e)	{
			source = "Unexpected error";
		}

		if(source != "Unexpected error") {
			address = source.split("\"formatted_address\" : \"")[1].split("\n")[0];
			Toast.makeText(
					getApplicationContext(),
					address, Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(
					getApplicationContext(),
					"It's fucked m8", Toast.LENGTH_LONG).show();
		}

		return address;
	}

	public void onLocationReceived(double lat, double lon) {
		String address = getAddress(Double.toString(lat), Double.toString(lon));
		gps.stopListeningForUpdates();
		Toast.makeText(
				getApplicationContext(),
				address, Toast.LENGTH_LONG).show();
	}
}
