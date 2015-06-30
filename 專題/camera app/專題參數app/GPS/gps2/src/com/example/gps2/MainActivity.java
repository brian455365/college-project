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

public class MainActivity extends Activity implements LocationListener {  // �갵 LocationListener����

	private LocationManager locationManager;
	private LocationListener locationListener;
	TextView textView01;
	TextView textView02;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView01 = (TextView)findViewById(R.id.TextView01);   // findViewById : ��M��������
		textView02 = (TextView)findViewById(R.id.TextView02);
		UpdateLocation();
	}

	
	public void UpdateLocation(){
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 
		/*LocationManager 
		: �B�z�����a�z��m�����D
		: ���ઽ�������,�u��z�L�H�U��k : LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		*/
		locationListener = new LocationListener() {
			/*LocationListener
			: �Q�Ω���m���ܮɳq��LocationManager
			: �Q��reuqestLocationUpdates(String , long , float , LocationListener)��k�N��ť�����U�� LocationManager
			*/
			@Override
			public void onLocationChanged(Location newLocation) {  //���m���ܮɩI�s����k
				Double longitude = newLocation.getLongitude() * 1000000;   // getLongitude() , getLatitude(): Location���O�����ؤ�k
				Double latitude = newLocation.getLatitude() * 1000000;
				Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue());  // intValue() : �N�����ন���,Object���O���U���l���O����k
				textView01.setText("�g��"+longitude);  //  setText() : �]�mtext 
				textView02.setText("�n��"+latitude);
			}
			@Override
			public void onProviderDisabled(String provider) {  //��provider�����ɩI�s����k
				// TODO Auto-generated method stub			
			}
			@Override
			public void onProviderEnabled(String provider) {  //��provider�ҥήɩI�s����k
				// TODO Auto-generated method stub				
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {  //��provider���A���ܮɩI�s����k
				// TODO Auto-generated method stub			
			} 				
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);  // GPS_PROVIDER : LocationManager���U��provider
	}																								   //              : This provider determines location using satellites. Depending on conditions, this provider may take a while to return a location fix. Requires the permission ACCESS_FINE_LOCATION
																									   // �Ҧ�provider���^��string
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onLocationChanged(Location location) {     // �갵 LocationListener��������ݭnoverride��method
		// TODO Auto-generated method stub
		Double longitude = location.getLongitude() * 1000000;
		Double latitude = location.getLatitude() * 1000000;
		Log.i("Location=", "X=" + longitude.intValue() + ", Y=" + latitude.intValue());
		textView01.setText("�g��"+longitude);
		textView02.setText("�n��"+latitude);
	}

	@Override
	public void onProviderDisabled(String provider) { // �갵 LocationListener��������ݭnoverride��method
		// TODO Auto-generated method stub	
	}

	@Override
	public void onProviderEnabled(String provider) { // �갵 LocationListener��������ݭnoverride��method
		// TODO Auto-generated method stub	
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) { // �갵 LocationListener��������ݭnoverride��method
		// TODO Auto-generated method stub		
	}

}
