package com.sanmi.activity;

import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.zuwo.data.Recharge;
import com.zuwo.userinfo.mUserInfo;

public class PayRankActivity extends BaseActivity{
	
	private TextView mContent;
	private Button mSubmit;
	private LodingDialog dialog;
	private String amount;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.pay_rank);
		dialog=LodingDialog.DialogFactor(PayRankActivity.this, "正在加载...", false);
		PublicRequest http = new PublicRequest(handler);
		http.RankAmount(PayRankActivity.this, mUserInfo.GetUserInfo(PayRankActivity.this).getRank_id());
		init();
	}

	/**
	 * 
	 */
	private void init() {
		mContent = (TextView) findViewById(R.id.rank_amount);
		mSubmit = (Button) findViewById(R.id.rank_submit);
		mSubmit.setOnClickListener(this);
	}

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,ImageView mRight) {
		super.SetTitleView(mLeft, mCenter, mRight);
		mLeft.setVisibility(View.VISIBLE);
		mRight.setVisibility(View.GONE);
		mCenter.setText("成为会员");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rank_submit:
			PublicRequest http = new PublicRequest(handler);
			http.RechargeRank(PayRankActivity.this, mUserInfo.GetUserInfo(PayRankActivity.this).getRank_id(),mUserInfo.GetUserInfo(PayRankActivity.this).getId(),amount);
			break;

		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			switch (msg.what) {
			case Constants.RANKAMOUNT:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						amount= jsonObj.getJSONObject("data").getString("rank_amount");
						if(amount!=null){
							mContent.setText(amount+"元");
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 1).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.RECHARGE_RANK:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData= jsonObj.getString("data");
						Recharge data = JsonUtil.instance().fromJson(jsonData, new TypeToken<Recharge>(){}.getType());
						if(data!=null){
							Intent intent = new Intent();
							intent.putExtra("order_sn", data.getOut_trade_no());
							intent.putExtra("order_id", data.getOrder_id());
							intent.putExtra("name", "充值会员");
							intent.putExtra("amount", amount);
							intent.putExtra("type", 2);
							intent.setClass(PayRankActivity.this, PayDemoActivity.class);
							startActivity(intent);
							finish();
						}
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	};

}
