package com.sanmi.activity;
import org.json.JSONObject;
import com.bset.tool.Texttool;
import com.dream.framework.utils.dialog.LodingDialog;
import com.sanmi.http.Constants;
import com.sanmi.http.PublicRequest;
import com.zuwo.userinfo.mUserInfo;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author Administrator
 */
public class TiXianActivity extends BaseActivity {
	
	private EditText mName;
	private EditText mCard;
	private EditText mAmount;
	@SuppressWarnings("unused")
	private EditText mAmount2;
	private Button mSubmit;
	private LodingDialog dialog;
	private TextView mTx;
	private double jsondata ;//费用百分比（需要除以100）
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_tixian);
		init();
		dialog=LodingDialog.DialogFactor(TiXianActivity.this, "正在提取现金", false);
		PublicRequest http2 = new PublicRequest(mHandler);
		http2.TiXianAmount2(TiXianActivity.this, mUserInfo.GetUserInfo(TiXianActivity.this).getId());
		
	}
	private void init() {
		mName = (EditText) findViewById(R.id.tixian_name);
		mCard = (EditText) findViewById(R.id.tixian_card);
		mAmount = (EditText) findViewById(R.id.tixian_amount);
		mAmount2 = (EditText) findViewById(R.id.tixian_amount2);
		mSubmit = (Button) findViewById(R.id.tixian_submit);
		mTx=(TextView) findViewById(R.id.tv_tixian);
		mSubmit.setOnClickListener(this);
		mAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				setAmount(s);		     
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			
			}
		});
		
	}
	private void setAmount(CharSequence s){
		if(s!=null && !s.toString().trim().equals("") && !s.toString().trim().equals(".")){
			Double amount=Double.valueOf(s.toString())*jsondata/100;
			Texttool.setText(TiXianActivity.this, R.id.tixian_amount2,amount+"");
		}
	}
	/**
	 *
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("提现");
		mRight.setVisibility(View.GONE);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.tixian_submit:
			if (!Texttool.Havecontent(TiXianActivity.this, R.id.tixian_name)) {
				com.bset.tool.Toast.ToastMe(TiXianActivity.this, "请输入姓名");
			}else if (!Texttool.Havecontent(TiXianActivity.this, R.id.tixian_card)) {
				com.bset.tool.Toast.ToastMe(TiXianActivity.this, "请输入银行卡号");
			}else if (!Texttool.Havecontent(TiXianActivity.this, R.id.tixian_amount)) {
				com.bset.tool.Toast.ToastMe(TiXianActivity.this, "请输入金额");
			}else {
				dialog=LodingDialog.DialogFactor(TiXianActivity.this, "正在提取现金", false);
				PublicRequest http = new PublicRequest(mHandler);
				http.TiXianAmount(TiXianActivity.this, mUserInfo.GetUserInfo(TiXianActivity.this).getId(), mAmount.getText().toString().trim(), mCard.getText().toString().trim(), mName.getText().toString().trim());
			}
			break;
		}
	}
	
	//回调handler
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (dialog != null) {
				dialog.dismiss();
			}
			switch (msg.what) {
			case Constants.TIXIAN_AMOUNT:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
						finish();  
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case Constants.TIXIANT:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						jsondata = jsonObj.getDouble("data");
						
						String s=mUserInfo.GetUserInfo(TiXianActivity.this).getRank_id();
						if (s.equals(1)) {
							mTx.setText("你是普通会员，提取现金需要"+jsondata+"%"+"的手续费！");
						}
						else{
							mTx.setText("你是VIP会员，提取现金需要"+jsondata+"%"+"的手续费！");
						}
						 
					}else{
						Toast.makeText(getApplicationContext(), jsonObj.getString("message"), Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	};
}
