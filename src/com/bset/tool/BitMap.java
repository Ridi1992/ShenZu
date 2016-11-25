package com.bset.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class BitMap {

	public static Bitmap returnBitMap(String url) {   
        URL myFileUrl = null;   
        Bitmap bitmap = null;   
        try {   
         myFileUrl = new URL(url);   
        } catch (MalformedURLException e) {   
         e.printStackTrace();   
        }   
        try {   
         HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();   
         conn.setDoInput(true);   
         conn.connect();   
         InputStream is = conn.getInputStream();   
         bitmap = BitmapFactory.decodeStream(is);   
         is.close();   
        } catch (IOException e) {   
         e.printStackTrace();   
        }   
        return bitmap;   
     } 
	
	public static Bitmap drawableToBitmap(Drawable drawable){ 
		int width = drawable.getIntrinsicWidth(); 
		int height = drawable.getIntrinsicHeight(); 
		Bitmap bitmap = Bitmap.createBitmap(width, height, 
		drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 
		: Bitmap.Config.RGB_565); 
		Canvas canvas = new Canvas(bitmap); 
		drawable.setBounds(0,0,width,height); 
		drawable.draw(canvas); 
		return bitmap; 
	}
	
	/**
	 * 把Bitmap转Byte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 本地获取图片转换成位图
	 * @param 图片路径
	 * @return 位图
	 */
	public static Bitmap getBitmap(String path){
		File file = new File(path);
   	 if (file.exists()) {
   		 	Bitmap bm=BitmapFactory.decodeFile(path);
   			return bm;
   	 	}else{
   	 		return null;
   	 	}
	}
	/**
	 * 
	 * @int id
	 * @return
	 */
	public static Bitmap getBitmap(Context context,int id){
		Bitmap bitmap	= BitmapFactory.decodeResource(
				context.getResources(),id);
		if (bitmap!=null) {
			return bitmap;
		}else{
			return null;
		}
	}
	
	   
	   // decode这个图片并且按比例缩放以减少内存消?，虚拟机对每张图片的缓存大小也是有限制的  
	    private Bitmap decodeFile(File f) {  
	        try {  
	            // decode image size  
	            BitmapFactory.Options o = new BitmapFactory.Options();  
	            o.inJustDecodeBounds = true;  
	            BitmapFactory.decodeStream(new FileInputStream(f), null, o);  
	            // Find the correct scale value. It should be the power of 2.  
	            final int REQUIRED_SIZE = 400;  
	            int width_tmp = o.outWidth, height_tmp = o.outHeight;
	            Log.i("压缩图片,宽", "Width="+o.outWidth);
	            Log.i("压缩图片,宽", "Height="+o.outHeight);
	            int scale = 1;  
	            while (true) {  
	                if (width_tmp / 2 < REQUIRED_SIZE  
	                        || height_tmp / 2 < REQUIRED_SIZE)  
	                    break;  
	                width_tmp /= 2;  
	                height_tmp /= 2;  
	                scale *= 2;  
	            }  
	            // decode with inSampleSize  
	            BitmapFactory.Options o2 = new BitmapFactory.Options(); 
	            o2.inJustDecodeBounds = false;
	            o2.inSampleSize = scale; 
	            Log.i("压缩图片,采码率", "scale="+scale);
	            
	            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);  
	        } catch (FileNotFoundException e) {  
	        }  
	        return null;  
	    }  
	    
	    public static void CopyStream(InputStream is, OutputStream os) {  
	        final int buffer_size = 1024;  
	        try {  
	            byte[] bytes = new byte[buffer_size];  
	            for (;;) {  
	                int count = is.read(bytes, 0, buffer_size);  
	                if (count == -1)  
	                    break;  
	                os.write(bytes, 0, count);  
	            }  
	        } catch (Exception ex) {  
	        }  
	    } 
	    /**
	     * 图片分辨率
	     * @param path
	     * @return
	     */
	    
	    public static Bitmap BitMapChange(String path){
			
			if(path==null){
				return null;
			}
			
			BitmapFactory.Options opts = new Options(); 
			// 不读取像素数组到内存中，仅读取图片的信息                 
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opts); 
			// 从Options中获取图片的分辨率                 
			int imageHeight = opts.outHeight;  
			int imageWidth = opts.outWidth; 
			// 获取Android屏幕的服务                 
			// 应该使用getSize()，但是这里为了向下兼容所以依然使用它们                 
			int windowHeight = 500;  
			int windowWidth =500;
			// 计算采样率                 
			int scaleX = imageWidth / windowWidth; 
			Log.i("压缩图片", "imageWidth="+imageWidth);
			Log.i("压缩图片", "windowWidth="+windowWidth);
			int scaleY = imageHeight / windowHeight; 
			int scale = 1; 
			// 采样率依照最大的方向为准                 
			if (scaleX > scaleY && scaleY >= 1) { 
				scale = scaleX; 
				Log.i("压缩图片,采码率", "scale="+scale);
				}
				if (scaleX < scaleY && scaleX >= 1) 
				{ 
					scale = scaleY;
					Log.i("压缩图片,采码率", "scale="+scale);
					}
					// false表示读取图片像素数组到内存中，依照设定的采样率                 
					opts.inJustDecodeBounds = false; 
					// 采样率                 
					opts.inSampleSize = scale; 
					Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
//					setPicToView(bitmap);
					return bitmap;
		}
}
