package com.example.cripz.thereporter;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSLocation extends Service implements LocationListener{

	private final Context context;

	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;

	Location location;

	double latitude;
	double longitude;

	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

	protected LocationManager locationManager;
	protected LocationListener locationListener;

	public GPSLocation(Context context) {
		this.context = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// Define a listener that responds to location updates
			locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					if (location.getLatitude() != 0 && location.getLongitude() != 0){
						((ReportActivity) context).onLocationReceived(location.getLatitude(), location.getLongitude());
						Log.d("GPS", "on location changed");
					}
				}

				public void onStatusChanged(String provider, int status, Bundle extras) {}

				public void onProviderEnabled(String provider) {
					Log.d("GPS", "PROVIDER ENABLED" + provider);
				}

				public void onProviderDisabled(String provider) {
					Log.d("GPS", "PROVIDER DISABLED" + provider);
				}
			};

// Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);

			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

//				if(isGPSEnabled) {
//                    this.canGetLocation = true;
//					if(location == null) {
//						locationManager.requestLocationUpdates(
//								LocationManager.GPS_PROVIDER,
//								MIN_TIME_BW_UPDATES,
//								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//
//						if(locationManager != null) {
//							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//							if(location != null) {
//								latitude = location.getLatitude();
//								longitude = location.getLongitude();
//							}
//						}
//					}
//				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	public void stopListeningForUpdates(){
		if (locationManager != null && locationListener != null){
			Log.e("GPS", "stop listening");
			locationManager.removeUpdates(locationListener);
		}
	}

	public void stopUsingGPS() {
		if(locationManager != null) {
			locationManager.removeUpdates(GPSLocation.this);
		}
	}

	public double getLatitude() {
		if(location != null) {
			latitude = location.getLatitude();
		}
		return latitude;
	}

	public double getLongitude() {
		if(location != null) {
			longitude = location.getLongitude();
		}

		return longitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

		alertDialog.setTitle("GPS is settings");

		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				context.startActivity(intent);
			}
		});

		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
