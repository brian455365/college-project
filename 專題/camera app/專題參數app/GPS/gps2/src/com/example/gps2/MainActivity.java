package com.example.gps2;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {  // 實做 LocationListener介面

	private LocationManager locationManager;
	private LocationListener locationListener;
	TextView textView01;
	TextView textView02;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView01 = (TextView)findViewById(R.id.TextView01);   // findViewById : 找尋介面元件
		textView02 = (TextView)findViewById(R.id.TextView02);
		UpdateLocation();
	}

	
	public void UpdateLocation(){
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		/*LocationManager 
		: 處理相關地理位置的問題
		: 不能直接實體化,只能透過以下方法 : LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		*/
		locationListener = new LocationListener() {
			/*LocationListener
			: 利用於當位置改變時通知LocationManager
			: 利用reuqestLocationUpdates(String , long , float , LocationListener)方法將監聽器註冊到 LocationManager
			*/
			@Override
			public void onLocationChanged(Location newLocation) {  //當位置改變時呼叫此方法
				Double longitude = newLocation.getLongitude() * 1000000;   // getLongitude() , getLatitude(): Location類別的內建方法
				Double latitude = newLocation.getLatitude() * 1000000;
				Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue());  // intValue() : 將物件轉成整數,Object類別底下的子類別的方法
				textView01.setText("經度"+longitude);  //  setText() : 設置text 
				textView02.setText("緯度"+latitude);
			}
			@Override
			public void onProviderDisabled(String provider) {  //當provider關閉時呼叫此方法
				// TODO Auto-generated method stub			
			}
			@Override
			public void onProviderEnabled(String provider) {  //當provider啟用時呼叫此方法
				// TODO Auto-generated method stub				
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {  //當provider狀態改變時呼叫此方法
				// TODO Auto-generated method stub			
			} 				
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);  // GPS_PROVIDER : LocationManager底下的provider
	}																								   //              : This provider determines location using satellites. Depending on conditions, this provider may take a while to return a location fix. Requires the permission ACCESS_FINE_LOCATION
																									   // 所有provider都回傳string
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {     // 實做 LocationListener介面之後需要override的method
		// TODO Auto-generated method stub
		Double longitude = location.getLongitude() * 1000000;
		Double latitude = location.getLatitude() * 1000000;
		Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue());
		textView01.setText("經度"+longitude);
		textView02.setText("緯度"+latitude);
	}

	@Override
	public void onProviderDisabled(String provider) { // 實做 LocationListener介面之後需要override的method
		// TODO Auto-generated method stub	
	}

	@Override
	public void onProviderEnabled(String provider) { // 實做 LocationListener介面之後需要override的method
		// TODO Auto-generated method stub	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { // 實做 LocationListener介面之後需要override的method
		// TODO Auto-generated method stub		
	}

}
