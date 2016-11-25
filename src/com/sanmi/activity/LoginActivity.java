package com.sanmi.activity;

/**
 * ¼
 */
import org.json.JSONObject;
import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class LoginActivity extends BaseActivity {
	private Button mLogin;
	private TextView mXiuGai;
	private Button mRegister;
	private boolean isYesOrNo=false;
	
	private LodingDialog dialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		new Title(this);//״
		init();//ʼؼ
		
	}
	
	/**
	 * ʼؼ
	 */
	private void init() {
		mLogin=(Button) this.findViewById(R.id.btn_login);
		mRegister=(Button) this.findViewById(R.id.btn_register);
		mXiuGai=(TextView) this.findViewById(R.id.tv_forget);
		mLogin.setOnClickListener(this);
		mRegister.setOnClickListener(this);
		mXiuGai.setOnClickListener(this);
		
		
	}
	/**
	 * ñ״̬
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mLeft.setVisibility(View.GONE);
	}
	private void getdata(String user_name,String password){
		dialog=LodingDialog.DialogFactor(LoginActivity.this, "正在登录", false);
		PublicRequest http=new PublicRequest(handler);
		http.Login(LoginActivity.this, user_name, password);
	}
	
	Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			try {
				if(dialog!=null){
					dialog.dismiss();
				}
			switch (msg.what) {
			case Constants.LOGIN:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getJSONObject("status").getString("succeed").equals("1")) {
					String jsonData = jsonObj.getJSONObject("data").getString("user");
					UserInfo data=JsonUtil.instance().fromJson(jsonData, new TypeToken<UserInfo>(){}.getType());
					if(data!=null){
						data.setIslogin(true);
						mUserInfo.SaveUserInfo(LoginActivity.this, data);
						Toast.makeText(getApplicationContext(), "登录成功", 0).show();
						startToMainactivity();
						finish();
						/*if(mUserInfo.GetUserInfo(LoginActivity.this).Islogin()){
							判断用户是否登陆的方法
						}*/
					}
				}else{
					Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
				}
				break;
			case 404:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), 0).show();
				break;
			}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		Intent mIntent=null;
		switch (v.getId()) {
		case R.id.btn_login:
			/*********************жǷǵһν***************************/
			SharedPreferences pref = getSharedPreferences("login", 0);  
			Editor editor = pref.edit();  
			editor.putBoolean("isYesOrNo", false);  
			editor.commit(); 
			/*********************жǷǵһν***************************/
			
				
			if(!Texttool.Havecontent(LoginActivity.this, R.id.name)){
				Toast.makeText(getApplicationContext(), "请输入手机号", 0).show();
				
				return;
			}else if(!Texttool.Havecontent(LoginActivity.this, R.id.password)){
				Toast.makeText(getApplicationContext(), "请输入密码", 0).show();
				return;
			}else {
				
				getdata(Texttool.Gettext(LoginActivity.this, R.id.name), 
						Texttool.Gettext(LoginActivity.this, R.id.password));
			}
				
			break;
		case R.id.btn_register:
			mIntent=new Intent(getApplication(), RegisterActivity.class);
			startActivity(mIntent);
			break;
		case R.id.tv_forget:
			mIntent=new Intent(getApplication(),XiuGaiMiMaActivity.class);
			startActivity(mIntent);
			break;
		}
	}

	protected void startToMainactivity() {
		Intent intent=new Intent(getApplication(), MainActivity.class);
		startActivity(intent);
		finish();
	}

	private long exitTime = 0;
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
	    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
	      this.exitApp();
	    }
	    return true;
	  }
	  return super.dispatchKeyEvent(event);
	}
	private void exitApp() {
	  if ((System.currentTimeMillis() - exitTime) > 2000) {
	    Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
	    exitTime = System.currentTimeMillis();//
	  } else {
	    finish();	
	  }
	}
}

