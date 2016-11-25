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
import com.sanmi.adapter.TiXianAdapter;
import com.sanmi.http.Constants;
import com.sanmi.http.JsonUtil;
import com.sanmi.http.PublicRequest;
import com.sanmi.refresh.PullToRefreshListView;
import com.sanmi.refresh.PullToRefreshBase.OnRefreshListener;
import com.sanmi.view.Title;
import com.zuwo.data.TiXian;
import com.zuwo.userinfo.mUserInfo;

public class ZhanghuTXActivity extends BaseActivity{
	
	private PullToRefreshListView mRefreshListView;
	private ListView mListView;
	private ArrayList<TiXian> mList;
	private TiXianAdapter mAdapter;
	
	private int page = 1;
	private int pagenum = 100;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.zhanghu_tx);
		new Title(this);//
		init();//
		sendHttp();
		}
	private void sendHttp() {
		PublicRequest http = new PublicRequest(handler);
		http.TiXianList(ZhanghuTXActivity.this, mUserInfo.GetUserInfo(ZhanghuTXActivity.this).getId(),page+"",pagenum+"");
	}
	/**
	 * ؼ
	 */
	private void init() {
		mList = new ArrayList<TiXian>();
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_tixian);
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
					mList=new ArrayList<TiXian>();
					mAdapter.notifyDataSetChanged();
					
					sendHttp();
					
//					mRefreshListView.onRefreshComplete();  
				} else{
					if (mRefreshListView.getRefreshType() == PullToRefreshListView.MODE_PULL_UP_TO_REFRESH) {
//						lls=LodingDialog.DialogFactor(ZhanghuTXActivity.this, "正在加载...", false);
						if (mList.size()/30 == page+1) {
							page = mList.size()/30;
							Log.i("aaaaa", "aaaa=-"+page);
							sendHttp();
						}else {
//						lls.dismiss();
						com.bset.tool.Toast.ToastMe(ZhanghuTXActivity.this, "没有更多");
						mRefreshListView.onRefreshComplete();
						}
					}
				}
			}
		});
	}
	
	@Override
	protected void SetTitleView(ImageView mLeft, TextView mCenter,
			ImageView mRight) {
		// TODO Auto-generated method stub
		super.SetTitleView(mLeft, mCenter, mRight);
		mRight.setVisibility(View.GONE);
		mCenter.setText("提现明细");
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
			case Constants.TIXIANLIST:
				JSONObject jsonObj = new JSONObject(msg.obj.toString());
				if(jsonObj.getString("code").equals("1")) {
					String jsonData = jsonObj.getJSONObject("data").getString("record");
					ArrayList<TiXian> data=JsonUtil.instance().fromJson(jsonData, new TypeToken<ArrayList<TiXian>>(){}.getType());
					if(data!=null){
						mAdapter = new TiXianAdapter(data, ZhanghuTXActivity.this);
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
