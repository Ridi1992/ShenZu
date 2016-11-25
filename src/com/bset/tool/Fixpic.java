package com.bset.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Fixpic  {
	 public void setView_W_H(WindowManager wm, ImageView view,Bitmap bitmap){
		 if(view!=null && bitmap!=null){
	        int width = wm.getDefaultDisplay().getWidth();
	        int height = wm.getDefaultDisplay().getHeight();
	        int bitmapwidth=bitmap.getWidth();
	        int bitmapheight=bitmap.getHeight();
	        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
	                LinearLayout.LayoutParams.FILL_PARENT,      
	                LinearLayout.LayoutParams.WRAP_CONTENT      
	        );   
	        linearParams.width = width;
	        linearParams.height = (int) (width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
	        view.setLayoutParams(linearParams); // ʹúõĲֲӦõؼmyGrid 
//	        ((ImageView) view).setImageBitmap(bitmap);//Ӧ֮ͼƬװȥ
		 }
	    }
	 public static void setView_W_H(ImageView view,int  width){
		 if(view!=null ){
			 LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
					 LinearLayout.LayoutParams.FILL_PARENT,      
					 LinearLayout.LayoutParams.WRAP_CONTENT      
					 );   
			 linearParams.width = width;
			 linearParams.height = (int) (width*3/4);
			 view.setLayoutParams(linearParams); 
		 }
	 }
	 public void setViewparent_W_H(WindowManager wm, View view,Bitmap bitmap){
		 if(view!=null && bitmap!=null){	
//	        WindowManager wm = this.getWindowManager(); 
	        int width = wm.getDefaultDisplay().getWidth();
	        int height = wm.getDefaultDisplay().getHeight();
	        
//	        Bitmap bitmap = BitmapFactory.decodeResource(.getResources(), drawable_id);
	        int bitmapwidth=bitmap.getWidth();
	        int bitmapheight=bitmap.getHeight();
	        Log.i("bitmapheight", "=="+bitmapheight);
	        
	        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
	                LinearLayout.LayoutParams.FILL_PARENT,      
	                LinearLayout.LayoutParams.WRAP_CONTENT      
	        );   
	        linearParams.width = width;// ؼĸǿ
	        linearParams.height = (int) (width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
	        view.setLayoutParams(linearParams); // ʹúõĲֲӦõؼmyGrid 
//	        ((ImageView) view).setImageBitmap(bitmap);
		 }
	    }
	 public void setImageview_h_w(Context context,int width, View view){
	        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
	                LinearLayout.LayoutParams.FILL_PARENT,      
	                LinearLayout.LayoutParams.WRAP_CONTENT      
	        );
	        linearParams.width = (int) ((width-dip2px(context,105))/3);// 95ΪͼƬռdp
	        linearParams.height =(int) ((width-dip2px(context,105))/3);//(int) ((float)linearParams.width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
	        view.setLayoutParams(linearParams);
		 }
	 public void setmarktImageview_h_w(Context context,int width, View view){
//		 if(view!=null && bitmap!=null){
//	        int bitmapwidth=bitmap.getWidth();
//	        int bitmapheight=bitmap.getHeight();
	        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
	                LinearLayout.LayoutParams.FILL_PARENT,      
	                LinearLayout.LayoutParams.WRAP_CONTENT      
	        );
	        linearParams.width = (int) ((width-dip2px(context,10))/3);// 95ΪͼƬռdp
	        linearParams.height =(int) ((width-dip2px(context,10))/3);//(int) ((float)linearParams.width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
	        view.setLayoutParams(linearParams);
//	        }
		 }
	 public void setImageview_h_w_forum(Context context,int width, View view,Bitmap bitmap){
		 if(view!=null && bitmap!=null){
	        int bitmapwidth=bitmap.getWidth();
	        int bitmapheight=bitmap.getHeight();
	        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
	                LinearLayout.LayoutParams.FILL_PARENT,      
	                LinearLayout.LayoutParams.WRAP_CONTENT      
	        );
	        linearParams.width = (int) ((width-dip2px(context,20))/3);// 95ΪͼƬռdp
//	        linearParams.height = linearParams.width;
	        linearParams.height = (int) ((float)linearParams.width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
	        view.setLayoutParams(linearParams);
	        }
		 if(view!=null && bitmap==null){
			 LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
		                LinearLayout.LayoutParams.FILL_PARENT,      
		                LinearLayout.LayoutParams.WRAP_CONTENT      
		        );
		        linearParams.width = (int) ((width-dip2px(context,20))/3);// 95ΪͼƬռdp
		        linearParams.height = linearParams.width;
//		        linearParams.height = (int) ((float)linearParams.width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
		        view.setLayoutParams(linearParams); 
		 }
		 }
		/**bitmap͸ؼĿǿImageViewĿߣֹʧ*/
		 public void setImageview_h_w_identity(Context context,int width, View view,Bitmap bitmap){
			 if(view!=null && bitmap!=null){
		        int bitmapwidth=bitmap.getWidth();
		        int bitmapheight=bitmap.getHeight();
		        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(      
		                LinearLayout.LayoutParams.FILL_PARENT,      
		                LinearLayout.LayoutParams.WRAP_CONTENT      
		        );
		        linearParams.width = (int) (width-dip2px(context,30));// 95ΪͼƬռdp
		        linearParams.height = (int) ((float)linearParams.width*((float)bitmapheight/(float)bitmapwidth));// ؼĸǿ
//		        Log.i("͸===", "="+linearParams.width+"=="+linearParams.height);
		        view.setLayoutParams(linearParams);
		        }
			 }
		public static int dip2px(Context context, float dpValue) {  
	        final float scale = context.getResources().getDisplayMetrics().density;  
	        Log.i("px===", ""+(dpValue * scale + 0.5f));
	        return (int) (dpValue * scale + 0.5f);  
		}
}
