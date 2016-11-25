package com.sanmi.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.sanmi.adapter.JiaoYiAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.Title;
import com.zuwo.data.ChongZhi;
import com.zuwo.data.JiaoYi;
import com.zuwo.userinfo.mUserInfo;

public class ZhanghuJYActivity extends BaseActivity{
	
	private PullToRefreshListView mRefreshListView;
	private ListView mListView;
	private ArrayList<JiaoYi> mList;
	private JiaoYiAdapter mAdapter;
	
	private int page = 1;
	private int pagenum = 50;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.zhanghu_jy);
		new Title(this);//
		init();//
		sendHttp();
		}
	private void sendHttp() {
		PublicRequest http = new PublicRequest(handler);
		http.JiaoYiList(ZhanghuJYActivity.this, mUserInfo.GetUserInfo(ZhanghuJYActivity.this).getId(),page+"",pagenum+"");
	}
	/**
	 * ؼ
	 */
	private void init() {
		mList = new ArrayList<JiaoYi>();
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_jiaoyi);
		mListView = mRefreshListView.getRefreshableView();
		listViewRefresh();//
	}
	
	/**
	 *上拉下拉刷新
	 */
	private void listViewRefresh() {
		// ˢ·
		mRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (mRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_DOWN_TO_REFRESH) {
//					lls=LodingDialog.DialogFactor(ZhanghuTXActivity.this, "正在刷新...", false);
					page = 0;
					mList=new ArrayList<JiaoYi>();
					mAdapter.notifyDataSetChanged();
					
					sendHttp();
					
//					mPullToRefreshListView01.onRefreshComplete();  
				} else{
					if (mRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
//						lls=LodingDialog.DialogFactor(ZhanghuTXActivity.this, "正在加载...", false);
						if (mList.size()/30 == page+1) {
							page = mList.size()/30;
							Log.i("aaaaa", "aaaa=-"+page);
							sendHttp();
						}else {
//						lls.dismiss();
						com.bset.tool.Toast.ToastMe(ZhanghuJYActivity.this, "没有更多");
						mRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	
	/**
	 * ״
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("交易记录");
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
	
	Handler handler =new Handler(){
		public void handleMessage(Message msg) {
			try {
//				if(dialog!=null){
//					dialog.dismiss();
//				}
			mRefreshListView.onRefreshComplete(); 
			switch (msg.what) {
			case Constants.JIAOYILIST:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getJSONObject("data").getString("record");
					ArrayList<JiaoYi> data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<JiaoYi>>(){}.getType());
					if(data!=null){
						mAdapter = new JiaoYiAdapter(data, ZhanghuJYActivity.this);
						mListView.setAdapter(mAdapter);
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
