package com.sanmi.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.bset.tool.Texttool;
import com.bset.tool.myTherd;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.data.GetCode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 注册
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends BaseActivity {
	private Button mRegister;
	private EditText mMobile, mMobile_code, mPassWord, mPassWord2, mNickName;
	private TextView mTvYanZhen, mXieYi;
	private String phoneNumber;
	private String yanZhen;
	private String mPassword;
	private String mPassword2;
	private CheckBox mCheck;
	private int mTime;
	private Handler mHandler = new Handler();
	private final String PHONE = "^(((13[0-9])|(15([0-9]))|(18[0-9])|(17[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";

	private LodingDialog dialog;
	private String mobilec;
	private int mobile_codec;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_regsister);
		new Title(this);// ״̬ɫ
		new Title(this);
		init();// ʼ
	}

	/**
	 * ʼ
	 */
	private void init() {
		mRegister = (Button) this.findViewById(R.id.register);
		mMobile = (EditText) this.findViewById(R.id.et_mobile);
		mMobile_code = (EditText) this.findViewById(R.id.et_mobilecode);
		mPassWord = (EditText) this.findViewById(R.id.et_password);
		mPassWord2 = (EditText) this.findViewById(R.id.et_password2);
		mTvYanZhen = (TextView) findViewById(R.id.tv_yanzhengma);
		mCheck = (CheckBox) findViewById(R.id.register_check);
		mXieYi = (TextView) findViewById(R.id.xieyi);
		mRegister.setOnClickListener(this);
		mTvYanZhen.setOnClickListener(this);
		mXieYi.setOnClickListener(this);
		mTime = 60;
	}

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.GONE);
		mCenter.setText("注册");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.register:
			if (yanzheng()) {
				if (mCheck.isChecked()) {
					getRegister(
							Texttool.Gettext(RegisterActivity.this,R.id.et_mobile),
							Texttool.Gettext(RegisterActivity.this, R.id.et_mobilecode),
							mobilec, mobile_codec + "", 
							Texttool.Gettext(RegisterActivity.this, R.id.et_password)
							);
				} else {
					Toast.makeText(RegisterActivity.this, "请同意条款",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(RegisterActivity.this, "请完善信息",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tv_yanzhengma:// 获取验证码
			phoneNumber = mMobile.getText().toString();
			Pattern regex = Pattern.compile(PHONE);
			Matcher matcher = regex.matcher(phoneNumber);
			boolean flagPhone = matcher.matches();
			if (phoneNumber.equals("") || phoneNumber == null) {
				Toast.makeText(getApplication(), "请输入手机号", Toast.LENGTH_LONG).show();
			} else if (!flagPhone) {
				Toast.makeText(this, "手机号码格式错误", Toast.LENGTH_LONG).show();
			} else {
				myTherd therd=new myTherd();
				
				getdata(Texttool.Gettext(RegisterActivity.this, R.id.et_mobile));
			}
			break;
		case R.id.xieyi:
			Intent mIntent2 = new Intent(getApplication(), XieYiActivity.class);
			startActivity(mIntent2);
			break;
		default:
			break;
		}
	}

	private void getRegister(String mobile, String mobile_code, String mobilec,
			String mobile_codec, String password) {
		// TODO Auto-generated method stub  
		dialog = LodingDialog 
				.DialogFactor(RegisterActivity.this, "正在注册", false);
		PublicRequest http = new PublicRequest(handler);
		http.Register(RegisterActivity.this, mobile, mobile_code, mobilec,
				mobile_codec, password);
	}

	private void getdata(String mobile) {
		dialog = LodingDialog.DialogFactor(RegisterActivity.this, "正在获取验证码",
				false);
		PublicRequest http = new PublicRequest(handler);
		http.getCode(RegisterActivity.this, mobile);
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (msg.what) {
				case Constants.GETCODE:
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
				case Constants.REGISTER:
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

	// private Runnable runnable=new Runnable() {
	//
	// @Override
	// public void run() {
	// lls.dismiss();
	// Toast.makeText(getApplication(), "עɹ", 1).show();
	// Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
	// startActivity(intent);
	// }
	// };

	/**
	 * жϷ
	 */
	public boolean yanzheng() {
		phoneNumber = mMobile.getText().toString();
		yanZhen = mMobile_code.getText().toString();
		mPassword = mPassWord.getText().toString();
		mPassword2 = mPassWord2.getText().toString();
		if (phoneNumber.isEmpty() || yanZhen.isEmpty() || mPassword.isEmpty()
				|| mPassword2.isEmpty()) {
			Toast.makeText(RegisterActivity.this, "请保证信息完整性",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!mPassword.equals(mPassword2)) {
			Toast.makeText(RegisterActivity.this, "两次输入密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	/**
	 * ڲ̵߳ʱ
	 * 
	 * @author Administrator
	 * 
	 */
	/*class myTherd extends Thread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			while (mTime > 0) {
				mTime--;
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						mTvYanZhen.setText("获取验证码" + mTime + "" + "秒");// ʾʣʱ
						mTvYanZhen.setClickable(false);// ܵ
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			while (mTime == 0) {
				mTime = 60;
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						mTvYanZhen.setClickable(true);// ܵ
						mTvYanZhen.setText("获取验证码");// ʾʣʱ
					}
				});

			}

		}
	}*/
}
