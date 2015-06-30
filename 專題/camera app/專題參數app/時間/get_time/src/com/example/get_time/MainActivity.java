package com.example.get_time;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {

	private TextView textView1;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		Date dt=new Date();
		String dts=sdf.format(dt);
	
		textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setText(dts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
