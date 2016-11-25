package com.sanmi.activity;
import org.json.JSONObject;

import com.bset.tool.Texttool;
import com.bset.tool.Toast;
import com.dream.framework.utils.dialog.LodingDialog;
import com.sanmi.http.Constants;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.userinfo.mUserInfo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * @author Administrator
 */
public class XiaDanActivity extends BaseActivity {
	private Button mQueRen;
	private EditText mPhone;
	private LodingDialog lls;//Dialog
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_xiadan);
		mQueRen=(Button) findViewById(R.id.bt_querenyuyue);
		mPhone=(EditText) findViewById(R.id.et_dingdan_phone);
		mQueRen.setOnClickListener(this);
	}
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		new Title(this);//״̬ɫ
		mRight.setVisibility(View.GONE);
		mCenter.setText("预约");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		if (v==mQueRen) {
			String phone=Texttool.Gettext(XiaDanActivity.this, R.id.et_dingdan_phone);
			if (!Texttool.Havecontent(XiaDanActivity.this, R.id.et_dingdan_phone)) {
				Toast.ToastMe(XiaDanActivity.this, "请输入联系电话", 1);
				return;
			}
			if (!Texttool.Pattern_phone(Texttool.Gettext(XiaDanActivity.this, R.id.et_dingdan_phone))) {
				Toast.ToastMe(XiaDanActivity.this, "请输入正确的联系电话", 1);
				return;
			}
			String user_id = mUserInfo.GetUserInfo(XiaDanActivity.this).getId();
			lls=LodingDialog.DialogFactor(XiaDanActivity.this, "正在预约", false);
			PublicRequest http = new PublicRequest(mHandler);
			http.Reservation(XiaDanActivity.this,getIntent().getStringExtra("id2"),user_id,phone);
		}
	}
	//回调handler
		private Handler mHandler = new Handler() {
			
			public void handleMessage(android.os.Message msg) {
				if (lls != null) {
					lls.dismiss();
				}
				switch (msg.what) {
				
				case Constants.RESERVATION:
					try {
						JSONObject jsonObj = new JSONObject(msg.obj.toString());
						if(jsonObj.getString("code").equals("1")) {
							Toast.ToastMe(XiaDanActivity.this, "预约成功", 1);
							XiaDanActivity.this.finish();
						}else{
							Toast.ToastMe(XiaDanActivity.this, jsonObj.getString("message"), 1);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			};
		};
	

}
