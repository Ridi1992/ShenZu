package com.sanmi.activity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

import com.bset.tool.Texttool;
import com.bset.tool.Toast;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;

import com.sanmi.adapter.MeAdapter;

import com.sanmi.adapter.DialoglistAadapter;

import com.sanmi.http.Constants;
import com.sanmi.http.HttpFileUpTool;
import com.sanmi.http.HttpURL;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.http.HttpFileUpTool.Myreturn;
import com.sanmi.loader.ImageLoader;
import com.sanmi.view.ActionSheetDialog;
import com.sanmi.view.Title;
import com.sanmi.view.ActionSheetDialog.OnSheetItemClickListener;
import com.sanmi.view.ActionSheetDialog.SheetItemColor;
import com.sanmi.view.CircularImage;
import com.zuwo.data.MeFaBu;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ƸϽ
 * 
 * @author Administrator
 * 
 */
public class WanShanActivity extends BaseActivity {
	WanShanActivity mcontent;
	private WanShanActivity activity;
	private ImageView mLeft;
	private LodingDialog lldialog;
	private RelativeLayout mTouXiang;
	private Button mMake;
	private com.sanmi.view.CircularImage mCircularImage;
	private LodingDialog dialog;
	/*ͷļ */
	private static final String IMAGE_FILE_NAME = "face.jpg";

