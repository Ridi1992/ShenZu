package com.sanmi.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.bset.tool.Texttool;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.data.GetCode;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ޸
 * 
 * @author Administrator
 * 
 */
public class XiuGaiMiMaActivity extends BaseActivity {
	private Button mSure;
	private EditText mNewPassWord;
	private EditText mNewPassWord2;
	private EditText mAuthCode;
	private EditText mPhoneNumber;
	private TextView mCallCode;
	private int i;
	private Handler Allhandler = new Handler();
	private String phoneNumber;
	private String newPassWord;
	private String newPassWord2;
	private String authCode;
	private final String PHONE = "^(((13[0-9])|(15([0-9]))|(18[0-9])|(17[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
	private com.dream.framework.utils.dialog.LodingDialog lls;
	
	private int mobile_codec;
	private String mobilec;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_xiugaimima);
		new Title(this);//״̬ɫ
		init();//ʼ
	}

	/**
	 * ʼ
	 */
	private void init() {
		mSure = (Button) findViewById(R.id.bt_xiugai);
		mNewPassWord = (EditText) findViewById(R.id.et_newpassword);
		mNewPassWord2 = (EditText) findViewById(R.id.et_newpassword2);
		mAuthCode = (EditText) findViewById(R.id.et_authcode);
		mCallCode = (TextView) findViewById(R.id.tv_authcode);
		mPhoneNumber = (EditText) findViewById(R.id.et_x_phonenumber);
		mCallCode.setOnClickListener(this);
		mSure.setOnClickListener(this);
		i = 60;
	}

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("修改密码");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		i=60;
		this.finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v == mCallCode) {
			phoneNumber=mPhoneNumber.getText().toString();
			Pattern regex = Pattern.compile(PHONE);
			Matcher matcher = regex.matcher(phoneNumber);
			boolean flagPhone = matcher.matches();
			if (phoneNumber==null||phoneNumber.equals("")) {
				Toast.makeText(getApplication(), "请填写手机号", Toast.LENGTH_LONG)
				.show();
			}else if (!flagPhone) {
				Toast.makeText(this, "请输入手机号", Toast.LENGTH_LONG).show();
			}else if (!phoneNumber.isEmpty()&&flagPhone) {
				lls = com.dream.framework.utils.dialog.LodingDialog.DialogFactor(this, "正在获取验证码...", false);
				getForgetCode(Texttool.Gettext(XiuGaiMiMaActivity.this, R.id.et_x_phonenumber));
				new MyTherd().start();
			}
		}
		if (v == mSure) {
			if (isSure()) {
				lls = com.dream.framework.utils.dialog.LodingDialog.DialogFactor(this, "正在修改...", false);
				getForget(Texttool.Gettext(XiuGaiMiMaActivity.this, R.id.et_x_phonenumber),
						  Texttool.Gettext(XiuGaiMiMaActivity.this, R.id.et_authcode),
						  Texttool.Gettext(XiuGaiMiMaActivity.this, R.id.et_newpassword));
			}

		}
	}
	private void getForgetCode(String mobile) {
		PublicRequest http=new PublicRequest(handler);
		http.ResetPassword_Send(XiuGaiMiMaActivity.this, mobile);
		
	}
	
	private void getForget(String mobile,String mobile_code,String password) {
		PublicRequest http=new PublicRequest(handler);
		http.ResetPassword_Check(XiuGaiMiMaActivity.this, mobile, mobile_code, mobilec, mobile_codec+"", password);
		
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				if (lls != null) {
					lls.dismiss();
				}
				switch (msg.what) {
				case Constants.RESETPASSWORD_SEND:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						GetCode data = JsonUtil.instance().fromJson(jsonData,new TypeToken<GetCode>() {
								}.getType());
						if (data != null) {
							Log.i("aaaaa", "aa="+data.getMobile()+"bb="+data.getMobile_code());
							mobilec = data.getMobile();
							mobile_codec = data.getMobile_code();
						}
					} else {
						Toast.makeText(getApplicationContext(),jsonObj.getString("message"), 0).show();
					}
					break;
				case Constants.RESETPASSWORD_CHECK:
					JSONObject jsonObj2 = new JSONObject(msg.obj.toString());
					if (jsonObj2.getString("code").equals("1")) {
						Toast.makeText(getApplicationContext(),jsonObj2.getString("message"), 0).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),jsonObj2.getString("message"), 0).show();
					}
					break;
				case 404:
					Toast.makeText(getApplicationContext(), msg.obj.toString(),0).show();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	
    /**
     * ж
     * @return
     */
	public boolean isSure(){
		phoneNumber=mPhoneNumber.getText().toString();
		newPassWord=mNewPassWord.getText().toString();
		newPassWord2=mNewPassWord2.getText().toString();
		authCode=mAuthCode.getText().toString();
		if (phoneNumber.isEmpty()||newPassWord.isEmpty()||newPassWord2.isEmpty()||authCode.isEmpty()) {
			Toast.makeText(XiuGaiMiMaActivity.this, "请完善信息",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!newPassWord.equals(newPassWord2)) {
			Toast.makeText(XiuGaiMiMaActivity.this, "两次密码不一致",Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
		
	}
	/**
	 * ڲ  ȡ֤
	 * @author Administrator
	 *
	 */

	class MyTherd extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (i > 0) {
				i--;
				Allhandler.post(new Runnable() {

					@Override
					public void run() {
						mCallCode.setText("剩余" + i + "" + "秒");
						mCallCode.setClickable(false);
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			while (i == 0) {
				i = 60;
				Allhandler.post(new Runnable() {

					@Override
					public void run() {
						mCallCode.setClickable(true);// ܵ
						mCallCode.setText("获取验证码");// ʾʣʱ
					}
				});

			}

		}
	}

}
