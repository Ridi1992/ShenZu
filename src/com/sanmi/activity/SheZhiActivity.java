package com.sanmi.activity;
import org.json.JSONException;
import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.zuwo.data.SheZhiPhone;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Ϣ
 * @author Administrator
 *
 */
public class SheZhiActivity extends BaseActivity {
	private ImageView mLeft;
	private Button mTuiChu;
	private LodingDialog mDialog;
	private TextView phone,qq;
	private SheZhiPhone mList;
	
	private RelativeLayout mToFeedBack;
	private RelativeLayout mToAbout;
	private RelativeLayout mCheckUpdate;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_shezhi);
		init();//ʼ+
		gethttp();
	}
	/**
	 * 网络请求
	 */
	private void gethttp() {
		mDialog = LodingDialog.DialogFactor(SheZhiActivity.this, "正在加载", false);
		PublicRequest http = new PublicRequest(mHandler);
		http.shezhiphone(SheZhiActivity.this);
	}
	/**
	 * ʼ
	 */
	private void init() {
		mLeft=(ImageView) findViewById(R.id.iv_left);
		mTuiChu=(Button) findViewById(R.id.bt_tuichu);
		mLeft.setOnClickListener(this);
		mTuiChu.setOnClickListener(this);
		phone=(TextView) findViewById(R.id.shezhi_phone);
		qq=(TextView) findViewById(R.id.tv_shezhiqq);
		
		mToFeedBack = (RelativeLayout) findViewById(R.id.tofeedback);
		mToFeedBack.setOnClickListener(this);
		mToAbout = (RelativeLayout) findViewById(R.id.toabout);
		mToAbout.setOnClickListener(this);
		mCheckUpdate = (RelativeLayout) findViewById(R.id.check_update);
		mCheckUpdate.setOnClickListener(this);
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
		mCenter.setText("设置");
	}
	/**
	 * onClick
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		
		switch (v.getId()) {
		case R.id.iv_left:
			SheZhiActivity.this.finish();
			break;
		case R.id.tofeedback:
			startActivity(new Intent(SheZhiActivity.this, FeedBackActivity.class));
			break;
		case R.id.check_update:
			String version_code = getVresonCode();
			if (!version_code.equals("")) {
				mDialog=LodingDialog.DialogFactor(SheZhiActivity.this, "正在加载...", false);
				PublicRequest http = new PublicRequest(mHandler);
				http.CheckUpdate(SheZhiActivity.this,version_code);
			}else {
				com.bset.tool.Toast.ToastMe(SheZhiActivity.this, "获取版本失败");
			}
			break;
		case R.id.toabout:
			startActivity(new Intent(SheZhiActivity.this, AboutUsActivity.class));
			break;
		case R.id.bt_tuichu:
			UserInfo info=new UserInfo();
			info.setIslogin(false);
			if(mUserInfo.SaveUserInfo(SheZhiActivity.this, info)){
				MainActivity.miaActivity.finish();
				Intent intent2 = new Intent(SheZhiActivity.this, LoginActivity.class);
				startActivity(intent2);
				SheZhiActivity.this.finish();
			}
			break;
		
		}
	}
	
	private String getVresonCode(){
		String code = "";
			try {
				PackageManager manager = this.getPackageManager();
				PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
				code = info.versionCode+"";
				return code;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		return code;
	}
	
	private Handler mHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (mDialog != null) {
				mDialog.dismiss();
			}
			switch (msg.what) {
			case Constants.UPDATE:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}else if (jsonObj.getString("code").equals("2")) {
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.SHEZHIPHONE:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsondata = jsonObj.getString("data");
						mList = JsonUtil.instance().fromJson(jsondata,
								new TypeToken<SheZhiPhone>() {
								}.getType());
//						new Task().execute();
						if (mList != null) {
							phone.setText(mList.getPhone());
							phone.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									if (v==phone) {
										Intent intent2 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+mList.getPhone()));
										startActivity(intent2);
									}
									
								}
							});
							qq.setText(mList.getQq());
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		};
	};
	class Task extends AsyncTask<Void, Void, SheZhiPhone>{

		@Override
		protected SheZhiPhone doInBackground(Void... params) {
			return mList;
		}
		@Override
		protected void onPostExecute(SheZhiPhone result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			System.out.println(result.getPhone()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		
	}
}