	/* ʶ */
	private static final int CODE_GALLERY_REQUEST = 0xa0;
	private static final int CODE_CAMERA_REQUEST = 0xa1;
	private static final int CODE_RESULT_REQUEST = 0xa2;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		activity=this;
		setContentView(R.layout.activity_wanshan);
		new Title(this);
		init();// ʼ
		setUserinfo();
	}

	private void setUserinfo(){
		UserInfo data=mUserInfo.GetUserInfo(WanShanActivity.this);
		if(data!=null){
			String imgurl=data.getFace();
			if(imgurl!=null){
				ImageLoader imageLoader=new ImageLoader(activity);
				imageLoader.DisplayImage(HttpURL.IMGPATH+imgurl, mCircularImage);
			}
			Texttool.setText(activity, R.id.nickname, data.getNickname());
			Texttool.setText(activity, R.id.address, data.getAddress());
			Texttool.setText(activity, R.id.height, data.getHeight());
			Texttool.setText(activity, R.id.work, data.getWork());
			Texttool.setText(activity, R.id.age, data.getAge());
			Texttool.setText(activity, R.id.signature, data.getSignature());
			if(data.getSex()==1){
				Texttool.setText(activity, R.id.sex, "男");
			}else{
				Texttool.setText(activity, R.id.sex, "女");
			}
		}
	}
	/**
	 * ʼ
	 */
	private void init() {
		mLeft = (ImageView) findViewById(R.id.iv_left);
		mTouXiang = (RelativeLayout) findViewById(R.id.ll_tx_shezhi);
		mCircularImage = (CircularImage) findViewById(R.id.civ_wanshan);
		mMake=(Button) findViewById(R.id.wnashan_bt);
//		mXiangCe=(LinearLayout) findViewById(R.id.ll_xiangce);
		mLeft.setOnClickListener(this);
		mTouXiang.setOnClickListener(this);
		mMake.setOnClickListener(this);
//		mXiangCe.setOnClickListener(this);
	}

	public void SelectSex() {
		    AlertDialog.Builder builder = new AlertDialog.Builder(
				 mcontent);
	        final View v = LayoutInflater.from(mcontent).inflate(
	                R.layout.dialog_select, null);
	        builder.setView(v);
	        final TextView title = (TextView) v.findViewById(R.id.dialog_title);
	        final ListView listview = (ListView) v.findViewById(R.id.dialoglistview);
	        title.setText("选择性别");
	        final Dialog dialog = builder.create();
	        dialog.show();
	        dialog.getWindow().setLayout((int)( mcontent.getWindowManager().getDefaultDisplay().getWidth()), WindowManager.LayoutParams.WRAP_CONTENT);
	        final ArrayList< String > sex=new ArrayList<String>();
	        sex.add("男");
	        sex.add("女");
	        DialoglistAadapter madapter=new DialoglistAadapter(mcontent,sex);
	        listview.setAdapter(madapter);
	        listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
}
	/**
	 * ñ
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("完善个人信息");
	}

	
	/**
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		if (v==mLeft) {
			WanShanActivity.this.finish();
		}
		
		if (v == mTouXiang) {
			ActionSheetDialog sheetDialog = new ActionSheetDialog(this);
			sheetDialog.builder().setCancelable(true)
					.setCanceledOnTouchOutside(false);
			sheetDialog.addSheetItem("照相机", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);

							if (hasSdcard()) {
								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CODE_CAMERA_REQUEST);
						}
					});
			sheetDialog.addSheetItem("相册选择", SheetItemColor.Blue,
					new OnSheetItemClickListener() {
						@Override
						public void onClick(int which) {
							Intent intentFromGallery = new Intent();
							// ļ
							intentFromGallery.setType("image/*");
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									CODE_GALLERY_REQUEST);
						}
					});
			sheetDialog.show();
		}
		if (v==mMake) {
			panduan();
		}
		
	}
	
	/**
	 * 提交用户信息
	 */
	private void panduan() {
		/*if(!Texttool.Havecontent(WanShanActivity.this, R.id.nickname)){
			Toast.ToastMe(getApplication(), "完善用户名");
			return ;
		}
		if(!Texttool.Havecontent(WanShanActivity.this, R.id.age)){
			Toast.ToastMe(getApplication(), "完善年龄");
			return ;
		}
		if(!Texttool.Havecontent(WanShanActivity.this, R.id.address)){
			Toast.ToastMe(getApplication(), "完善地址");
			return ;
		}
		if(!Texttool.Havecontent(WanShanActivity.this, R.id.height)){
			Toast.ToastMe(getApplication(), "完善身高");
			return ;
		}
		if(!Texttool.Havecontent(WanShanActivity.this, R.id.work)){
			Toast.ToastMe(getApplication(), "完善工作");
			return ;
		}
		if(!Texttool.Havecontent(WanShanActivity.this, R.id.signature)){
			Toast.ToastMe(getApplication(), "完善签名");
			return ;
		}*/
		String nickname=Texttool.Gettext(WanShanActivity.this, R.id.nickname);
		String age=Texttool.Gettext(WanShanActivity.this, R.id.age);
		String address=Texttool.Gettext(WanShanActivity.this, R.id.address);
		String height=Texttool.Gettext(WanShanActivity.this, R.id.height);
		String work=Texttool.Gettext(WanShanActivity.this, R.id.work);
		String signature=Texttool.Gettext(WanShanActivity.this, R.id.signature);
		String user_id=mUserInfo.GetUserInfo(WanShanActivity.this).getId();
		dialog=LodingDialog.DialogFactor(WanShanActivity.this, "正在提交...", false);
		PublicRequest http=new PublicRequest(handler2);
		http.wanshan(WanShanActivity.this, user_id, age, address, height, work, signature,nickname);
	}
	private Handler handler2=new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(dialog!=null){
					dialog.dismiss();
				}
			switch (msg.what) {
			case Constants.WANSHAN:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getString("data");
					UserInfo data=JsonUtil.instance().fromJson(jsonData, new TypeToken<UserInfo>(){}.getType());
					if(data!=null){
						data.setIslogin(true);
						mUserInfo.SaveUserInfo(WanShanActivity.this, data);
					}
					Toast.ToastMe(getApplication(),"提交成功");
				}else{
					Toast.ToastMe(getApplication(),jsonObj.getString("message"));
				}
				break;
			case 404:
				Toast.ToastMe(getApplication(),msg.obj.toString());
				break;
			}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};

	/**
	 * 返回值
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		// ûûнЧò
		if (resultCode == Activity.RESULT_OK) {

			switch (requestCode) {
			case CODE_GALLERY_REQUEST:
				cropRawPhoto(intent.getData());
				break;

			case CODE_CAMERA_REQUEST:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					cropRawPhoto(Uri.fromFile(tempFile));
				} else {
					Toast.ToastMe(getApplication(), "没有SDCard!");
				}
				break;
			case CODE_RESULT_REQUEST:
				if (intent != null) {
					getImageToView(intent);
				}
				break;
			}
			super.onActivityResult(requestCode, resultCode, intent);
		}

	}
	/**
	 * 裁剪图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// òü
		intent.putExtra("crop", "true");

		// aspectX , aspectY :ߵı
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : üͼƬ
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 
	 * 得到图片  放在view中
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			if(photo!=null){
				mCircularImage.setImageBitmap(photo);
				uphead(photo);
			}
		}
	}

	/**
	 * 判断sd卡是否存在
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private void uphead(Bitmap bit) {
		if(bit==null){
			com.bset.tool.Toast.ToastMe(getApplicationContext(), "图片错误");
			return;
		}
		final String user_id=mUserInfo.GetUserInfo(WanShanActivity.this).getId();
		final HttpFileUpTool upload=new HttpFileUpTool();
		lldialog=LodingDialog.DialogFactor(WanShanActivity.this, "正在更新头像", false);
		final Map<String, Bitmap> files = new HashMap<String, Bitmap>();
		files.put("picture"+0, bit);
		new Thread(new Runnable() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				try {
					upload.uphead(WanShanActivity.this,user_id,files,new Myreturn() {
						@Override
						public void result(final int i, final String string) {
							WanShanActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									if(lldialog!=null){
										lldialog.dismiss();
									}
									if(i==1){
										UserInfo data=JsonUtil.instance().fromJson(string, new TypeToken<UserInfo>(){}.getType());
										if(data!=null){
											data.setIslogin(true);
											mUserInfo.SaveUserInfo(WanShanActivity.this, data);
										}
										setUserinfo();
									}else{
										com.bset.tool.Toast.ToastMe(getApplicationContext(), string);
									}
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
}
