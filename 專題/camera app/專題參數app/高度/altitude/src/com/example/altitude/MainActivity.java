package com.example.altitude;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		locationServiceInitial();
	}
	
	private LocationManager lms;
	private void locationServiceInitial() {
		lms = (LocationManager) getSystemService(LOCATION_SERVICE);
		Location location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);	
		getLocation(location);
	}
	
	private void getLocation(Location location) {	
		if(location != null) {
			TextView altitude_txt = (TextView) findViewById(R.id.altitude);
			Double altitude = location.getAltitude();
			altitude_txt.setText(String.valueOf(altitude));		
		}
		else {
			Toast.makeText(this, "can not locate the coordinates", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		getLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
