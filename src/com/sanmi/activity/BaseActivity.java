package com.sanmi.activity;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
/**
 * ԶActivity
 * ʵֱ?table 
 * @author Administrator
 */
public class BaseActivity extends FragmentActivity implements OnClickListener {
	private ImageView mLeft;
	private TextView mCenter;
	private ImageView mRight;
	private RadioGroup mGroup;
	private RadioButton mButtonLeft;
	private RadioButton mButtonRight;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		//ʼؼ
		mContext=this;
		initView(mContext);
		
	}
	/**
	 * ʼؼ
	 */
	public void initView(Context mContext) {
		
		mCenter=(TextView)((Activity) mContext).findViewById(R.id.tv_center);
		mLeft=(ImageView) ((Activity) mContext).findViewById(R.id.iv_left);
		mRight=(ImageView) ((Activity) mContext).findViewById(R.id.iv_right);
		mGroup=(RadioGroup) ((Activity) mContext).findViewById(R.id.rg_title2);
		mButtonLeft=(RadioButton) ((Activity) mContext).findViewById(R.id.rb_title2_01);
		mButtonRight=(RadioButton) ((Activity) mContext).findViewById(R.id.rb_title2_02);
		
	}
	@Override
	protected void onStart() {
		super.onStart();
		if (mLeft == null) {
			mLeft = (ImageView) findViewById(R.id.iv_left);
			mLeft.setOnClickListener(this);
		}
		
		if (mRight == null) {
			mRight = (ImageView) findViewById(R.id.iv_right);
			
		}
		if (mCenter == null) {
			mCenter = (TextView) findViewById(R.id.tv_center);
		}
		if (mGroup==null) {
			mGroup=(RadioGroup) findViewById(R.id.rg_title2);
			mGroup.setOnClickListener(this);
		}
		if (mButtonLeft==null) {
			mButtonLeft=(RadioButton) findViewById(R.id.rb_title2_01);
			mButtonLeft.setOnClickListener(this);
		}
		if (mButtonRight==null) {
			mButtonRight=(RadioButton) findViewById(R.id.rb_title2_02);
			mButtonRight.setOnClickListener(this);
		}
		
		
		SetTitleView(mLeft, mCenter, mRight);
		SetTitle2View(mGroup,mButtonLeft, mButtonRight);
	}
	/**
	 * ñ
	 * @param mLeft
	 * @param mCenter
	 * @param mRight
	 */
	protected void SetTitleView(ImageView mLeft,TextView mCenter,ImageView mRight){
          mLeft.setOnClickListener(this);
          mRight.setOnClickListener(this);
          
	}
	/**
	 * @param mGroup
	 * @param mButtonLeft
	 * @param mButtonRight
	 * 
	 */
	protected void SetTitle2View(RadioGroup mGroup,RadioButton mButtonLeft,RadioButton mButtonRight){
		mButtonLeft.setOnClickListener(this);
		mButtonRight.setOnClickListener(this);
		mGroup.setOnClickListener(this);
	}
	
	/**
	 * ¼
	 */
	@Override
	public void onClick(View v) {
		if (v==mLeft) {
			this.finish();
		}
	
		if (v==mButtonLeft) {
			mButtonLeft.setChecked(true);
			mButtonRight.setChecked(false);
		}
		if (v==mButtonRight) {
			mButtonLeft.setChecked(false);
			mButtonRight.setChecked(true);	
	}
}
	/**
	 * ׽¼ť
	 * 
	 * Ϊ Activity ̳ TabActivity  onKeyDown ӦԸ dispatchKeyEvent
	 * һ Activity  onKeyDown Ϳ
	 *//*
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

	*//**
	 * ˳
	 *//*
	private void exitApp() {
	  // ж2ε¼ʱ
	  if ((System.currentTimeMillis() - exitTime) > 2000) {
	    Toast.makeText(this, "ٰһ˳", Toast.LENGTH_SHORT).show();
	    exitTime = System.currentTimeMillis();//ˢʱ
	  } else {
	    finish();
	  }
	}
	*/
	/*****************END*****************/
}