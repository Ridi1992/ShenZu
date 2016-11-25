package com.sanmi.activity;

import org.json.JSONObject;

import com.sanmi.http.Constants;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Administrator
 */
public class MeNunberActivity extends BaseActivity {
	private ImageView mLeft;
	private TextView mUserAmount;
	private Button mChonZhi,mTiXian;
	private LinearLayout mJiaoYiDetail;
	private LinearLayout mChongZhiDtail;
	private LinearLayout mTiXianDetail;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_menumber);
		new Title(this);//
		init();//ʼ
		}
	/**
	 * ؼ
	 */
	private void init() {
		mLeft=(ImageView) findViewById(R.id.iv_left);
		mChonZhi=(Button) findViewById(R.id.bt_chongzhi);
		mUserAmount = (TextView) findViewById(R.id.user_amount);
		mTiXian=(Button) findViewById(R.id.bt_tixian);
		mLeft.setOnClickListener(this);
		mChonZhi.setOnClickListener(this);
		mTiXian.setOnClickListener(this);
		mJiaoYiDetail = (LinearLayout) findViewById(R.id.zhanghu_jiaoyi);
		mChongZhiDtail = (LinearLayout) findViewById(R.id.zhanghu_chongzhi);
		mTiXianDetail = (LinearLayout) findViewById(R.id.zhanghu_tixian);
		mJiaoYiDetail.setOnClickListener(this);
		mChongZhiDtail.setOnClickListener(this);
		mTiXianDetail.setOnClickListener(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		PublicRequest http = new PublicRequest(handler);
		http.GetUserAmount(MeNunberActivity.this, mUserInfo.GetUserInfo(MeNunberActivity.this).getId());
	
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
		mCenter.setText("我的账户");
	}
	/**
	 * onClick¼
	 */
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_left:
			this.finish();
			break;
		case R.id.bt_chongzhi:
			intent.setClass(MeNunberActivity.this, ChongZhiActivity.class);
			startActivity(intent);
			break;
		case R.id.bt_tixian:
			intent.setClass(MeNunberActivity.this, TiXianActivity.class);
			startActivity(intent);
			break;
		case R.id.zhanghu_jiaoyi:
			intent.setClass(MeNunberActivity.this, ZhanghuJYActivity.class);
			startActivity(intent);
			break;
		case R.id.zhanghu_chongzhi:
			intent.setClass(MeNunberActivity.this, ZhanghuCZActivity.class);
			startActivity(intent);
			break;
		case R.id.zhanghu_tixian:
			intent.setClass(MeNunberActivity.this, ZhanghuTXActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			try {
//				if(dialog!=null){
//					dialog.dismiss();
//				}
			switch (msg.what) {
			case Constants.USER_AMOUNT:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String data = jsonObj.getJSONObject("data").getString("user_money");
//					UserInfo data=JsonUtil.instance().fromJson(jsonData, new TypeToken<UserInfo>(){}.getType());
					if(data!=null){
						mUserAmount.setText(data);
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

}
