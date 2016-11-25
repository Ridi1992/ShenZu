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
import com.sanmi.adapter.ChongZhiAdapter;
import com.sanmi.adapter.TiXianAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.Title;
import com.zuwo.data.ChongZhi;
import com.zuwo.data.JiaoYi;
import com.zuwo.data.TiXian;
import com.zuwo.userinfo.mUserInfo;

public class ZhanghuCZActivity extends BaseActivity{
	
	private PullToRefreshListView mRefreshListView;
	private ListView mListView;
	private ArrayList<ChongZhi> mList;
	private ChongZhiAdapter mAdapter;
	
	private int page = 1;
	private int pagenum = 100;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.zhanghu_cz);
		init();//
		new Title(this);//
		sendHttp();
		}
		
	private void sendHttp() {
		PublicRequest http = new PublicRequest(handler);
		http.ChongzhiList(ZhanghuCZActivity.this, mUserInfo.GetUserInfo(ZhanghuCZActivity.this).getId(),page+"",pagenum+"");
	}

	/**
	 * ؼ
	 */
	private void init() {
		mList = new ArrayList<ChongZhi>();
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_chongzhi);
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
					mList=new ArrayList<ChongZhi>();
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
						com.bset.tool.Toast.ToastMe(ZhanghuCZActivity.this, "没有更多");
						mRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	
	/**
	 * ñ״̬
	 */
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("充值明细");
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
			case Constants.CHONGZHILIST:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getJSONObject("data").getString("record");
					ArrayList<ChongZhi> data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<ChongZhi>>(){}.getType());
					if(data!=null){
						mAdapter = new ChongZhiAdapter(data, ZhanghuCZActivity.this);
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
