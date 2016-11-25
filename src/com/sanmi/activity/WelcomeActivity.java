package com.sanmi.activity;
import com.zuwo.userinfo.mUserInfo;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ӭ 1. 2.3ת¼
 */

public class WelcomeActivity extends Activity {
	private Handler mHandler;
	private RelativeLayout rl_layout;// ͼ
	boolean isFirstIn = false;//жǷǵһν
	private boolean frist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		rl_layout = (RelativeLayout) findViewById(R.id.rl_relativelayout);
		// ʼ
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.activity_welcome);
		rl_layout.startAnimation(animation);
		mHandler = new Handler();
		mHandler.postDelayed(denglu_runnable, 3000);
		/*********************жǷǵһν***************************/
		SharedPreferences pref = getSharedPreferences("myActivityName", 0);  
		//ȡӦֵûиֵ˵δд룬trueΪĬֵ  
		isFirstIn = pref.getBoolean("isFirstIn", true); 
		/*********************жǷǵһν***************************/
		SharedPreferences pref2 = getSharedPreferences("first", 0);  
		//ȡӦֵûиֵ˵δд룬trueΪĬֵ  
		frist = pref2.getBoolean("first0", true); 
	}

	/**
	 * תҳ浽¼߳
	 */
	private final Runnable denglu_runnable = new Runnable() {

		@Override
		public void run() {
			if (isFirstIn) {
				/*********************жǷǵһν***************************/
				SharedPreferences pref = getSharedPreferences("myActivityName", 0);  
				Editor editor = pref.edit();  
				editor.putBoolean("isFirstIn", false);  
				editor.commit(); 
				/*********************жǷǵһν***************************/
				Intent intent = new Intent(WelcomeActivity.this,
						ViewPagerActivity.class);
				startActivity(intent);
				boolean frist=false;
				//WelcomeActivity.this.finish();
				if (frist) {
					SharedPreferences pref2 = getSharedPreferences("first", 0);  
					Editor editor2 = pref2.edit();  
					editor2.putBoolean("first0", false);  
					editor2.commit(); 
					Intent intent2 = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					startActivity(intent2);
				}
			}else{
				
				Intent intent;
				if(mUserInfo.GetUserInfo(WelcomeActivity.this)!=null && mUserInfo.GetUserInfo(WelcomeActivity.this).isIslogin()){
					intent = new Intent(WelcomeActivity.this,
							MainActivity.class);
				}else{
					intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
				}
				
				startActivity(intent);
				
			}
			WelcomeActivity.this.finish();
		}
	};
}
