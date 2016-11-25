package com.sanmi.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.alipay.sdk.pay.demo.PayDemoActivity;
import com.bset.tool.Texttool;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.MianFeiAapter;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.data.InfoList;
import com.zuwo.data.Recharge;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ֵ
 * @author Administrator
 *
 */
public class ChongZhiActivity extends BaseActivity {
	private EditText mYuE;
	private Button mNext;
	
	//a
	/****************START SPINNER******************************/
	/****************END SPINNER******************************/
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_chzhi);
		new Title(this);
		init();
	}
	/**
	 * ʼ
	 */
	private void init() {
		mYuE=(EditText) findViewById(R.id.et_amount);
		setPricePoint(mYuE);
		mNext=(Button) findViewById(R.id.bt_next);
		mNext.setOnClickListener(this);
	}
	/**
	 * 
	 */
	
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("充值");
		mRight.setVisibility(View.GONE);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v==mNext) {
			if (Texttool.Havecontent(ChongZhiActivity.this, R.id.et_amount)) {
//					double d = Double.valueOf(Texttool.Gettext(ChongZhiActivity.this, R.id.et_amount));
					PublicRequest http = new PublicRequest(mHandler);
					http.RechargeAmount(ChongZhiActivity.this, Texttool.Gettext(ChongZhiActivity.this, R.id.et_amount),mUserInfo.GetUserInfo(ChongZhiActivity.this).getId());
			}else {
				com.bset.tool.Toast.ToastMe(ChongZhiActivity.this, "请输入金额");
			}
		}
	}
	
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
//			if (lls != null) {
//				lls.dismiss();
//			}
			switch (msg.what) {
			case Constants.RECHARGE_AMOUNT:
				try {
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if(jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						Recharge data = JsonUtil.instance().fromJson(jsonData, new TypeToken<Recharge>(){}.getType());
						if(data!=null){
							//TODO
							Intent intent = new Intent();
							intent.putExtra("order_sn", data.getOut_trade_no());
							intent.putExtra("order_id", data.getOrder_id());
							intent.putExtra("name", "充值");
							intent.putExtra("amount", Texttool.Gettext(ChongZhiActivity.this, R.id.et_amount));
							intent.putExtra("type", 1);
							intent.setClass(ChongZhiActivity.this, PayDemoActivity.class);
							startActivity(intent);
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
	
	
	/**
	 * 监听小数只能两位
	 * @param editText
	 */
	public void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
			@Override
			public void afterTextChanged(Editable arg0) {
			}
        });
    }

}
