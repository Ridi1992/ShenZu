package com.sanmi.activity;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.sanmi.fragment.FuJinFragment;
import com.sanmi.fragment.HomeFragment;
import com.sanmi.fragment.MeFragment;
import com.sanmi.fragment.MsgFragment;
import com.sanmi.http.Constants;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.MyApplication;
import com.sanmi.view.MyApplication.myLocation;
import com.zuwo.userinfo.mUserInfo;

/**
 * 主页
 * @author Administrator
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{
	public static MainActivity miaActivity;
	private HomeFragment mHomeFragment;//首页
	private FuJinFragment mFuJinFragment;//附近
	public MsgFragment mMsgFragment;//信息
	private MeFragment mMeFragment;//我的
	private FragmentManager mManager;
	private TextView mCenter;
	private ImageView mRight;
   
	
	boolean isfist = true;


	private RadioGroup mRadioGroup;
	private RadioButton mButton01;
	private RadioButton mButton02;
	private RadioButton mButton03;
	private RadioButton mButton04;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		miaActivity=this;
		checksex();
		setContentView(R.layout.activity_main);

		init();//初始化控件
		fragment();//fragment创建及添加
		
		FragmentTransaction mTransaction = mManager
				.beginTransaction();//Fragment的事物重要信息
		mTransaction.replace(R.id.fg_fragment, mHomeFragment);
		mTransaction.commit();
		setmylocation();
		((MyApplication)getApplication()).setAlias(mUserInfo.GetUserInfo(MainActivity.this).getUser_name());
	}
	/**
	 * 检查是否已经设置性别
	 */
	private void checksex(){
		if(mUserInfo.GetUserInfo(MainActivity.this).getSex()==0){
			Intent intent = new Intent(this,SetSexActivity.class);
			startActivity(intent);
			finish();
			return;
		}
	}
	private void setmylocation(){
		((MyApplication)getApplication()).getmyLocation(new myLocation() {
			@Override
			public void result(double nlatitude, double nlontitude, boolean success) {
				if(success){
					PublicRequest http=new PublicRequest(handler2);
			    	http.setmylocation(getApplicationContext(),mUserInfo.GetUserInfo(MainActivity.this).getId(),nlontitude+"",nlatitude+"");
				}
				
			}
		});
    	
    }
    @SuppressLint("HandlerLeak")
	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				switch (msg.what) {
				case Constants.SETMYLOCATION:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
//						String jsonData = jsonObj.getString("data");
//						Accept mList = JsonUtil.instance().fromJson(jsonData,
//								new TypeToken<Accept>() {
//								}.getType());
						System.out.println("经纬度设置成功");
					} else {
						
					}
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	@Override
	public void onClick(View v) {
		FragmentTransaction mTransaction = mManager
				.beginTransaction();
		switch (v.getId()) {
		case R.id.f_rb01:
			mTransaction.replace(R.id.fg_fragment, mHomeFragment);
			mTransaction.commit();
			mCenter.setText("首页");
			mRight.setVisibility(View.GONE);
			break;
		case R.id.f_rb02:
			mTransaction.replace(R.id.fg_fragment, mFuJinFragment);
			mTransaction.commit();
			mRight.setVisibility(View.VISIBLE);
			mCenter.setText("附近");
			
			break;
		case R.id.f_rb03:
			mTransaction.replace(R.id.fg_fragment, mMsgFragment);
			mTransaction.commit();
			mCenter.setText("消息");
			mRight.setVisibility(View.GONE);
			
			break;
		case R.id.f_rb04:
			mTransaction.replace(R.id.fg_fragment, mMeFragment);
			mTransaction.commit();
			mCenter.setText("我的");
			mRight.setVisibility(View.GONE);
		
			break;
		case R.id.iv_right:
			/****************START PopWindow ******************/
			mFuJinFragment.showWindow(v);
			break;
		}
	}

	/**
	 * fragment创建及添加
	 */
	private void fragment() {
		mManager = getSupportFragmentManager();
		
		mHomeFragment = new HomeFragment();
		mFuJinFragment = new FuJinFragment();
		mMsgFragment = new MsgFragment();
		mMeFragment = new MeFragment();
	}
	/**
	 * 初始化控件
	 */
	private void init() {
		mCenter = (TextView) findViewById(R.id.tv_center);
		
		mRight=(ImageView) findViewById(R.id.iv_right);
	   mRadioGroup = (RadioGroup) findViewById(R.id.f_rg);
		mButton01 = (RadioButton) findViewById(R.id.f_rb01);
		mButton02 = (RadioButton) findViewById(R.id.f_rb02);
		mButton03 = (RadioButton) findViewById(R.id.f_rb03);
		mButton04 = (RadioButton) findViewById(R.id.f_rb04);
		
		mRadioGroup.setOnClickListener(this);
		mButton01.setOnClickListener(this);
		mButton02.setOnClickListener(this);
		mButton03.setOnClickListener(this);
		mButton04.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	/**
	 * 设置标题状态
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		
		if (isfist) {
			mCenter.setText("首页");
			mRight.setVisibility(View.GONE);
			mLeft.setVisibility(View.GONE);
			isfist = false;
		}
	}
	
	/****************START PopWindow ******************/
	
	/**
	 * 捕捉返回事件按钮
	 * 
	 * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
	 * 一般的 Activity 用 onKeyDown 就可以了
	 */
	private long exitTime = 0;
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	  if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	    if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
	      this.exitApp();
	    }
	    return true;
	  }
	  return super.dispatchKeyEvent(event);
	}

	/**
	 * 退出程序
	 */
	private void exitApp() {
	  // 判断2次点击事件时间
	  if ((System.currentTimeMillis() - exitTime) > 2000) {
	    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
	    exitTime = System.currentTimeMillis();//刷新时间
	  } else {
	    finish();
	  }
	}
	/*****************END*****************/
	
}
