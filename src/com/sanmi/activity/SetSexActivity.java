package com.sanmi.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

public class SetSexActivity extends BaseActivity {
	private LodingDialog dialog;

	private ArrayList<CheckBox> check;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.setsexactivity);
		initview();
	}

	private void initview() {
		findViewById(R.id.send).setOnClickListener(this);
		check=new ArrayList<CheckBox>();
		check.add((CheckBox) findViewById(R.id.check0));
		check.add((CheckBox) findViewById(R.id.check1));
		check.get(0).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					check.get(1).setChecked(false);
				}
			}
		});
		check.get(1).setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					check.get(0).setChecked(false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.send:
			int sex=0;
			if(check.get(0).isChecked()){
				sex=1;
			}
			if(check.get(1).isChecked()){
				sex=2;
			}
			if(sex==0){
				com.bset.tool.Toast.ToastMe(getApplicationContext(),"请选择性别");
				return;
			}
			if(!Texttool.Havecontent(SetSexActivity.this, R.id.nickname)){
				com.bset.tool.Toast.ToastMe(getApplicationContext(),"请输入昵称");
				return;
			}
			send(sex,Texttool.Gettext(SetSexActivity.this,R.id.nickname));
			break;
		}
	}
	private void send(int sex,String nickname) {
		dialog = LodingDialog.DialogFactor(SetSexActivity.this, "正在提交数据",
				false);
		PublicRequest http = new PublicRequest(handler2);
		http.setSex(SetSexActivity.this, mUserInfo.GetUserInfo(SetSexActivity.this).getId() ,sex,nickname);
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (msg.what) {
				case Constants.SETSEX:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						UserInfo data = JsonUtil.instance().fromJson(jsonData,
								new TypeToken<UserInfo>() {
								}.getType());
						if(data!=null){
							data.setIslogin(true);
							mUserInfo.SaveUserInfo(SetSexActivity.this, data);
						}
						startTomianActivity();
						com.bset.tool.Toast.ToastMe(getApplicationContext(), "设置成功");
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								jsonObj.getString("message"), 0).show();
					}
					break;
				case 404:
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							0).show();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	/**
	 * 跳转到首页
	 */
	private void startTomianActivity(){
		Intent intent =new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("提示");
		mRight.setVisibility(View.GONE);
		mLeft.setVisibility(View.VISIBLE);
	}

}
