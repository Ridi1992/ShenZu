package com.sanmi.activity;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;

import com.dream.framework.utils.dialog.LodingDialog;
import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.MeAdapter;
import com.sanmi.business.MeData;
import com.sanmi.fengzhuang.MeDatas;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.Title;
import com.zuwo.data.MeFaBu;
import com.zuwo.userinfo.UserInfo;
import com.zuwo.userinfo.mUserInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 我的发布
 * @author Administrator
 */
public class MeFaBuActivity extends BaseActivity {
	private LodingDialog dialog;
	private com.sanmi.refresh.PullToRefreshListView mPullToRefreshListView;//ˢlistview
	private LodingDialog lls;//ˢDialog
	private ImageView mLeft;
	private ArrayList<MeFaBu> mList = new ArrayList<MeFaBu>();
	private ListView mListView;//ˢlistview
	private MeAdapter mMeAdapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.mefragment);
		init();//初始化控件
		getHttp();
//		listViewRefresh();//ˢ
		mPullToRefreshListView.setPullToRefreshEnabled(false);
	
	}
	/**
	 * 网络请求
	 */
	private void getHttp() {
		String user_id=mUserInfo.GetUserInfo(MeFaBuActivity.this).getId();
		dialog=LodingDialog.DialogFactor(MeFaBuActivity.this, "正在加载...", false);
		PublicRequest http=new PublicRequest(handler2);
		http.ResetMeFaBu(MeFaBuActivity.this,user_id);
	}
	private Handler handler2=new Handler(){
		public void handleMessage(android.os.Message msg) {
			try {
				if(dialog!=null){
					dialog.dismiss();
				}
			switch (msg.what) {
			case Constants.MeFaBu:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getString("data");
					mList=JsonUtil.instance().fromJson(jsonData, new TypeToken <ArrayList<MeFaBu>>(){}.getType());
					if(mList!=null){
						System.out.println(mList.size()+"");
						mMeAdapter = new MeAdapter(mList, MeFaBuActivity.this, null);
						mListView.setAdapter(mMeAdapter);
						mListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								int i=arg2-1;
								StartToACCeptActivity(i);
							}

							
						});
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
	/**
	 *
	 */
	
	
	private void StartToACCeptActivity(int arg2) {
		Intent intent=new Intent(MeFaBuActivity.this, AcceptActivity.class);
		intent.putExtra("id", mList.get(arg2).getId());
		startActivity(intent);
	}
	private void listViewRefresh() {
		// ˢ·
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// ˢ
				if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {// ˢ
					 lls=LodingDialog.DialogFactor(MeFaBuActivity.this, "正在加载...", false);
					 getHttp();
					
					 mPullToRefreshListView.onRefreshComplete();
					 lls.dismiss();
				} else{
					
					if (mPullToRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {// 
					
						lls=LodingDialog.DialogFactor(MeFaBuActivity.this, "正在加载...", false);
						getHttp();
						mPullToRefreshListView.onRefreshComplete();
						lls.dismiss();
					}
				}
					
					
					
			}
			
		});
	}
	/**
	 * ʼؼ
	 */
	private void init() {
//		View mInflater=LayoutInflater.from(getApplication()).inflate(R.layout.listview_head, null);
		mPullToRefreshListView=(PullToRefreshListView) findViewById(R.id.lv_me_fabu);
		mLeft=(ImageView) findViewById(R.id.iv_left);
		mLeft.setOnClickListener(this);
		mListView=mPullToRefreshListView.getRefreshableView();
//		mListView.addHeaderView(mInflater);
	}

	/**
	 * ñ״̬
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("我的发布");
	}
	/**
	 * onClick¼
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.iv_left:
			this.finish();
			break;
		}
	}

	
}
