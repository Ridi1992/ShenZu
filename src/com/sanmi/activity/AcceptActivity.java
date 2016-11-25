package com.sanmi.activity;

import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.AcceptAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.zuwo.data.Accept;
import com.zuwo.userinfo.mUserInfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AcceptActivity extends BaseActivity {
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;
	private ListView mListView;
	private AcceptAdapter mAcceptAdapter;
	private LodingDialog dialog;
	private String message_id;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_accept);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_me_fabu_accept);
		mListView = mPullToRefreshListView.getRefreshableView();
		mPullToRefreshListView.setPullToRefreshEnabled(false);
		message_id = getIntent().getStringExtra("id");
		gethttp(message_id);
		mAcceptAdapter = new AcceptAdapter(AcceptActivity.this, null,
				message_id);
		mListView.setAdapter(mAcceptAdapter);
	}

	private void gethttp(String mIntent) {
		dialog = LodingDialog.DialogFactor(AcceptActivity.this, "正在加载...",
				false);
		PublicRequest http = new PublicRequest(handler2);
		http.ResetYuYueList(AcceptActivity.this, mIntent);
	}

	private Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				if (dialog != null) {
					dialog.dismiss();
				}
				switch (msg.what) {
				case Constants.YUYUELIST:
					JSONObject jsonObj = new JSONObject(msg.obj.toString());
					if (jsonObj.getString("code").equals("1")) {
						String jsonData = jsonObj.getString("data");
						Accept mList = JsonUtil.instance().fromJson(jsonData,
								new TypeToken<Accept>() {
								}.getType());
						if (mList != null) {
							mAcceptAdapter.setdata(mList);
							mAcceptAdapter.notifyDataSetChanged();
						}
					} else {
						
						mHandler.postDelayed(runnable, (long) 0.001);
						finish();
					}
					break;
				case Constants.ACCEPT:
					JSONObject jsonObj1 = new JSONObject(msg.obj.toString());
					if (jsonObj1.getString("code").equals("1")) {
						String jsonData = jsonObj1.getString("data");
						Accept mList = JsonUtil.instance().fromJson(jsonData,
								new TypeToken<Accept>() {
								}.getType());
						if (mList != null) {
							mAcceptAdapter.setdata(mList);
							mAcceptAdapter.notifyDataSetChanged();
						}
					} else {
						Toast.makeText(getApplicationContext(),
								jsonObj1.getString("message"), 0).show();
					}
					break;
				case 404:
					Toast.makeText(getApplicationContext(), msg.obj.toString(),
							0).show();
					break;
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		};
	};
	private Handler mHandler=new Handler();
	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			Intent intent=new Intent(AcceptActivity.this,NoAcceptActivity.class);
		    startActivity(intent);
		}
	};

	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mCenter.setText("预约列表");
		mRight.setVisibility(View.GONE);
		mLeft.setVisibility(View.VISIBLE);
	}

	/**
	 * 接受预约
	 * @param id
	 * @param mIntent
	 */
	public void accept(String id, String message_id) {
		dialog = LodingDialog.DialogFactor(AcceptActivity.this, "正在接受", false);
		PublicRequest http = new PublicRequest(handler2);
		http.accept(getApplicationContext(),
				mUserInfo.GetUserInfo(AcceptActivity.this).getId(), id,
				message_id);
	}

	/**
	 * 跳转到支付工资页面
	 * 
	 * @param id
	 */
	public void pay(String id) {
		 
	}
}
