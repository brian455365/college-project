package com.example.focal_length;

import java.io.File;

import android.hardware.Camera;
//import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
//import android.view.Menu;
//import android.widget.ImageView;
//import android.widget.TextView;


public class MainActivity extends Activity{

    int focul_length;
    Parameters params;
    File mFile;
	Camera mcamera;
    public int PICTURE_ACTIVITY_CODE = 1;
    public String FILENAME = "sdcard/photo.jpg";
    Camera.Parameters cameraParameters;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       textView1 = (TextView)findViewById(R.id.TextView1);
       launchTakePhoto();
    }

    private void launchTakePhoto() {
    	mcamera = Camera.open();
///   	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraParameters = mcamera.getParameters();
//        CameraInfo myinfo = new CameraInfo();
        float l=cameraParameters.getFocalLength(); // Here its creating Null Pointer Exception      
///        mFile = new File(FILENAME);
///        System.out.println("My Focul Length:--"+l);
        textView1.setText(String.valueOf(l));
        mcamera.release();
///        Uri outputFileUri = Uri.fromFile(mFile);
///        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
///        startActivityForResult(intent, PICTURE_ACTIVITY_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//         if (requestCode == PICTURE_ACTIVITY_CODE){
//        	 if (resultCode == RESULT_OK){
//        		 ImageView myimageView = (ImageView) findViewById(R.id.imageView1);
//        		 Uri inputFileUri = Uri.fromFile(mFile);
//        		 myimageView.setImageURI(inputFileUri);
//        	 }
//         }
     }
}
