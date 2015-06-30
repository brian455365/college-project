package com.example.camera_1;

import java.math.BigDecimal;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;


public class MainActivity extends Activity implements LocationListener {

	private Preview camera_view;
	private String Tag = "MainAvtivity";
	
	public static float azimuth,pitch,roll;
	public static String out_longitude,out_latitude,out_Altitude;
//	public static float focal_length;
//	public static String get_time;
	
	Sensor sensor;
	Thread thread_updatelocation;
	LocationManager locationManager;
	LocationListener locationListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		out_longitude="get..";
		out_latitude="get..";
		out_Altitude="get..";
		SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		List<android.hardware.Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensorList.size() > 0) {	
            sensor = sensorList.get(0);	//取得方向感測器
            Toast.makeText(this,"Orientation sensor  present", Toast.LENGTH_SHORT).show();
        }
		else {     	
        	Toast.makeText(this,"Orientation sensor not present", Toast.LENGTH_SHORT).show();
        }
		sensorManager.registerListener(orientationListener,sensor, SensorManager.SENSOR_DELAY_NORMAL,null);	//註冊SensorEventListener
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);		
		
		locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location newLocation) {  
				Double longitude = newLocation.getLongitude() * 1000000; 
				Double latitude = newLocation.getLatitude() * 1000000;
				Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue()); 
				out_longitude = String.valueOf(longitude);
				out_latitude = String.valueOf(latitude);
				Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				out_Altitude = String.valueOf(location.getAltitude());
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
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

		
		
		
		
		
		
//		thread_updatelocation = new thread_UpdateLocation();
//		thread_updatelocation.start();
		
	   	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
    	requestWindowFeature(Window.FEATURE_NO_TITLE); // 啟用視窗的擴展特性。
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 將螢幕設定為全螢幕
    	camera_view = new Preview(this);
    	setContentView(camera_view); 
	}

/*	class thread_UpdateLocation extends Thread {
    	@Override
		public void run() {
			super.run();
			locationListener = new LocationListener() {
				@Override
				public void onLocationChanged(Location newLocation) {  
					Double longitude = newLocation.getLongitude() * 1000000; 
					Double latitude = newLocation.getLatitude() * 1000000;
					Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue()); 
					out_longitude = String.valueOf(longitude);
					out_latitude = String.valueOf(latitude);
					Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					out_Altitude = String.valueOf(location.getAltitude());
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
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
			};
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    	}	
    }
*/	
	
	
	
	
	
	
	
	private SensorEventListener orientationListener = new SensorEventListener() {		
		@Override		
		public void onAccuracyChanged(Sensor arg0, int arg1) {
		}

		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				azimuth = sensorEvent.values[0]; //取得方位角(沿著Z軸旋轉)
				pitch = sensorEvent.values[1];   //取得投擲角(沿著X軸旋轉)
				roll = sensorEvent.values[2];	   //取得滾動角(沿著Y軸旋轉)
//				if(momemtary_roll==1){
//					textView11.setText("azimuth(z) : " + azimuth);
//					textView12.setText("pitch(x) : " + pitch);
//					textView13.setText("roll(y) : " + roll);
//					textView08.setText(Integer.toString(momemtary_roll));
//					momemtary_roll = 0;
//				}
			}
		}    	
    };
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Double longitude = location.getLongitude() * 1000000;
		Double latitude = location.getLatitude() * 1000000;
		Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue());
		out_longitude = String.valueOf(longitude);
		out_latitude = String.valueOf(latitude);
		out_Altitude = String.valueOf(location.getAltitude());
//		textView03.setText("經度"+longitude);
//		textView04.setText("緯度"+latitude);	
//		textView05.setText(String.valueOf(location.getAltitude()));
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
