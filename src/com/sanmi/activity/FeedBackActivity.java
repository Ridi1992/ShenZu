package com.sanmi.activity;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bset.tool.Texttool;
import com.google.gson.reflect.TypeToken;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.view.Title;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

public class FeedBackActivity extends BaseActivity {

	private EditText mContent;
	private EditText mTel;
	private Button mSubmit;

	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.feedback);
		new Title(this);// ״
		init();// ؼ
	};

	/**
	 * ؼ
	 */
	private void init() {
		mContent = (EditText) findViewById(R.id.fk_content);
		mTel = (EditText) findViewById(R.id.fk_tel);
		mSubmit = (Button) findViewById(R.id.fk_submit);
		mSubmit.setOnClickListener(this);
		}

	/**
	 * 
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mLeft.setVisibility(View.VISIBLE);
		mCenter.setText("意见反馈");
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.fk_submit:
			if (!Texttool.Havecontent(FeedBackActivity.this, R.id.fk_content)) {
				com.bset.tool.Toast.ToastMe(FeedBackActivity.this, "请输入反馈内容");
			}else if(!Texttool.Havecontent(FeedBackActivity.this, R.id.fk_tel)){
				com.bset.tool.Toast.ToastMe(FeedBackActivity.this, "请输入联系方式");
			}else {
				PublicRequest http = new PublicRequest(mHandler);
				http.FeedBack(FeedBackActivity.this, mUserInfo.GetUserInfo(FeedBackActivity.this).getId(), Texttool.Gettext(FeedBackActivity.this, R.id.fk_content), Texttool.Gettext(FeedBackActivity.this, R.id.fk_tel));
			}
			break;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
//				if(dialog!=null){
//					dialog.dismiss();
//				}
			switch (msg.what) {
			case Constants.FEEDBACK:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					Toast.makeText(getApplicationContext(), jsonObj.getString("message"), 0).show();
					finish();
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