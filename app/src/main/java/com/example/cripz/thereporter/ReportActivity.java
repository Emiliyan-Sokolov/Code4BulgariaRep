package com.example.cripz.thereporter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class ReportActivity extends ActionBarActivity {

	GPSLocation gps;
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

		if(gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			while(latitude == 0 && longitude == 0){
				gps = new GPSLocation(ReportActivity.this);
				latitude = gps.getLatitude();
				longitude = gps.getLongitude();
			}

			String address = getAddress(Double.toString(latitude), Double.toString(longitude));

			Toast.makeText(
					getApplicationContext(),
					"Your Location is -\nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
		} else {
			gps.showSettingsAlert();
		}
	}

	public String getAddress(String lat, String lon)
	{
		String address = "";
		String source = "";
		BufferedReader in = null;
		String line;

		String MAPS_API = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";

		try {
			URL url = new URL(MAPS_API + lat + ',' + lon);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = in.readLine()) != null) {
				source += line;
			}
		} catch (Exception e) {
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
}
