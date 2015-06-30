package com.example.camera_open;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class Preview extends SurfaceView implements SurfaceHolder.Callback, PictureCallback{

	private SurfaceHolder mHolder;
	private Camera mcamera;
	private RawCallback mRawCallback;
	
	private static int state = 0; // 紀錄相片拍再次數 (用來存檔用)
	public Preview(Context context) {
		super(context);
		
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mRawCallback = new RawCallback();
		
		setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				mcamera.takePicture(mRawCallback,mRawCallback,null,Preview.this);
			}
		});
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters parameter = mcamera.getParameters();
		parameter.setPreviewSize(width, height);
		mcamera.setParameters(parameter);
		
		mcamera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mcamera = Camera.open();
		
		configure(mcamera);
		try{
			mcamera.setPreviewDisplay(holder);
		}
		catch(IOException exception){
			closeCamera();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		closeCamera();
	}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		mcamera.startPreview();

		File saveFile = new File("/sdcard/photo"+ String.valueOf(state)+".jpg");
		state++;
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(saveFile);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.write(data);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		savaFile(data);
	}

	private void configure(Camera camera){
		Camera.Parameters parameter = camera.getParameters();
		
		List<Integer> formats = parameter.getSupportedPictureFormats();
		if(formats.contains(PixelFormat.RGB_565))
			parameter.setPictureFormat(PixelFormat.RGB_565);
		else
			parameter.setPictureFormat(PixelFormat.JPEG);
		
		List<Size> sizes = parameter.getSupportedPictureSizes();
		Camera.Size size = sizes.get(sizes.size()-1);
		parameter.setPictureSize(size.width, size.height);
		
		List<String> flashModes = parameter.getSupportedFlashModes();
		if(flashModes.size() > 0)
			parameter.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
		
		List<String> sceneModes = parameter.getSupportedSceneModes();
		if(sceneModes.contains(Camera.Parameters.SCENE_MODE_ACTION))
			parameter.setSceneMode(Camera.Parameters.SCENE_MODE_ACTION);
		else
			parameter.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
		
		parameter.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
		
		camera.setParameters(parameter);
		
	}
	
	private void closeCamera(){
		if(mcamera != null){
			mcamera.stopPreview();
			mcamera.release();
			mcamera = null;
		}
	}

}
