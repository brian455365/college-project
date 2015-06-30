package com.example.camera_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;


public class Preview extends SurfaceView implements SurfaceHolder.Callback, PictureCallback{
	// 得到一個Surface對象時，同時會得到一個Canvas（畫布）對象
	// Surface是管理顯示內容的數據。而SurfaceView就是把這些數據顯示出來到屏幕上面
	// 可以通過SurfaceHolder來控制surface的尺寸和格式，或者修改surface的像素，監視surface的變化等等
	// SurfaceHolder.Callback是用來監聽surface的改變
	// 調用SurfaceHolder.Callback需要寫  
	// 1. void surfaceChanged(SurfaceHolder holder, int format, int width, int height) ,  surface發生改變時被調用
	// 2. void surfaceCreated(SurfaceHolder holder) , 在surface創建時被調用
	// 3. void surfaceDestroyed(SurfaceHolder holder) , 在surface銷毀時被調用
	// Camera.PictureCallback：拍照、產生圖片時觸發
	private SurfaceHolder mHolder;
	private Camera mcamera;
	//private RawCallback mRawCallback;
	private String Tag = "Preview";
	
	private Context mContext;   	// NEW
	
	//private IActivity iactivity;
	public Preview(Context context) {		
		super(context);
		
		mContext = context;			// NEW
		
//		iactivity = (IActivity) context;	
		mHolder = getHolder();
		// 得到SurfaceHolder對象
		mHolder.addCallback(this);
		// 為何使用Callback接口?
		// 使用SurfaceView有一個原則，所有的繪圖工作必須得在Surface被創建之後才能開始，而在Surface被銷毀之前必須結束。
		// 所以Callback中的surfaceCreated和surfaceDestroyed就成了繪圖處理代碼的邊界。
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 設置SurfaceHolder 的類型
		//mRawCallback = new RawCallback();
		
		
		setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Log.d(Tag, "5");
				
				/*** ERROR ***/
			
				mcamera.takePicture(null,null, photoCallback);       			// LG Correct Version
//				mcamera.takePicture(null,null,  photoCallback, photoCallback);	// NEXUS Correct Version
				
				
				//mcamera.takePicture(null,photoCallback, photoCallback);       // NEXUS 會跑入 Preview onPicture Taken
																				// 但是 data 為 NULL
				
				
				//mcamera.takePicture(photoCallback,photoCallback,null);
				//mcamera.takePicture(null, photoCallback, photoCallback);		
				//mcamera.takePicture(null,photoCallback,photoCallback);			
				//mcamera.takePicture(null,mRawCallback,mRawCallback);
				
				//mcamera.takePicture(null,mRawCallback,null);
				//mcamera.takePicture(null,mRawCallback,mRawCallback);
				//mcamera.takePicture(null,mRawCallback,null);						
				//mcamera.takePicture(mRawCallback,mRawCallback,null,Preview.this);			
				//mcamera.takePicture(mRawCallback,mRawCallback,mRawCallback);
				//mcamera.takePicture(mRawCallback,mRawCallback,jpegCallback);	
				//mcamera.takePicture(mRawCallback,mRawCallback,mRawCallback,Preview.this);		
				//mcamera.takePicture(mRawCallback,mRawCallback,Preview.this);
				/*** END ERROR ***/
				
				Log.d(Tag, "6");
//				closeCamera();  如果放在這,畫面停住,不會儲存
				//mcamera.takePicture(shutterCallback, rawCallback, jpegCallback);
				// takePicture 方法要实现3个回调函数，分别是： Camera.ShutterCallback (快门) 和 两个Camera.PictureCallback(圖項數據)
				// shutterCallback要實现Camera.ShutterCallback接口； rawCallback, jpegCallback要實现Camera.PictureCallback接口
			}
		});
	}
	
	// 每一台手機的 PreviewSize 皆不同, 因此需要去個別計算      (NEW) 
	private Camera.Size getBestPreviewSize(int width, int height)
	{
	        Camera.Size result=null;    
	        Camera.Parameters p = mcamera.getParameters();
	        for (Camera.Size size : p.getSupportedPreviewSizes()) {
	            if (size.width<=width && size.height<=height) {
	                if (result==null) {
	                    result=size;
	                } else {
	                    int resultArea=result.width*result.height;
	                    int newArea=size.width*size.height;

	                    if (newArea>resultArea) {
	                        result=size;
	                    }
	                }
	            }
	        }
	    return result;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters parameter = mcamera.getParameters();
		
		/***CHANGE HERE ***/
		 Camera.Size size = getBestPreviewSize(width, height);
		 parameter.setPreviewSize(size.width, size.height);		 
		 // NEW SET
//		 parameter.setPictureSize(size.width, size.height);
		 // END NEW SET 
		 mcamera.setParameters(parameter);
		 mcamera.startPreview();
		 
		 Log.d(Tag, "3");
		/*** CHANGE ENDS ***/
		 
	/*	
			parameter.setPreviewSize(width, height); 	
			mcamera.setParameters(parameter);		
			mcamera.startPreview();
	*/
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
				
		mcamera = Camera.open();		
		configure(mcamera);
		try{
			mcamera.setPreviewDisplay(holder);
		}
		catch(IOException exception){
			Log.d(Tag, "4");
			closeCamera();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		closeCamera();
	}
	
	Camera.PictureCallback photoCallback=new Camera.PictureCallback(){

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
//			mcamera.startPreview();                           //   註解掉的地方
			
			Log.d(Tag, "PictureTaken HERE");
			Log.d(Tag, "PictureTaken: "+data.toString());
			
			float focal_length = camera.getParameters().getFocalLength();
			
			Date dt=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dts=sdf.format(dt);
//			MainActivity.get_time = dts;
			
			//File saveFile = new File("/sdcard/photo"+ dts+".jpg");
			File saveFile = new File(Environment.getExternalStorageDirectory(),  "Pic"+dts+".jpg");
			FileOutputStream outStream = null;
			try {
				outStream = new FileOutputStream(saveFile);
				
				Toast.makeText(mContext,"Image saved: "+saveFile.toString()+"\n" +"focal_length: "+String.valueOf(focal_length)+"\n" +"get_time: "+ dts+"\n" +"Image sensor: "+"6.16mm x 4.62mm"+"\n" +"target height: "+"200 m",Toast.LENGTH_LONG).show();
/*				Toast.makeText(mContext,                                 //  檔案儲存的路徑名
					     "Image saved: " + saveFile.toString(),
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "focal_length: " + String.valueOf(focal_length), //  焦距
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "get_time: " + dts,                              //  時間
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "Image sensor: " + "6.16mm x 4.62mm",             //  感光元件
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "target height: " + "200 m",                      //  目標高度
					     Toast.LENGTH_LONG).show();
*/				
				
				double a = 6.16 / (2*(double)focal_length);
				double theta = 2*(double) Math.toDegrees(Math.atan(a));
				double b = 4.62 / (2*(double)focal_length);
				double theta2 = 2*(double) Math.toDegrees(Math.atan(b));
				Toast.makeText(mContext,"horizonal view angle: "+String.valueOf(theta)+"\n" +"vertical view angle: "+String.valueOf(theta2)+"\n" +"azimuth(z) : "+String.valueOf(MainActivity.azimuth)+"\n" +"pitch(x): "+String.valueOf(MainActivity.pitch)+"\n" +"roll(y): "+String.valueOf(MainActivity.roll),Toast.LENGTH_LONG).show();
//				Toast.makeText(mContext,
//					     "horizonal view angle: " + String.valueOf(theta),  //  水平視角
//					     Toast.LENGTH_LONG).show();
				
//				a = 4.62 / (2*(double)focal_length);
//				theta = 2*(double) Math.toDegrees(Math.atan(a));
//				Toast.makeText(mContext,
//					     "vertical view angle: " + String.valueOf(theta),  //  垂直視角
//					     Toast.LENGTH_LONG).show();
/*				Toast.makeText(mContext,
					     "azimuth(z) : " + String.valueOf(MainActivity.azimuth), //  roll : z
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "pitch(x) : " + String.valueOf(MainActivity.pitch),   //  roll : x
					     Toast.LENGTH_LONG).show();
				Toast.makeText(mContext,
					     "roll(y) : " + String.valueOf(MainActivity.roll),    //  roll : y
					     Toast.LENGTH_LONG).show();
*/				Toast.makeText(mContext,
						"waitning for gps...",
						Toast.LENGTH_LONG).show();
				if(MainActivity.out_longitude != "get.."){                     
					Toast.makeText(mContext,
							"longitude : " + MainActivity.out_longitude,
							Toast.LENGTH_LONG).show();
					Toast.makeText(mContext,
							"latitude : " + MainActivity.out_latitude,
							Toast.LENGTH_LONG).show();
					Toast.makeText(mContext,
							"Altitude : " + MainActivity.out_Altitude,
							Toast.LENGTH_LONG).show();
					mcamera.startPreview();                                    //   如果取不到gps,就停止啟動照相機的瀏覽
				}
				
				
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				outStream.write(data);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outStream.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		
/*		@Override
		 public void onPictureTaken(byte[] data, Camera camera) {
		  // TODO Auto-generated method stub
		 
		 
		  Uri uriTarget = mContext.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new ContentValues());

		  OutputStream imageFileOS;
		  try {
		   imageFileOS = mContext.getContentResolver().openOutputStream(uriTarget);
		   imageFileOS.write(data);
		   imageFileOS.flush();
		   imageFileOS.close();
		   
		   Toast.makeText(mContext,
				     "Image saved: " + uriTarget.toString(),
				     Toast.LENGTH_LONG).show();
		   
		  
		  } catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		
*/
//		  camera.startPreview();
		  
	//	    Intent intent = new Intent();
	//		intent.setClass(mContext, TEST.class);
	//	    mContext.startActivity(intent); 
			//mContext.this.finish(); 		
	};
	

	// NEW onPictureTaken()  [Empty]
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		// Do Nothing
		Log.d(Tag, "HERE!");
	}
	// END NEW onPictureTaken()  [Empty]
	
	
/*
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		mcamera.startPreview();
		
		Log.d(Tag, "PictureTaken");
		Log.d(Tag, "PictureTaken: "+data.toString());
		
		MainActivity.focal_length = camera.getParameters().getFocalLength();
		
		Date dt=new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dts=sdf.format(dt);
		MainActivity.get_time = dts;
		
		//File saveFile = new File("/sdcard/photo"+ dts+".jpg");
		File saveFile = new File(Environment.getExternalStorageDirectory(),  "Pic"+dts+".jpg");
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(saveFile);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			outStream.write(data);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			outStream.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
//		closeCamera(); // 如果放在這,畫面停住,會儲存
		Log.d(Tag, "1");
		
	}// END onPictureTaken()
*/	
	
	private void configure(Camera camera){
		Camera.Parameters parameter = camera.getParameters();
		
		List<Integer> formats = parameter.getSupportedPictureFormats();
		if(formats.contains(PixelFormat.RGB_565))
			parameter.setPictureFormat(PixelFormat.RGB_565);
		else
			parameter.setPictureFormat(PixelFormat.JPEG);
		
		List<Size> sizes = parameter.getSupportedPictureSizes();		
/*		// NEW
		Size mSize;
		mSize = sizes.get(0);
		parameter.setPictureSize(mSize.width, mSize.height);
		// END NEW	
*/		
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
		
		parameter.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		
		camera.setParameters(parameter);
		
	}
	
	private void closeCamera(){
		Log.d(Tag, "Camera Stopped 1");
		if(mcamera != null){
			Log.d(Tag, "Camera Stopped 2");
			mcamera.stopPreview();
			mcamera.release();
			mcamera = null;
//			ReportActivity.out();
			Log.d(Tag, "Camera Stopped 3");
//			iactivity.out();
			Log.d(Tag, "Camera Stopped 4");
		}

	}

}
