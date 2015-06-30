package com.example.camera_open;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private Preview camera_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	    
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	camera_view = new Preview(this);
    	setContentView(camera_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
