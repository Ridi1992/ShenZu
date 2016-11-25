package com.sanmi.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bset.tool.Texttool;
import com.bset.tool.Toast;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sanmi.http.HttpFileUpTool;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.HttpFileUpTool.Myreturn;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

/**
 *      ?    
 * 
 * @author Administrator
 * 
 */
public class RenZhengActivity extends BaseActivity {
	private LodingDialog lldialog;
	private ImageView mLeft;
	private ImageView mImageView01;// 身份证正面
	private ImageView mImageView02;// 身份证反面
	private ImageView mImageView03;// 本人照片
	/***************相册开始************************/
	public String selectedPicture1 = null;
	public String selectedPicture2 = null;
	public String selectedPicture3 = null;
	/***************相册结束************************/
	public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_renzheng);
		mLeft = (ImageView) findViewById(R.id.iv_left);
		mImageView01 = (ImageView) findViewById(R.id.rengzheng_01);
		mImageView02 = (ImageView) findViewById(R.id.rengzheng_02);
		mImageView03 = (ImageView) findViewById(R.id.rengzheng_03);
		mImageView01.setOnClickListener(this);
		mLeft.setOnClickListener(this);
		mImageView02.setOnClickListener(this);
		mImageView03.setOnClickListener(this);
		findViewById(R.id.send).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send();
			}
		});
		/***************相册开始************************/
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(100 * 1024 * 1024).diskCacheFileCount(300)
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
		
		/******************相册结束************************/
	}

	/**
	 *    ?   
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("身份认证");

	}

	private void send() {
		if(!Texttool.Havecontent(RenZhengActivity.this, R.id.nickname)){
			Toast.ToastMe(getApplication(), "请填写真实姓名");
			return ;
		}
		if(!Texttool.Havecontent(RenZhengActivity.this, R.id.idCard)){
			Toast.ToastMe(getApplication(), "请填写身份证号码");
			return ;
		}
		if(!Texttool.Patternidcard(Texttool.Gettext(RenZhengActivity.this, R.id.idCard))){
			Toast.ToastMe(getApplication(), "请填写正确的身份证号码");
			return ;
		}
		if(selectedPicture1==null||selectedPicture2==null||selectedPicture3==null){
			Toast.ToastMe(getApplicationContext(), "请完善图片信息");
			return;
		}
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("user_id", mUserInfo.GetUserInfo(RenZhengActivity.this).getId());
			jsonObject.put("nickname", Texttool.Gettext(this, R.id.nickname));
			jsonObject.put("idCard", Texttool.Gettext(this, R.id.idCard));
			senddata(jsonObject.toString());
		} catch (Exception e) {
		}
	}
	private void senddata(final String data) {
		final HttpFileUpTool upload=new HttpFileUpTool();
		lldialog=LodingDialog.DialogFactor(RenZhengActivity.this, "正在提交数据", false);
		final Map<String, Bitmap> files = new HashMap<String, Bitmap>();
		files.put("picture"+0, fitBitmap(selectedPicture1));
		files.put("picture"+1, fitBitmap(selectedPicture2));
		files.put("picture"+2, fitBitmap(selectedPicture3));
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					upload.certify(RenZhengActivity.this,data, files,new Myreturn() {
						@Override
						public void result(final int i, final String string) {
							RenZhengActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if(lldialog!=null){
										lldialog.dismiss();
									}
									if(i==1){
										Toast.ToastMe(getApplicationContext(), "提交成功");
										UserInfo data=JsonUtil.instance().fromJson(string, new TypeToken<UserInfo>(){}.getType());
										if(data!=null){
											data.setIslogin(true);
											mUserInfo.SaveUserInfo(RenZhengActivity.this, data);
										}
									}else{
										Toast.ToastMe(getApplicationContext(), string);
									}
									finish();
								}
							});

						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
		}).start();
		
	}

	/**
	 * onClick    
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent intent=new Intent(RenZhengActivity.this, SelectPictureActivityRenzheng.class);
		if (v == mImageView01) {
			startActivityForResult(intent,1);
		}
		if (v == mImageView02) {
			startActivityForResult(intent,2);
		}
		if (v == mImageView03) {
			startActivityForResult(intent,3);
		}
	}
	/***********************相片选择开始*******************************/
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==1) {
			if (resultCode == RESULT_OK) {
				selectedPicture1=data.getStringExtra(INTENT_SELECTED_PICTURE);
				if(selectedPicture1!=null&&!selectedPicture1.equals("")){
						mImageView01.setImageBitmap(fitBitmap(selectedPicture1));
						System.out.println("selectedPicture1#################################"+selectedPicture1.toString());
				}
			}
		}
		if (requestCode==2) {
			if (resultCode == RESULT_OK) {
				selectedPicture2=data.getStringExtra(INTENT_SELECTED_PICTURE);
				if(selectedPicture2!=null&&!selectedPicture2.equals("")){
						mImageView02.setImageBitmap(fitBitmap(selectedPicture2));
						
				}
			}
		}
		if (requestCode==3) {
			if (resultCode == RESULT_OK) {
				selectedPicture3=data.getStringExtra(INTENT_SELECTED_PICTURE);
				if(selectedPicture3!=null&&!selectedPicture3.equals("")){
						mImageView03.setImageBitmap(fitBitmap(selectedPicture3));
				}
			}
		}
	
	}

	/**
	 * 改变图片分辨率,使图片刚好和手机屏幕适配
	 */
	private Bitmap fitBitmap(String path){
		
			if(path==null){
				return null;
			}
			
			BitmapFactory.Options opts = new Options(); 
			// 不读取像素数组到内存中，仅读取图片的信息 ??????????????? 
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opts); 
			// 从Options中获取图片的分辨率 ??????????????? 
			int imageHeight = opts.outHeight;  
			int imageWidth = opts.outWidth; 
			// 获取Android屏幕的服务 ??????????????? 
			// 应该使用getSize()，但是这里为了向下兼容所以依然使用它们 ??????????????? 
			int windowHeight = 500;  
			int windowWidth =500;
			// 计算采样率 ??????????????? 
			int scaleX = imageWidth / windowWidth; 
			Log.i("压缩图片", "imageWidth="+imageWidth);
			Log.i("压缩图片", "windowWidth="+windowWidth);
			int scaleY = imageHeight / windowHeight; 
			int scale = 1; 
			// 采样率依照最大的方向为准 ??????????????? 
			if (scaleX > scaleY && scaleY >= 1) { 
				scale = scaleX; 
				Log.i("压缩图片,采码率", "scale="+scale);
				}
				if (scaleX < scaleY && scaleX >= 1) 
				{ 
					scale = scaleY;
					Log.i("压缩图片,采码率", "scale="+scale);
					}
					// false表示读取图片像素数组到内存中，依照设定的采样率 ??????????????? 
					opts.inJustDecodeBounds = false; 
					// 采样率 ??????????????? 
					opts.inSampleSize = scale; 
					Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
					return bitmap;
		}
	/***********************相片选则结束*******************************/
	
}