package com.sanmi.activity;

import org.json.JSONObject;

import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.data.ZhiFU;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ZhiFuActivity extends BaseActivity{
	
	private String appointment_id;
	private TextView mAmountRent;
	private TextView mCounterRent;
	private TextView mFactRent;
	private double mCounterFee;
	private String pay_type;
	private String user_money;
	private Button mPay;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		appointment_id = getIntent().getStringExtra("appointment_id");
		PublicRequest http = new PublicRequest(handler);
		http.CheckOrder(ZhiFuActivity.this, appointment_id);
		
		setContentView(R.layout.zhifu);
		new Title(this);//
		initView();
	}
	private void initView() {
		mAmountRent = (TextView) findViewById(R.id.zhifu_amount_rent);
		mCounterRent = (TextView) findViewById(R.id.zhifu_counter_rent);
		mFactRent = (TextView) findViewById(R.id.zhifu_fact_rent);
		mPay = (Button) findViewById(R.id.zhifu_pay);
		mPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pay_type.equals("1")) {
					ToRecharge();
				}else if (pay_type.equals("2")) {
					PublicRequest http = new PublicRequest(handler);
					http.Pay(ZhiFuActivity.this, appointment_id);
				}
			}
		});
	}
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.GONE);
		mCenter.setText("支付");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		this.finish();
	}
	
	private void ToRecharge(){
		startActivity(new Intent().setClass(ZhiFuActivity.this, ChongZhiActivity.class));
	}
	
	Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			try {
//				if(dialog!=null){
//					dialog.dismiss();
//				}
			switch (msg.what) {
			case Constants.CHECKORDER:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getString("data");
					ZhiFU data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ZhiFU>(){}.getType());
					if(data!=null){
						mAmountRent.setText(data.getAppointment().getRent()+"");
						mCounterFee = data.getAppointment().getCounter_fee();
						double shouxufei = data.getAppointment().getRent() * mCounterFee / 100;
						mCounterRent.setText(shouxufei+"");
						mFactRent.setText(data.getAppointment().getFact_rent()+"");
						pay_type = data.getPay_type();
						user_money = data.getUser_money();
					}
				}else{
					Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
				}
				break;
			case Constants.PAY:
				JSONObject jsonObj2 = new JSONObject(msg.obj.toString());
				if(jsonObj2.getString("code").equals("1")) {
					Toast.makeText(getApplicationContext(), jsonObj2.getString("message"), 0).show();
					MeCallActivity.activity.refresh();
					finish();
				}else{
					Toast.makeText(getApplicationContext(), jsonObj2.getString("message"), 0).show();
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
