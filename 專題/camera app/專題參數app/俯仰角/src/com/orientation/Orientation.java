package com.orientation;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Orientation extends Activity {
    private TextView orient;
	private Sensor sensor;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        orient = (TextView)findViewById(R.id.orient);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        
        List<android.hardware.Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensorList.size() > 0) {
            sensor = sensorList.get(0);
            Toast.makeText(this,"Orientation sensor  present", Toast.LENGTH_SHORT).show();
        }
        else {
        	orient.setText("Orientation sensor not present");
        	Toast.makeText(this,"Orientation sensor not present", Toast.LENGTH_SHORT).show();
        }
        boolean a;
        a=sensorManager.registerListener(orientationListener,sensor, SensorManager.SENSOR_DELAY_NORMAL,null);
        	if(a==true)orient.setText("¦w¦w");
   // 	Toast.makeText(Orientation.this,"WTF", Toast.LENGTH_SHORT).show();

    }
    
    private SensorEventListener orientationListener = new SensorEventListener() {
		@Override
		
		public void onAccuracyChanged(Sensor arg0, int arg1) {
	    //	Toast.makeText(Orientation.this,"WTF", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onSensorChanged(SensorEvent sensorEvent) {
		//	Toast.makeText(Orientation.this,"WTFFFF", Toast.LENGTH_SHORT).show();
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				float azimuth = sensorEvent.values[0];
				float pitch = sensorEvent.values[1];
				float roll = sensorEvent.values[2];
/*
				if (pitch < -45 && pitch > -135) {
				//	Toast.makeText(this,"Top side of the phone is Up!", Toast.LENGTH_SHORT).show();
					orient.setText("Top side of the phone is Up!");
					
	            } else if (pitch > 45 && pitch < 135) {

	            	orient.setText("Bottom side of the phone is Up!");

	            } else if (roll > 45) {
	            	
					orient.setText("Right side of the phone is Up!");
					
	            } else if (roll < -45) {

	            	orient.setText("Left side of the phone is Up!");
	            }
				*/
			//	Toast.makeText(Orientation.this,"azimuth " + azimuth +"\npitch "+pitch+"\nroll " + roll, Toast.LENGTH_SHORT).show();
					orient.setText("azimuth " + azimuth +"\npitch "+pitch+"\nroll " + roll);
			}
		}
    	
    };
}